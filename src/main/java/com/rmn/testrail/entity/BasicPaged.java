package com.rmn.testrail.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This object represents paginated response from TestRail API for bulk artifacts that reflect v6.7 and
 * are specified on the {@link JsonAlias} annotation on "list" field
 *
 * @author mgage
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicPaged extends BaseEntity {

    @JsonProperty("offset")
    private Integer offset;
    public Integer getOffset() { return offset; }
    public void setOffset(Integer offset) { this.offset = offset; }

    @JsonProperty("limit")
    private Integer limit;
    public Integer getLimit() { return limit; }
    public void setLimit(Integer limit) { this.limit = limit; }

    @JsonProperty("size")
    private Integer size;
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    @JsonProperty("_links")
    private Links links;
    public Links getLinks() { return links; }
    public void setLinks(Links links) { this.links = links; }

    @JsonAlias({ "attachments", "cases", "milestones", "plans", "projects", "results", "runs", "sections", "tests" })
    private List<?> list;
    public List<?> getList() { return list; }
    public void setList(List<?> list) { this.list = list; }


    private static class Links {

        @JsonProperty("next")
        private String next;
        public String getNext() { return next; }
        public void setNext(String next) { this.next = next; }

        @JsonProperty("prev")
        private String prev;
        public String getPrev() { return prev; }
        public void setPrev(String prev) { this.prev = prev; }
    }
}
