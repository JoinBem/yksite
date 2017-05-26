package com.youkke.site.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jayway.jsonpath.internal.Path;

import javassist.expr.NewArray;

@Controller
public class IndexController {
	
	@GetMapping("/*")
	public String test(HttpServletRequest request, Model model){
		System.err.println("---");
//		String domain =request.getServerName(); 
//		String path = request.getRequestURI().replaceAll("/", "");
//		Site site = siteService.findurl(domain);
//		List<Tag> tags = siteService.findurl(domain, path);
		
		
		//StringBuffer url = request.getRequestURL();  

		//String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString();  
//		System.out.println(tempContextUrl);
//		System.out.println(Context);

		/*JSONObject tagjson = JSONObject.parseObject(site.get(0).getTemplate().getTemptag().getTagjson());
		JSONArray tagarray = JSONArray.parseArray(tagjson.get(Context).toString());
		if(!tagarray.isEmpty()){
			for(int i = 0; i < tagarray.size(); i++){
				System.err.println(tagarray.get(i));
			}
		}else{
			System.err.println("noContext");
		}*/
        //model.addAttribute("list", site);
		return "index";
	}
	
}
