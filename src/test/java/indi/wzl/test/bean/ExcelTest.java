package indi.wzl.test.bean;

import indi.wzl.annotation.DateFormat;
import indi.wzl.annotation.ExcelCell;

import java.util.Date;

public class ExcelTest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3144028426130682043L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 序位
	 */
	private Double sort;

	/**
	 * 劵号
	 */
	private Double ticketNo;

	/**
	 * 劵密码
	 */
	@ExcelCell(2)
	private String ticketPassword;

	/**
	 * 所属兑换劵id
	 */
	private Long ticketId;

	/**
	 * 有效结束日期
	 */
	@ExcelCell(3)
	@DateFormat("yyyy-MM-dd")
	private Date endTime;

	/**
	 * 修改人账号
	 */
	private String modifyUserAcc;

	/**
	 * 创建人账号
	 */
	private String createUserAcc;

	/**
	 * 修改日期
	 */
	private Date modifyTime;

	/**
	 * 创建日期
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSort() {
		return sort;
	}

	public void setSort(Double sort) {
		this.sort = sort;
	}

	public Double getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(Double ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getTicketPassword() {
		return ticketPassword;
	}

	public void setTicketPassword(String ticketPassword) {
		this.ticketPassword = ticketPassword;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserAcc() {
		return modifyUserAcc;
	}

	public void setModifyUserAcc(String modifyUserAcc) {
		this.modifyUserAcc = modifyUserAcc;
	}

	public String getCreateUserAcc() {
		return createUserAcc;
	}

	public void setCreateUserAcc(String createUserAcc) {
		this.createUserAcc = createUserAcc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}
	
}
