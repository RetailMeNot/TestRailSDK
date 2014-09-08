package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author mmerrell
 */
public class TestSuite extends BaseEntity {
    @JsonProperty("id")
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("project_id")
    private Integer projectId;
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    @JsonProperty("url")
    private String url;
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    /**
     * Returns the list of Section entities contained within this TestSuite
     * @return the list of Section entities contained within this TestSuite
     */
    public List<Section> getSections() {
        return getTestRailService().getSections(this.projectId, this.getId());
    }

    /**
     * Returns the list of TestCase entities contained within this TestSuite
     * @return the list of TestCase entities contained within this TestCase
     */
    public List<TestCase> getTestCases() {
        return getTestRailService().getTestCases(projectId, this.getId());
    }
}