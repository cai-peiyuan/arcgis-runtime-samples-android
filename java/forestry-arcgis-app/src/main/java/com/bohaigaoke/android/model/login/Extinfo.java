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
public class Extinfo {

    private String id_;
    private String email_;
    @JsonProperty("fixed_phone_")
    private String fixed_phone_;
    @JsonProperty("mobile_phone_")
    private String mobile_phone_;
    private String address_;
    private String zip_;
    private String birthday_;
    private String idno_;
    private String qq_;
    private String remark_;
    @JsonProperty("bytearray_id_")
    private String bytearray_id_;
    public void setId_(String id_) {
         this.id_ = id_;
     }
     public String getId_() {
         return id_;
     }

    public void setEmail_(String email_) {
         this.email_ = email_;
     }
     public String getEmail_() {
         return email_;
     }

    public void setFixedPhone_(String fixedPhone_) {
         this.fixed_phone_ = fixedPhone_;
     }
     public String getFixedPhone_() {
         return fixed_phone_;
     }

    public void setMobilePhone_(String mobilePhone_) {
         this.mobile_phone_ = mobilePhone_;
     }
     public String getMobilePhone_() {
         return mobile_phone_;
     }

    public void setAddress_(String address_) {
         this.address_ = address_;
     }
     public String getAddress_() {
         return address_;
     }

    public void setZip_(String zip_) {
         this.zip_ = zip_;
     }
     public String getZip_() {
         return zip_;
     }

    public void setBirthday_(String birthday_) {
         this.birthday_ = birthday_;
     }
     public String getBirthday_() {
         return birthday_;
     }

    public void setIdno_(String idno_) {
         this.idno_ = idno_;
     }
     public String getIdno_() {
         return idno_;
     }

    public void setQq_(String qq_) {
         this.qq_ = qq_;
     }
     public String getQq_() {
         return qq_;
     }

    public void setRemark_(String remark_) {
         this.remark_ = remark_;
     }
     public String getRemark_() {
         return remark_;
     }

    public void setBytearrayId_(String bytearrayId_) {
         this.bytearray_id_ = bytearrayId_;
     }
     public String getBytearrayId_() {
         return bytearray_id_;
     }

}