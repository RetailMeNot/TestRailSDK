package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author mmerrell
 */
public class TestSuiteCreator extends BaseEntity {
    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}