package net.oschina.app.api.remote;

import com.baidu.location.BDLocation;
import com.bohaigaoke.android.Constants;
import com.bohaigaoke.android.map.location.Gps;
import com.bohaigaoke.android.model.login.Userinfo_;
import com.bohaigaoke.android.model.query.Rows;
import com.bohaigaoke.android.util.BHUtil;
import com.bohaigaoke.android.util.DemoUtil;
import com.bohaigaoke.android.util.DeviceUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.oschina.app.api.ApiHttpClient;

import org.kymjs.kjframe.utils.StringUtils;

import java.util.Map;

public class OSChinaApi {

	/**
	 * 登陆
	 * 
	 * @param username
	 * @param password
	 * @param handler
	 */
	public static void login(String username, String password,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("account_", username);
		params.put("password_", password);
		params.put("device_id_", DeviceUtil.getDeviceId());
		
		// params.put("keep_login", 1);
		String loginurl = "doLogin.jhtml";
		ApiHttpClient.post(loginurl, params, handler);
	}

	/**
	 * 获取专题列表
	 * 
	 * @param userInfo_
	 * @param handler
	 */
	public static void getThemeData(Userinfo_ userInfo_, String parent_id_,
			int start_, int limit_, AsyncHttpResponseHandler handler) {

		if (userInfo_ == null) {
			userInfo_ = DemoUtil.getDemoLoginJson().getUserinfo_();
		}
		RequestParams params = new RequestParams();
		// params.put("account_", userInfo_.getAccount_());
		// params.put("userid_", userInfo_.getId_());
		params.put("parent_id_", parent_id_);
		params.put("start", start_);
		params.put("limit", limit_);
		params.put("sort", "[{\"property\":\"enname_\",\"direction\":\"ASC\"}]");
		String url = "mis/theme/listAos_mis_theme.jhtml";
		ApiHttpClient.post(url, params, handler);
	}

	/**
	 * 获取专题下数据表
	 * 
	 * @param userInfo_
	 * @param handler
	 */
	public static void getThemeLayer(Userinfo_ userInfo_, int start_,
			int limit_, String theme_id_, AsyncHttpResponseHandler handler) {

		if (userInfo_ == null) {
			userInfo_ = DemoUtil.getDemoLoginJson().getUserinfo_();
		}
		RequestParams params = new RequestParams();
		// params.put("account_", userInfo_.getAccount_());
		// params.put("userid_", userInfo_.getId_());
		params.put("theme_id_", theme_id_);
		params.put("querytype", "accurate");
		params.put("start", start_);
		params.put("limit", limit_);
		params.put("sort",
				"[{\"property\":\"table_name_\",\"direction\":\"ASC\"}]");
		String url = "mis/theme_table/listAos_mis_theme_table.jhtml";
		ApiHttpClient.post(url, params, handler);
	}

	/**
	 * 获取专题字段列表
	 * 

	 * @param handler
	 */
	public static void getThemeField(Userinfo_ userInfo_, int start_,
			int limit_, String theme_id_, AsyncHttpResponseHandler handler) {
		if (userInfo_ == null) {
			userInfo_ = DemoUtil.getDemoLoginJson().getUserinfo_();
		}
		RequestParams params = new RequestParams();
		// params.put("account_", userInfo_.getAccount_());
		// params.put("userid_", userInfo_.getId_());
		params.put("theme_id_", theme_id_);

		params.put("sort",
				"[{\"property\":\"sequence_\",\"direction\":\"ASC\"}]");

		params.put("start", start_);
		params.put("limit", limit_);
		String url = "mis/theme_table_field/listAos_mis_theme_table_field.jhtml";
		ApiHttpClient.post(url, params, handler);
	}

	/**
	 * 获取数据表字段列表
	 * 

	 * @param handler
	 */
	public static void getTableField(Userinfo_ userInfo_, int start_,
			int limit_, String table_id_, AsyncHttpResponseHandler handler) {
		if (userInfo_ == null) {
			userInfo_ = DemoUtil.getDemoLoginJson().getUserinfo_();
		}
		RequestParams params = new RequestParams();
		// params.put("account_", userInfo_.getAccount_());
		// params.put("userid_", userInfo_.getId_());
		params.put("table_id_", table_id_);
		params.put("start", start_);
		params.put("limit", limit_);
		String url = "mis/theme_table_field/listAos_mis_theme_table_field.jhtml";
		ApiHttpClient.post(url, params, handler);
	}

