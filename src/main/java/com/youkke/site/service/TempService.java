package com.youkke.site.service;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	
	public void savetemp(Template temp){
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
