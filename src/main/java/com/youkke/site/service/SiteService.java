package com.youkke.site.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.youkke.site.dao.SiteDao;

@Component
@Transactional
public class SiteService {

	@Autowired
	private SiteDao siteDao;
}
