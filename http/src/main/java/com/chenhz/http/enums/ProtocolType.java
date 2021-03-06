package com.chenhz.http.enums;

public enum ProtocolType {
    HTTP("http"),
    HTTPS("https");

    private final String protocol;

    private ProtocolType(String protocol) {
        this.protocol = protocol;
    }

    public String toString() {
        return this.protocol;
    }
}
