package com.rmn.testrail.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jsteigel
 */
public class EmptyMilestone extends BaseEntity {
    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("due_on")
    private String dueOn;
    public String getDueOn() { return dueOn; }
    public void setDueOn(String dueOn) { this.dueOn = dueOn; }
}