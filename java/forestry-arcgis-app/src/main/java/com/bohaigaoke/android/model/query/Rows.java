/**
  * Copyright 2017 aTool.org 
  */
package com.bohaigaoke.android.model.query;

import com.bohaigaoke.aos.core.typewrap.TypeConvertUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;


/**
 * Auto-generated: 2017-12-06 11:44:29
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Rows extends HashMap<String, String> implements  Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/*private String id_;
    private String name_;
    private String enname_;
    @JsonProperty("table_count_")
    private int table_count_;
    public void setId_(String id_) {
         this.id_ = id_;
     }
     public String getId_() {
         return id_;
     }

    public void setName_(String name_) {
         this.name_ = name_;
     }
     public String getName_() {
         return name_;
     }

    public void setEnname_(String enname_) {
         this.enname_ = enname_;
     }
     public String getEnname_() {
         return enname_;
     }

    public void setTableCount_(int tableCount_) {
         this.table_count_ = tableCount_;
     }
     public int getTableCount_() {
         return table_count_;
     }*/
	/**
	 * 以String类型返回属性
	 * 
	 * @param pKey
	 * @return String 键值
	 */
	public String getString(String pKey) {
		Object obj = null;
		try {
			obj = TypeConvertUtil.convert(get(pKey), "String", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj != null)
			return (String) obj;
		else
			return "";
	}
	
	
	/**
	 * 以Integer类型返回属性
	 * 
	 * @param pKey
	 * @return Integer 键值
	 * @throws Exception
	 */
	public Integer getInteger(String pKey) {
		Object obj = null;
		try {
			obj = TypeConvertUtil.convert(get(pKey), "Integer", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj != null)
			return (Integer) obj;
		else
			return null;
	}
	
	/**
	 * 以BigDecimal类型返回属性
	 * 
	 * @param pKey
	 * @return BigDecimal 键值
	 */
	public BigDecimal getBigDecimal(String pKey) {
		Object obj = null;
		try {
			obj = TypeConvertUtil.convert(get(pKey), "BigDecimal", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj != null)
			return (BigDecimal) obj;
		else
			return null;
	}
	/**
	 * 以Double类型返回属性
	 * 
	 * @param pKey
	 * @return Double 键值
	 */
	public Double getDouble(String pKey) {
		Object obj = null;
		try {
			obj = TypeConvertUtil.convert(get(pKey), "Double", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj != null)
			return (Double) obj;
		else
			return null;
	}
}