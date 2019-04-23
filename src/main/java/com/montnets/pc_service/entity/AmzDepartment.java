package com.montnets.pc_service.entity;

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

	@Column(name="DATA_SRC_URL")
	private String dataSrcUrl;
	
	@Column(name="DATA_TAR_URL")
	private String dataTarUrl;

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

	@Column(name="SHOW_NAME_CN")
	private String showNameCn;

	@Column(name="SHOW_NAME_EN")
	private String showNameEn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	private String url;

	@Column(name="URL_DOMAIN")
	private String urlDomain;
	
	public AmzDepartment() {
	}
	
	public String toString() {
		return ""
				+ "id=" + id + ","
				+ "depId=" + depId + ","
				+ "depLevel=" + depLevel + ","
				+ "parentDepId=" + parentDepId + ","
				+ "parentId=" + parentId + ","
				+ "showNameEn=" + showNameEn + ","
				+ "showNameCn=" + showNameCn + ","
				+ "urlDomain=" + urlDomain + ","
				+ "url=" + url + ","
				+ "dataSrcUrl=" + dataSrcUrl + ","
				+ "dataTarUrl=" + dataTarUrl + ","
				+ "depStatus=" + depStatus + ","
				+ "createTime=" + createTime + ","
				+ "updateTime=" + updateTime + ","
				;
	}

	public String getDataTarUrl() {
		return dataTarUrl;
	}

	public void setDataTarUrl(String dataTarUrl) {
		this.dataTarUrl = dataTarUrl;
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

	public String getDataSrcUrl() {
		return dataSrcUrl;
	}

	public void setDataSrcUrl(String dataSrcUrl) {
		this.dataSrcUrl = dataSrcUrl;
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

	public String getShowNameCn() {
		return showNameCn;
	}

	public void setShowNameCn(String showNameCn) {
		this.showNameCn = showNameCn;
	}

	public String getShowNameEn() {
		return showNameEn;
	}

	public void setShowNameEn(String showNameEn) {
		this.showNameEn = showNameEn;
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