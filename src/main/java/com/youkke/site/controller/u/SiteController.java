package com.youkke.site.controller.u;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JFileChooser;
import javax.validation.Valid;
import javax.xml.ws.spi.http.HttpContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
import com.youkke.site.domain.Temptag;
import com.youkke.site.service.SiteService;
import com.youkke.site.service.TempService;
import com.youkke.site.utils.ServiceException;

@Controller
public class SiteController<E> {

	@Autowired
	private SiteService siteService;
	
	protected String sessuserid = "0042dd84ff4d4246a0e3d06095392a86";
	
	
	@GetMapping("/u/")
	public String index(){
		return "admin";
	}
	
	@GetMapping("/u/sites")
	public String sites(Model model){
		List<Site> site = siteService.get(sessuserid);
        model.addAttribute("list", site);
		return "site";
	}
	
	@GetMapping("/u/site/input")
	public String input(){
		return "site_input";
	}
	
	@PostMapping("/u/site")
	@ResponseBody
	public void create(@Valid SiteCreateForm siteCreateForm){
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(siteCreateForm.getDomain().get(0));
		//Template template = new Template(sessuserid, siteCreateForm.getTempname(), siteCreateForm.getTemptitle(), jsonArray.toString(), siteCreateForm.getTempcontent(), "yes", 10000d);
		Site site = new Site(sessuserid, siteCreateForm.getSitename(), null, jsonArray.toString());
		//tempService.savetemp(template, jsonArray);
		siteService.save(site, jsonArray);
		
//		Site site = siteService.get(sessuserid);
//		System.err.println(site.getTemplate().getTemptag().get(0).getTagjson());
		
//		siteService.delete(sessuserid);
		
	}
	
	@GetMapping("/u/site/{id}/edit")
	public String edit(@PathVariable String id, Model model){
		model.addAttribute("site", siteService.findById(id));
		return "site_update";
	}
	
	@PostMapping("/u/site/{id}")
	@ResponseBody
	public Map<String, Object> update(@PathVariable String id, @Valid SiteCreateForm siteCreateForm){
		Map<String, Object> map = new HashMap<String, Object>();
		Site site = siteService.findById(id);
		siteService.update(site, siteCreateForm);
		return map;
	}
	
	

	@PostMapping("/u/site/{id}/delete")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable String id){
		Map<String, Object> map = new HashMap<String, Object>();
		siteService.delete(id);
		map.put("result", "success");
		return map;
	}
	
}
