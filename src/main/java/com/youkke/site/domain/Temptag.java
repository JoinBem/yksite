package com.youkke.site.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Temptag {
	@Id
	private String id;
	private String file;
	private String tagjson;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JsonBackReference
	private Template template;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getTagjson() {
		return tagjson;
	}
	public void setTagjson(String tagjson) {
		this.tagjson = tagjson;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	
	
	
}
