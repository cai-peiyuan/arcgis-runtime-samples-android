package com.bohaigaoke.forestry;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bohaigaoke.android.map.layer.TianDiTuMethodsClass;
import com.bohaigaoke.android.map.location.DrawLocate;
import com.bohaigaoke.android.map.location.Gps;
import com.bohaigaoke.android.map.location.GraphicUtil;
import com.bohaigaoke.android.map.location.PositionUtil;
import com.bohaigaoke.forestry.fragment.BaseFragment;
import com.bohaigaoke.forestry.fragment.LocationFragment;
import com.bohaigaoke.forestry.fragment.LocationToXYFragment;
import com.bohaigaoke.forestry.service.LocationService;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.LicenseResult;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.io.RequestConfiguration;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.layers.WmtsLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.loadable.LoadStatusChangedEvent;
import com.esri.arcgisruntime.loadable.LoadStatusChangedListener;
import com.esri.arcgisruntime.location.LocationDataSource;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LayerViewStateChangedEvent;
import com.esri.arcgisruntime.mapping.view.LayerViewStateChangedListener;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedEvent;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedListener;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.ogc.wmts.WmtsLayerInfo;
import com.esri.arcgisruntime.ogc.wmts.WmtsService;
import com.esri.arcgisruntime.ogc.wmts.WmtsServiceInfo;
import com.esri.arcgisruntime.symbology.FillSymbol;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BaseFragment.BaseMessage {

    private final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout map_switch_pop_window_contentView; // 地图切换工具窗口view
    private LinearLayout location_to_xy_pop_window_contentView; // 地图坐标定位view
    public FrameLayout black_modal_Layout; //遮罩层
    private PopupWindow map_switch_popupWindow, //显示工具popupWindow容器
            info_detail_popupWindow; //详细信息气泡框
    LinearLayout location_to_xy_popupWindow; //详细信息气泡框

    public FrameLayout mLayoutTop1, mLayoutTop2, mLayoutBottom1,
            mLayoutBottom2, mLayoutBottomLeft, mLayoutBottomRight,
            mLayoutCenter, mLayoutBottomCenter, mLayoutTopLeft,
            mLayoutTopRight, mLayoutWindow;

    private LayoutInflater layoutInflater;// 充气筒实例
    private RelativeLayout info_detail_pop_window_contentView;
    private Button
            m_popwin_ButtonPopGetXy, //拾取坐标按钮
            m_popwin_ButtonPopMapToXy, //定位到坐标按钮
            m_popwin_ButtonPopCeju, //测距按钮
            m_popwin_ButtonPopCemian,  //侧面积按钮
            m_popwin_ButtonPopQingchu, // 清除按钮
            m_popwin_ButtonPopJietu,  // 截图按钮
            m_popwin_ButtonPopQuantu,  // 全图按钮
            m_popwin_ButtonPopDingwei,  // 定位按钮
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

    private TextView mMapLoadStatusTextView, mAppDebugTextView;

    private LocationFragment mLocationFragment; //我的位置

    boolean goToMyLocation = false;
    double bestScale = 10000d;

    public static MainActivity selfObj;

    public MapView mMapView;
    //GraphicsOverlay变量
    public GraphicsOverlay mGraphicsOverlay;

    //天地图底图类型
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


    private SimpleMarkerSymbol circleMarkerSymbol;


    /**
     * ------------------------------lbs service and sensor
     * -------------------------------------
     */
    // 定位服务
    private LocationService locationService;
    // 方向傳感器
    private Sensor aSensor;
    private Sensor mSensor;
    float[] accelerometerValues = new float[3];
    float[] magneticFieldValues = new float[3];
    private SensorManager sensorManager;
    private final SensorEventListener mySensorEventListener = new SensorEventListener() {
        // 可以得到传感器实时测量出来的变化值
        public void onSensorChanged(SensorEvent event) {
            // 方向传感器
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                magneticFieldValues = event.values;
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                accelerometerValues = event.values;
            calculateOrientation();
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }
    };


    /**
     * //我的位置
     * --------------------------------------------------------------------
     * --------
     */

    private Point myLocPoint; //我的位置点对象
    private PictureMarkerSymbol ml_pictureMarkerSymbol;

    // 误差圆样式
    public FillSymbol ml_circle_fillSymbol;
    public SimpleLineSymbol ml_circle_lineSymbol;

    private Polygon myLocAroundPolygon, query_PointAroundPolygon;
    private Graphic myLocGraphic;
    private Graphic myLocAroundGraphic, query_PointAroundGraphic;
    private Graphic mainBorderGraphic;

    private int mlGraphicId = -1;
    private int mlAourndGraphicId = -1;

    LocationToXYFragment locationToXYFragment;

    /**
     * 初始化页面显示元素
     */
    private void initView() {




        mLayoutTop1 = (FrameLayout) findViewById(R.id.layout_top1);
        mLayoutTop2 = (FrameLayout) findViewById(R.id.layout_top2);
        mLayoutBottom1 = (FrameLayout) findViewById(R.id.layout_bottom1);
        mLayoutBottom2 = (FrameLayout) findViewById(R.id.layout_bottom2);
        mLayoutBottomLeft = (FrameLayout) findViewById(R.id.layout_bottom_left);
        mLayoutBottomRight = (FrameLayout) findViewById(R.id.layout_bottom_right);

        mAppDebugTextView = (TextView) findViewById(R.id.appDebugText);
        mMapLoadStatusTextView = (TextView) findViewById(R.id.mapLoadStatusResult);

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

        m_popwin_ButtonPopQuantu = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_quantu); // 全图范围按钮
        m_popwin_ButtonPopDingwei = (Button) map_switch_pop_window_contentView.findViewById(R.id.pop_dingwei);// 点定位按钮


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


        /**
         * 弹出框内容 定位到坐标
         */
        location_to_xy_pop_window_contentView = (LinearLayout) layoutInflater.inflate(R.layout.layout_location_to_xy_popwindow, null);
        m_popwin_ButtonPopGetXy = (Button) map_switch_pop_window_contentView.findViewById(R.id.button_getXY);// 选取坐标按钮
        m_popwin_ButtonPopMapToXy = (Button) map_switch_pop_window_contentView.findViewById(R.id.button_mapToXy);// 定位到坐标按钮


        //    动态添加设施服务
//        初始化布局参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


//        创建最外层的LinearLayout
        location_to_xy_popupWindow = new LinearLayout(this);
//        设置布局参数
        location_to_xy_popupWindow.setLayoutParams(layoutParams);
//        设置子View的Linearlayout
        location_to_xy_popupWindow.setOrientation(LinearLayout.VERTICAL);


        location_to_xy_popupWindow.addView(location_to_xy_pop_window_contentView);



        /**
         * 点击地图元素显示详细信息框
         */
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
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            initView();// 初始化视图资源
            // initThemeLayer();// 初始化数据分类和数据列表
            // initFragment();//初始化fragment
            initlistener();// 設置按鈕監聽
            initMap();// 初始化地圖
            //initMyLocationCompant();
            selfObj = this;

        } catch (Exception e) {
            String msg = e.getMessage();
            msg = msg + "";
            e.printStackTrace();
        }
    }

    /**
     * 初始化我的位置相关组件
     */
    private void initMyLocationCompant() {

        // -----------location config ------------
        locationService = ((AppContext) getApplication()).locationService;
        // 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(bdLocationListener);


        // 注册方向传感器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(mySensorEventListener, aSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mySensorEventListener, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
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


        // Listener on change in map load status
        map.addLoadStatusChangedListener(new LoadStatusChangedListener() {
            @Override
            public void loadStatusChanged(LoadStatusChangedEvent loadStatusChangedEvent) {
                String mapLoadStatus;
                mapLoadStatus = loadStatusChangedEvent.getNewLoadStatus().name();
                // map load status can be any of LOADING, FAILED_TO_LOAD, NOT_LOADED or LOADED
                // set the status in the TextView accordingly
                switch (mapLoadStatus) {
                    case "LOADING":
                        mMapLoadStatusTextView.setText(R.string.status_loading);
                        mMapLoadStatusTextView.setTextColor(Color.BLUE);
                        break;

                    case "FAILED_TO_LOAD":
                        mMapLoadStatusTextView.setText(R.string.status_loadFail);
                        mMapLoadStatusTextView.setTextColor(Color.RED);
                        break;

                    case "NOT_LOADED":
                        mMapLoadStatusTextView.setText(R.string.status_notLoaded);
                        mMapLoadStatusTextView.setTextColor(Color.GRAY);
                        break;

                    case "LOADED":
                        mMapLoadStatusTextView.setText(R.string.status_loaded);
                        mMapLoadStatusTextView.setTextColor(Color.GREEN);
                        break;

                    default:
                        mMapLoadStatusTextView.setText(R.string.status_loadError);
                        mMapLoadStatusTextView.setTextColor(Color.WHITE);
                        break;
                }

                Log.d(TAG, mapLoadStatus);
            }
        });


        //不显示logo
        mMapView.setAttributionTextVisible(false);
        mMapView.setMap(map);

        //添加注记图层
        map.getOperationalLayers().add(webTiledLayer_tianditu_vector_annotation_chinese_mercator);
        mMapView.setLongClickable(true);

        mMapView.getGraphicsOverlays().add(GraphicUtil.getMainBorderPolygon());

        mMapView.setMagnifierEnabled(true);
        mMapView.setViewpointGeometryAsync(GraphicUtil.main_borderGeometry, 20d);

        // 创建一个新GraphicsOverlay并把它添加到MapView
        mGraphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay);

        //我的位置
        locationDisplay = mMapView.getLocationDisplay();
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);


        locationDisplay.addLocationChangedListener(new LocationDisplay.LocationChangedListener() {

            @Override
            public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
                LocationDisplay locationDisplay1 = locationChangedEvent.getSource();
                LocationDataSource.Location location = locationChangedEvent.getLocation();
                Point point = location.getPosition(); //位置
                double x = point.getX();
                double y = point.getY();
                double z = point.getZ();
                double veloCity = location.getVelocity(); // 加速度
                double verticalAccuracy = location.getVerticalAccuracy(); //准确度
                double horizontalAccuracy = location.getHorizontalAccuracy();//水平精度
                Calendar gpsTime = location.getTimeStamp();
                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gpsTime.getTime());
                Log.e(TAG, "x:" + x);
                Log.e(TAG, "y:" + y);
                Log.e(TAG, "z:" + z);
                Log.e(TAG, "veloCity:" + veloCity);
                Log.e(TAG, "verticalAccuracy:" + verticalAccuracy);
                Log.e(TAG, "horizontalAccuracy:" + horizontalAccuracy);

                mAppDebugTextView.setText("date:" + dateStr + " \n"
                        + "date:" + dateStr + " \n"
                        + "x:" + x + " \n"
                        + "y:" + y + " \n"
                        + "z:" + z + " \n"
                        + "veloCity:" + veloCity + " \n"
                        + "verticalAccuracy:" + verticalAccuracy + " \n"
                        + "horizontalAccuracy:" + horizontalAccuracy + " \n"
                );

                if (goToMyLocation) {
                    mMapView.setViewpointCenterAsync(point, bestScale);
                    goToMyLocation = false;
                } else {
                    mMapView.setViewpointCenterAsync(point, mMapView.getMapScale());
                }

            }
        });

        //locationDisplay = mMapView.getLocationDisplay();
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
        ml_circle_fillSymbol = new SimpleFillSymbol();
        ml_circle_lineSymbol = new SimpleLineSymbol();
        ml_circle_fillSymbol.setOutline(ml_circle_lineSymbol);


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
        getMenuInflater().inflate(R.menu.main, menu);
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
    public static final int SHOW_LOCATION_TO_XY_POPWINDOW = 12;

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

            case SHOW_LOCATION_TO_XY_POPWINDOW:

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
        goToMyLocation = true;
        locationDisplay.startAsync();
    }

    /**
     * 取消定位到我的位置
     */
    public void stopLocated() {

        goToMyLocation = false;
        locationDisplay.stop();
    }

    /**
     * 按钮监听
     */
    private void initlistener() {
        // 底图切wind换按钮点击事件
        m_popwin_Button_mapmode_switch_btn_pressed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing()) {
                    map_switch_popupWindow.dismiss();
                }
            }
        });


        // 电子地图
        m_popwin_ButtonPopDianzhiMap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //设置底图
                map.setBasemap(tiandituVectorBaseMap);

                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing()) {
                    map_switch_popupWindow.dismiss();
                }
            }
        });
        // 影像地图
        m_popwin_ButtonPopYinxiangMap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //设置底图
                map.setBasemap(tiandituImageBaseMap);

                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing()) {
                    map_switch_popupWindow.dismiss();
                }
            }
        });


        // 地形地图
        m_popwin_ButtonPopDixXingMap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //设置底图
                map.setBasemap(tiandituDixingBaseMap);

                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing()) {
                    map_switch_popupWindow.dismiss();
                }
            }
        });

        m_popwin_ButtonPopQuantu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMapView.setViewpointGeometryAsync(GraphicUtil.main_borderGeometry, 20d);
                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing()) {
                    map_switch_popupWindow.dismiss();
                }
            }
        });

        m_popwin_ButtonPopDingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (map_switch_popupWindow != null && map_switch_popupWindow.isShowing()) {
                    map_switch_popupWindow.dismiss();
                }

                locationToXYFragment = new LocationToXYFragment();
                setFragment(R.id.layout_top2, locationToXYFragment);

            }
        });

    }


    /**
     * 定位到我的位置
     */


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener bdLocationListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location
                    && location.getLocType() != BDLocation.TypeServerError) {
                double x = location.getLongitude();
                double y = location.getLatitude();
                if (4.9E-324 == x || y == 4.9E-324 || x < -180 || y < -90
                        || x > 180 || y > 90) {
                    return;
                }
                Gps gps = PositionUtil.bd09_To_Gps84(y, x);
                if (myLocPoint == null) {
                    myLocPoint = new Point(gps.getWgLon(), gps.getWgLat());
                } else {
                    myLocPoint = new Point(gps.getWgLon(), gps.getWgLat());
                    //myLocPoint.setX(gps.getWgLon());
                    //myLocPoint.setY(gps.getWgLat());
                }
                // float direct = location.getDirection();
                // pictureMarkerSymbol_mylocation.setAngle(direct);


                double radius_degree = location.getRadius() / (2 * Math.PI * 6378137.0) * 360;// 精度米转换成度
                myLocGraphic = new Graphic(myLocPoint, ml_pictureMarkerSymbol);// 中心点图标
                if (myLocAroundPolygon == null) {
                    myLocAroundPolygon = DrawLocate.getCircle(myLocPoint, radius_degree); // 中心周边误差圆
                } else {
                    myLocAroundPolygon = DrawLocate.getCircle(myLocPoint, radius_degree);// 中心周边误差圆
                }
                myLocAroundGraphic = new Graphic(myLocAroundPolygon, ml_circle_fillSymbol);
                if (mlAourndGraphicId == -1) {
                    mGraphicsOverlay.getGraphics().add(myLocAroundGraphic);
                    mlAourndGraphicId = 1;
                } else {
                    mGraphicsOverlay.getGraphics().remove(myLocAroundGraphic);// 已经定位过// 就更新位置
                    mGraphicsOverlay.getGraphics().add(myLocAroundGraphic);
                }

                if (mlGraphicId == -1) {// 如果是第一次定位 则居中显示

                    mlGraphicId = 1;
                    mGraphicsOverlay.getGraphics().add(myLocGraphic);
                    // mapView.centerAt(myLocPoint, true);
                    mMapView.setViewpointCenterAsync(myLocPoint);
                } else {
                    mGraphicsOverlay.getGraphics().remove(myLocGraphic);    // 已经定位过 就更新位置
                    mGraphicsOverlay.getGraphics().add(myLocGraphic);
                }
                // mlgl.updateGraphic(mlGraphicId, myLocGraphic);
                //上报gps坐标
                //OSChinaApi.reportInfo(AppContext.getInstance().getUserInfo(), location, gps);
            }
        }

    };

    /**
     * 计算方位
     */
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);
        // Log.i(TAG, "转换之前：" + values[0]);// 要经过一次数据格式的转换，转换为度
        values[0] = (float) Math.toDegrees(values[0]);
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);
        // Log.i(TAG, "转换之后：" + values[0]);// values[0]就是水平手機頭部朝向
        // Log.e("方向感應：", values[0] + "," + values[1] + "," + values[2]);
