 package com.youkke.site.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.exceptions.TemplateInputException;

import com.youkke.site.domain.Tag;
import com.youkke.site.service.SiteService;


@Controller
public class IndexController {
	@Autowired
	private SiteService siteService;
	
	@GetMapping(value = "/{provinceId:[a-zA-Z_]{0,10}}")
	public String test(HttpServletRequest request, Model model) throws Exception {
        String domain =request.getServerName(); 
		String path = request.getRequestURI().replaceAll("/", "");
		List<Tag> tags = siteService.findCurrentTags(domain, path);
		if(tags != null){
			for(Tag item : tags){
				System.err.println(item.getName());
				System.err.println(item.getNumber());
			}
		}
		return "index";
	}
	
}
