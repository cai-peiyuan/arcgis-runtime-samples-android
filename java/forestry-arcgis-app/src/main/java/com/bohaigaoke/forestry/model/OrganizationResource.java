package com.bohaigaoke.forestry.model;

import java.io.Serializable;

public class OrganizationResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int organizationid;
	private String tablename;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(int organizationid) {
		this.organizationid = organizationid;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

}
