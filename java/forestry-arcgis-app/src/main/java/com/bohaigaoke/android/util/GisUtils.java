package com.bohaigaoke.android.util;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.bohaigaoke.android.model.query.Rows;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.EnvelopeBuilder;
import com.esri.arcgisruntime.geometry.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * GIS工具类
 * @author name
 *
 */
public class GisUtils
{
	
	

  public static int integerPower2of(int num)
  {
    if (num <= 0) {
      num = 1;
    }
    double pow = Math.log(num) / Math.log(2.0D);
    int pow_i = (int)pow;
    int temp = 2 << pow_i - 1;
    if (temp == num) {
      return num;
    }
    return (2 << pow_i);
  }

  public static Envelope calculateEnvelopeByBaseResourceList(List<Rows> list)
  {
	  if(list == null || list.size() == 0){
		  return null;
	  }
	  try
	  {
		  Envelope envelope = null;
          EnvelopeBuilder envelopeBuilder = null;
		  for (int i = 0; i < list.size(); ++i) {
			  Rows br = list.get(i);
			  if (i == 0) {
				  envelope = new Envelope(new Point(br.getDouble("longitude"), br.getDouble("latitude")), new Point(br.getDouble("longitude"), br.getDouble("latitude")));
                  envelopeBuilder = new EnvelopeBuilder(envelope);
			  }else{
				  if (Double.compare(br.getDouble("longitude"), envelopeBuilder.getXMax()) > 0) {
                    envelopeBuilder.setXMax(br.getDouble("longitude"));
				  }
				  if (Double.compare(br.getDouble("longitude"), envelopeBuilder.getXMin()) < 0) {
                    envelopeBuilder.setXMin(br.getDouble("longitude"));
				  }
				  if (Double.compare( br.getDouble("latitude"), envelopeBuilder.getYMax()) > 0) {
                    envelopeBuilder.setYMax( br.getDouble("latitude"));
				  }
				  if (Double.compare( br.getDouble("latitude"), envelopeBuilder.getYMin()) < 0) {
                    envelopeBuilder.setYMin( br.getDouble("latitude"));
				  }
			  }
		  }
		  return envelopeBuilder.toGeometry();
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  return null;
  }
  
  public static Envelope calculateEnvelope2D(Point[] points)
  {
    try
    {
      Envelope envelope = new Envelope(new Point(points[0].getX(), points[0].getY()), new Point(points[0].getX(), points[0].getY()));
      EnvelopeBuilder envelopeBuilder = new EnvelopeBuilder(envelope);
      for (int i = 0; i < points.length; ++i) {
        if (points[i] == null) {
          return null;
        }
        if (Double.compare(points[i].getX(), envelopeBuilder.getXMax()) > 0) {
          envelopeBuilder.setXMax(points[i].getX());
        }
        if (Double.compare(points[i].getX(), envelopeBuilder.getXMin()) < 0) {
          envelopeBuilder.setXMin(points[i].getX());
        }
        if (Double.compare(points[i].getY(), envelopeBuilder.getYMax()) > 0) {
          envelopeBuilder.setYMax(points[i].getY());
        }
        if (Double.compare(points[i].getY(), envelopeBuilder.getYMin()) < 0) {
          envelopeBuilder.setYMin(points[i].getY());
        }
      }
      return envelopeBuilder.toGeometry();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Envelope calculateEnvelope2D(float[] points)
  {
    try
    {
      int offset = 0;
      Envelope envelope = new Envelope(new Point(points[0], points[1]), new Point(points[0], points[1]));
      EnvelopeBuilder envelopeBuilder = new EnvelopeBuilder(envelope);
      for (int i = 0; i < points.length / 2; ++i) {
        offset = i * 2;
        if (Double.compare(points[offset], envelopeBuilder.getXMax()) > 0) {
          envelopeBuilder.setXMax(points[offset]);
        }
        if (Double.compare(points[offset], envelopeBuilder.getXMin()) < 0) {
          envelopeBuilder.setXMin(points[offset]);
        }
        if (Double.compare(points[(offset + 1)], envelopeBuilder.getYMax()) > 0) {
          envelopeBuilder.setYMax(points[(offset + 1)]);
        }
        if (Double.compare(points[(offset + 1)], envelopeBuilder.getYMin()) < 0) {
          envelopeBuilder.setYMin(points[(offset + 1)]);
        }
      }
      return envelopeBuilder.toGeometry();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean copyResourceTo(String res, String dest, boolean override)
  {
    InputStream is = null;
    OutputStream os = null;
    try {
      is = GisUtils.class.getResourceAsStream(res);
      if (is == null) {
        return false;
      }
      File f = new File(dest);
      if (f.exists())
        if (override) {
          f.delete();
        } else {
          return true;
        }
      os = new FileOutputStream(f);
      byte[] buffer = new byte[1024];
      int len = 0;
      while (true) {
        len = is.read(buffer);
        if (len <= 0) break;
        os.write(buffer);
      }

      os.flush();
      return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      try {
        if (is != null)
          is.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      try {
        if (os != null)
          os.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return false;
  }

  public static float normalize(float in, float min, float max)
  {
    if (Float.compare(in, min) < 0)
      in = min;
    else if (Float.compare(in, max) > 0) {
      in = max;
    }
    return ((in - min) / (max - min));
  }

  public static float normalize(int in, int min, int max)
  {
    if (in < min)
      in = min;
    else if (in > max) {
      in = max;
    }
    return ((in - min) / (max - min));
  }

  public static Envelope caculateEnvelopeOfVisibleMap(float centerx, float centery, float resolution, int mapWidth, int mapHeight)
  {
    Envelope env = new Envelope(new Point(centerx,centery), new Point(centerx,centery));
    EnvelopeBuilder envelopeBuilder = new EnvelopeBuilder(env);
    float dw = resolution * mapWidth;
    float dh = resolution * mapHeight;
    envelopeBuilder.setXMin(centerx - (dw / 2.0F));
    envelopeBuilder.setXMax(centerx + dw / 2.0F);
    envelopeBuilder.setYMin(centery - (dh / 2.0F));
    envelopeBuilder.setYMax(centery + dh / 2.0F);
    return envelopeBuilder.toGeometry();
  }

  public static float getDensity(Context context) {
    if (context == null) {
      return 1.0F;
    }
    WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(dm);
    return dm.density;
  }

  public static int getTotalMemory()
  {
    FileReader fr = null;
    BufferedReader br = null;
    String path = "/proc/meminfo";
    try {
      fr = new FileReader(path);
      br = new BufferedReader(fr, 2048);
      String str = br.readLine();
      String[] arrayOfString = str.split("\\s+");
      int i = Integer.valueOf(arrayOfString[1]).intValue();

      return i;
    }
    catch (Exception e)
    {
    }
    finally
    {
      if (br != null) {
        try {
          br.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fr != null) {
        try {
          fr.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return 0;
  }

  public static Drawable getDrawable(Resources res, String path)
  {
    InputStream inputStream = null;
    try {
      inputStream = GisUtils.class.getResourceAsStream(path);
      Drawable drawable = new BitmapDrawable(res, inputStream);
      Drawable localDrawable1 = drawable;

      return localDrawable1;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      try {
        if (inputStream != null)
          inputStream.close();
      } catch (Exception localException4) {
      }
    }
    return null;
  }

  public static Drawable getNinePatchDrawable(Resources res, String path)
  {
    try
    {
      InputStream is = GisUtils.class.getResourceAsStream(path);
      Bitmap bitmap = BitmapFactory.decodeStream(is);
      is.close();
      byte[] chunk = bitmap.getNinePatchChunk();
      if ((chunk != null) && 
        (true == NinePatch.isNinePatchChunk(chunk))) {
        Drawable drawable = new NinePatchDrawable(res, bitmap, chunk, new Rect(), null);
        if (drawable != null) {
          return drawable;
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean isTableExists(SQLiteDatabase db, String table)
  {
    if ((db == null) || (!(db.isOpen())) || (table == null) || (table.length() <= 0)) {
      return false;
    }
    Cursor cur = null;
    try {
      StringBuilder sql = new StringBuilder(50);
      sql.append("select * from sqlite_master where type='table' and name='");
      sql.append(table);
      sql.append("'");
      cur = db.rawQuery(sql.toString(), null);
      if ((cur != null) && (cur.getCount() > 0)) {
        return true;
      }
    }
    catch (Exception e)
    {
    }
    finally
    {
      if (cur != null) {
        cur.close();
      }
    }
    return false;
  }

  public static Point modelViewToMap(float[] pnts, float centerx, float centery, float resolution)
  {
    if ((pnts == null) || (pnts.length < 3)) {
      return null;
    }
    float lon = pnts[0] * resolution + centerx;
    float lat = pnts[1] * resolution + centery;
    return new Point(lon, lat);
  }

  public static boolean modelViewToMap(float[] pnts, float centerx, float centery, float resolution, float[] outPnts)
  {
    if ((pnts == null) || (pnts.length < 3) || (outPnts == null) || (pnts.length != outPnts.length)) {
      return false;
    }
    int inOffset = 0;
    int outOffset = 0;
    for (int i = 0; i < pnts.length / 3; ++i) {
      inOffset = i * 2;
      outOffset = i * 3;
      outPnts[outOffset] = (pnts[inOffset] * resolution + centerx);
      outPnts[(outOffset + 1)] = (pnts[(inOffset + 1)] * resolution + centery);
    }
    return true;
  }

  public static float modelViewToMapX(float x, float centerx, float resolution)
  {
    if (Float.isNaN(x)) {
      return (0.0F / 0.0F);
    }
    return (x * resolution + centerx);
  }

  public static float modelViewToMapY(float y, float centery, float resolution)
  {
    if (Float.isNaN(y)) {
      return (0.0F / 0.0F);
    }
    return (y * resolution + centery);
  }

  public static double[] mapToModelView(Point point, double centerx, double centery, double resolution)
  {
    if ((point == null) ) {
      return null;
    }
    return mapToModelView(point.getX(), point.getY(), centerx, centery, resolution);
  }

  public static double[] mapToModelView(double x, double y, double centerx, double centery, double resolution)
  {
	  double dx = (x - centerx) / resolution;
	  double dy = (y - centery) / resolution;
	  double[] pnts = new double[3];
    pnts[0] = dx;
    pnts[1] = dy;
    pnts[2] = 0.0F;
    return pnts;
  }

  public static float[] mapToModelView(float[] points, float centerx, float centery, float resolution)
  {
    if ((points == null) || (points.length < 2)) {
      return null;
    }
    int offsetIn = 0;
    int offsetOut = 0;
    float[] pnts = new float[points.length / 2 * 3];
    for (int i = 0; i < points.length / 2; ++i) {
      offsetIn = i * 2;
      offsetOut = i * 3;
      float dx = (points[offsetIn] - centerx) / resolution;
      float dy = (points[(offsetIn + 1)] - centery) / resolution;
      pnts[offsetOut] = dx;
      pnts[(offsetOut + 1)] = dy;
      pnts[(offsetOut + 2)] = 0.0F;
    }
    return pnts;
  }

  public static boolean mapToModelView(float[] inPoints, float[] outPoints, float centerx, float centery, float resolution)
  {
    if ((inPoints == null) || (inPoints.length < 2) || (outPoints == null)) {
      return false;
    }
    int offsetIn = 0;
    int offsetOut = 0;
    for (int i = 0; i < inPoints.length / 2; ++i) {
      offsetIn = i * 2;
      offsetOut = i * 3;
      float dx = (inPoints[offsetIn] - centerx) / resolution;
      float dy = (inPoints[(offsetIn + 1)] - centery) / resolution;
      outPoints[offsetOut] = dx;
      outPoints[(offsetOut + 1)] = dy;
      outPoints[(offsetOut + 2)] = 0.0F;
    }
    return true;
  }

  public static float mapToModelViewX(float x, float centerx, float resolution)
  {
    if (Float.isNaN(x)) {
      return (0.0F / 0.0F);
    }
    if (false == Float.isNaN(resolution)) {
      return ((x - centerx) / resolution);
    }
    return (0.0F / 0.0F);
  }

  public static float mapToModelViewY(float y, float centery, float resolution)
  {
    if (Float.isNaN(y)) {
      return (0.0F / 0.0F);
    }
    if (false == Float.isNaN(resolution)) {
      return ((y - centery) / resolution);
    }
    return (0.0F / 0.0F);
  }

 

  public static void screenToSurface(float[] pnts, int width, int height, float tilt)
  {
    pnts[0] -= width / 2;
    pnts[1] = (height / 2 - pnts[1]);
    pnts[2] = 0.0F;
    tilt = (float)Math.toRadians(tilt);
    float y = 0.0F;
    float z = 0.0F;
    float x = 0.0F;
    if (pnts[1] < 0.0F) {
      y = (float)(pnts[1] / (1.0D + pnts[1] * Math.tan(tilt) / 200.0D));
      z = (float)(y * Math.tan(tilt));
    } else {
      y = (float)(pnts[1] / (1.0D - (pnts[1] * Math.abs(Math.tan(tilt)) / 200.0D)));
      z = (float)(y * Math.tan(tilt));
    }
    x = pnts[0] * (200.0F - z) / 200.0F;
    pnts[0] = x;
    pnts[1] = y;
    pnts[2] = z;
  }

  public static void surfaceToScreen(float[] pnts, int width, int height)
  {
    pnts[0] *= 200.0F / (200.0F - pnts[2]);
    pnts[1] *= 200.0F / (200.0F - pnts[2]);
    pnts[0] = (width / 2 + pnts[0]);
    pnts[1] = (height / 2 - pnts[1]);
  }

}