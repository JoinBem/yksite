package com.youkke.site.service;

import javax.el.ELException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.youkke.site.dao.TempDao;
import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
import com.youkke.site.domain.Temptag;

@Component
@Transactional
public class TempService {

	@Autowired
	private TempDao tempDao;
	
	public void savetag(Temptag tag){
		tempDao.savetag(tag);
	}
	
	public void savetemp(Template temp, JSONArray jsonArray){
		System.err.println(temp.getPath());
        for (int i = 0; i < jsonArray.size(); i++) {
            Template template = tempDao.getTempPath(jsonArray.get(i).toString());
            if(null != template){
            	System.err.println("-----------");
            	throw new ELException("a");
            }
        }
        System.err.println("-----------success");
        tempDao.savetemp(temp);
	}
	
	public void updateTag(Temptag tag){
		tempDao.updateTag(tag);
	}
	
	public void updateTemp(Template temp){
		tempDao.updateTemp(temp);
	}
}
