package com.spring.backend.payload.response;

import com.spring.backend.models.User;

import java.util.List;

public class UserRegResponse {

	private long totalCount;
	private int currentPage;
	private int pageSize;
	private List<User> userReg;
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<User> getUserReg() {
		return userReg;
	}
	public void setUserReg(List<User> userReg) {
		this.userReg = userReg;
	}


}
