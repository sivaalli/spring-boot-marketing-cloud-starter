package com.marketing.cloud;

public class McClientException extends RuntimeException{

    private static final long serialVersionUID = -2676582811620017323L;

    public McClientException() {
        super();
    }

    public McClientException(String message) {
        super(message);
    }

    public McClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public McClientException(Throwable cause) {
        super(cause);
    }
}
