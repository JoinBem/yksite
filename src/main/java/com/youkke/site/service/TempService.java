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
	
	public void savetemp(Template temp, JSONArray jsonArray){
		List<String> list = tempDao.getTempPath();
		for(int i = 0; i < list.size(); i++){
			//JSONObject json = JSON.parseObject(list.get(i));
			//JSONArray array = JSON.parseArray(json.get("domain").toString());
			for (int j = 0; j < jsonArray.size(); j++){
				if(list.get(i).contains(jsonArray.get(j).toString())){
					System.err.println("-----------");
					throw new ServiceException("domain.exists", "domain");
				}
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