	/**
	 * 通用查询api
	 * 
	 * @param userInfo_
	 * @param qParam
	 * @param handler
	 */
	public static void commonQuery(Userinfo_ userInfo_, Rows layerInfo,
			Map<String, String> qParam, AsyncHttpResponseHandler handler) {

		RequestParams params = new RequestParams();
		// params.put("account_", userInfo_.getAccount_());
		// params.put("userid_", userInfo_.getId_());
		params.put("query_type_", qParam.get("query_type_"));
		
		if(!StringUtils.isEmpty(qParam.get("coords_"))){
			params.put("coords_", qParam.get("coords_"));
		}
		
		params.put("columns_", qParam.get("columns_"));
		params.put("table_name_", qParam.get("table_name_"));
		String where_condition_ = "";

		if (qParam.get("columns_query_") != null
				&& !"".equals(qParam.get("columns_query_"))
				&& qParam.get("keyword_") != null
				&& !"".equals(qParam.get("keyword_"))) {
			where_condition_ = qParam.get("columns_query_") + " like '%"
					+ qParam.get("keyword_") + "%'";
		} else {
			where_condition_ = "1=1";
		}
		if (qParam.get("sqcode_s_") != null
				&& !"".equals(qParam.get("sqcode_s_"))) {
			where_condition_ += " and sqcode in (" + qParam.get("sqcode_s_")
					+ ")";
		}
		if (qParam.get("jdcode_s_") != null
				&& !"".equals(qParam.get("jdcode_s_"))) {
			where_condition_ += " and jdcode in (" + qParam.get("jdcode_s_")
					+ ")";
		}
		params.put("where_condition_", where_condition_);

		int start = (Integer.valueOf(qParam.get("page")) - 1)
				* Constants.QUERY_PAGE_SIZE;

		params.put("page", String.valueOf(qParam.get("page")));
		params.put("start", String.valueOf(start));
		// params.put("start", qParam.get("start"));
		params.put("limit", qParam.get("limit"));

		String url = "mis/common/query.jhtml";

		ApiHttpClient.post(url, params, handler);
	}

	/**
	 * id_: imsi_: imei_: owner_: release_: sdk_: brand_: model_: fingerprint_:
	 * densitydpi_: width_: height_: xdpi_: ydpi_: online_:
	 * last_time_:2017-12-13 11:17:15 longitude_: latitude_: radius_: addrstr_:
	 * line_number_:
	 */
	public static void reportInfo(Userinfo_ userInfo_, BDLocation location,
			Gps gps) {
		String deviceId = DeviceUtil.getDeviceId();

		RequestParams params = new RequestParams();
		params.put("id_", deviceId);
		params.put("imei_", deviceId);
		params.put("imsi_", deviceId);
		params.put("owner_", userInfo_.getName_());
		params.put("sdk_", android.os.Build.VERSION.SDK);
		params.put("release_", android.os.Build.VERSION.RELEASE);
		params.put("brand_", android.os.Build.BRAND);
		params.put("model_", android.os.Build.MODEL);
		params.put("online_", "1");
		params.put("last_time_", BHUtil.getDateTimeStr());
		// params.put("last_time_", location.getTime());
		params.put("longitude_", gps.getWgLon());
		params.put("latitude_", gps.getWgLat());
		params.put("radius_", location.getRadius());
		params.put("addrstr_", location.getAddrStr());
		params.put("line_number_", DeviceUtil.getNativePhoneNumber());

		String url = "mobile/device/saveAa_devices.jhtml";
		ApiHttpClient.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

			}

			@Override
			public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

			}

		});
	}

	public static void logout(Userinfo_ userInfo_, AsyncHttpResponseHandler handler ) {
		String deviceId = DeviceUtil.getDeviceId();
		RequestParams params = new RequestParams();
		params.put("id_", deviceId);
		params.put("online_", "0");
		String url = "logout.jhtml";
		ApiHttpClient.post(url, params,handler);
		ApiHttpClient.cleanCookie();
	}

	/**
	 * 通用统计接口
	 * 
	 * @param userInfo
	 * @param tableInfo
	 * @param qParam
	 * @param asyncHttpResponseHandler
	 */
	public static void commonCount(Userinfo_ userInfo, Rows tableInfo,
			Map<String, String> qParam,
			AsyncHttpResponseHandler asyncHttpResponseHandler) {

		RequestParams params = new RequestParams();
		params.put("count_type_", "jdsq");
		params.put("table_name_", qParam.get("table_name_"));

		if (qParam.get("columns_query_") != null
				&& !"".equals(qParam.get("columns_query_"))
				&& qParam.get("keyword_") != null
				&& !"".equals(qParam.get("keyword_"))) {
			params.put("where_condition_", qParam.get("columns_query_")
					+ " like '%" + qParam.get("keyword_") + "%'");
		} else {
			params.put("where_condition_", "1=1");
		}
		String url = "mis/common/count.jhtml";

		ApiHttpClient.post(url, params, asyncHttpResponseHandler);

	}

}
