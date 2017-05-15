package com.youkke.site.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Template {
	@Id
	private String id;
	private String userid;
	//private User user;
	private String name;
	private String title;
	private String path;
	private String content;
	private String type;//用户自定义模板，认证后上架销售的模板
	private Double price;
	private Timestamp ctime;
	@OneToMany(mappedBy = "template",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Temptag> temptag;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Timestamp getCtime() {
		return ctime;
	}
	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	public List<Temptag> getTemptag() {
		return temptag;
	}
	public void setTemptag(List<Temptag> temptag) {
		this.temptag = temptag;
	}

	
	
}
