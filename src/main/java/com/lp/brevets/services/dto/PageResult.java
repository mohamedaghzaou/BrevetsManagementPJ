package com.lp.brevets.services.dto;

import java.util.List;

public class PageResult<T> {
	private final List<T> items;
	private final int currentPage;
	private final int totalPages;
	private final int pageSize;
	private final long totalResults;
	private final boolean hasPagination;

	public PageResult(List<T> items, int currentPage, int totalPages, int pageSize, long totalResults,
			boolean hasPagination) {
		this.items = items;
		this.currentPage = currentPage;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.totalResults = totalResults;
		this.hasPagination = hasPagination;
	}

	public List<T> getItems() {
		return items;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getTotalResults() {
		return totalResults;
	}

	public boolean isHasPagination() {
		return hasPagination;
	}
}
