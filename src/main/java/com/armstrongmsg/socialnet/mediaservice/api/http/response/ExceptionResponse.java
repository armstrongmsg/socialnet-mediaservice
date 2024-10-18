package com.armstrongmsg.socialnet.mediaservice.api.http.response;

public class ExceptionResponse {
    private String exceptionMessage;
    private String requestDetails;

    public ExceptionResponse(String exceptionMessage, String requestDetails) {
        this.exceptionMessage = exceptionMessage;
        this.requestDetails = requestDetails;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getRequestDetails() {
        return requestDetails;
    }
}
