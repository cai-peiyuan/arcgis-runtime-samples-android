package com.bohaigaoke.forestry;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bohaigaoke.android.Constants;
import com.bohaigaoke.android.model.login.LoginJson;
import com.bohaigaoke.android.util.JsonUtil;
import com.bohaigaoke.android.util.StringUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.oschina.app.AppConfig;
import net.oschina.app.api.ApiHttpClient;
import net.oschina.app.api.remote.OSChinaApi;
import net.oschina.app.util.TDevice;
import net.oschina.app.util.TLog;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.protocol.HttpContext;


/**
 * 登录页面
 * Created by Administrator on 2017/6/5.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    protected static final String TAG = LoginActivity.class.getSimpleName();
    private Button btn_login;
    private Button btn_login_reset;
    private ImageView img_setting;

    private EditText et_lgname;
    private EditText et_lgpassword;
    private String lgname;
    private String lgpassword;
    private String userId;
    private String conpany;
    private ProgressDialog progress_login;
    private String api_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();

        initSysParam();

        progress_login = new ProgressDialog(LoginActivity.this);
        progress_login.setTitle("正在登录");
        progress_login.setMessage("loading......");
        progress_login.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress_login.setCancelable(true);
        et_lgname.setText(AppContext.getInstance().getProperty("user.account"));
        //et_lgpassword.setText(CyptoUtils.decode("oschinaApp", AppContext.getInstance().getProperty("user.pwd")));
    }


    /**
     * 初始化系统参数
     */
    private void initSysParam() {
        api_url = AppContext.getInstance().getProperty("sys.api_url");
        if (api_url == null) {
            api_url = ApiHttpClient.API_URL;
        } else {
            ApiHttpClient.API_URL = api_url;
        }
        String page_size = AppContext.getInstance().getProperty("sys.page_size");
        if (page_size != null) {
            Constants.QUERY_PAGE_SIZE = Integer.valueOf(page_size);
        }

        String input_main_border_color = AppContext.getInstance().getProperty("sys.input_main_border_color");
        if (input_main_border_color != null) {
            Constants.LINECOLOR_STR = input_main_border_color;
            Constants.LINECOLOR = Color.parseColor(input_main_border_color);
        }

    }

    private void initListener() {
        img_setting.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_login_reset.setOnClickListener(this);
    }

    private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login_reset = (Button) findViewById(R.id.btn_login_reset);
        et_lgname = (EditText) findViewById(R.id.et_lgname);
        et_lgpassword = (EditText) findViewById(R.id.et_lgpassword);
        img_setting = (ImageView) findViewById(R.id.img_setting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                handleLogin();
                break;
            case R.id.btn_login_reset:
                et_lgname.setText("");
                et_lgpassword.setText("");
                finish();
                break;
            case R.id.img_setting:
                showSettingDialog();
                break;
        }
    }

    /**
     * 显示设置服务地址对话框
     */
    private void showSettingDialog() {

        final EditText et = new EditText(this);
        et.setText(api_url);
        new AlertDialog.Builder(this).setTitle("设置服务地址")
                .setIcon(R.drawable.route_nav_setting)
                .setView(et)
                .setPositiveButton("确定", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        AppContext.showToastShort(R.string.api_url_valid);
                        AppContext.getInstance().setProperty("sys.api_url", input);
                        ApiHttpClient.API_URL = input;
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 登录
     */
    private void handleLogin() {
        et_lgname.setText("root");
        et_lgpassword.setText("1");

        if (!prepareForLogin()) {
            return;
        }
        lgname = et_lgname.getText().toString();
        lgpassword = et_lgpassword.getText().toString();
        progress_login = ProgressDialog.show(LoginActivity.this, "正在登录", "正在验证用户名密码...", true, true, new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {
                ApiHttpClient.cancelAll(LoginActivity.this);
                Toast.makeText(getApplication(), "取消登录", Toast.LENGTH_SHORT).show();
            }
        });
        OSChinaApi.login(lgname, lgpassword, mLoginHandler);
    }

    /**
     * 登录检查
     *
     * @return
     */
    private boolean prepareForLogin() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return false;
        }
        String uName = et_lgname.getText().toString();
        if (StringUtils.isEmpty(uName)) {
            AppContext.showToastShort(R.string.tip_please_input_username);
            et_lgname.requestFocus();
            return false;
        }
        // 去除邮箱正确性检测
        // if (!StringUtils.isEmail(uName)) {
        // AppContext.showToastShort(R.string.tip_illegal_email);
        // mEtUserName.requestFocus();
        // return false;
        // }
        String pwd = et_lgpassword.getText().toString();
        if (StringUtils.isEmpty(pwd)) {
            AppContext.showToastShort(R.string.tip_please_input_password);
            et_lgpassword.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 登录回调函数
     */
    private final AsyncHttpResponseHandler mLoginHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            try {
                AsyncHttpClient client = ApiHttpClient.getHttpClient();
                HttpContext httpContext = client.getHttpContext();
                CookieStore cookies = (CookieStore) httpContext.getAttribute(HttpClientContext.COOKIE_STORE);
                if (cookies != null) {
                    String tmpcookies = "";
                    boolean hasJsessionId = false;
                    for (Cookie c : cookies.getCookies()) {
                        String cname = c.getName();
                        String cvalue = c.getValue();
                        if ("JSESSIONID".equalsIgnoreCase(cname)) {
                            if (!hasJsessionId) {
                                tmpcookies += (cname + "=" + cvalue) + ";";
                                hasJsessionId = true;
                            }
                        } else {
                            tmpcookies += (cname + "=" + cvalue) + ";";
                        }
                    }
                    TLog.log(TAG, "cookies:" + tmpcookies);
                    AppContext.getInstance().setProperty(AppConfig.CONF_COOKIE, tmpcookies);
                    // ApiHttpClient.setCookie(ApiHttpClient.getCookie(AppContext.getInstance()));
                    ApiHttpClient.setCookie(tmpcookies);
                    //HttpConfig.sCookie = tmpcookies;
                }
                String resStr = new String(bytes);
                TLog.log(TAG, "resStr:" + resStr);
                LoginJson loginJson = JsonUtil.fromJson(resStr, LoginJson.class);

                if (loginJson.getAppcode() == 1) {
                    AppContext.getInstance().saveUserInfo(loginJson.getUserinfo_());
                    AppContext.getInstance().setUserInfo(loginJson.getUserinfo_());
                    Toast.makeText(getApplication(), "用户[" + loginJson.getUserinfo_().getName_() + "]登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (loginJson.getAppcode() == -5) {
                    appAlert(loginJson.getAppmsg());
                } else if (loginJson.getAppcode() == -4) {
                    appAlert(loginJson.getAppmsg());
                } else if (loginJson.getAppcode() == -3) {
                    appAlert(loginJson.getAppmsg());
                } else if (loginJson.getAppcode() == -2) {
                    appAlert(loginJson.getAppmsg());
                } else if (loginJson.getAppcode() == -1) {
                    appAlert(loginJson.getAppmsg());
                } else {
                    Toast.makeText(getApplication(), loginJson.getAppmsg(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(i, headers, bytes, e);
            }
            progress_login.dismiss();
        }

        @Override
        public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
            String resStr = new String(bytes);
            TLog.log(TAG, "resStr:" + resStr);
            final AlertDialog.Builder normalDialog = new AlertDialog.Builder(
                    LoginActivity.this);
            normalDialog.setIcon(R.mipmap.warning_blue);
            normalDialog.setTitle("登录错误");
            normalDialog.setMessage(throwable.getMessage());
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
            );
            // 显示
            normalDialog.show();
            progress_login.dismiss();
        }


    };

    /**
     * 提示框
     *
     * @param appmsg
     */
    private void appAlert(String appmsg) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(appmsg)
                /*.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })*/
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();
    }

    private long lastClickTime = 0;

    @Override
    public void onBackPressed() {
        if (lastClickTime <= 0) {
            Toast.makeText(this, "再按一次后退键退出应用", Toast.LENGTH_SHORT).show();
            lastClickTime = System.currentTimeMillis();
        } else {
            long currentClickTime = System.currentTimeMillis();
            if (currentClickTime - lastClickTime < 2000) {
                finish();
            } else {
                Toast.makeText(this, "再按一次后退键退出应用", Toast.LENGTH_SHORT).show();
                lastClickTime = System.currentTimeMillis();
            }
        }
    }

	/*protected int mFinishCount = 0;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mFinishCount = 0;
		return super.dispatchTouchEvent(ev);
	}
	@Override
	public void finish() {
		mFinishCount++;
		if (mFinishCount == 1) {
			Toast.makeText(this, "再按一次退出！", Toast.LENGTH_SHORT).show();
		} else if (mFinishCount == 2) {
			super.finish();
		}
	}*/

    /*
    public class LoginAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progress_login.show();
        }

        @Override
        protected String doInBackground(String... params) {
        	try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            String result = null;
            String response ;//= LoginUntil.login(params[0], params[1]);
            response = "{\"success\":true,\"data\":{\"company\":\"花溪区农房管理处\",\"name\":\"张三\",\"userid\":\"asdferdfasdfsdfa\"},\"resMsg\":null}";
            if ("fail".equals(response)) {
                result = response;
            } else {
                JSONTokener jsonParser = new JSONTokener(response);
                JSONObject jsonObject;
                try {
                    jsonObject = (JSONObject) jsonParser.nextValue();
                    result = jsonObject.getString("success");
                    JSONObject jsonObject_loginUser = jsonObject.getJSONObject("data");
                    lgname = jsonObject_loginUser.getString("name");
                    userId = jsonObject_loginUser.getString("userid");
                    conpany = jsonObject_loginUser.getString("company");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if ("true".equals(result)) {

                Toast.makeText(getApplication(), "登陆成功", Toast.LENGTH_LONG).show();

                UserBean userBean = new UserBean();
                userBean.setLoginName(lgname);
                userBean.setCompany(conpany);
                userBean.setUserId(userId);
              //  AppContext.getInstance().setUserBean(userBean);
                ConfigLoader configLoader = new ConfigLoader();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                String loginName = et_lgname.getText().toString();
                //保存登录用户名
                configLoader.saveUserName(loginName);

                startActivity(intent);

            } else if ("wrong".equals(result)) {
                Toast.makeText(getApplication(), "用户名或密码错误", Toast.LENGTH_LONG).show();
            } else if ("fail".equals(result)) {
                Toast.makeText(getApplication(), "网络或服务器异常", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "登陆失败", Toast.LENGTH_LONG).show();
            }
            progress_login.dismiss();
        }


    }*/


}

