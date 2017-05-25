package com.youkke.site.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youkke.site.controller.TempCreateForm;
import com.youkke.site.dao.TempDao;
import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
import com.youkke.site.domain.Temptag;
import com.youkke.site.utils.ServiceException;

@Component
@Transactional
public class TempService {

	@Autowired
	private TempDao tempDao;
	
	public void savetag(Temptag tag){
		tempDao.savetag(tag);
	}
	
	public void savetemp(Template temp, TempCreateForm tempCreateForm){
		String filePath = "F://test//";
		JSONObject jsonObject = new JSONObject();
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
				//group前一定要先find
				matcher_tag.find();
				File input = new File(filePath + fileName);
				Document doc = null;
				try {
					doc = Jsoup.parse(input, "UTF-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i = 0; i < doc.getElementsByAttribute("yksite").size(); i++){
					jsonArray.add(doc.getElementsByAttribute("yksite").get(i).attr("yksite"));
				}
				String pathfile = matcher_tag.group().replaceAll(".html", "");
				jsonObject.put(pathfile, jsonArray.toString());
			}
		}
		Temptag tamptag = new Temptag();
		tamptag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		tamptag.setTagjson(jsonObject.toString());
		tamptag.setTemplate(temp);
		
        tempDao.savetemp(temp);
        tempDao.savetag(tamptag);
	}
	
	public void updateTag(Temptag tag){
		tempDao.updateTag(tag);
	}
	
	public void updateTemp(Template temp, TempCreateForm tempCreateForm){
		temp.setName(tempCreateForm.getTempname());
		temp.setTitle(tempCreateForm.getTemptitle());
		temp.setContent(tempCreateForm.getTempcontent());
		temp.setPrice(Double.parseDouble(tempCreateForm.getTempprice()));
		tempDao.updateTemp(temp);
	}
	
	public List<Template> get(String userid){
		return tempDao.get(userid);
	}
	
	public Template findById(String id){
		return tempDao.findById(id);
	}
	
	public void deleteTemp(String id){
		Template template = tempDao.findById(id);
		tempDao.deleteTemp(template);
	}
}
