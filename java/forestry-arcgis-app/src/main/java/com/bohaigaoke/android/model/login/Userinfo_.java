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
public class Userinfo_ {

    private String id_;
    private String account_;
    private String name_;
    private String sex_;
    @JsonProperty("org_id_")
    private String org_id_;
    private String status_;
    private String type_;
    @JsonProperty("extInfo")
    private Extinfo extInfo;
    @JsonProperty("cfgInfo")
    private Cfginfo cfgInfo;
    @JsonProperty("orgInfo")
    private Orginfo orgInfo;
    public void setId_(String id_) {
         this.id_ = id_;
     }
     public String getId_() {
         return id_;
     }

    public void setAccount_(String account_) {
         this.account_ = account_;
     }
     public String getAccount_() {
         return account_;
     }

    public void setName_(String name_) {
         this.name_ = name_;
     }
     public String getName_() {
         return name_;
     }

    public void setSex_(String sex_) {
         this.sex_ = sex_;
     }
     public String getSex_() {
         return sex_;
     }

    public void setOrgId_(String orgId_) {
         this.org_id_ = orgId_;
     }
     public String getOrgId_() {
         return org_id_;
     }

    public void setStatus_(String status_) {
         this.status_ = status_;
     }
     public String getStatus_() {
         return status_;
     }

    public void setType_(String type_) {
         this.type_ = type_;
     }
     public String getType_() {
         return type_;
     }

    public void setExtinfo(Extinfo extinfo) {
         this.extInfo = extinfo;
     }
     public Extinfo getExtinfo() {
         return extInfo;
     }

    public void setCfginfo(Cfginfo cfginfo) {
         this.cfgInfo = cfginfo;
     }
     public Cfginfo getCfginfo() {
         return cfgInfo;
     }

    public void setOrginfo(Orginfo orginfo) {
         this.orgInfo = orginfo;
     }
     public Orginfo getOrginfo() {
         return orgInfo;
     }

}