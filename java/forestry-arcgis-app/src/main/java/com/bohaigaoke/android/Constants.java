package com.bohaigaoke.android;

import android.graphics.Color;

import com.esri.arcgisruntime.symbology.SimpleLineSymbol;


public class Constants {

	public static final String POLYGON_START = "MULTIPOLYGON (((";
	public static final String POLYGON_SPLIT = "\\)\\), \\(\\(";
	public static final String POLYGON_SPLIT_ = "\\), \\(";
	public static final String POLYGON_END = ")))";

	public static final String JD = "jd";
	public static final String SQ = "sq";
	// 西城区正中心X，Y
	public static double CENTER_X = 116.3542231655120000;
	public static double CENTER_Y = 39.91940207;

	public static double MIN_X = 116.3152282864410;
	public static double MIN_Y = 39.8673445082118;

	public static double MAX_X = 116.3932180445830;
	public static double MAX_Y = 39.9714596292014;

	// 偏移的西城坐标

	public static double CENTER_X_A = 117.0025829855180000;
	public static double CENTER_Y_A = 2.76679742;

	public static double MIN_X_A = 116.9725332968270;
	public static double MIN_Y_A = 2.7145357161011;

	public static double MAX_X_A = 117.0326326742090;
	public static double MAX_Y_A = 2.8190591232859;

	public static final double BJ_54_OFFSET_X = -54634.3823521530000;
	public static final double BJ_54_OFFSET_Y = 4114879.9876113700000;
	
	public static final double MAP_MAX_SCALE = 4000;
	public static final double LAYER_COUNT_JD_MIN_SCALE = 55555;
	public static final float GRAPHIC_OUTLINE_WIDTH_NORMAL= 1.0f;
	public static final float GRAPHIC_OUTLINE_WIDTH_HIGHTLIGHT = 2.0f;
	
	
	public static String LINECOLOR_STR = "#0099FF";
	public static int LINECOLOR = Color.parseColor(LINECOLOR_STR);
	public static int TEXTCOLOR = Color.parseColor("#ff0000");
	
	
	
	public static void initSymbols(){
		OUT_LINE_SYMBOL_NORMAL = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, LINECOLOR, GRAPHIC_OUTLINE_WIDTH_NORMAL);
		OUT_LINE_SYMBOL_HIGHTLIGHT = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, TEXTCOLOR, GRAPHIC_OUTLINE_WIDTH_HIGHTLIGHT);
		
	}
	
	public static SimpleLineSymbol OUT_LINE_SYMBOL_NORMAL = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, LINECOLOR, GRAPHIC_OUTLINE_WIDTH_NORMAL );
	public static SimpleLineSymbol OUT_LINE_SYMBOL_HIGHTLIGHT = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, TEXTCOLOR, GRAPHIC_OUTLINE_WIDTH_HIGHTLIGHT);
	
	public static SimpleLineSymbol MAIN_BORDER_LINE_SYMBOL = new SimpleLineSymbol(SimpleLineSymbol.Style.DASH_DOT_DOT, Color.parseColor("#0000FF"),  2.0f);
	
	
	public static int ALPHA_NORMAL = 50;
	public static int ALPHA_HIGHTLIGHT = 90;
	
	public static int MAX_BIG_MARKER_NUM = 20;  // 显示大图标数目
	
	public static int QUERY_PAGE_SIZE = 200;
	
	public static class QueryType{
		public static String NORMAL = "1";
		public static String SQUARE = "2";
		public static String CIRCLE = "3";
		public static String POLYGON = "4";
		public static String LINEBUFFER = "5";
	}

}
