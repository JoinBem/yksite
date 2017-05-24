package com.youkke.site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.youkke.site.controller.SiteCreateForm;
import com.youkke.site.dao.SiteDao;
import com.youkke.site.domain.Site;

@Component
@Transactional
public class SiteService {

	@Autowired
	private SiteDao siteDao;
	
	public void save(Site site){
			siteDao.save(site);
	}

	public List<Site> get(String userid){
		return siteDao.get(userid);
	}
	
	public void update(Site site, SiteCreateForm siteCreateForm){
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < siteCreateForm.getDomain().size(); i++){
			jsonArray.add(siteCreateForm.getDomain().get(i));
		}
		site.setName(siteCreateForm.getSitename());
		site.setDomainjson(jsonArray.toString());
		siteDao.update(site);
	}
	
	public void delete(String id){
		Site site = siteDao.findById(id);
		siteDao.delete(site);
	}
	
//	public List<String> getDomain(String userid){
//		return siteDao.getDomain(userid);
//	}
	
	public Site findById(String id){
		return siteDao.findById(id);
	}
}
