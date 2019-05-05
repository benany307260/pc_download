package com.bentest.spiders.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bentest.spiders.httppool.HttpPoolManager;

@Component
@Order(20)
public class InitHttpPool implements ApplicationRunner {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			boolean result = HttpPoolManager.getInstance().initHttpPool();
			if(result) {
				log.info("初始化HttpPool，成功。");
			}else {
				log.info("初始化HttpPool，失败。");
			}
		} catch (Exception e) {
			log.error("初始化HttpPool，异常。", e);
		}
	}
}