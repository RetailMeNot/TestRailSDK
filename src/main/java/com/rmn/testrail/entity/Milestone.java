package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author jsteigel
 */
public class Milestone extends BaseEntity {
    @JsonProperty("id")
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("is_completed")
    private Boolean isCompleted;
    public Boolean isCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    @JsonProperty("url")
    private String url;
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @JsonProperty("completed_on")
    private String completedOn;
    public String getCompletedOn() { return completedOn; }
    public void setCompletedOn(String completedOn) { this.completedOn = completedOn; }

    @JsonProperty("description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("due_on")
    private String dueOn;
    public String getDueOn() { return dueOn; }
    public void setDueOn(String dueOn) { this.dueOn = dueOn; }

    @JsonProperty("project_id")
    private Integer projectId;
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    @JsonProperty("start_on")
    private String startOn;
    public String getStartOn() { return startOn; }
    public void setStartOn(String startOn) { this.startOn = startOn; }

    @JsonProperty("started_on")
    private String startedOn;
    public String getStartedOn() { return startedOn; }
    public void setStartedOn(String startedOn) { this.startedOn = startedOn; }

    @JsonProperty("is_started")
    private String isStarted;
    public String getIsStarted() { return isStarted; }
    public void setIsStarted(String isStarted) { this.isStarted = isStarted; }

    @JsonProperty("parent_id")
    private String parentId;
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

}
