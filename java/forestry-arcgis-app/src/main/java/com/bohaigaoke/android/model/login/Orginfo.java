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
public class Orginfo {

    private String id_;
    @JsonProperty("cascade_id_")
    private String cascade_id_;
    private String name_;
    @JsonProperty("parent_id_")
    private String parent_id_;
    @JsonProperty("pareant_name_")
    private String pareant_name_;
    @JsonProperty("is_leaf_")
    private String is_leaf_;
    @JsonProperty("is_auto_expand_")
    private String is_auto_expand_;
    private String status_;
    private String type_;
    @JsonProperty("biz_code_")
    private String biz_code_;
    @JsonProperty("create_time_")
    private String create_time_;
    @JsonProperty("creater_id_")
    private String creater_id_;
    @JsonProperty("sort_no_")
    private int sort_no_;
    public void setId_(String id_) {
         this.id_ = id_;
     }
     public String getId_() {
         return id_;
     }

    public void setCascadeId_(String cascadeId_) {
         this.cascade_id_ = cascadeId_;
     }
     public String getCascadeId_() {
         return cascade_id_;
     }

    public void setName_(String name_) {
         this.name_ = name_;
     }
     public String getName_() {
         return name_;
     }

    public void setParentId_(String parentId_) {
         this.parent_id_ = parentId_;
     }
     public String getParentId_() {
         return parent_id_;
     }

    public void setPareantName_(String pareantName_) {
         this.pareant_name_ = pareantName_;
     }
     public String getPareantName_() {
         return pareant_name_;
     }

    public void setIsLeaf_(String isLeaf_) {
         this.is_leaf_ = isLeaf_;
     }
     public String getIsLeaf_() {
         return is_leaf_;
     }

    public void setIsAutoExpand_(String isAutoExpand_) {
         this.is_auto_expand_ = isAutoExpand_;
     }
     public String getIsAutoExpand_() {
         return is_auto_expand_;
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

    public void setBizCode_(String bizCode_) {
         this.biz_code_ = bizCode_;
     }
     public String getBizCode_() {
         return biz_code_;
     }

    public void setCreateTime_(String createTime_) {
         this.create_time_ = createTime_;
     }
     public String getCreateTime_() {
         return create_time_;
     }

    public void setCreaterId_(String createrId_) {
         this.creater_id_ = createrId_;
     }
     public String getCreaterId_() {
         return creater_id_;
     }

    public void setSortNo_(int sortNo_) {
         this.sort_no_ = sortNo_;
     }
     public int getSortNo_() {
         return sort_no_;
     }

}