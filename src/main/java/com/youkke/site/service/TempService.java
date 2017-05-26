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
import com.youkke.site.controller.u.TempCreateForm;
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
	
	protected String sessuserid = "0042dd84ff4d4246a0e3d06095392a86";
	
	public void savetag(Temptag tag){
		tempDao.savetag(tag);
	}
	
	public void savetemp(TempCreateForm tempCreateForm){
		Template temp = new Template(sessuserid, tempCreateForm.getTempname(), tempCreateForm.getTemptitle(), "test", tempCreateForm.getTempcontent(), "yes", Double.parseDouble(tempCreateForm.getTempprice()));
		
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
			
			Pattern pattern_html = Pattern.compile("\\/template\\/zh_CN\\/");
			Pattern pattern_tag = Pattern.compile("[a-zA-Z0-9_]*\\.html");
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
				String path = matcher_tag.group().replaceAll(".html", "");
				if(!jsonArray.isEmpty()){
					Temptag tamptag = new Temptag();
					tamptag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					tamptag.setTagjson(jsonArray.toString());
					tamptag.setPath(path);
					tamptag.setTemplate(temp);
			        tempDao.savetag(tamptag);
				}
			}

		}

        tempDao.savetemp(temp);
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
