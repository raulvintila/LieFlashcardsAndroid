package com.raulvintila.app.lieflashcards.SearchAPI;

import java.util.List;

public class Cursor {

    private String estimatedResultCount;
    private Number currentPageIndex;
    private String moreResultsUrl;
    private List<Page> pages;
    private String resultCount;
    private String searchResultTime;

    public void setEstimatedResultCount(String estimatedResultCount) {
        this.estimatedResultCount = estimatedResultCount;
    }

    public String getEstimatedResultCount() {
        return estimatedResultCount;
    }

    public void setCurrentPageIndex(Number currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public Number getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setMoreResultsUrl(String moreResultsUrl) {
        this.moreResultsUrl = moreResultsUrl;
    }

    public String getMoreResultsUrl() {
        return moreResultsUrl;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setResultCount(String resultCount) {
        this.resultCount = resultCount;
    }

    public String getResultCount() {
        return resultCount;
    }

    public void setSearchResultTime(String searchResultTime) {
        this.searchResultTime = searchResultTime;
    }

    public String getSearchResultTime() {
        return searchResultTime;
    }

}
