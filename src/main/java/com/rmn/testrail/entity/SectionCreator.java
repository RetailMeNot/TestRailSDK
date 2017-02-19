package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author ragePowered
 */
public class SectionCreator extends BaseEntity {
    @JsonProperty("description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("suite_id")
    private Integer suiteId;
    public Integer getSuiteId() { return suiteId; }
    public void setSuiteId(Integer suiteId) { this.suiteId = suiteId; }

    @JsonProperty("parent_id")
    private Integer parentId;
    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
