package com.bentest.spiders.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the amz_departments database table.
 * 
 */
@Entity
@Table(name="amz_departments")
@NamedQuery(name="AmzDepartment.findAll", query="SELECT a FROM AmzDepartment a")
public class AmzDepartment implements Serializable {
	private static final long serialVersionUID = 1L;

	//@Id
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="FROM_HTML_FILE_PATH")
	private String fromHtmlFilePath;
	
	@Column(name="HTML_FILE_PATH")
	private String htmlFilePath;

	@Id
	@Column(name="DEP_ID")
	private String depId;

	@Column(name="DEP_LEVEL")
	private Integer depLevel;

	@Column(name="DEP_STATUS")
	private Integer depStatus;

	@Column(name="PARENT_DEP_ID")
	private String parentDepId;

	@Column(name="PARENT_ID")
	private Long parentId;

	@Column(name="DEP_NAME_CN")
	private String depNameCn;

	@Column(name="DEP_NAME")
	private String depName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	private String url;

	@Column(name="URL_DOMAIN")
	private String urlDomain;
	
	@Column(name="SOFT_NUM")
	private Integer softNum;
	
	@Column(name="DEP_ID_ALL")
	private String depIdAll;
	
	public AmzDepartment() {
	}
	
	public AmzDepartment(String depName, String url, Integer softNum) {
		this.depName = depName;
		this.url = url;
		this.softNum = softNum;
	}
	
	public String toString() {
		return ""
				+ "id=" + id + ","
				+ "depId=" + depId + ","
				+ "depLevel=" + depLevel + ","
				+ "parentDepId=" + parentDepId + ","
				+ "parentId=" + parentId + ","
				+ "depName=" + depName + ","
				+ "depNameCn=" + depNameCn + ","
				+ "urlDomain=" + urlDomain + ","
				+ "url=" + url + ","
				+ "htmlFilePath=" + htmlFilePath + ","
				+ "fromHtmlFilePath=" + fromHtmlFilePath + ","
				+ "depStatus=" + depStatus + ","
				+ "softNum=" + softNum + ","
				+ "createTime=" + createTime + ","
				+ "updateTime=" + updateTime + ","
				;
	}

	public String getDepIdAll() {
		return depIdAll;
	}

	public void setDepIdAll(String depIdAll) {
		this.depIdAll = depIdAll;
	}

	public Integer getSoftNum() {
		return softNum;
	}

	public void setSoftNum(Integer softNum) {
		this.softNum = softNum;
	}

	public String getFromHtmlFilePath() {
		return fromHtmlFilePath;
	}

	public void setFromHtmlFilePath(String fromHtmlFilePath) {
		this.fromHtmlFilePath = fromHtmlFilePath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public Integer getDepLevel() {
		return depLevel;
	}

	public void setDepLevel(Integer depLevel) {
		this.depLevel = depLevel;
	}

	public Integer getDepStatus() {
		return depStatus;
	}

	public void setDepStatus(Integer depStatus) {
		this.depStatus = depStatus;
	}

	public String getParentDepId() {
		return parentDepId;
	}

	public void setParentDepId(String parentDepId) {
		this.parentDepId = parentDepId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDepNameCn() {
		return depNameCn;
	}

	public void setDepNameCn(String depNameCn) {
		this.depNameCn = depNameCn;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlDomain() {
		return urlDomain;
	}

	public void setUrlDomain(String urlDomain) {
		this.urlDomain = urlDomain;
	}

}