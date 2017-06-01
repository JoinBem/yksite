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
import com.youkke.site.controller.u.TemplateCreateForm;
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
	
	public void savetemp(TemplateCreateForm tempCreateForm){
		Template temp = new Template(sessuserid, tempCreateForm.getTempname(), tempCreateForm.getTemptitle(), "test", tempCreateForm.getTempcontent(), "yes", Double.parseDouble(tempCreateForm.getTempprice()));
		
		String filePath = "F://test//";
		for(MultipartFile element: tempCreateForm.getFile()){
			JSONArray jsonArray = new JSONArray();
			element.getOriginalFilename();
			String fileName = element.getOriginalFilename();
			//System.err.println(fileName);
			File dest = new File(filePath + fileName);
			System.err.println(filePath + fileName);
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
			
			Pattern patternHtml = Pattern.compile("\\/template\\/zh_CN\\/");
			Matcher matcherHtml = patternHtml.matcher(fileName);
			if(matcherHtml.find()){
				Pattern patternTag = Pattern.compile("[a-zA-Z0-9_]*\\.html");
				Matcher matcherTag = patternTag.matcher(fileName);
				//group前一定要先find
				matcherTag.find();
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
				String path = matcherTag.group().replaceAll(".html", "");
				String file = filePath + fileName;
				if(!jsonArray.isEmpty()){
					Temptag tamptag = new Temptag();
					tamptag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					tamptag.setTagjson(jsonArray.toString());
					tamptag.setPath(path);
					tamptag.setTemplate(temp);
					tamptag.setFile(file);
			        tempDao.savetag(tamptag);
				}
			}

		}

        tempDao.savetemp(temp);
	}
	
	public void updateTag(Temptag tag){
		tempDao.updateTag(tag);
	}
	
	public void updateTemp(Template temp, TemplateCreateForm tempCreateForm){
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
