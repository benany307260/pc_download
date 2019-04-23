package com.montnets.pc_service.listpage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.montnets.pc_service.entity.AmzProduct;

import cn.hutool.core.util.StrUtil;

@Service
public class ProductListHtmlProcess {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	

	public List<AmzProduct> getListPage(int type, String htmlFilePath){
		if(type == 1) {
			return getListFirstPage(htmlFilePath);
		}
		else if(type == 2) {
			return getListSecondAfter(htmlFilePath);
		}
		else {
			log.error("解析html获取产品列表，未定义的类型。");
			return null;
		}
	}
	
	private List<AmzProduct> getListFirstPage(String htmlFilePath) {
		try
		{
			Document doc = Jsoup.parse( new File(htmlFilePath) , "utf-8" );
		    if(doc == null) {
		    	log.error("解析html获取产品列表，获取不到页面对象。htmlFilePath="+htmlFilePath);
		    	return null;
		    }
		    
		    Elements liEls = doc.select("div#mainResults li");
		    if(liEls == null || liEls.size() < 1) {
		    	return null;
		    }
		    
		    List<AmzProduct> prodList = new ArrayList<>();
		    
		    for(Element liEl : liEls) {
		    	if(liEl == null) {
		    		continue;
		    	}
		    	String asin = liEl.attr("data-asin");
		    	if(StrUtil.isBlank(asin)) {
		    		continue;
		    	}
		    	
		    	Elements aEls = liEl.select("a");
		    	if(aEls == null || aEls.size() < 1) {
		    		continue;
		    	}
		    	for(Element aEl : aEls) {
		    		String href = aEl.attr("href");
		    		if(StrUtil.isBlank(href)) {
		    			continue;
		    		}
		    		if(href.indexOf(asin) < 0) {
		    			continue;
		    		}
		    		AmzProduct product = new AmzProduct();
		    		product.setProdAsin(asin);
		    		product.setProdUrl(href);
		    		prodList.add(product);
		    		break;
		    	}
		    }
		    return prodList;
		} 
		catch (Exception e) 
		{
		    log.error("解析html获取产品列表，异常。path="+htmlFilePath, e);
		    return null;
		}
	}
	
	private List<AmzProduct> getListSecondAfter(String htmlFilePath) {
		try
		{
			Document doc = Jsoup.parse( new File(htmlFilePath) , "utf-8" );
		    if(doc == null) {
		    	log.error("解析html获取产品列表，获取不到页面对象。htmlFilePath="+htmlFilePath);
		    	return null;
		    }
		    
		    Elements aEls = doc.select("span[data-component-type=s-product-image] a");
		    if(aEls == null || aEls.size() < 1) {
		    	return null;
		    }
		    
		    List<AmzProduct> prodList = new ArrayList<>();
		    
		    for(Element aEl : aEls) {
		    	if(aEl == null) {
		    		continue;
		    	}
	    		String href = aEl.attr("href");
	    		if(StrUtil.isBlank(href)) {
	    			continue;
	    		}
	    		AmzProduct product = new AmzProduct();
	    		product.setProdUrl(href);
	    		prodList.add(product);
		    }
		    return prodList;
		} 
		catch (Exception e) 
		{
		    log.error("解析html获取产品列表2，异常。path="+htmlFilePath, e);
		    return null;
		}
	}
	
	
	public static void main(String[] args) {
		try {
			ProductListHtmlProcess html = new ProductListHtmlProcess();
			//String mkdir = "C:/Users/lenovo/git/pc_service/page/%s";
			String mkdir = "F:\\study\\amz\\git\\pc_service\\page\\list-page\\%s";
			
			/*for(int i = 1; i <= 1; i++) {
				//i=11;
				String pageName = "pet-supplies-"+i+".html";
				String path = String.format(mkdir, pageName);
				List<AmzProduct> prodList = html.getListPageFromHtml(path);
				
				for(AmzProduct prod : prodList) {
					System.out.println(prod.getProdAsin()+"---"+prod.getProdUrl());
				}
				
			}*/
			
			String pageName = "pet-supplies-3.html";
			String path = String.format(mkdir, pageName);
			List<AmzProduct> prodList = html.getListSecondAfter(path);
			
			for(AmzProduct prod : prodList) {
				System.out.println(prod.getProdUrl());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
