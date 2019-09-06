package com.bohaigaoke.forestry.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 结果基本信息
 * @author name
 *
 */
public class BaseResource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String address;
	private String tel;
	private String X;
	private String Y;
	private Map<String, String> attMap;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getX() {
		return X;
	}
	
	public Double getX_D() {
		return Double.parseDouble(X);
	}

	public void setX(String x) {
		X = x;
	}

	public String getY() {
		return Y;
	}
	
	public double getY_D() {
		return Double.parseDouble(Y);
	}

	public void setY(String y) {
		Y = y;
	}

	public Map<String, String> getAttMap() {
		return attMap;
	}

	public void setAttMap(Map<String, String> attMap) {
		this.attMap = attMap;
	}
}
