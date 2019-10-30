/**
 * TODO :
 * @author  : yiming 
 * 2016年8月27日 下午4:08:46 / 精致科技 copyright	
 */
package com.edwin.smartdevelop.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.edwin.smartdevelop.core.properties.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import weixin.popular.support.TicketManager;

@Slf4j
@WebListener
public class TicketManagerListener implements ServletContextListener {

    @Autowired
    Config conf;


    @Override
	public void contextInitialized(ServletContextEvent sce) {
        log.info("------------------TicketManagerListener----contextInitialized---------------");
		TicketManager.init(conf.getAppID(), 15, 60 * 119);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
        log.info("------------------TicketManagerListener----contextInitialized---------------");
		TicketManager.destroyed();
	}

}
