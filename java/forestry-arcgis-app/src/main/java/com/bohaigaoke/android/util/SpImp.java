package com.bohaigaoke.android.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpImp {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    private String customer_service_phone;
    private String map;


    public SpImp(Context context) {
        sp = context.getSharedPreferences(SpPublic.SP_NAME,
                Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public String getMap() {
        return sp.getString(SpPublic.MAP, "");
    }

    public void setMap(String map) {
        editor.putString(SpPublic.MAP, map).toString();
        editor.commit();
    }

    public String getLanguage() {
        return sp.getString(SpPublic.LANGUAGE, "");
    }

    public void setLanguage(String language) {
        editor.putString(SpPublic.LANGUAGE, language).toString();
        editor.commit();
    }

    public String getDevice() {
        return sp.getString(SpPublic.DEVICE, "");
    }

    public void setDevice(String device) {
        editor.putString(SpPublic.DEVICE, device).toString();
        editor.commit();
    }

    public String getIP() {
        return sp.getString(SpPublic.IP, "");
    }

    public void setIP(String ip) {
        editor.putString(SpPublic.IP, ip).toString();
        editor.commit();
    }

    public Float getLatitude() {
        return sp.getFloat(SpPublic.LATITUDE, 0.0f);
    }

    public void setLatitude(Float latitude) {
        editor.putFloat(SpPublic.LATITUDE, latitude);
        editor.commit();
    }

    public Float getLongitude() {
        return sp.getFloat(SpPublic.LONGITUDE, 0.0f);
    }

    public void setLongitude(Float longitude) {
        editor.putFloat(SpPublic.LONGITUDE, longitude);
        editor.commit();
    }

    public String getMobile_phone() {
        return sp.getString(SpPublic.MOBILE_PHONE, "");
    }

    public void setMobile_phone(String mobile_phone) {
        editor.putString(SpPublic.MOBILE_PHONE, mobile_phone).toString();
        editor.commit();
    }

    public String getPassword() {
        return sp.getString(SpPublic.PASSWORD, "");
    }

    public void setPassword(String password) {
        editor.putString(SpPublic.PASSWORD, password).toString();
        editor.commit();
    }

    public String getLoginName() {
        return sp.getString(SpPublic.LOGIN_NAME, "");
    }

    public void setLoginName(String loginName) {
        editor.putString(SpPublic.LOGIN_NAME, loginName).toString();
        editor.commit();
    }

    public String getUser_name() {
        return sp.getString(SpPublic.USER_NAME, "");
    }

    public void setUser_name(String user_name) {
        editor.putString(SpPublic.USER_NAME, user_name).toString();
        editor.commit();
    }

    public String getToken_id() {
        return sp.getString(SpPublic.TOKEN_ID, "");
    }

    public void setToken_id(String token_id) {
        editor.putString(SpPublic.TOKEN_ID, token_id).toString();
        editor.commit();
    }

    public String getUser_id() {
        return sp.getString(SpPublic.USER_ID, "");
    }

    public void setUser_id(String user_id) {
        editor.putString(SpPublic.USER_ID, user_id).toString();
        editor.commit();
    }

    public String getDepartment_id() {
        return sp.getString(SpPublic.DEPARTMENT_ID, "");
    }

    public void setDepartment_id(String department_id) {
        editor.putString(SpPublic.DEPARTMENT_ID, department_id).toString();
        editor.commit();
    }


    public String getDepartment_name() {
        return sp.getString(SpPublic.DEPARTMENT_NAME, "");
    }

    public void setDepartment_name(String department_name) {
        editor.putString(SpPublic.DEPARTMENT_NAME, department_name).toString();
        editor.commit();
    }

    public String getCustomer_service_phone() {
        return sp.getString(SpPublic.CUSTOMER_SERVICE_PHONE, "");
    }

    public void setCustomer_service_phone(String customer_service_phone) {
        editor.putString(SpPublic.CUSTOMER_SERVICE_PHONE, customer_service_phone).toString();
        editor.commit();
    }

    public int getFatiguedriving() {
        return sp.getInt(SpPublic.FATIGUEDRIVING, 0);
    }

    public void setFatiguedriving(int fatiguedriving) {
        editor.putInt(SpPublic.FATIGUEDRIVING, fatiguedriving);
        editor.commit();
    }

    public int getSpeedingremind() {
        return sp.getInt(SpPublic.SPEEDINGREMIND, 0);
    }

    public void setSpeedingremind(int speedingremind) {
        editor.putInt(SpPublic.SPEEDINGREMIND, speedingremind);
        editor.commit();
    }

    public int getAccidentalarming() {
        return sp.getInt(SpPublic.ACCIDENTALARMING, 0);
    }

    public void setAccidentalarming(int accidentalarming) {
        editor.putInt(SpPublic.ACCIDENTALARMING, accidentalarming);
        editor.commit();
    }

    public int getLocalmessage() {
        return sp.getInt(SpPublic.LOCALMESSAGE, 0);
    }

    public void setLocalmessage(int localmessage) {
        editor.putInt(SpPublic.LOCALMESSAGE, localmessage);
        editor.commit();
    }

    public boolean getIsfirst() {
        return sp.getBoolean(SpPublic.ISFIRST, true);
    }

    public void setIsfirst(boolean isfirst) {
        editor.putBoolean(SpPublic.ISFIRST, isfirst);
        editor.commit();
    }
}
