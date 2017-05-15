package com.youkke.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.youkke.site.service.SiteService;

@Controller
public class IndexController {

	@Autowired
	private SiteService siteService;
	
	@GetMapping(value = {"/", "/index"})
	public String indexHtml(){
		return "index";
	}
	
}
