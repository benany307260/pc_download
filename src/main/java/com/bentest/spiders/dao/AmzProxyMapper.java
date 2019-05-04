package com.bentest.spiders.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
//@Repository
public interface AmzProxyMapper {

	/**
	 * 查询状态为可用，但过期的代理数量
	 * @return
	 */
	@Select("SELECT COUNT(1) FROM amz_proxy WHERE STATUS=0 AND EXPIRE_TIME < NOW() ")
	public Integer getExpireProxyCount();
	
	/**
	 * 更新过期代理为不可用
	 */
	@Update("UPDATE amz_proxy SET STATUS=1, UPDATE_TIME=NOW() WHERE STATUS=0 AND EXPIRE_TIME < NOW() ")
	public Integer updateProxyNoUse();
	
}