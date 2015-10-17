package com.raulvintila.app.lieflashcards.SearchAPI;

/**
 * Created by raulvintila on 7/31/15.
 */
public class Result {
    /*{"GsearchResultClass","width":"","height":"","imageId":","tbWidth":"","tbHeight":"",
        "unescapedUrl":"","visibleUrl":"",
            "title":"","titleNoFormatting":"","originalContextUrl":"",
            "content":"","contentNoFormatting":"","tbUrl":""},*/
    String gsearchResultClass;
    String width;
    String height;
    String imageId;
    String tbWidth;
    String tbHeight;
    String unescapedUrl;
    String url;
    String visibleUrl;
    String title;
    String titleNoFormatting;
    String originalContextUrl;
    String content;
    String contentNoFormatting;
    String tbUrl;


    public void setGsearchResultClass(String gsearchResultClass) {
        this.gsearchResultClass = gsearchResultClass;
    }

    public String getGsearchResultClass() {
        return gsearchResultClass;
    }

    public void setContentNoFormatting(String contentNoFormatting) {
        this.contentNoFormatting = contentNoFormatting;
    }

    public String getContentNoFormatting() {
        return contentNoFormatting;
    }


    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return imageId;
    }


    public void setOriginalContextUrl(String originalContextUrl) {
        this.originalContextUrl = originalContextUrl;
    }

    public String getOriginalContextUrl() {
        return originalContextUrl;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    public void setTbHeight(String tbHeight) {
        this.tbHeight = tbHeight;
    }

    public String getTbHeight() {
        return tbHeight;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }

    public String getTbUrl() {
        return tbUrl;
    }


    public void setTbWidth(String tbWidth) {
        this.tbWidth = tbWidth;
    }


    public String getTbWidth() {
        return tbWidth;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }



    public void setTitleNoFormatting(String titleNoFormatting) {
        this.titleNoFormatting = titleNoFormatting;
    }

    public String getTitleNoFormatting() {
        return titleNoFormatting;
    }



    public void setUnescapedUrl(String unescapedUrl) {
        this.unescapedUrl = unescapedUrl;
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getUrl() {
        return this.url;
    }


    public void setVisibleUrl(String visibleUrl) {
        this.visibleUrl = visibleUrl;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }



    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }



}
