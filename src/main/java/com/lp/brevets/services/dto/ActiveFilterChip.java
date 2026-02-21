package com.lp.brevets.services.dto;

public class ActiveFilterChip {
	private final String label;
	private final String removeUrl;

	public ActiveFilterChip(String label, String removeUrl) {
		this.label = label;
		this.removeUrl = removeUrl;
	}

	public String getLabel() {
		return label;
	}

	public String getRemoveUrl() {
		return removeUrl;
	}
}
