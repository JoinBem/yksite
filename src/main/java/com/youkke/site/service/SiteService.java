package com.youkke.site.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.youkke.site.controller.u.SiteCreateForm;
import com.youkke.site.dao.SiteDao;
import com.youkke.site.domain.Site;
import com.youkke.site.domain.Tag;
import com.youkke.site.domain.Template;
import com.youkke.site.utils.ServiceException;

@Component
@Transactional
public class SiteService {

	@Autowired
	private SiteDao siteDao;
	
	public void save(Site site, JSONArray jsonArray){
		List<String> list = siteDao.getDomain();
		for(int i = 0; i < list.size(); i++){
			for (int j = 0; j < jsonArray.size(); j++){
				if(list.get(i).contains(jsonArray.get(j).toString())){
					System.err.println("-----------");
					throw new ServiceException("domain.exists", "domain");
				}
			}
		}
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
	

   
   public Site findurl(String url){
	   return siteDao.findurl(url);
   }
}
