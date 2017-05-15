package com.youkke.site.domain;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Site {
	@Id
	private String id;
	private String userid;
	//private User user;
	private String name;
	private String filepath;
	private String domainjson;
	@OneToOne
	@JoinColumn(name ="template_id")
	private Template template;
	
	public Site(String userid, String name, String filepath, String domainjson){
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
		this.name = name;
		this.filepath = filepath;
		this.domainjson = domainjson;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getDomainjson() {
		return domainjson;
	}
	public void setDomainjson(String domainjson) {
		this.domainjson = domainjson;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	
	
}
