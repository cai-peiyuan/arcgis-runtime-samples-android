package com.bohaigaoke.forestry;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bohaigaoke.android.map.layer.TianDiTuMethodsClass;
import com.bohaigaoke.android.map.location.GraphicUtil;
import com.bohaigaoke.forestry.fragment.BaseFragment;
import com.bohaigaoke.forestry.fragment.LocationFragment;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.LicenseResult;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.io.RequestConfiguration;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.layers.WmtsLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.LayerViewStateChangedEvent;
import com.esri.arcgisruntime.mapping.view.LayerViewStateChangedListener;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedEvent;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedListener;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.ogc.wmts.WmtsLayerInfo;
import com.esri.arcgisruntime.ogc.wmts.WmtsService;
import com.esri.arcgisruntime.ogc.wmts.WmtsServiceInfo;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BaseFragment.BaseMessage {

    private final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout map_switch_pop_window_contentView; // 地图切换工具窗口view
    public FrameLayout black_modal_Layout; //遮罩层
    private PopupWindow map_switch_popupWindow,
            info_detail_popupWindow; // 显示工具popupWindow容器
    private LayoutInflater layoutInflater;// 充气筒实例
    private RelativeLayout info_detail_pop_window_contentView;
    private Button m_popwin_ButtonPopCeju, //测距按钮
            m_popwin_ButtonPopCemian,  //侧面积按钮
            m_popwin_ButtonPopQingchu, // 清除按钮
            m_popwin_ButtonPopJietu,  // 截图按钮
            m_popwin_ButtonPop_point_marker,  // 标注点按钮
            m_popwin_ButtonPop_line_marker,  // 标注线按钮
            m_popwin_ButtonPop_polygon_marker,  // 标注面按钮
            m_popwin_ButtonPop_point_around_query,  // 点周边按钮
            m_popwin_ButtonPop_line_around_query,  // 线周边按钮
            m_popwin_ButtonPop_polygon_query,  // 空间查询按钮
            m_popwin_ButtonPopBiaozhu; //
    private ImageButton mImageReliSwitch, m_popwin_ButtonPopDianzhiMap, m_popwin_ButtonPopYinxiangMap,
            m_popwin_ButtonPopDixXingMap,
            m_popwin_Button_mapmode_switch_btn_pressed;

    private LocationFragment mLocationFragment; //我的位置

    public static MainActivity selfObj;

    public MapView mMapView;
    public Basemap tiandituVectorBaseMap, tiandituImageBaseMap, tiandituDixingBaseMap;
    public ArcGISMap map;
    private static final String CLIENT_ID = "runtimelite,1000,rud5969339388,none,D7MFA0PL40H94P7EJ009";
    public LicenseResult licenseResult;

    public static WebTiledLayer webTiledLayer_tianditu_vector_mercator, webTiledLayer_tianditu_image_mercator, webTiledLayer_tianditu_dixing_mercator;//天地图底图

    public static WebTiledLayer webTiledLayer_tianditu_vector_annotation_chinese_mercator;//天地图标注
    //设置初始化地点和比例尺
    double centerX = GraphicUtil.center_lon;
    double centerY = GraphicUtil.center_lat;
    int level = 11;

    LocationDisplay locationDisplay;

    /**
     * 初始化页面显示元素
     */
    private void initView() {
        //遮罩层
        black_modal_Layout = (FrameLayout) findViewById(R.id.layout_black_modal);

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        info_detail_pop_window_contentView = (RelativeLayout) layoutInflater.inflate(R.layout.layout_detail_wind, null);

        mImageReliSwitch = (ImageButton) findViewById(R.id.reliBtn);

        /**
         * 弹出框内容 切换地图
         */
        map_switch_pop_window_contentView = (LinearLayout) layoutInflater.inflate(R.layout.layout_map_switch_popwindow, null);
        m_popwin_ButtonPopCeju = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_ceju);// 测距按钮
        m_popwin_ButtonPopCemian = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_cemian);// 测面按钮
        m_popwin_ButtonPopQingchu = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_clear);// 清除按钮

        m_popwin_ButtonPop_point_marker = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_point_marker);// 标注点
        m_popwin_ButtonPop_line_marker = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_line_marker);// 标注线
        m_popwin_ButtonPop_polygon_marker = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_polygon_marker);// 标注面

        m_popwin_ButtonPop_point_around_query = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_point_around_query);// 点周边
        //m_popwin_ButtonPop_line_around_query = (Button) view_switch_pop_window.findViewById(R.id.pop_line_around_query);// 线周边
        m_popwin_ButtonPop_polygon_query = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_polygon_query);// 面查询


        m_popwin_ButtonPopJietu = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_jietu); // 截图按钮
        m_popwin_ButtonPopBiaozhu = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_biaozhu);// 标注按钮
        m_popwin_ButtonPopDianzhiMap = (ImageButton) map_switch_pop_window_contentView.findViewById(R.id.dianziBtn);// 电子地图按钮
        m_popwin_ButtonPopDianzhiMap.setImageResource(R.drawable.map_vect);// 默认选中电子地图
        m_popwin_ButtonPopYinxiangMap = (ImageButton) map_switch_pop_window_contentView.findViewById(R.id.yinxiangBtn);// 卫星影像地图按钮
        m_popwin_ButtonPopDixXingMap = (ImageButton) map_switch_pop_window_contentView.findViewById(R.id.dixingBtn);// 地形地图按钮
        m_popwin_Button_mapmode_switch_btn_pressed = (ImageButton) map_switch_pop_window_contentView.findViewById(R.id.mapmode_switch_btn_pressed);    // popwindow 内显示popwindow切换按钮
        /**
         * 弹出框对象 切换地图
         */
        map_switch_popupWindow = new PopupWindow(map_switch_pop_window_contentView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        map_switch_popupWindow.setFocusable(true);
        map_switch_popupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        map_switch_popupWindow.setAnimationStyle(R.style.PopupAnimation);// 设置动画
        map_switch_popupWindow.setBackgroundDrawable(new BitmapDrawable());// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        map_switch_popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // 设置popupwindow隐藏监听事件
            public void onDismiss() {
                black_modal_Layout.setVisibility(View.GONE);
            }
        });
        info_detail_popupWindow = new PopupWindow(info_detail_pop_window_contentView,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, true);
        info_detail_popupWindow.setFocusable(true);
        info_detail_popupWindow.setOutsideTouchable(false);// 设置在外点击消失
        info_detail_popupWindow.setAnimationStyle(R.style.PopupAnimation);// 设置动画
        info_detail_popupWindow.setBackgroundDrawable(new BitmapDrawable());// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        info_detail_popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // 设置popupwindow隐藏监听事件
            public void onDismiss() {
                black_modal_Layout.setVisibility(View.GONE);
            }
        });

        // 我的位置fragment
        mLocationFragment = (LocationFragment) getFragmentManager().findFragmentById(R.id.fragment_location);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            initView();// 初始化视图资源
            // initThemeLayer();// 初始化数据分类和数据列表
            // initFragment();//初始化fragment
            initlistener();// 設置按鈕監聽
            initMap();// 初始化地圖
            selfObj = this;

        } catch (Exception e) {
            String msg = e.getMessage();
            msg = msg + "";
            e.printStackTrace();
        }
    }

    /***
     * 初始化地图资源
     */
    private void initMap() {

        licenseResult = ArcGISRuntimeEnvironment.setLicense(CLIENT_ID);

        mMapView = findViewById(R.id.mapView);

        //  天地图矢量底图
        webTiledLayer_tianditu_vector_mercator = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_MERCATOR);
        tiandituVectorBaseMap = new Basemap(webTiledLayer_tianditu_vector_mercator);
        //  天地图影像底图
        webTiledLayer_tianditu_image_mercator = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_MERCATOR);
        tiandituImageBaseMap = new Basemap(webTiledLayer_tianditu_image_mercator);
        //  天地图地形底图
        webTiledLayer_tianditu_dixing_mercator = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_TERRAIN_MERCATOR);
        tiandituDixingBaseMap = new Basemap(webTiledLayer_tianditu_dixing_mercator);
        //  天地图矢量标注图层
        webTiledLayer_tianditu_vector_annotation_chinese_mercator = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR);

        // ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 25.8138d, 112.9834d, 13);
        map = new ArcGISMap();
        //设置底图
        map.setBasemap(tiandituVectorBaseMap);
        //设置初始化视野
        map.setInitialViewpoint(new Viewpoint(new Point(centerX, centerY, SpatialReference.create(4326)), TianDiTuMethodsClass.SCALES[level]));
        //不显示logo
        mMapView.setAttributionTextVisible(false);
        mMapView.setMap(map);

        //添加注记图层
        map.getOperationalLayers().add(webTiledLayer_tianditu_vector_annotation_chinese_mercator);
        mMapView.setLongClickable(true);

        mMapView.getGraphicsOverlays().add(GraphicUtil.getMainBorderPolygon());

        mMapView.setMagnifierEnabled(true);
        mMapView.setViewpointGeometryAsync(GraphicUtil.main_borderGeometry, 20d);


        locationDisplay = mMapView.getLocationDisplay();
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
        locationDisplay.startAsync();

        locationDisplay = mMapView.getLocationDisplay();
        Resources resources = getResources();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(resources, R.drawable.arcgisruntime_location_display_default_symbol));
        final PictureMarkerSymbol campsiteSymbol = new PictureMarkerSymbol(bitmapDrawable);
        campsiteSymbol.loadAsync();
        campsiteSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                locationDisplay.setDefaultSymbol(campsiteSymbol);//设置默认符号
                locationDisplay.setShowAccuracy(true);//隐藏符号的缓存区域
            }
        });


        //Point center = new Point(112.9834d,25.8138d, SpatialReference.create(4326));// CGCS2000
        //mMapView.setViewpointCenterAsync(center,577791.7098721985);

        mMapView.addLayerViewStateChangedListener(new LayerViewStateChangedListener() {
            @Override
            public void layerViewStateChanged(LayerViewStateChangedEvent layerViewStateChangedEvent) {

            }
        });


        /**
         * 地图比例尺变化监听
         */
        mMapView.addMapScaleChangedListener(new MapScaleChangedListener() {
            @Override
            public void mapScaleChanged(MapScaleChangedEvent mapScaleChangedEvent) {
                //更新比例尺
                mLocationFragment.setScale(mMapView.getMapScale());// 設置比例尺信息
            }
        });



        //mMapView.setOnTouchListener();\
        //地图初始化完成后调用事件
        map.addDoneLoadingListener(new Runnable() {

            @Override
            public void run() {

            }
        });

        //地图底图切换事件监听
        map.addBasemapChangedListener(new ArcGISMap.BasemapChangedListener() {

            @Override
            public void basemapChanged(ArcGISMap.BasemapChangedEvent basemapChangedEvent) {
                basemapChangedEvent.getOldBasemap();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public static final int SHOW_MAP_SWITCH_POPWINDOW = 11;

    @Override
    public void sendMessage(Message msg) {
        switch (msg.what) {
            case SHOW_MAP_SWITCH_POPWINDOW:
                //底层遮罩层
                black_modal_Layout.setVisibility(black_modal_Layout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                if (map_switch_popupWindow != null && map_switch_pop_window_contentView != null && !map_switch_popupWindow.isShowing()) {
                    map_switch_popupWindow.showAsDropDown(mImageReliSwitch);
                }
                break;
        }

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 定位到我的位置
     */
    public void startLocated() {
        //locationService.start();// 定位SDK
        // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
    }

    /**
     * 取消定位到我的位置
     */
    public void stopLocated() {
//        locationService.stop();// 定位SDK
//        // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
//        if (mlGraphicId != -1) {
//            myLocationGraphicLayer.removeGraphic(mlGraphicId);
//            mlGraphicId = -1;
//        }
//        if (mlAourndGraphicId != -1) {
//            myLocationGraphicLayer.removeGraphic(mlAourndGraphicId);
//            mlAourndGraphicId = -1;
//        }
    }

    /**
     * 按钮监听
     */
    private void initlistener() {
        // 底图切wind换按钮点击事件
        m_popwin_Button_mapmode_switch_btn_pressed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing())
                    map_switch_popupWindow.dismiss();
            }
        });


        // 电子地图
        m_popwin_ButtonPopDianzhiMap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //设置底图
                map.setBasemap(tiandituVectorBaseMap);

                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing())
                    map_switch_popupWindow.dismiss();
            }
        });
        // 影像地图
        m_popwin_ButtonPopYinxiangMap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //设置底图
                map.setBasemap(tiandituImageBaseMap);

                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing())
                    map_switch_popupWindow.dismiss();
            }
        });


        // 地形地图
        m_popwin_ButtonPopDixXingMap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //设置底图
                map.setBasemap(tiandituDixingBaseMap);

                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing())
                    map_switch_popupWindow.dismiss();
            }
        });

        /**
         * 定位到我的位置
         */

    }
}
