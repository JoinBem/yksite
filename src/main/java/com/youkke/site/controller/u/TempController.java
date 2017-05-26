package com.youkke.site.controller.u;

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
import com.alibaba.fastjson.JSONObject;
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
		tempService.savetemp(tempCreateForm);
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
}
