/**
  * Copyright 2017 aTool.org 
  */
package com.bohaigaoke.android.model.login;

/**
 * Auto-generated: 2017-12-06 1:47:54
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class LoginJson {

    private Userinfo_ userinfo_;
    private int appcode;
    private String appmsg;
    private boolean _default_boolean_a;
    public void setUserinfo_(Userinfo_ userinfo_) {
         this.userinfo_ = userinfo_;
     }
     public Userinfo_ getUserinfo_() {
         return userinfo_;
     }

    public void setAppcode(int appcode) {
         this.appcode = appcode;
     }
     public int getAppcode() {
         return appcode;
     }

    public void setAppmsg(String appmsg) {
         this.appmsg = appmsg;
     }
     public String getAppmsg() {
         return appmsg;
     }

    public void setDefaultBooleanA(boolean DefaultBooleanA) {
         this._default_boolean_a = DefaultBooleanA;
     }
     public boolean getDefaultBooleanA() {
         return _default_boolean_a;
     }

}