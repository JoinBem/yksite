package com.youkke.site.controller;

import java.io.File;
import java.util.List;

public class SiteCreateForm {
	
	private String sitename;
	private List<String> domain;
	private String template;
	private String tempname;
	private String temptitle;
	private String tempcontent;
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public List<String> getDomain() {
		return domain;
	}
	public void setDomain(List<String> domain) {
		this.domain = domain;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getTempname() {
		return tempname;
	}
	public void setTempname(String tempname) {
		this.tempname = tempname;
	}
	public String getTemptitle() {
		return temptitle;
	}
	public void setTemptitle(String temptitle) {
		this.temptitle = temptitle;
	}
	public String getTempcontent() {
		return tempcontent;
	}
	public void setTempcontent(String tempcontent) {
		this.tempcontent = tempcontent;
	}
	
	
}
