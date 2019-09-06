package com.bohaigaoke.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDPath() {
        String sdPath = null;
        // 判断sd卡是否存在
        boolean sdCardExit = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExit) {
            // 获取根目录
            sdPath = Environment.getExternalStorageDirectory().toString();
        }
        return sdPath;
    }

    /**
     * 返回32位UUID字符串
     *
     * @return
     */
    public static String getUUID32() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapInLocal(String path) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        //opts.inJustDecodeBounds = true; //设置为true, 加载器不会返回图片, 而是设置Options对象中以out开头的字段.即仅仅解码边缘区域
        // 指定加载可以加载出图片.
        opts.inJustDecodeBounds = false;
        // 使用计算出来的比例进行缩放
        opts.inSampleSize = 0;
        return BitmapFactory.decodeFile(path, opts);

        /* try {
              FileInputStream fis = new FileInputStream(path);
              return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
           } catch (FileNotFoundException e) {
              e.printStackTrace();
              return null;
         }*/
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        if (filePath == null || "".equals(filePath)) {
            return;
        }
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 图片文件进行压缩处理
     *
     * @param sourceFilePath
     * @param targetFilePath
     * @return
     */
    public static boolean dealImage(String sourceFilePath, String targetFilePath) {
        try {
            int dh = 1024;
            int dw = 768;
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds = true; //当为true时  允许查询图片不为 图片像素分配内存
            InputStream is = new FileInputStream(sourceFilePath);
            Bitmap bmp = BitmapFactory.decodeStream(is, null, factory);
            int hRatio = (int) Math.ceil(factory.outHeight / (float) dh); //图片是高度的几倍
            int wRatio = (int) Math.ceil(factory.outWidth / (float) dw); //图片是宽度的几倍
            System.out.println("hRatio:" + hRatio + "  wRatio:" + wRatio);
            //缩小到  1/ratio的尺寸和 1/ratio^2的像素  
            if (hRatio > 1 || wRatio > 1) {
                if (hRatio > wRatio) {
                    factory.inSampleSize = hRatio;
                } else
                    factory.inSampleSize = wRatio;
            }
            factory.inJustDecodeBounds = false;
            is.close();
            is = new FileInputStream(sourceFilePath);
            bmp = BitmapFactory.decodeStream(is, null, factory);
            OutputStream outFile = new FileOutputStream(targetFilePath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 60, outFile);
            outFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 缩放图片资源到指定的尺寸
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {    
        int width = drawable.getIntrinsicWidth();    
        int height = drawable.getIntrinsicHeight();    
        Bitmap oldbmp = drawableToBitmap(drawable);    
        Matrix matrix = new Matrix();    
        float scaleWidth = ((float) w / width);    
        float scaleHeight = ((float) h / height);    
        matrix.postScale(scaleWidth, scaleHeight);    
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,    
                matrix, true);    
        return new BitmapDrawable(null, newbmp);    
    }    
        
    public static Bitmap drawableToBitmap(Drawable drawable) {    
        int width = drawable.getIntrinsicWidth();    
        int height = drawable.getIntrinsicHeight();    
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888    
                : Bitmap.Config.RGB_565;    
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);    
        Canvas canvas = new Canvas(bitmap);    
        drawable.setBounds(0, 0, width, height);    
        drawable.draw(canvas);    
        return bitmap;    
    }   
    
    
    /**
     * URL检查<br>
     * <br>
     * @param pInput     要检查的字符串<br>
     * @return boolean   返回检查结果<br>
     */
    public static boolean isUrl (String pInput) {
        if(pInput == null){
            return false;
        }
        String regEx = "^(http|https|ftp)//://([a-zA-Z0-9//.//-]+(//:[a-zA-"
            + "Z0-9//.&%//$//-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
            + "2}|[1-9]{1}[0-9]{1}|[1-9])//.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
            + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-4][0-9]|"
            + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-"
            + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
            + "-9//-]+//.)*[a-zA-Z0-9//-]+//.[a-zA-Z]{2,4})(//:[0-9]+)?(/"
            + "[^/][a-zA-Z0-9//.//,//?//'///////+&%//$//=~_//-@]*)*$";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }

    
    public static String getHost(String url) {  
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {  
            url = "http://" + url;  
        }  
      
        String returnVal = "";  
        try {  
            Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");  
            Matcher m = p.matcher(url);  
            if (m.find()) {  
                returnVal = m.group();  
            }  
      
        } catch (Exception e) {  
        }  
        if ((returnVal.endsWith(".html") || returnVal.endsWith(".htm"))) {  
            returnVal = "";  
        }  
        return returnVal;  
    }  
    
    public static void main(String[] args) {
		System.out.println(getHost("http://127.0.0.1:10086/#"));
	}
}
