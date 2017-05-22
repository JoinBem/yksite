package com.youkke.site.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
@Component
@Transactional
public class SiteDao {
	
	@PersistenceContext
	private EntityManager entityManager;	//实体管理器
	
	public Session getSession(){
		return entityManager.unwrap(Session.class);
	}
	
	public void save(Site site){
		getSession().save(site);
	}
	
	public void update(Site site){
		getSession().update(site);
	}
	
	public Site get(String userid){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Site> criteria = builder.createQuery(Site.class);
		Root<Site> root = criteria.from(Site.class);
		criteria.where(builder.equal(root.get("userid"), userid));
		Site site =  entityManager.createQuery(criteria).getSingleResult();
		return site;
	}
	
	public void delete(String id){
		Site site =get(id);
		getSession().delete(site);
	}
	
	public String getDomain(String userid){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> criteria = builder.createQuery(String.class);
		Root<Site> root = criteria.from(Site.class);
		criteria.select(root.get("domainjson"));
		criteria.where(builder.equal(root.get("userid"), userid));
		String domain =  entityManager.createQuery(criteria).getSingleResult();
		return domain;
	}

}
