/**
  * Copyright 2017 aTool.org 
  */
package com.bohaigaoke.android.model.query;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;
/**
 * Auto-generated: 2017-12-06 11:44:29
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class PageResult implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@JsonProperty("_total")
    private int _total;
    @JsonProperty("_rows")
    private List<Rows> _rows;
    public void setTotal(int Total) {
         this._total = Total;
     }
     public int getTotal() {
         return _total;
     }

    public void setRows(List<Rows> Rows) {
         this._rows = Rows;
     }
     public List<Rows> getRows() {
         return _rows;
     }

}