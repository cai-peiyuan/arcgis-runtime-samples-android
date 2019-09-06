package com.bohaigaoke.forestry.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bohaigaoke.forestry.MainActivity;
import com.bohaigaoke.forestry.R;

/**
 * zoom in and zoom out btn
 *
 * @author name
 */
public class ZoomFragment extends BaseFragment {

    private ImageButton zoomIn, zoomOut;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoom, container, false);
        zoomIn = (ImageButton) view.findViewById(R.id.zoominBtn);
        zoomOut = (ImageButton) view.findViewById(R.id.zoomoutBtn);
        zoomIn.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                if (getActivity().getClass().getName()
                        .equals(MainActivity.class.getName())) {
                    //放大地图zoomin
                    MainActivity.selfObj.mMapView.setViewpointScaleAsync(MainActivity.selfObj.mMapView.getMapScale() * 0.5);
                } else {
                }
            }
        });
        zoomOut.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (getActivity().getClass().getName()
                        .equals(MainActivity.class.getName())) {
                    //缩小地图zoomout
                    MainActivity.selfObj.mMapView.setViewpointScaleAsync(MainActivity.selfObj.mMapView.getMapScale() * 2);
                } else {
                }
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
