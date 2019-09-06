/**
 * Copyright 2017 aTool.org 
 */
package com.bohaigaoke.android.model.count;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2017-12-28 16:34:48
 * 
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class CountResult {

	@JsonProperty("sqResult")
	private List<Sqresult> sqResult;
	@JsonProperty("jdResult")
	private List<Jdresult> jdResult;

	public void setSqresult(List<Sqresult> sqresult) {
		this.sqResult = sqresult;
	}

	public List<Sqresult> getSqresult() {
		return sqResult;
	}

	public void setJdresult(List<Jdresult> jdresult) {
		this.jdResult = jdresult;
	}

	public List<Jdresult> getJdresult() {
		return jdResult;
	}
	
	public int getTotal(){
		int total = 0;
		if(jdResult != null)
		for(Jdresult jd: jdResult){
			total += jd.getCnt_();
		}
		return total;
	}
	
	public int getJdCount(){
		int total = 0;
		if(jdResult != null)
			total = jdResult.size();
		return total;
	}

}