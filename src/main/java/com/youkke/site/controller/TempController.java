package com.youkke.site.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
import com.youkke.site.domain.Temptag;
import com.youkke.site.service.TempService;

@Controller
public class TempController {

	@Autowired
	private TempService tempService;
	
	protected String sessuserid = "0042dd84ff4d4246a0e3d06095392a86";
	
	@GetMapping("/temp")
	public String tempHtml(Model model){
		List<Template> templdate = tempService.get(sessuserid);
		model.addAttribute("list", templdate);
		return "temp";
	}
	
	@GetMapping("/temp/input")
	public String inputHtml(){
		return "temp_input";
	}
	
	@GetMapping("/temp/update/{id}")
	public String updateHtml(@PathVariable String id, Model model){
		model.addAttribute("list", tempService.findById(id));
		return "temp_update";
	}
	
	@PostMapping("/temp/input")
	@ResponseBody
	public Map<String, Object> Create(@Valid TempCreateForm tempCreateForm){
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = new Template(sessuserid, tempCreateForm.getTempname(), tempCreateForm.getTemptitle(), "test", tempCreateForm.getTempcontent(), "yes", Double.parseDouble(tempCreateForm.getTempprice()));
		tempService.savetemp(template);
		
		String filePath = "F://test//";
		for(MultipartFile element: tempCreateForm.getFile()){
			JSONArray jsonArray = new JSONArray();
			element.getOriginalFilename();
			String fileName = element.getOriginalFilename();
			//System.err.println(fileName);
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
			
			String regex_html = "\\/template\\/zh_CN\\/";
			String regex_tag = "[a-zA-Z0-9_]*\\.html";
			Pattern pattern_html = Pattern.compile(regex_html);
			Pattern pattern_tag = Pattern.compile(regex_tag);
			Matcher matcher_html = pattern_html.matcher(fileName);
			if(matcher_html.find()){
				Matcher matcher_tag = pattern_tag.matcher(fileName);
				matcher_tag.find();
				//System.err.println(matcher_tag.group().replaceAll(".html", ""));
				File input = new File(filePath + fileName);
				Document doc = null;
				try {
					doc = Jsoup.parse(input, "UTF-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//String content = doc.getElementsByAttribute("yksite").attr("yksite");
				for(int i = 0; i < doc.getElementsByAttribute("yksite").size(); i++){
					jsonArray.add(doc.getElementsByAttribute("yksite").get(i).attr("yksite"));
					//System.err.println(doc.getElementsByAttribute("yksite").get(i).attr("yksite"));
					//System.err.println(matcher_tag.group().replaceAll(".html", ""));
				}
				if(!jsonArray.isEmpty()){
					String pathfile = matcher_tag.group().replaceAll(".html", "");
					Temptag tamptag = new Temptag();
					tamptag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					tamptag.setTagjson(jsonArray.toString());
					tamptag.setFile(pathfile);
					tamptag.setTemplate(template);
					tempService.savetag(tamptag);
				}
			}
		}
		
		return map;
	}
	
	@PostMapping("/temp/update/{id}")
	@ResponseBody
	public Map<String, Object> update(@PathVariable String id, @Valid TempCreateForm tempCreateForm){
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = tempService.findById(id);
		tempService.updateTemp(template, tempCreateForm);
		return map;
	}
	
	@PostMapping("/temp/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable String id){
		Map<String, Object> map = new HashMap<String, Object>();
		tempService.deleteTemp(id);
		map.put("result", "success");
		return map;
	}
//	
//	@PostMapping("/upload")
//	@ResponseBody
//	public void upload(@RequestParam("file") List<MultipartFile> file){
//		String filePath = "F://test//";
//		JSONArray jsonArray = new JSONArray();
//		for(MultipartFile element: file){
//			element.getOriginalFilename();
//			String fileName = element.getOriginalFilename();
//			//System.err.println(fileName);
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
//			String regex_html = "\\/template\\/zh_CN\\/";
//			String regex_tag = "[a-zA-Z0-9_]*\\.html";
//			Pattern pattern_html = Pattern.compile(regex_html);
//			Pattern pattern_tag = Pattern.compile(regex_tag);
//			Matcher matcher_html = pattern_html.matcher(fileName);
//			if(matcher_html.find()){
//				Matcher matcher_tag = pattern_tag.matcher(fileName);
//				matcher_tag.find();
//				//System.err.println(matcher_tag.group().replaceAll(".html", ""));
//				File input = new File(filePath + fileName);
//				Document doc = null;
//				try {
//					doc = Jsoup.parse(input, "UTF-8");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				//String content = doc.getElementsByAttribute("yksite").attr("yksite");
//				for(int i = 0; i < doc.getElementsByAttribute("yksite").size(); i++){
//					jsonArray.add(doc.getElementsByAttribute("yksite").get(i).attr("yksite"));
//					System.err.println(doc.getElementsByAttribute("yksite").get(i).attr("yksite"));
//					System.err.println(matcher_tag.group().replaceAll(".html", ""));
//				}
//			}
//		}
////		Document doc = null;
////		try {
////			doc = Jsoup.connect("http://class.breadem.com/party").get();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		String title = doc.outerHtml();
////		System.err.println(title);
//	}
}
