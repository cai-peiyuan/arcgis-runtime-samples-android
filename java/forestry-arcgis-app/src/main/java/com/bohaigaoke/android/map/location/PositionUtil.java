package com.bohaigaoke.android.map.location;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;

/**
 * 各地图API坐标系统比较与转换;
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
 * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
 * GCJ02坐标系：即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。
 * 谷歌中国地图和搜搜中国地图采用的是GCJ02地理坐标系; BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;
 * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。 chenhua
 */
public class PositionUtil {

	public static final String BAIDU_LBS_TYPE = "bd09ll";

	public static double pi = 3.1415926535897932384626;
	public static double a = 6378245.0;
	public static double ee = 0.00669342162296594323;

	/**
	 * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static Gps gps84_To_Gcj02(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			return null;
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new Gps(mgLat, mgLon);
	}

	/**
	 * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
	 * */
	public static Gps gcj_To_Gps84(double lat, double lon) {
		Gps gps = transform(lat, lon);
		double lontitude = lon * 2 - gps.getWgLon();
		double latitude = lat * 2 - gps.getWgLat();
		return new Gps(latitude, lontitude);
	}

	/**
	 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
	 * 
	 * @param gg_lat
	 * @param gg_lon
	 */
	public static Gps gcj02_To_Bd09(double gg_lat, double gg_lon) {
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return new Gps(bd_lat, bd_lon);
	}

	/**
	 * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
	 * bd_lat * @param bd_lon * @return
	 */
	public static Gps bd09_To_Gcj02(double bd_lat, double bd_lon) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
		double gg_lon = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		return new Gps(gg_lat, gg_lon);
	}

	/**
	 * (BD-09)-->84
	 * 
	 * @param bd_lat
	 * @param bd_lon
	 * @return
	 */
	public static Gps bd09_To_Gps84(double bd_lat, double bd_lon) {

		Gps gcj02 = PositionUtil.bd09_To_Gcj02(bd_lat, bd_lon);
		Gps map84 = PositionUtil.gcj_To_Gps84(gcj02.getWgLat(),
				gcj02.getWgLon());
		return map84;

	}

	public static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	public static Gps transform(double lat, double lon) {
		if (outOfChina(lat, lon)) {
			return new Gps(lat, lon);
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new Gps(mgLat, mgLon);
	}

	public static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
				+ 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	public static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
				* Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
				* pi)) * 2.0 / 3.0;
		return ret;
	}

	public static void main(String[] args) {

		// 北斗芯片获取的经纬度为WGS84地理坐标 31.426896,119.496145
		Gps gps = new Gps(31.426896, 119.496145);
		System.out.println("gps :" + gps);
		Gps gcj = gps84_To_Gcj02(gps.getWgLat(), gps.getWgLon());
		System.out.println("gcj :" + gcj);
		Gps star = gcj_To_Gps84(gcj.getWgLat(), gcj.getWgLon());
		System.out.println("star:" + star);
		Gps bd = gcj02_To_Bd09(gcj.getWgLat(), gcj.getWgLon());
		System.out.println("bd :" + bd);
		Gps gcj2 = bd09_To_Gcj02(bd.getWgLat(), bd.getWgLon());
		System.out.println("gcj :" + gcj2);
	}

	public static String getBDLocationResult(BDLocation location) {
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		/**
		 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
		 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
		 */
		sb.append(location.getTime());
		sb.append("\nlocType : ");// 定位类型
		sb.append(location.getLocType());
		sb.append("\nlocType description : ");// *****对应的定位类型说明*****
		sb.append(location.getLocTypeDescription());
		sb.append("\nlatitude : ");// 纬度
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");// 经度
		sb.append(location.getLongitude());
		sb.append("\nradius : ");// 半径
		sb.append(location.getRadius());
		sb.append("\nCountryCode : ");// 国家码
		sb.append(location.getCountryCode());
		sb.append("\nCountry : ");// 国家名称
		sb.append(location.getCountry());
		sb.append("\ncitycode : ");// 城市编码
		sb.append(location.getCityCode());
		sb.append("\ncity : ");// 城市
		sb.append(location.getCity());
		sb.append("\nDistrict : ");// 区
		sb.append(location.getDistrict());
		sb.append("\nStreet : ");// 街道
		sb.append(location.getStreet());
		sb.append("\naddr : ");// 地址信息
		sb.append(location.getAddrStr());
		sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
		sb.append(location.getUserIndoorState());
		sb.append("\nDirection(not all devices have value): ");
		sb.append(location.getDirection());// 方向
		sb.append("\nlocationdescribe: ");
		sb.append(location.getLocationDescribe());// 位置语义化信息
		sb.append("\nPoi: ");// POI信息
		if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
			for (int i = 0; i < location.getPoiList().size(); i++) {
				Poi poi = (Poi) location.getPoiList().get(i);
				sb.append(poi.getName() + ";");
			}
		}
		if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());// 速度 单位：km/h
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());// 卫星数目
			sb.append("\nheight : ");
			sb.append(location.getAltitude());// 海拔高度 单位：米
			sb.append("\ngps status : ");
			sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
			sb.append("\ndescribe : ");
			sb.append("gps定位成功");
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
			// 运营商信息
			if (location.hasAltitude()) {// *****如果有海拔高度*****
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
			}
			sb.append("\noperationers : ");// 运营商信息
			sb.append(location.getOperators());
			sb.append("\ndescribe : ");
			sb.append("网络定位成功");
		} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
			sb.append("\ndescribe : ");
			sb.append("离线定位成功，离线定位结果也是有效的");
		} else if (location.getLocType() == BDLocation.TypeServerError) {
			sb.append("\ndescribe : ");
			sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
		} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
			sb.append("\ndescribe : ");
			sb.append("网络不同导致定位失败，请检查网络是否通畅");
		} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
			sb.append("\ndescribe : ");
			sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
		}
		Log.i("founder", sb.toString());
		return sb.toString();
	}
	

}