package com.youkke.site.controller.u;

import java.io.File;
import java.util.List;

public class SiteCreateForm {
	
	private String sitename;
	private List<String> domain;
	private String template;
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
	
	
}
