package com.youkke.site.domain;

public class Tag {
	private String code;
	private String name;
	private Integer number;
	
	public Tag(String code) {
		this.code = code;
		//切割
		//。。。。。。。
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
}
