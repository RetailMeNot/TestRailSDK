package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author vliao
 */
public class Priority extends BaseEntity {
    @JsonProperty("id")
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @JsonProperty("is_default")
    private boolean isDefault;
    public boolean getIsDefault() { return isDefault; }
    public void setIsDefault(boolean isDefault) { this.isDefault = isDefault; }

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("priority")
    private Integer priority;
    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    @JsonProperty("short_name")
    private String shortName;
    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }
}
