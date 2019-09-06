/**
  * Copyright 2017 aTool.org 
  */
package com.bohaigaoke.android.model.login;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2017-12-06 1:47:54
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Cfginfo {

    private String id_;
    private String theme_;
    private String skin_;
    @JsonProperty("is_show_top_nav_")
    private String is_show_top_nav_;
    @JsonProperty("navbar_btn_style_")
    private String navbar_btn_style_;
    @JsonProperty("tab_focus_color_")
    private String tab_focus_color_;
    @JsonProperty("nav_tab_index_")
    private String nav_tab_index_;
    public void setId_(String id_) {
         this.id_ = id_;
     }
     public String getId_() {
         return id_;
     }

    public void setTheme_(String theme_) {
         this.theme_ = theme_;
     }
     public String getTheme_() {
         return theme_;
     }

    public void setSkin_(String skin_) {
         this.skin_ = skin_;
     }
     public String getSkin_() {
         return skin_;
     }

    public void setIsShowTopNav_(String isShowTopNav_) {
         this.is_show_top_nav_ = isShowTopNav_;
     }
     public String getIsShowTopNav_() {
         return is_show_top_nav_;
     }

    public void setNavbarBtnStyle_(String navbarBtnStyle_) {
         this.navbar_btn_style_ = navbarBtnStyle_;
     }
     public String getNavbarBtnStyle_() {
         return navbar_btn_style_;
     }

    public void setTabFocusColor_(String tabFocusColor_) {
         this.tab_focus_color_ = tabFocusColor_;
     }
     public String getTabFocusColor_() {
         return tab_focus_color_;
     }

    public void setNavTabIndex_(String navTabIndex_) {
         this.nav_tab_index_ = navTabIndex_;
     }
     public String getNavTabIndex_() {
         return nav_tab_index_;
     }

}