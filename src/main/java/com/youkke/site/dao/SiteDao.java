package com.youkke.site.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.servlet.http.HttpServletRequest;

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
	
	public List<Site> get(String userid){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Site> criteria = builder.createQuery(Site.class);
		Root<Site> root = criteria.from(Site.class);
		criteria.where(builder.equal(root.get("userid"), userid));
		List<Site> site =  entityManager.createQuery(criteria).getResultList();
		return site;
	}
	

	
	public Site findById(String id){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Site> criteria = builder.createQuery(Site.class);
		Root<Site> root = criteria.from(Site.class);
		criteria.where(builder.equal(root.get("id"), id));
		Site site =  entityManager.createQuery(criteria).getSingleResult();
		return site;
	}
	
	public void delete(Site site){
		getSession().delete(site);
	}
	
	public List<String> getDomain(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> criteria = builder.createQuery(String.class);
		Root<Site> root = criteria.from(Site.class);
		criteria.select(root.get("domainjson"));
		List<String> domain =  entityManager.createQuery(criteria).getResultList();
		return domain;
	}
	
	

	
	
	public List<Site> findurl(String url){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Site> criteria = builder.createQuery(Site.class);
		Root<Site> root = criteria.from(Site.class);
		System.err.println(url);
		criteria.where(builder.like(root.get("domainjson"), "%"+url+"%"));
		List<Site> site =  entityManager.createQuery(criteria).getResultList();
		System.err.println(site);
		return site;
	}

}
