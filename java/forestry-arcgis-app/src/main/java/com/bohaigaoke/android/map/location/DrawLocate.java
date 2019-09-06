package com.bohaigaoke.android.map.location;

import android.graphics.drawable.BitmapDrawable;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;


/**
 * 定位绘图
 * 
 * @author wuxin
 * @time 10:29
 */
public class DrawLocate {
	
	private static Graphic graphicCenterPoint;
	private static Graphic graphicErrorRound;

	public static void main(String[] args) {
		
		// DrawLocate.drawPoint(point, location.getRadius(), baiduLayer, image, Color.parseColor("#0099FF"), Color.parseColor("#0099FF"));  

	}
	/**
	 * 绘制定位
	 * @param center 中心点
	 * @param radius 误差圆半径（单位：米）
	 * @param graphicsLayer 绘制图层
	 * @param centerImage 中心点图片
	 * @param errorRoundColorFill 误差圆颜色
	 * @param errorRoundColorLine 误差圆边线颜色
	 */
	public static void drawPoint(Point center, double radius,
								 GraphicsOverlay graphicsLayer, BitmapDrawable centerImage,
								 int errorRoundColorFill, int errorRoundColorLine) {
		
		/* 清除图层 */
		if(graphicsLayer != null){
			graphicsLayer.clearSelection();
			graphicsLayer.getGraphics();
			//graphicsLayer.removeAll();
		}
		
		/* 绘制中心点 */
		//PictureMarkerSymbol symbol = PictureMarkerSymbol.createAsync(centerImage);
		//symbol.setOffsetY(10.0f);
		//graphicCenterPoint = new Graphic(center, symbol);
		
		/* 绘制误差圆 */
		if(radius > 0){
			
			//Polygon polygon = new Polygon();
			//getCircle(center, radius, polygon);
			//FillSymbol fillSymbol = new SimpleFillSymbol(errorRoundColorFill);
			//fillSymbol.setAlpha(10);
			//SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, errorRoundColorLine, 2.0f);
			//fillSymbol.setOutline(lineSymbol);
			//graphicErrorRound = new Graphic(polygon, fillSymbol);
			
		}
		
		/* 绘制上图 */
		//graphicsLayer.(new Graphic[]{graphicCenterPoint, graphicErrorRound});

	}

	/**
	 * 获取圆的图形对象
	 * 
	 * @param center
	 * @param radius
	 * @return
	 */
	public static Polygon getCircle(Point center, double radius) {
		//Polygon polygon = new Polygon();
		//getCircle(center, radius, polygon);
		//return polygon;
		return null;
	}

	/**
	 * 
	 * @param center
	 *            中心点
	 * @param radius
	 *            半径（米）
	 * @param circle
	 *            圆的图形对象
	 */
	public static void getCircle(Point center, double radius, Polygon circle) {
//		circle.setEmpty();
//		Point[] points = getPoints(center, radius);
//		circle.startPath(points[0]);
//		for (int i = 1; i < points.length; i++)
//			circle.lineTo(points[i]);
	}

	/**
	 * 通过中心点和半径计算得出圆形的边线点集合
	 * 
	 * @param center
	 * @param radius
	 * @return
	 */
	public static Point[] getPoints(Point center, double radius) {
		Point[] points = new Point[50];
		double sin;
		double cos;
		double x;
		double y;
		for (double i = 0; i < 50; i++) {
			sin = Math.sin(Math.PI * 2 * i / 50);
			cos = Math.cos(Math.PI * 2 * i / 50);
			x = center.getX() + radius * sin;
			y = center.getY() + radius * cos;
			points[(int) i] = new Point(x, y);
		}
		return points;
	}

}
