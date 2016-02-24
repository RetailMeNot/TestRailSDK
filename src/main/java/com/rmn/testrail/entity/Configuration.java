package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author vliao
 */
public class Configuration extends BaseEntity {
    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName( String name ) { this.name = name; }
}
