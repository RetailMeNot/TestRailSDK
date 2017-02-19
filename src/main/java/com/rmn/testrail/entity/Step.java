package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

public class Step extends BaseEntity {

    public Step() {
    }

    public Step(String content, String expected) {
        this.content = content;
        this.expected = expected;
    }

    @JsonProperty("content")
    private String content;
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @JsonProperty("expected")
    private String expected;
    public String getExpected() { return expected; }
    public void setExpected(String expected) { this.expected = expected; }
}
