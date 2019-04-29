package com.bentest.spiders.sys;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.bentest.spiders.entity.AmzUA;
import com.bentest.spiders.http.UAUtils;
import com.bentest.spiders.repository.AmzUARespository;

import cn.hutool.core.util.StrUtil;

@Component
public class LoadUAData implements ApplicationRunner {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AmzUARespository amzUARespository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			List<AmzUA> amzUAList = amzUARespository.findAll();
			if(CollectionUtils.isEmpty(amzUAList)) {
				log.error("加载UA数据，从数据库获取数据为空。");
				return;
			}
			log.info("加载UA数据，从数据库获取数据"+amzUAList.size()+"条。");
			
			List<String> uaList = new ArrayList<>();
			for(AmzUA ua : amzUAList) {
				if(ua == null) {
					continue;
				}
				if(StrUtil.isBlank(ua.getUa())) {
					continue;
				}
				uaList.add(ua.getUa());
			}
			
			UAUtils.setUaList(uaList);
			log.info("加载UA数据，"+uaList.size()+"条。");
		} catch (Exception e) {
			log.error("加载UA数据，异常。", e);
		}
	}
}