package com.bentest.spiders.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bentest.spiders.entity.AmzCmdtask;

@Mapper
//@Repository
public interface CmdMapper {

	
	
	/**
	 * 查询指令表
	 */
	@Select("SELECT * FROM amz_cmdtask WHERE ID>#{id} AND CMD_STATUS=0 AND CMD_TYPE IN(#{cmdTypes}) ORDER BY ID ASC ")
	@Results({ 
		@Result(property = "cmdStatus", column = "CMD_STATUS"),
		@Result(property = "cmdText", column = "CMD_TEXT"),
		@Result(property = "cmdType", column = "CMD_TYPE"),
		@Result(property = "createTime", column = "CREATE_TIME"),
		@Result(property = "updateTime", column = "UPDATE_TIME")
			})
	public List<AmzCmdtask> getCmdTask(@Param("id") Integer id, @Param("cmdTypes") String cmdTypes);
	
	/**
	 * 查询指令表
	 */
	@Select("SELECT * FROM amz_cmdtask WHERE CMD_STATUS=3 AND CMD_TYPE IN(#{cmdTypes}) AND DEAL_COUNT <= #{dealCount} ORDER BY ID ASC ")
	@Results({ 
		@Result(property = "cmdStatus", column = "CMD_STATUS"),
		@Result(property = "cmdText", column = "CMD_TEXT"),
		@Result(property = "cmdType", column = "CMD_TYPE"),
		@Result(property = "createTime", column = "CREATE_TIME"),
		@Result(property = "updateTime", column = "UPDATE_TIME")
			})
	public List<AmzCmdtask> getRetryCmdTask(@Param("cmdTypes") String cmdTypes, @Param("dealCount") Integer dealCount);
	
	/**
	 * 查询指令表
	 */
	@Update("UPDATE amz_cmdtask SET CMD_STATUS=#{newCmdStatus},UPDATE_TIME=NOW() WHERE ID=#{id} AND CMD_STATUS=#{oldCmdStatus} ")
	public Integer updateCmdStatus(@Param("id") Integer id, @Param("newCmdStatus") Integer newCmdStatus, @Param("oldCmdStatus") Integer oldCmdStatus);
	
	@Update("UPDATE amz_cmdtask SET CMD_STATUS=#{newCmdStatus},UPDATE_TIME=NOW(),DEAL_COUNT=#{dealCount} WHERE ID=#{id} AND CMD_STATUS=#{oldCmdStatus} ")
	public Integer updateCmdStatus(@Param("id") Integer id, @Param("newCmdStatus") Integer newCmdStatus, @Param("oldCmdStatus") Integer oldCmdStatus, @Param("dealCount") Integer dealCount);
	
	/*@Select("SELECT * FROM amz_cmdtask WHERE CMD_TYPE=#{cmdType}  AND ID>#{id} ORDER BY ID ASC ")
	@Results({ 
		@Result(property = "cmdStatus", column = "CMD_STATUS"),
		@Result(property = "cmdText", column = "CMD_TEXT"),
		@Result(property = "cmdType", column = "CMD_TYPE"),
		@Result(property = "createTime", column = "CREATE_TIME"),
		@Result(property = "updateTime", column = "UPDATE_TIME")
			})
	public List<AmzCmdtask> getCmdTask(@Param("cmdType") Integer cmdType, @Param("id") Integer id);*/
	
}