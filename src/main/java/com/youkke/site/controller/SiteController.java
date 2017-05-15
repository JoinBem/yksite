package com.youkke.site.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
import com.youkke.site.domain.Temptag;
import com.youkke.site.service.SiteService;

@Controller
public class SiteController {

	@Autowired
	private SiteService siteService;
	
	@GetMapping(value = {"/", "/index"})
	public String indexHtml(){
		return "index";
	}
	
	@PostMapping("input")
	@ResponseBody
	public void create(@Valid SiteCreateForm siteCreateForm){
		Site site = new Site();
		Template template = new Template();
		Temptag temptag = new Temptag();
	}
}
