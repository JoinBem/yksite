package com.youkke.site.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
import com.youkke.site.domain.Temptag;

@Component
@Transactional
public class TempDao {

	@PersistenceContext
	private EntityManager entityManager;	//实体管理器
	
	public Session getSession(){
		return entityManager.unwrap(Session.class);
	}
	
	public void savetag(Temptag tag){
		getSession().save(tag);
	}
	
	public void savetemp(Template temp){
		getSession().save(temp);
	}
	
	public void updateTag(Temptag tag){
		getSession().update(tag);
	}
	
	public void updateTemp(Template temp){
		getSession().update(temp);
	}
}
