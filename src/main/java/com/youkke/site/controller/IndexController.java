package com.youkke.site.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.youkke.site.domain.Site;
import com.youkke.site.domain.Tag;
import com.youkke.site.service.SiteService;


@Controller
public class IndexController {
	@Autowired
	private SiteService siteService;
	
	@GetMapping("/*")
	public String test(HttpServletRequest request, Model model){
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
