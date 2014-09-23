package com.maryamq.imageclient.model;

import java.io.Serializable;

public class UrlMetaData implements Serializable {
	static final String SEARCH_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=%s&rsz=8%s";
	
	public String imageSize = "";
	public String imageType = "";
	public String siteFilter = "";
	public String colorFilter = "";
	
	public String getUrl(String query, int page) {
		return String.format(SEARCH_URL, query, getQueryParams(page));
	}
	
	public String getQueryParams(int page) {
		String queryParams = "";
		if (imageSize != null && !imageSize.isEmpty()) {
			queryParams += "&imgsz=" + imageSize.toLowerCase();
		}
		
		if (imageType != null && !imageType.isEmpty()) {
			queryParams += "&imgtype="  + imageType.toLowerCase();
		}
		
		if (siteFilter != null && !siteFilter.isEmpty()) {
			queryParams += "&as_sitesearch=" + siteFilter.toLowerCase();
		}
		
		if (colorFilter != null && !colorFilter.isEmpty()) {
			queryParams += "&imgcolor=" + colorFilter.toLowerCase();
		}
		queryParams += "&start=" + page;

		return queryParams;
	}
	


}
