package com.account.persist.model;

import java.util.Date;

/**
 * Created by Summer.Xia on 08/27/2014.
 */
public class Credit extends Base {
	private static final long serialVersionUID = 1L;
	private String cardId;
	private Date transactionDate;
	private Date bookKeepingDate;
	private String transactionDesc;
	private String balanceCurrency;
	private Double balanceMoney;
	private Integer cardTypeId;
	private String cardTypeName;
	private Integer deleted;
	private Integer consumptionType;
	private String consumeID;
	private String consumeName;
	private String demoArea;
	private String recordID;
	
	private String transactionDateStartStr; 
	private Date transactionDateStart;
	private String transactionDateEndStr;
	private Date transactionDateEnd;
	private String[] consumes;
	private String weekName;
	private String year;
	private String month;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getBookKeepingDate() {
		return bookKeepingDate;
	}

	public void setBookKeepingDate(Date bookKeepingDate) {
		this.bookKeepingDate = bookKeepingDate;
	}

	public String getTransactionDesc() {
		return transactionDesc;
	}

	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}

	public String getBalanceCurrency() {
		return balanceCurrency;
	}

	public void setBalanceCurrency(String balanceCurrency) {
		this.balanceCurrency = balanceCurrency;
	}

	public Double getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public Integer getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(Integer cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getConsumptionType() {
		return consumptionType;
	}

	public void setConsumptionType(Integer consumptionType) {
		this.consumptionType = consumptionType;
	}

	public String getConsumeID() {
		return consumeID;
	}

	public void setConsumeID(String consumeID) {
		this.consumeID = consumeID;
	}

	public String getConsumeName() {
		return consumeName;
	}

	public void setConsumeName(String consumeName) {
		this.consumeName = consumeName;
	}

	public String getDemoArea() {
		return demoArea;
	}

	public void setDemoArea(String demoArea) {
		this.demoArea = demoArea;
	}

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	public Date getTransactionDateStart() {
		return transactionDateStart;
	}

	public void setTransactionDateStart(Date transactionDateStart) {
		this.transactionDateStart = transactionDateStart;
	}

	public Date getTransactionDateEnd() {
		return transactionDateEnd;
	}

	public void setTransactionDateEnd(Date transactionDateEnd) {
		this.transactionDateEnd = transactionDateEnd;
	}
	
	public String getTransactionDateStartStr() {
		return transactionDateStartStr;
	}

	public void setTransactionDateStartStr(String transactionDateStartStr) {
		this.transactionDateStartStr = transactionDateStartStr;
	}

	public String getTransactionDateEndStr() {
		return transactionDateEndStr;
	}

	public void setTransactionDateEndStr(String transactionDateEndStr) {
		this.transactionDateEndStr = transactionDateEndStr;
	}

	public String[] getConsumes() {
		return consumes;
	}

	public void setConsumes(String[] consumes) {
		this.consumes = consumes;
	}

	public String getWeekName() {
		return weekName;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
