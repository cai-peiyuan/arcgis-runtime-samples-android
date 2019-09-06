package com.bohaigaoke.forestry.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bohaigaoke.forestry.MainActivity;
import com.bohaigaoke.forestry.R;


public class SwitchFragment extends BaseFragment {

    private ImageButton switchButten, reliMapButten, reliMapButtenPassed,
            topLayoutjiantou;
    private LinearLayout topLayout, listLayout, passedLayout;
    private ListView miduListView;
    //private RelimapAdapter reliAdapter;
    //private List<MapLayer> populationMapLayers;
    private TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_switch, container, false);

        tvTitle = (TextView) view.findViewById(R.id.relimap_item_name);//当前选中的图层名称

        switchButten = (ImageButton) view.findViewById(R.id.switchBtn);//底图切换按钮
        reliMapButten = (ImageButton) view.findViewById(R.id.reliBtn);//熱力圖按鈕

        reliMapButtenPassed = (ImageButton) view.findViewById(R.id.relimap_btnpassed);
        topLayoutjiantou = (ImageButton) view.findViewById(R.id.relimap_jiantou);
        topLayout = (LinearLayout) view.findViewById(R.id.relimap_midulayout);
        topLayout.setVisibility(View.GONE);
        listLayout = (LinearLayout) view.findViewById(R.id.relimap_list_layout);
        listLayout.setVisibility(View.GONE);
        miduListView = (ListView) view.findViewById(R.id.relimap_list);
        //getPopulationMapLayerList();
        //reliAdapter = new RelimapAdapter(getActivity(), this,populationMapLayers);
        //miduListView.setAdapter(reliAdapter);
        passedLayout = (LinearLayout) view.findViewById(R.id.relimap_btnpassed_layout);
        passedLayout.setVisibility(View.GONE);
        switchButten.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Message msg = new Message();
                msg.what = MainActivity.SHOW_MAP_SWITCH_POPWINDOW;
                baseMessage.sendMessage(msg);//向Activity 发送消息  在Activity内处理消息
            }
        });
        reliMapButten.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                tvTitle.setText("人口密度");
                passedLayout.setVisibility(View.VISIBLE);
                topLayout.setVisibility(View.VISIBLE);
            }
        });
        reliMapButtenPassed.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                listLayout.setVisibility(View.GONE);
                topLayout.setVisibility(View.GONE);
                passedLayout.setVisibility(View.GONE);
                // 设置人口密度所有图层的visible为不可见(false)
                //((MapActivity) getActivity()).setPopulationMapLayerVisible("NONE");
            }
        });

        topLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                listLayout.setVisibility(listLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                if (listLayout.getVisibility() == View.VISIBLE) {
                    topLayoutjiantou.setImageResource(R.mipmap.relimap_shangla);
                } else {
                    topLayoutjiantou.setImageResource(R.mipmap.relimap_xiala);
                }
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void getPopulationMapLayerList() {
		/*MapLayerMgr mgr = new MapLayerMgr();
		populationMapLayers = mgr.getPopulationMapLayerList();*/
    }

    public void setFragmentTitle(String title) {
        tvTitle.setText(title);
        listLayout.setVisibility(listLayout.getVisibility() == View.VISIBLE ? View.GONE
                : View.VISIBLE);
        if (listLayout.getVisibility() == View.VISIBLE) {
            topLayoutjiantou.setImageResource(R.mipmap.relimap_shangla);
        } else {
            topLayoutjiantou.setImageResource(R.mipmap.relimap_xiala);
        }
    }

}
