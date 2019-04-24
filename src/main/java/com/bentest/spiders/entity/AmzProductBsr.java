package com.bentest.spiders.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the amz_product_bsr database table.
 * 
 */
@Entity
@Table(name="amz_product_bsr")
@NamedQuery(name="AmzProductBsr.findAll", query="SELECT a FROM AmzProductBsr a")
public class AmzProductBsr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Column(name="PROD_ID")
	private Long prodId;
	
	@Column(name="PROD_ASIN")
	private String prodAsin;

	private Integer bsr;

	@Column(name="BSR_DEPID")
	private String bsrDepid;

	@Column(name="BSR_DEP_NAME")
	private String bsrDepName;

	@Column(name="BSR_URL")
	private String bsrUrl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME")
	private Date updateTime;
	
	@Column(name="SORT_NUM")
	private Integer sortNum;

	public AmzProductBsr() {
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getProdAsin() {
		return prodAsin;
	}

	public void setProdAsin(String prodAsin) {
		this.prodAsin = prodAsin;
	}

	public Integer getBsr() {
		return bsr;
	}

	public void setBsr(Integer bsr) {
		this.bsr = bsr;
	}

	public String getBsrDepid() {
		return bsrDepid;
	}

	public void setBsrDepid(String bsrDepid) {
		this.bsrDepid = bsrDepid;
	}

	public String getBsrDepName() {
		return bsrDepName;
	}

	public void setBsrDepName(String bsrDepName) {
		this.bsrDepName = bsrDepName;
	}

	public String getBsrUrl() {
		return bsrUrl;
	}

	public void setBsrUrl(String bsrUrl) {
		this.bsrUrl = bsrUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}