package com.youkke.site.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.youkke.site.dao.SiteDao;
import com.youkke.site.dao.TempDao;
import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
import com.youkke.site.domain.Temptag;

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
	
	public void update(Site site){
		siteDao.update(site);
	}
	
//	public void delete(String id){
//		siteDao.delete(id);
//	}
	
//	public List<String> getDomain(String userid){
//		return siteDao.getDomain(userid);
//	}
	
	public Site findById(String id){
		return siteDao.findById(id);
	}
}