/**
 * var login_failure = {
 * "appmsg" : "some error msg",
 * "appcode" : -3,
 * "_default_boolean_a" : false
 * }
 * var login_success = {
 * "userinfo_" : {
 * "id_" : "fa04db9dd2f54d61b0c8202a25de2dc6",
 * "account_" : "root",
 * "name_" : "超级用户",
 * "sex_" : "1",
 * "org_id_" : "1771",
 * "status_" : "1",
 * "type_" : "2",
 * "extInfo" : {
 * "id_" : "fa04db9dd2f54d61b0c8202a25de2dc6",
 * "email_" : "307916217@qq.com",
 * "fixed_phone_" : "01082343689",
 * "mobile_phone_" : "18630379822",
 * "address_" : "北四环西路",
 * "zip_" : "262114450@qq.com",
 * "birthday_" : "2016-08-30",
 * "idno_" : "1303231988020170252",
 * "qq_" : "307916217",
 * "remark_" : "超级用户拥有系统最高权限。",
 * "bytearray_id_" : "100000060"
 * },
 * "cfgInfo" : {
 * "id_" : "fa04db9dd2f54d61b0c8202a25de2dc6",
 * "theme_" : "classic",
 * "skin_" : "neptune",
 * "is_show_top_nav_" : "true",
 * "navbar_btn_style_" : "icon",
 * "tab_focus_color_" : "#0099AA",
 * "nav_tab_index_" : "1"
 * },
 * "orgInfo" : {
 * "id_" : "1771",
 * "cascade_id_" : "0.001.009.001",
 * "name_" : "沂源县公安局",
 * "parent_id_" : "1770",
 * "pareant_name_" : "淄博市公安局",
 * "is_leaf_" : "0",
 * "is_auto_expand_" : "0",
 * "status_" : "1",
 * "type_" : "1",
 * "biz_code_" : "370323000000",
 * "create_time_" : "2016-01-19 15:19:44",
 * "creater_id_" : "1",
 * "sort_no_" : 1
 * }
 * },
 * "appcode" : 1,
 * "appmsg" : "some error msg",
 * "_default_boolean_a" : false
 * }
 */
