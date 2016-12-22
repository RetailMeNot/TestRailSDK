package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * A Project as defined within the API. This object offers some convenience methods, which will remove some of the busy work about re-querying that
 *  you would have to handle otherwise.
 *
 *  I did not implement the "get_runs" end-point, because I doubt it is useful, it's slow,
 *   it returns a gigantic payloads, and if you really want it, you can stitch it together yourself.
 *   I'm afraid if I implement it, someone will use it and be completely baffled by the results...
 *   It returns ALL test runs underneath ALL test plans within the history of the project. Useless. If
 *   you can present me with a good use-case for implementing it, I'll be all ears (and very surprised)
 *
 * @author mmerrell
 */
public class ProjectCreator extends BaseEntity {
    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("announcement")
    private String announcement;
    public String getAnnouncement() { return announcement; }
    public void setAnnouncement( String announcement ) { this.announcement = announcement; }

    @JsonProperty("show_announcement")
    private Boolean showAnnouncement;
    public Boolean isShowAnnouncement() { return showAnnouncement; }
    public void setShowAnnouncement(Boolean showAnnouncement) { this.showAnnouncement = showAnnouncement; }

    @JsonProperty("suite_mode")
    private Integer suiteMode;
    public Integer getSuiteMode() { return suiteMode; }
    public void setSuiteMode(Integer suiteMode) { this.suiteMode = suiteMode; }
}
