package com.marketing.cloud.response;

public enum Status {

    Success, PartialSuccess, Fail;

    public static Status from(String status) {
        if (status == null) return null;
        if (status.equals("OK")) return Success;
        return Fail;
    }
}
