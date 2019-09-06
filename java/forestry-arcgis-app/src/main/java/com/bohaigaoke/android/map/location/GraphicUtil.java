package com.bohaigaoke.android.map.location;

import android.graphics.Color;

import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;


/**
 * 应用住区域位置与边界
 *
 * @author name
 * <p>
 * 全北京范围坐标 XMin: 115.4168547433507 YMin: 39.44146088421875 XMax:
 * 117.508133814809 YMax: 41.059232388923604 Spatial Reference: 4326
 * (4326)
 */
public class GraphicUtil {

    public static double center_lon = 113.00915300031, center_lat = 25.77358000002;

    public static String region_coord_str = "113.139954 26.839148,113.176835 26.824997,113.202129 26.833126,113.233449 26.806096,113.256553 26.829047,113.275026 26.830679,113.269377 26.784607,113.289024 26.766894,113.330305 26.813971,113.400192 26.803701,113.422129 26.771931,113.398105 26.71194,113.375163 26.702771,113.412499 26.639567,113.396513 26.598517,113.435936 26.558344,113.482368 26.567562,113.506874 26.604444,113.53503 26.603387,113.532266 26.591175,113.560772 26.576694,113.566319 26.522887,113.588755 26.525493,113.581917 26.515077,113.601849 26.507064,113.578248 26.468409,113.608955 26.429844,113.577555 26.406774,113.574464 26.384313,113.613558 26.310159,113.607932 26.286915,113.715031 26.234396,113.739396 26.189424,113.729889 26.152693,113.685653 26.105071,113.703007 26.070336,113.744348 26.049398,113.776451 26.072315,113.783794 26.116417,113.822047 26.090083,113.847374 26.123814,113.884509 26.133058,113.91063 26.175603,113.941197 26.187748,113.955337 26.154162,114.029038 26.19161,114.055026 26.168452,114.083213 26.171483,114.09981 26.192162,114.17609 26.21769,114.181423 26.200332,114.211682 26.20637,114.227322 26.184675,114.213437 26.169957,114.232182 26.155392,114.183729 26.124439,114.103403 26.103307,114.115671 26.088906,114.101556 26.072452,114.038491 26.079757,114.002716 26.017285,114.021784 25.994566,114.015546 25.97591,114.023046 25.896323,113.964978 25.837055,113.956615 25.780386,113.909235 25.731799,113.945323 25.62482,113.977321 25.605166,113.974361 25.57622,113.988136 25.566238,113.982569 25.533372,113.957781 25.531209,113.939884 25.500873,113.953943 25.487928,113.935113 25.464291,113.939714 25.44469,113.881814 25.439623,113.872902 25.384641,113.834586 25.366398,113.815832 25.333295,113.752766 25.332791,113.742703 25.368473,113.731858 25.351375,113.701248 25.363746,113.674915 25.337394,113.605594 25.330524,113.577238 25.30926,113.576196 25.345712,113.53719 25.370945,113.517052 25.356289,113.513543 25.373165,113.478278 25.379069,113.440222 25.362229,113.412764 25.401215,113.368963 25.403777,113.354116 25.441088,113.3084 25.44572,113.297833 25.518678,113.278438 25.497043,113.2436 25.516964,113.220721 25.512532,113.174207 25.473986,113.146391 25.494948,113.134829 25.474975,113.148266 25.473031,113.112773 25.449954,113.124657 25.416084,113.086744 25.420137,113.074527 25.386007,113.030259 25.370003,113.018202 25.348734,112.96283 25.352775,112.926632 25.325824,112.92155 25.300393,112.89541 25.314351,112.888638 25.341991,112.867168 25.330428,112.847269 25.339359,112.851248 25.293192,112.869387 25.277158,112.863141 25.251781,112.896238 25.240704,112.950736 25.256996,112.987199 25.250076,113.029188 25.202421,112.969036 25.171173,112.963713 25.15398,113.012666 25.086199,112.973209 25.031085,113.004592 24.980205,113.006505 24.948937,112.988649 24.929806,112.899245 24.924869,112.866467 24.898942,112.783024 24.895261,112.777431 24.945821,112.739139 24.960495,112.736551 25.001432,112.709848 25.028916,112.708418 25.085078,112.654638 25.135736,112.625603 25.144012,112.557554 25.127487,112.502428 25.139561,112.452851 25.154786,112.439441 25.188451,112.397143 25.142528,112.36397 25.191494,112.35684 25.194261,112.370159 25.241244,112.349927 25.249287,112.359569 25.265604,112.340005 25.290632,112.392959 25.313601,112.422866 25.366639,112.442957 25.367118,112.453584 25.387103,112.430043 25.380125,112.414104 25.399248,112.43627 25.404086,112.432253 25.423232,112.386467 25.46072,112.38904 25.497013,112.362838 25.50542,112.369488 25.5427,112.315515 25.527891,112.308249 25.550641,112.244889 25.569087,112.253585 25.584482,112.234024 25.612352,112.253764 25.639398,112.23989 25.704366,112.298462 25.73702,112.336565 25.693797,112.367718 25.734943,112.388942 25.736567,112.373917 25.756043,112.38329 25.805678,112.3564 25.811977,112.345923 25.838345,112.383172 25.847056,112.353497 25.912389,112.318799 25.882136,112.262514 25.899133,112.315021 25.94998,112.292026 25.962027,112.306147 25.96551,112.287567 25.9773,112.300325 25.985064,112.289466 25.997888,112.335457 26.055244,112.321611 26.056621,112.332586 26.076331,112.313633 26.099164,112.278797 26.105772,112.247977 26.078484,112.223087 26.108012,112.227663 26.123243,112.278935 26.148059,112.299638 26.136484,112.30867 26.166619,112.351783 26.164247,112.375961 26.213038,112.391431 26.187076,112.474805 26.178535,112.481925 26.152994,112.502854 26.165292,112.502356 26.145019,112.520383 26.137703,112.523993 26.154789,112.576661 26.160041,112.565937 26.19929,112.583546 26.224829,112.620885 26.192868,112.62273 26.142249,112.66663 26.133855,112.635738 26.109882,112.651458 26.103781,112.672561 26.114059,112.6711 26.131049,112.681614 26.11738,112.707488 26.137898,112.732365 26.135961,112.733188 26.149315,112.742066 26.131846,112.756934 26.137734,112.752268 26.153191,112.776614 26.148054,112.784455 26.180147,112.812129 26.192992,112.815885 26.162794,112.873753 26.151748,112.917815 26.159913,112.925154 26.176261,112.966049 26.155747,113.021675 26.167303,113.035901 26.191446,113.020018 26.247912,113.078365 26.251193,113.089329 26.29004,113.123842 26.295915,113.132878 26.329502,113.165351 26.329273,113.148654 26.370212,113.16698 26.379693,113.156314 26.406216,113.195226 26.413155,113.209438 26.434429,113.185879 26.451871,113.226931 26.48363,113.217557 26.503552,113.158335 26.527281,113.181894 26.610018,113.177785 26.692185,113.154497 26.690506,113.112013 26.731064,113.126726 26.748348,113.09185 26.75872,113.089699 26.777813,113.145247 26.795391,113.139954 26.839148";
    public static GraphicsOverlay main_borderPolyline;
    public static Geometry main_borderGeometry;

