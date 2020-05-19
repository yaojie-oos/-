package com.db.sys.entity;

import java.io.Serializable;

public class SysPwd implements Serializable{
	
	private static final long serialVersionUID = 299869050741546450L;
	private String pwd;
	private String newPwd;
	private String cfgPwd;
	private String salt;
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getCfgPwd() {
		return cfgPwd;
	}
	public void setCfgPwd(String cfgPwd) {
		this.cfgPwd = cfgPwd;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
	
}
