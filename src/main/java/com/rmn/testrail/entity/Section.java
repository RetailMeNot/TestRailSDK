package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author mmerrell
 */
public class Section extends BaseEntity {
    @JsonProperty("id")
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName( String name ) { this.name = name; }

    @JsonProperty("description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription( String description ) { this.description = description; }

    @JsonProperty("parent_id")
    private Integer parentId;
    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }

    @JsonProperty("depth")
    private Integer depth;
    public Integer getDepth() { return depth; }
    public void setDepth(Integer depth) { this.depth = depth; }

    @JsonProperty("display_order")
    private Integer displayOrder;
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }

    @JsonProperty("suite_id")
    private Integer suiteId;
    public Integer getSuiteId() { return suiteId; }
    public void setSuiteId(Integer suiteId) { this.suiteId = suiteId; }

    public Integer getProjectId() {
        return getTestRailService().getTestSuite(getSuiteId()).getProjectId();
    }

    /**
     * Returns the complete list of TestCases within this Section
     * @return the complete list of TestCases within this Section
     */
    public List<TestCase> getTestCases() {
        return getTestRailService().getTestCases(getProjectId(),getSuiteId(), this.getId());
    }
}
