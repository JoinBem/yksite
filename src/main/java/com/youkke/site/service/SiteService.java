package com.youkke.site.service;

import java.util.ArrayList;
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
import com.youkke.site.domain.Temptag;
import com.youkke.site.utils.ServiceException;

@Component
@Transactional
public class SiteService {

	@Autowired
	private SiteDao siteDao;
	
	public void save(Site site, JSONArray jsonArray){
		for(int i = 0; i < jsonArray.size(); i++){
			Site currentSite = siteDao.findByDomain(jsonArray.get(i).toString());
			if(currentSite != null){
				boolean status = (currentSite.getStatus() != null && currentSite.getStatus().equals("yes"));
				if(status){
					System.err.println("--------");
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
			Site currentSite = siteDao.findByDomain(siteCreateForm.getDomain().get(i).toString());
			if(currentSite != null){
				boolean status = (currentSite.getStatus() != null && currentSite.getStatus().equals("yes"));
				if(status){
					System.err.println("--------");
					throw new ServiceException("domain.exists", "domain");
				}	
			}
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
	
   public List<Tag> findCurrentTags(String url,String path){
	   List<Tag> tags = new ArrayList<Tag>();
	   Site site = siteDao.findByDomain(url);
	   if(site != null){
		   Temptag temptag = siteDao.findByPath(site.getTemplate(), path);
		   if(temptag != null){
			   tags = temptag.getTags(); 
		   }
	   }
	   return tags;
   }
}
