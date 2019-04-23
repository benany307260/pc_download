package com.montnets.pc_service.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the amz_cmdtask database table.
 * 
 */
@Entity
@Table(name="amz_cmdtask")
@NamedQuery(name="AmzCmdtask.findAll", query="SELECT a FROM AmzCmdtask a")
public class AmzCmdtask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="CMD_STATUS")
	private Integer cmdStatus;

	@Column(name="CMD_TEXT")
	private String cmdText;

	@Column(name="CMD_TYPE")
	private Integer cmdType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	public AmzCmdtask() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 指令状态。0-未处理；1-处理中；2-处理成功；3-处理失败
	 * @return
	 */
	public Integer getCmdStatus() {
		return cmdStatus;
	}

	/**
	 * 指令状态。0-未处理；1-处理中；2-处理成功；3-处理失败
	 * @param cmdStatus
	 */
	public void setCmdStatus(Integer cmdStatus) {
		this.cmdStatus = cmdStatus;
	}

	public String getCmdText() {
		return cmdText;
	}

	public void setCmdText(String cmdText) {
		this.cmdText = cmdText;
	}

	/**
	 * 指令类型。100-下载根类目html；101-处理根类目html；102-下载子类目html；103-处理子类目html
	 * @return
	 */
	public Integer getCmdType() {
		return cmdType;
	}

	/**
	 * 指令类型。100-下载根类目html；101-处理根类目html；102-下载子类目html；103-处理子类目html
	 * @param cmdType
	 */
	public void setCmdType(Integer cmdType) {
		this.cmdType = cmdType;
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