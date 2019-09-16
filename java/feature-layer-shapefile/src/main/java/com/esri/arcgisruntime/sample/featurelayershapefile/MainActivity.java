/* Copyright 2018 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.esri.arcgisruntime.sample.featurelayershapefile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ShapefileFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

  private final static String TAG = MainActivity.class.getSimpleName();

  private MapView mMapView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // create a new map to display in the map view with a streets basemap
    mMapView = findViewById(R.id.mapView);
    ArcGISMap map = new ArcGISMap(Basemap.createStreetsVector());
    mMapView.setMap(map);

    requestReadPermission();
  }

  /**
   * Creates a ShapefileFeatureTable from a service and, on loading, creates a FeatureLayer and add it to the map.
   */
  private void featureLayerShapefile() {
    // load the shapefile with a local path
    ShapefileFeatureTable shapefileFeatureTable = new ShapefileFeatureTable(
        Environment.getExternalStorageDirectory() + getString(R.string.shapefile_path));

    shapefileFeatureTable.loadAsync();
    shapefileFeatureTable.addDoneLoadingListener(() -> {
      if (shapefileFeatureTable.getLoadStatus() == LoadStatus.LOADED) {

        // create a feature layer to display the shapefile
        FeatureLayer shapefileFeatureLayer = new FeatureLayer(shapefileFeatureTable);

        // add the feature layer to the map
        mMapView.getMap().getOperationalLayers().add(shapefileFeatureLayer);

        // zoom the map to the extent of the shapefile
        mMapView.setViewpointAsync(new Viewpoint(shapefileFeatureLayer.getFullExtent()));




        // set an on touch listener to listen for click events
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
          @Override
          public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            // get the point that was clicked and convert it to a point in map coordinates
            Point clickPoint = mMapView
                    .screenToLocation(new android.graphics.Point(Math.round(motionEvent.getX()), Math.round(motionEvent.getY())));
            int tolerance = 10;
            double mapTolerance = tolerance * mMapView.getUnitsPerDensityIndependentPixel();
            // create objects required to do a selection with a query
            Envelope envelope = new Envelope(clickPoint.getX() - mapTolerance, clickPoint.getY() - mapTolerance,
                    clickPoint.getX() + mapTolerance, clickPoint.getY() + mapTolerance, mMapView.getSpatialReference());
            QueryParameters query = new QueryParameters();
            query.setGeometry(envelope);
            // call select features
            final ListenableFuture<FeatureQueryResult> featureQueryResultFuture = shapefileFeatureLayer
                    .selectFeaturesAsync(query, FeatureLayer.SelectionMode.NEW);
            // add done loading listener to fire when the selection returns
            featureQueryResultFuture.addDoneListener(() -> {
              try {
                // call get on the future to get the result
                FeatureQueryResult featureQueryResult = featureQueryResultFuture.get();
                // create an Iterator
                Iterator<Feature> iterator = featureQueryResult.iterator();
                Feature feature;
                // cycle through selections
                int counter = 0;
                while (iterator.hasNext()) {
                  feature = iterator.next();
                  counter++;
                  Log.d(TAG, "Selection #: " + counter + " Table name: " + feature.getFeatureTable().getTableName());
                }
                Toast.makeText(getApplicationContext(), counter + " features selected", Toast.LENGTH_SHORT).show();
              } catch (Exception e) {
                Log.e(TAG, "Select feature failed: " + e.getMessage());
              }
            });
            return super.onSingleTapConfirmed(motionEvent);
          }
        });




      } else {
        String error = "Shapefile feature table failed to load: " + shapefileFeatureTable.getLoadError().toString();
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
        Log.e(TAG, error);
      }
    });
  }

  /**
   * Request read permission on the device.
   */
  private void requestReadPermission() {
    // define permission to request
    String[] reqPermission = new String[] { Manifest.permission.READ_EXTERNAL_STORAGE };
    int requestCode = 2;
    // For API level 23+ request permission at runtime
    if (ContextCompat.checkSelfPermission(MainActivity.this,
        reqPermission[0]) == PackageManager.PERMISSION_GRANTED) {
      featureLayerShapefile();
    } else {
      // request permission
      ActivityCompat.requestPermissions(MainActivity.this, reqPermission, requestCode);
    }
  }

  /**
   * Handle the permissions request response.
   */
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      featureLayerShapefile();
    } else {
      // report to user that permission was denied
      Toast.makeText(MainActivity.this, getResources().getString(R.string.read_permission_denied),
          Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    mMapView.pause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mMapView.resume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mMapView.dispose();
  }
}
