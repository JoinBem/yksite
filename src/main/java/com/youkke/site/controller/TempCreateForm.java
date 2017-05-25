package com.youkke.site.controller;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class TempCreateForm {
	private String tempname;
	private String temptitle;
	private String tempcontent;
	private String tempprice;
	private List<MultipartFile> file;
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
	public String getTempprice() {
		return tempprice;
	}
	public void setTempprice(String tempprice) {
		this.tempprice = tempprice;
	}
	public List<MultipartFile> getFile() {
		return file;
	}
	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
	
	
}
