package com.youkke.site.controller.u;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youkke.site.domain.Template;
import com.youkke.site.service.TempService;

@Controller
public class TemplateController {

	@Autowired
	private TempService tempService;
	
	protected String sessuserid = "0042dd84ff4d4246a0e3d06095392a86";
	
	@GetMapping("/u/temps")
	public String index(Model model){
		List<Template> templdate = tempService.get(sessuserid);
		model.addAttribute("list", templdate);
		return "temp";
	}
	
	@GetMapping("/u/temp/input")
	public String input(){
		return "temp_input";
	}
	
	@PostMapping("/u/temp")
	@ResponseBody
	public Map<String, Object> Create(@Valid TemplateCreateForm tempCreateForm){
		Map<String, Object> map = new HashMap<String, Object>();
		tempService.savetemp(tempCreateForm);
		return map;
	}
	
	@GetMapping("/u/temp/{id}/edit")
	public String edit(@PathVariable String id, Model model){
		model.addAttribute("list", tempService.findById(id));
		return "temp_update";
	}
	
	@PostMapping("/u/temp/{id}")
	@ResponseBody
	public Map<String, Object> update(@PathVariable String id, @Valid TemplateCreateForm tempCreateForm){
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = tempService.findById(id);
		tempService.updateTemp(template, tempCreateForm);
		return map;
	}
	
	@PostMapping("/u/temp/{id}/delete")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable String id){
		Map<String, Object> map = new HashMap<String, Object>();
		tempService.deleteTemp(id);
		map.put("result", "success");
		return map;
	}
}
