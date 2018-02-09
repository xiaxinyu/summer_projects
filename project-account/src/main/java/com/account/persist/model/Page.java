package com.account.persist.model;

/**
 * Created by Summer.Xia on 08/31/2015.
 */
public class Page {
	private int pageIndex;
	private int pageSize;
	private int recordBegin;
	private int recordEnd;

	public Page() {
	}

	public Page(int pageIndex, int pageSize) {
		recordBegin = (pageIndex - 1) * pageSize;
		recordEnd = pageIndex * pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordBegin() {
		return recordBegin;
	}

	public void setRecordBegin(int recordBegin) {
		this.recordBegin = recordBegin;
	}

	public int getRecordEnd() {
		return recordEnd;
	}

	public void setRecordEnd(int recordEnd) {
		this.recordEnd = recordEnd;
	}
}
