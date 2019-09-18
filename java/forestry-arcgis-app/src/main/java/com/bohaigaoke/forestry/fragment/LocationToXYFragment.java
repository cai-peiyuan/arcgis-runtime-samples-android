package com.bohaigaoke.forestry.fragment;

import java.util.Map;

import com.bohaigaoke.android.model.query.Rows;
import com.bohaigaoke.forestry.MainActivity;
import com.bohaigaoke.forestry.R;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.Viewpoint;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.esri.arcgisruntime.mapping.Viewpoint;

/**
 * @author name
 */
public class LocationToXYFragment extends BaseFragment implements OnClickListener {
    private View mView;
    private EditText textViewX, textViewY;
    private Button btnMapToXy, btnGetMapXY;
    private Context context;

    public LocationToXYFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_location_to_xy_popwindow, container, false);

        btnMapToXy =  (Button) mView.findViewById(R.id.buttonMapToXy);
        btnGetMapXY =  (Button) mView.findViewById(R.id.buttonGetMapXY);

        textViewX = (EditText) mView.findViewById(R.id.editTextX);
        textViewY = (EditText) mView.findViewById(R.id.editTextX);



                //获取当前地图中心点
        Viewpoint viewpoint = MainActivity.selfObj.mMapView.getCurrentViewpoint(Viewpoint.Type.BOUNDING_GEOMETRY);
        double x = viewpoint.getTargetGeometry().getSpatialReference().getWkid();
        double y = viewpoint.getTargetGeometry().getExtent().getCenter().getY();

        setX(x);
        setY(y);

        //点击定位到坐标按钮
        btnMapToXy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                double x = Double.parseDouble(textViewX.getText().toString());
                double y = Double.parseDouble(textViewY.getText().toString());
                MainActivity.selfObj.mMapView.setViewpointCenterAsync(new Point(x,y,MainActivity.selfObj.spatialRefrence));
            }
        });

        return mView;
    }

    private void findView() {

    }


    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void setX(double v) {
        textViewX.setText(String.valueOf(v));


    }

    public void setY(double v) {
        textViewY.setText(String.valueOf(v));
    }
}
