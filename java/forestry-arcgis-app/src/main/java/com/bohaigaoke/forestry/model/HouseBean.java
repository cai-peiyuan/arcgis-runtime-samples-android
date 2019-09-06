package com.bohaigaoke.forestry.model;

import net.tsz.afinal.annotation.sqlite.Table;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
@Table(name = "housebean")
public class HouseBean {

    private int id;//数据编号

    private String objectid;//网格编号

    private String person_houseUser;//户主姓名
    private String person_userTel;//联系电话
    private String person_residenceAddr;//户籍所在地
    private String person_residenceType;//户口性质
    private String person_cardId;//身份证
    private String person_village;//行政村
    private String person_group;//组别

    private String house_buildTime;//房屋修建时间
    private String house_buildddr;//房屋修建地点
    private String house_area_living;//建筑面积——住宅
    private String house_area_subsidiary;//建筑面积——附属用房
    private String house_floor_living;//楼层数——住宅
    private String house_floor_subsidiary;//楼层数——附属用房
    private String house_structure_living;//结构——住宅
    private String house_structure_subsidiary;//结构——附属用房
    private String house_all_base_area;//宅基地用地面积
    private String house_area_allhouse;//房屋占地面积
    private String house_area_land;//家庭承包土地面积
    private String house_procedures;//手续办理情况

    private String name;
    private String sex;
    private String nation;
    private String cardId;
    private String tel;
    private String work;
    private String relationship;

    private String picurlfront;
    private String picurlbehind;
    private String picurlleft;
    private String picurlright;
    private String picurlfront_s;
    private String picurlbehind_s;
    private String picurlleft_s;
    private String picurlright_s;

    private String eastto;
    private String westto;
    private String northto;
    private String southto;


    private String spectial;
    private String collector;
    private String time;
    private String company;

    private List<File> files;

    private String memberInfos;

    private List<Member> list_member_upload;

    public String getEastto() {
        return eastto;
    }

    public void setEastto(String eastto) {
        this.eastto = eastto;
    }

    public String getNorthto() {
        return northto;
    }

    public void setNorthto(String northto) {
        this.northto = northto;
    }

    public String getSouthto() {
        return southto;
    }

    public void setSouthto(String southto) {
        this.southto = southto;
    }

    public String getWestto() {
        return westto;
    }

    public void setWestto(String westto) {
        this.westto = westto;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getMemberInfos() {
        return memberInfos;
    }

    public void setMemberInfos(String memberInfos) {
        this.memberInfos = memberInfos;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getPicurlbehind_s() {
        return picurlbehind_s;
    }

    public void setPicurlbehind_s(String picurlbehind_s) {
        this.picurlbehind_s = picurlbehind_s;
    }

    public String getPicurlfront_s() {
        return picurlfront_s;
    }

    public void setPicurlfront_s(String picurlfront_s) {
        this.picurlfront_s = picurlfront_s;
    }

    public String getPicurlleft_s() {
        return picurlleft_s;
    }

    public void setPicurlleft_s(String picurlleft_s) {
        this.picurlleft_s = picurlleft_s;
    }

    public String getPicurlright_s() {
        return picurlright_s;
    }

    public void setPicurlright_s(String picurlright_s) {
        this.picurlright_s = picurlright_s;
    }

    public String getPerson_residenceType() {
        return person_residenceType;
    }

    public void setPerson_residenceType(String person_residenceType) {
        this.person_residenceType = person_residenceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHouse_buildddr() {
        return house_buildddr;
    }

    public void setHouse_buildddr(String house_buildddr) {
        this.house_buildddr = house_buildddr;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHouse_all_base_area() {
        return house_all_base_area;
    }

    public void setHouse_all_base_area(String house_all_base_area) {
        this.house_all_base_area = house_all_base_area;
    }

    public String getHouse_area_allhouse() {
        return house_area_allhouse;
    }

    public void setHouse_area_allhouse(String house_area_allhouse) {
        this.house_area_allhouse = house_area_allhouse;
    }

    public String getHouse_area_land() {
        return house_area_land;
    }

    public void setHouse_area_land(String house_area_land) {
        this.house_area_land = house_area_land;
    }

    public String getHouse_area_living() {
        return house_area_living;
    }

    public void setHouse_area_living(String house_area_living) {
        this.house_area_living = house_area_living;
    }

    public String getHouse_area_subsidiary() {
        return house_area_subsidiary;
    }

    public void setHouse_area_subsidiary(String house_area_subsidiary) {
        this.house_area_subsidiary = house_area_subsidiary;
    }


    public String getHouse_buildTime() {
        return house_buildTime;
    }

    public void setHouse_buildTime(String house_buildTime) {
        this.house_buildTime = house_buildTime;
    }

    public String getHouse_floor_living() {
        return house_floor_living;
    }

    public void setHouse_floor_living(String house_floor_living) {
        this.house_floor_living = house_floor_living;
    }

    public String getHouse_floor_subsidiary() {
        return house_floor_subsidiary;
    }

    public void setHouse_floor_subsidiary(String house_floor_subsidiary) {
        this.house_floor_subsidiary = house_floor_subsidiary;
    }

    public String getHouse_procedures() {
        return house_procedures;
    }

    public void setHouse_procedures(String house_procedures) {
        this.house_procedures = house_procedures;
    }

    public String getHouse_structure_living() {
        return house_structure_living;
    }

    public void setHouse_structure_living(String house_structure_living) {
        this.house_structure_living = house_structure_living;
    }

    public String getHouse_structure_subsidiary() {
        return house_structure_subsidiary;
    }

    public void setHouse_structure_subsidiary(String house_structure_subsidiary) {
        this.house_structure_subsidiary = house_structure_subsidiary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPerson_cardId() {
        return person_cardId;
    }

    public void setPerson_cardId(String person_cardId) {
        this.person_cardId = person_cardId;
    }

    public String getPerson_group() {
        return person_group;
    }

    public void setPerson_group(String person_group) {
        this.person_group = person_group;
    }

    public String getPerson_houseUser() {
        return person_houseUser;
    }

    public void setPerson_houseUser(String person_houseUser) {
        this.person_houseUser = person_houseUser;
    }

    public String getPerson_residenceAddr() {
        return person_residenceAddr;
    }

    public void setPerson_residenceAddr(String person_residenceAddr) {
        this.person_residenceAddr = person_residenceAddr;
    }

    public String getPerson_userTel() {
        return person_userTel;
    }

    public void setPerson_userTel(String person_userTel) {
        this.person_userTel = person_userTel;
    }

    public String getPerson_village() {
        return person_village;
    }

    public void setPerson_village(String person_village) {
        this.person_village = person_village;
    }

    public String getPicurlbehind() {
        return picurlbehind;
    }

    public void setPicurlbehind(String picurlbehind) {
        this.picurlbehind = picurlbehind;
    }

    public String getPicurlfront() {
        return picurlfront;
    }

    public void setPicurlfront(String picurlfront) {
        this.picurlfront = picurlfront;
    }

    public String getPicurlleft() {
        return picurlleft;
    }

    public void setPicurlleft(String picurlleft) {
        this.picurlleft = picurlleft;
    }

    public String getPicurlright() {
        return picurlright;
    }

    public void setPicurlright(String picurlright) {
        this.picurlright = picurlright;
    }


    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpectial() {
        return spectial;
    }

    public void setSpectial(String spectial) {
        this.spectial = spectial;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public List<Member> getList_member_upload() {
        return list_member_upload;
    }

    public void setList_member_upload(List<Member> list_member_upload) {
        this.list_member_upload = list_member_upload;
    }
}
