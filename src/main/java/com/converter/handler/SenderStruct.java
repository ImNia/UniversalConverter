package com.converter.handler;

public class SenderStruct {
    //TODO другие имена
    private String from;
    private String to;

    public SenderStruct() {}
    public SenderStruct(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFromValue() {
        return from;
    }

    public void setFromValue(String fromValue) {
        this.from = fromValue;
    }

    public String getToValue() {
        return to;
    }

    public void setToValue(String toValue) {
        this.to = toValue;
    }
}
