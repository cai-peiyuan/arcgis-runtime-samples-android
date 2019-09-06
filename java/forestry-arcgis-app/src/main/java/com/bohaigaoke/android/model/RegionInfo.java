package com.bohaigaoke.android.model;

import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.mapping.view.Graphic;

import java.io.Serializable;



public class RegionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5820787666507236702L;
	
	public String id_; //主键
	public String region_id_; //辖区代码
	public String region_name_; // 辖区名称
	public String region_type_; //辖区类型
	public String border_coords_;//边界坐标
	public Graphic graphic; //统计结果
	public Graphic textGraphic;//结果文字
	public int graphicId; //统计结叠加地图的id
	public int textGraphicId;//文字地图id
	public Polygon polygon;
	
	public int color_; //color
	public int cnt_; //count

	public RegionInfo(String id_, String region_id_, String region_name_,
			String region_type_, String border_coords_) {
		super();
		this.id_ = id_;
		this.region_id_ = region_id_;
		this.region_name_ = region_name_;
		this.region_type_ = region_type_;
		this.border_coords_ = border_coords_;
	}

	public RegionInfo() {
		super();
	}

	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getRegion_id_() {
		return region_id_;
	}

	public void setRegion_id_(String region_id_) {
		this.region_id_ = region_id_;
	}

	public String getRegion_name_() {
		return region_name_;
	}

	public void setRegion_name_(String region_name_) {
		this.region_name_ = region_name_;
	}

	public String getRegion_type_() {
		return region_type_;
	}

	public void setRegion_type_(String region_type_) {
		this.region_type_ = region_type_;
	}

	public String getBorder_coords_() {
		return border_coords_;
	}

	public void setBorder_coords_(String border_coords_) {
		this.border_coords_ = border_coords_;
	}

	public Graphic getGraphic() {
		return graphic;
	}

	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}

	public Graphic getTextGraphic() {
		return textGraphic;
	}

	public void setTextGraphic(Graphic textGraphic) {
		this.textGraphic = textGraphic;
	}

	public int getGraphicId() {
		return graphicId;
	}

	public void setGraphicId(int graphicId) {
		this.graphicId = graphicId;
	}

	public int getTextGraphicId() {
		return textGraphicId;
	}

	public void setTextGraphicId(int textGraphicId) {
		this.textGraphicId = textGraphicId;
	}

	public int getColor_() {
		return color_;
	}

	public void setColor_(int color_) {
		this.color_ = color_;
	}

	public int getCnt_() {
		return cnt_;
	}

	public void setCnt_(int cnt_) {
		this.cnt_ = cnt_;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

}
