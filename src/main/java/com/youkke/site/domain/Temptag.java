package com.youkke.site.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Temptag {
	@Id
	private String id;
	private String file;
	private String path;
	private String tagjson;
	//news_10
	@Transient
	private List<Tag> tags;
	
	public List<Tag> getTags() {
		try {
			tags = new ArrayList<>();
			List<String> list = JSON.parseArray(tagjson, String.class);
			for (String code : list) {
				tags.add(new Tag(code));
			}
		}catch(Exception e){}
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	@ManyToOne(cascade = CascadeType.DETACH)
	private Template template;
	public Temptag(){
		
	}
	
	public Temptag(String file, String tagjson, Template template){
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
		this.file = file;
		this.tagjson = tagjson;
		this.template = template;
	}
	
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