    /**
     * 获取边界对象
     *
     * @return
     */
    public static GraphicsOverlay getMainBorderPolygon() {

        if (main_borderPolyline == null) {
            // line graphic
            PolylineBuilder lineGeometry = new PolylineBuilder(SpatialReferences.getWgs84());
            Point[] points = getMainBorderPoints();
            for (int i = 1; i < points.length; i++) {
                lineGeometry.addPoint(points[i]);
            }
            // solid blue line symbol
            SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.DASH_DOT_DOT, Color.parseColor("#FF0000"), 2f);
            // create graphic for polyline
            main_borderGeometry = lineGeometry.toGeometry();
            Graphic lineGraphic = new Graphic(main_borderGeometry);
            // create graphic overlay for polyline
            main_borderPolyline = new GraphicsOverlay();
            // create simple renderer
            SimpleRenderer lineRenderer = new SimpleRenderer(lineSymbol);
            // add graphic to overlay
            main_borderPolyline.setRenderer(lineRenderer);
            // add graphic to overlay
            main_borderPolyline.getGraphics().add(lineGraphic);
            // add graphics overlay to the MapView


        }
        return main_borderPolyline;
    }

    /**
     * 获取边界坐标数组
     *
     * @return
     */
    private static Point[] getMainBorderPoints() {
        String[] pointCoords = region_coord_str.split(",");
        Point[] points = new Point[pointCoords.length];
        int i = 0;
        for (String pointCoord : pointCoords) {
            points[i++] = new Point(Double.valueOf(pointCoord.split(" ")[0]), Double.valueOf(pointCoord.split(" ")[1]));
        }
        return points;
    }

    /**
     * 获取制定辖区统计结果集合
     * 生成面对象和文字graphic并且放回辖区信息对象
     * @param mCurCountResult
     * @param type
     * @return
     *//*
	public static boolean getRegionCountGraphics( CountResult mCurCountResult, String type) {
		if(Constants.JD.equals(type)){ // 生成街道辖区面
			List<Jdresult> jdResults = mCurCountResult.getJdresult();//统计结果取出街道数据
			for(Jdresult jdResult : jdResults){ //遍历街道统计数据
				RegionInfo  regionInfo = MainActivity.jdMap.get(jdResult.getJdcode()); //取出街道边界数据
				if(regionInfo != null){
					*//**
     * 生成辖区面 和文字
     *//*
					Polygon polygon = new Polygon();//初始化面图对象

					String border_coords = regionInfo.getBorder_coords_(); //边界坐标
					String rStr = border_coords.substring(
							Constants.POLYGON_START.length(),
							border_coords.length() - Constants.POLYGON_END.length()
							);

					String[] aPointStr = rStr.split(", "); //所有点  x y 坐标数组
					for (int i = 0; i < aPointStr.length; i++) { //遍历所有点
						String pointStr = aPointStr[i];
						String[] xy = pointStr.split(" ");
						Double x = Double.valueOf(xy[0]);
						Double y = Double.valueOf(xy[1]);
						 if (i==0){
	                        polygon.startPath(new Point(x,y));
	                     }else{
	                        polygon.lineTo(new Point(x,y));
	                     }
					}
					//取出显示颜色
					String colorStr = jdResult.getColor_();
					int color = Color.parseColor(colorStr);
					//设置颜色
					SimpleFillSymbol fillSymbol = new SimpleFillSymbol();
					fillSymbol.setAlpha(Constants.ALPHA_NORMAL);
					fillSymbol.setOutline(Constants.OUT_LINE_SYMBOL_NORMAL);

					Graphic graphic = new Graphic(polygon, fillSymbol);
					regionInfo.setGraphic(graphic);
					*//**
     * 生成辖区统计结果文字
     *//*
					Envelope env = new Envelope();
					polygon.queryEnvelope(env); // 中心点
					String txt = String.valueOf(jdResult.getCnt_());
					TextSymbol tt = new TextSymbol(16, txt, Constants.TEXTCOLOR, TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.MIDDLE);
					tt.setOffsetX(tt.getWidth()/2);
					tt.setOffsetY(tt.getHeight()/2);

					Graphic textGraphic = new Graphic(env.getCenter(), tt);
					regionInfo.setTextGraphic(textGraphic);
					regionInfo.setColor_(color); //设置到辖区信息
					regionInfo.setCnt_(jdResult.getCnt_()); //设置到辖区信息

					regionInfo.setPolygon(polygon);
					//MainActivity.jdMap.put(jdResult.getJdcode(), regionInfo); //放回
				}
			}
			return true;
		}

		if(Constants.SQ.equals(type)){
			List<Sqresult> sqResults = mCurCountResult.getSqresult();//统计结果取出社区数据
			for(Sqresult sqResult : sqResults){ //遍历街道统计数据
				RegionInfo  regionInfo = MainActivity.sqMap.get(sqResult.getSqcode()); //取出街道边界数据
				if(regionInfo != null){

					Polygon polygon = getPolygonsFormPolygonText(regionInfo.getBorder_coords_());

					String colorStr = sqResult.getColor_();
					int color = Color.parseColor(colorStr);

					SimpleFillSymbol fillSymbol = new SimpleFillSymbol(color);
					fillSymbol.setAlpha(Constants.ALPHA_NORMAL);
					fillSymbol.setOutline(Constants.OUT_LINE_SYMBOL_NORMAL );

					Graphic graphic = new Graphic(polygon, fillSymbol);
					regionInfo.setGraphic(graphic);
					*//**
     * 生成辖区统计结果文字
     *//*
					Envelope env = new Envelope();
					polygon.queryEnvelope(env); // 中心点
					String txt = String.valueOf(sqResult.getCnt_());
					TextSymbol tt = new TextSymbol(16, txt, Constants.TEXTCOLOR);
					tt.setOffsetX(tt.getWidth()/2);
					tt.setOffsetY(tt.getHeight()/2);
					Graphic textGraphic = new Graphic(env.getCenter(), tt);
					regionInfo.setTextGraphic(textGraphic);
					regionInfo.setColor_(color); //设置到辖区信息
					regionInfo.setCnt_(sqResult.getCnt_()); //设置到辖区信息

					regionInfo.setPolygon(polygon);
				}
			}
			return true;
		}
		return false;
	}


	*//**
     * 根据坐标文本，获取面对象
     *
     * @param coords
     * @return
     *//*
	public static Polygon getPolygonsFormPolygonText(String coords) {
		Polygon polygons = new Polygon();
		String rStr = coords.substring(Constants.POLYGON_START.length(),
				coords.length() - Constants.POLYGON_END.length());

		String[] polygonStr = rStr.split(Constants.POLYGON_SPLIT);
		if (polygonStr.length == 1) {
			polygonStr = rStr.split(Constants.POLYGON_SPLIT_);
		}
		if (polygonStr.length > 1) {
		}
		for (int j = 0; j < polygonStr.length; j++) {
			Polygon polygon = new Polygon();
			String[] aPointStr = polygonStr[j].split(", ");
			for (int i = 0; i < aPointStr.length; i++) {
				String pointStr = aPointStr[i];
				String[] xy = pointStr.split(" ");
				if (xy[0].indexOf("(") > 0 || xy[1].indexOf(")") > 0) {
					//System.out.println(pointStr);
				}
				try {
					String xs = xy[0];
					String ys = xy[1];
					if (xs.startsWith("(")) {
						xs = xs.substring(1);
					}
					if (ys.endsWith(")")) {
						ys = ys.substring(0, ys.length() - 2);
					}
					Double x = Double.valueOf(xs);
					Double y = Double.valueOf(ys);
					if(i == 0){
						polygon.startPath(new Point(x,y));
					}else{
						polygon.lineTo(new Point(x,y));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			polygon.closeAllPaths();
			polygons.add(polygon,true);
		}
		return polygons;
	}*/
}
