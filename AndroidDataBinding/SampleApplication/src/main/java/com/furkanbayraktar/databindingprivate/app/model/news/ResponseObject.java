package com.furkanbayraktar.databindingprivate.app.model.news;

import com.furkanbayraktar.databinding.model.BasePOJO;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/19/14.
 * Copyright Valensas A.S.
 */
public class ResponseObject extends BasePOJO{

    private ResponseData responseData;
    private String responseDetails;
    private Long responseStatus;

    public ResponseObject(ResponseData responseData, String responseDetails, Long responseStatus) {
        this.responseData = responseData;
        this.responseDetails = responseDetails;
        this.responseStatus = responseStatus;
    }

    public ResponseObject() {
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public Long getResponseStatus() {
        return responseStatus;
    }
}
