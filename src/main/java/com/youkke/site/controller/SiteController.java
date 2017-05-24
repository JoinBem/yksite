package com.youkke.site.controller;

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
	
	@Autowired
	private TempService tempService;
	
	protected String sessuserid = "0042dd84ff4d4246a0e3d06095392a86";
	
	
	@GetMapping("/index")
	public String testHtml(){
		return "index";
	}
	
	@GetMapping("/admin")
	public String adminHtml(Model model){
		List<Site> site = siteService.get(sessuserid);
        model.addAttribute("list", site);
		return "admin";
	}
	
	@GetMapping("/site/input")
	public String inputHtml(){
		return "site_input";
	}
	
	@GetMapping("/site/update/{id}")
	public String updateHtml(@PathVariable String id, Model model){
		model.addAttribute("site", siteService.findById(id));
		return "site_update";
	}
	
	@PostMapping("/site/update/{id}")
	@ResponseBody
	public Map<String, Object> update(@PathVariable String id, @Valid SiteCreateForm siteCreateForm){
		Map<String, Object> map = new HashMap<String, Object>();
		Site site = siteService.findById(id);
		siteService.update(site, siteCreateForm);
		return map;
	}
	
	@PostMapping("/site/input")
	@ResponseBody
	public void create(@Valid SiteCreateForm siteCreateForm){
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(siteCreateForm.getDomain().get(0));
		//Template template = new Template(sessuserid, siteCreateForm.getTempname(), siteCreateForm.getTemptitle(), jsonArray.toString(), siteCreateForm.getTempcontent(), "yes", 10000d);
		Site site = new Site(sessuserid, siteCreateForm.getSitename(), null, jsonArray.toString());
		//tempService.savetemp(template, jsonArray);
		siteService.save(site);
		
//		Site site = siteService.get(sessuserid);
//		System.err.println(site.getTemplate().getTemptag().get(0).getTagjson());
		
//		siteService.delete(sessuserid);
		
	}

	@PostMapping("/site/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable String id){
		Map<String, Object> map = new HashMap<String, Object>();
		siteService.delete(id);
		map.put("result", "success");
		return map;
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public void upload(@RequestParam("file") List<MultipartFile> file){
		String filePath = "F://test//";
		for(MultipartFile element: file){
			element.getOriginalFilename();
			String fileName = element.getOriginalFilename();
			System.err.println(fileName);
			File dest = new File(filePath + fileName);
			// 检测是否存在目录
	        if (!dest.getParentFile().exists()) {
	            dest.getParentFile().mkdirs();
	        }
			try {
				element.transferTo(dest);
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			File input = new File(filePath + fileName);
			Document doc = null;
			try {
				doc = Jsoup.parse(input, "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String content = doc.getElementsByAttribute("wxkey").html();
			
			System.err.println(content);
		}
//		Document doc = null;
//		try {
//			doc = Jsoup.connect("http://class.breadem.com/party").get();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String title = doc.outerHtml();
//		System.err.println(title);
	}
}
