package com.raulvintila.app.lieflashcards.SearchAPI;


public class Response {
    private ResponseData responseData;
    private String responseDetails;
    private Number responseStatus;

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseStatus(Number responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Number getResponseStatus() {
        return responseStatus;
    }
}
