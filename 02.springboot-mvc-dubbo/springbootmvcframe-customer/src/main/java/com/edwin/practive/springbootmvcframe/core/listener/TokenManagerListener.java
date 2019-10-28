package com.edwin.practive.springbootmvcframe.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.edwin.practive.springbootmvcframe.core.properties.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import weixin.popular.support.TokenManager;

/**
 * 初始化Token监听器
 * @author yiming
 *
 */

@Slf4j
@WebListener
public class TokenManagerListener implements ServletContextListener{

	@Autowired
    Config conf;


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("------------------TokenManagerListener----contextInitialized---------------");
		TokenManager.init(conf.getAppID(), conf.getAppsecret());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("------------------TokenManagerListener----destroyed---------------");
		TokenManager.destroyed();
	}
}
