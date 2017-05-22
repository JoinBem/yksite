package com.youkke.site.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		Site site = siteService.get(sessuserid);
		JSONObject json = JSON.parseObject(siteService.getDomain(sessuserid));
		JSONArray array = JSON.parseArray(json.get("domain").toString());
		ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            temp.add(array.get(i).toString());
        }
        model.addAttribute("list", site);
        model.addAttribute("domain", temp);
		return "admin";
	}
	
	@GetMapping("/input")
	public String inputHtml(){
		return "input";
	}
	
	@GetMapping("/update")
	public String updateHtml(){
		return "update";
	}
	
	@PostMapping("/input")
	@ResponseBody
	public void create(@Valid SiteCreateForm siteCreateForm){
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(siteCreateForm.getDomain());
		jsonArray.add("www");
		JSONObject json = new JSONObject();
		json.put("domain", jsonArray);
		Template template = new Template(sessuserid, siteCreateForm.getTempname(), siteCreateForm.getTemptitle(), json.toString(), siteCreateForm.getTempcontent(), "yes", 10000d);
		Temptag temptag = new Temptag("index", "{ \"content\": [" +"\"info\", \"goods\", \"photos\"" +"]}", template);
		Site site = new Site(sessuserid, siteCreateForm.getSitename(), null, json.toString(), template);
		tempService.savetemp(template, jsonArray);
		tempService.savetag(temptag);
		siteService.save(site);
		
//		Site site = siteService.get(sessuserid);
//		System.err.println(site.getTemplate().getTemptag().get(0).getTagjson());
		
//		siteService.delete(sessuserid);
		
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public void upload(@RequestParam("file") List<MultipartFile> file){
//		String filePath = "F://test//";
//		for(MultipartFile element: file){
//			element.getOriginalFilename();
//			String fileName = element.getOriginalFilename();
//			File dest = new File(filePath + fileName);
//			// 检测是否存在目录
//	        if (!dest.getParentFile().exists()) {
//	            dest.getParentFile().mkdirs();
//	        }
//			try {
//				element.transferTo(dest);
//			} catch (IllegalStateException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//			File input = new File(filePath + fileName);
//			Document doc = null;
//			try {
//				doc = Jsoup.parse(input, "UTF-8");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Element content = doc.getElementById("nav");
//			System.err.println(content);
//		}
		Document doc = null;
		try {
			doc = Jsoup.connect("http://class.breadem.com/party").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = doc.outerHtml();
		System.err.println(title);
	}
}
