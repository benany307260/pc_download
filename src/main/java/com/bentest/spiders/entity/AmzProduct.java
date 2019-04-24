package com.bentest.spiders.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the amz_products database table.
 * 
 */
@Entity
@Table(name="amz_products")
@NamedQuery(name="AmzProduct.findAll", query="SELECT a FROM AmzProduct a")
public class AmzProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Integer bsr;
	
	@Column(name="BSR_DEP_NAME")
	private String bsrDepName;
	
	@Column(name="BSR_JSON")
	private String bsrJson;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="FROM_DEP_ID")
	private String fromDepId;
	
	@Column(name="FROM_DEP_NAME")
	private String fromDepName;
	
	@Column(name="FROM_DEP_JSON")
	private String fromDepJson;

	@Column(name="PROD_ASIN")
	private String prodAsin;

	@Column(name="PROD_BRAND")
	private String prodBrand;

	@Column(name="PROD_BRAND_URL")
	private String prodBrandUrl;

	@Column(name="PROD_IMG_URL")
	private String prodImgUrl;

	@Column(name="PROD_TITLE")
	private String prodTitle;

	@Column(name="PROD_PRICE")
	private Double prodPrice;

	@Column(name="PROD_UPC")
	private String prodUpc;

	@Column(name="ASK_NUM")
	private Integer askNum;

	@Column(name="REVIEW_AVG")
	private Double reviewAvg;

	@Column(name="REVIEWS_NUM")
	private Integer reviewsNum;

	@Column(name="SELLER_NAME")
	private String sellerName;

	@Column(name="SELLER_TYPE")
	private Integer sellerType;

	@Column(name="SELLER_URL")
	private String sellerUrl;

	@Column(name="SELLERS_NUM")
	private Integer sellersNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME")
	private Date updateTime;
	
	@Column(name="PROD_AMZCHOICE")
	private Integer prodAmzchoice;
	
	@Column(name="AMZCHOICE_KEY")
	private String amzchoiceKey;
	
	@Column(name="AMZCHOICE_KEY_URL")
	private String amzchoiceKeyUrl;
	
	@Column(name="SHOP_NAME")
	private String shopName;
	
	@Column(name="SHOP_URL")
	private String shopUrl;

	@Column(name="FULFILL_NAME")
	private String fulfillName;
	
	@Column(name="FULFILL_URL")
	private String fulfillUrl;
	
	@Column(name="FBA_TYPE")
	private Integer fbaType;
	
	@Column(name="HTML_FILE_PATH")
	private String htmlFilePath;
	
	@Column(name="PROD_URL")
	private String prodUrl;
	
	@Transient
	private List<AmzProductBsr> productBsrList;

	public AmzProduct() {
	}

	public String getBsrDepName() {
		return bsrDepName;
	}

	public void setBsrDepName(String bsrDepName) {
		this.bsrDepName = bsrDepName;
	}

	public String getFromDepName() {
		return fromDepName;
	}

	public void setFromDepName(String fromDepName) {
		this.fromDepName = fromDepName;
	}

	public String getFulfillName() {
		return fulfillName;
	}

	public void setFulfillName(String fulfillName) {
		this.fulfillName = fulfillName;
	}

	public String getFulfillUrl() {
		return fulfillUrl;
	}

	public void setFulfillUrl(String fulfillUrl) {
		this.fulfillUrl = fulfillUrl;
	}

	public Integer getFbaType() {
		return fbaType;
	}

	public void setFbaType(Integer fbaType) {
		this.fbaType = fbaType;
	}

	public String getBsrJson() {
		return bsrJson;
	}

	public void setBsrJson(String bsrJson) {
		this.bsrJson = bsrJson;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getFromDepJson() {
		return fromDepJson;
	}

	public void setFromDepJson(String fromDepJson) {
		this.fromDepJson = fromDepJson;
	}

	public String getAmzchoiceKey() {
		return amzchoiceKey;
	}

	public void setAmzchoiceKey(String amzchoiceKey) {
		this.amzchoiceKey = amzchoiceKey;
	}

	public String getAmzchoiceKeyUrl() {
		return amzchoiceKeyUrl;
	}

	public void setAmzchoiceKeyUrl(String amzchoiceKeyUrl) {
		this.amzchoiceKeyUrl = amzchoiceKeyUrl;
	}

	public Integer getProdAmzchoice() {
		return prodAmzchoice;
	}

	public void setProdAmzchoice(Integer prodAmzchoice) {
		this.prodAmzchoice = prodAmzchoice;
	}

	public List<AmzProductBsr> getProductBsrList() {
		return productBsrList;
	}

	public void setProductBsrList(List<AmzProductBsr> productBsrList) {
		this.productBsrList = productBsrList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBsr() {
		return bsr;
	}

	public void setBsr(Integer bsr) {
		this.bsr = bsr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFromDepId() {
		return fromDepId;
	}

	public void setFromDepId(String fromDepId) {
		this.fromDepId = fromDepId;
	}

	public String getProdAsin() {
		return prodAsin;
	}

	public void setProdAsin(String prodAsin) {
		this.prodAsin = prodAsin;
	}

	public String getProdBrand() {
		return prodBrand;
	}

	public void setProdBrand(String prodBrand) {
		this.prodBrand = prodBrand;
	}

	public String getProdBrandUrl() {
		return prodBrandUrl;
	}

	public void setProdBrandUrl(String prodBrandUrl) {
		this.prodBrandUrl = prodBrandUrl;
	}

	public String getProdImgUrl() {
		return prodImgUrl;
	}

	public void setProdImgUrl(String prodImgUrl) {
		this.prodImgUrl = prodImgUrl;
	}

	public String getProdTitle() {
		return prodTitle;
	}

	public void setProdTitle(String prodTitle) {
		this.prodTitle = prodTitle;
	}

	public Double getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Double prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getProdUpc() {
		return prodUpc;
	}

	public void setProdUpc(String prodUpc) {
		this.prodUpc = prodUpc;
	}

	public Integer getAskNum() {
		return askNum;
	}

	public void setAskNum(Integer askNum) {
		this.askNum = askNum;
	}

	public Double getReviewAvg() {
		return reviewAvg;
	}

	public void setReviewAvg(Double reviewAvg) {
		this.reviewAvg = reviewAvg;
	}

	public Integer getReviewsNum() {
		return reviewsNum;
	}

	public void setReviewsNum(Integer reviewsNum) {
		this.reviewsNum = reviewsNum;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Integer getSellerType() {
		return sellerType;
	}

	public void setSellerType(Integer sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerUrl() {
		return sellerUrl;
	}

	public void setSellerUrl(String sellerUrl) {
		this.sellerUrl = sellerUrl;
	}

	public Integer getSellersNum() {
		return sellersNum;
	}

	public void setSellersNum(Integer sellersNum) {
		this.sellersNum = sellersNum;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

	public String getProdUrl() {
		return prodUrl;
	}

	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}

}