//        if (myLocationGraphicLayer != null && mlAourndGraphicId != -1
//                && ml_pictureMarkerSymbol != null && myLocPoint != null) {
//            synchronized (myLocationGraphicLayer) {
//                ml_pictureMarkerSymbol.setAngle(values[0]);
//                myLocGraphic = new Graphic(myLocPoint, ml_pictureMarkerSymbol);
//                myLocationGraphicLayer.updateGraphic(mlAourndGraphicId, myLocAroundGraphic);
//            }
//        }
        /*
         * if (values[0] >= -5 && values[0] < 5) { Log.i(TAG, "正北"); } else if
         * (values[0] >= 5 && values[0] < 85) { Log.i(TAG, "东北"); } else if
         * (values[0] >= 85 && values[0] <= 95) { Log.i(TAG, "正东"); } else if
         * (values[0] >= 95 && values[0] < 175) { Log.i(TAG, "东南"); } else if
         * ((values[0] >= 175 && values[0] <= 180) || (values[0]) >= -180 &&
         * values[0] < -175) { Log.i(TAG, "正南"); } else if (values[0] >= -175 &&
         * values[0] < -95) { Log.i(TAG, "西南"); } else if (values[0] >= -95 &&
         * values[0] < -85) { Log.i(TAG, "正西"); } else if (values[0] >= -85 &&
         * values[0] < -5) { Log.i(TAG, "西北"); }
         */
    }


    /**
     * 以定动态申请位权限为例
     */
    public void handPermission() {
        // 定位权限组
        String[] mPermissionGroup = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        // 过滤已持有的权限
        List<String> mRequestList = new ArrayList<>();
        for (String permission : mPermissionGroup) {
            if ((ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)) {
                mRequestList.add(permission);
            }
        }

        // 申请未持有的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !mRequestList.isEmpty()) {
            ActivityCompat.requestPermissions(this, mRequestList.toArray(
                    new String[mRequestList.size()]), 100);
        } else {
            // 权限都有了，就可以继续后面的操作
        }
    }


    public void setFragment(int layoutId, Fragment fragment) {
        if (fragment == null)
            return;
        ((FrameLayout) findViewById(layoutId)).setVisibility(View.VISIBLE);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layoutId, fragment);

        transaction.commit();
    }
}
