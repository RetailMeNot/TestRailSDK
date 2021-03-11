package com.rmn.testrail.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * This Enumeration represents bulk end-point api Json types for returned paginated entity
 * bulktype - represents short type name in lower case for entities in the data model
 *
 * Note: Includes only APIs supporting pagination as of TestRail 6.7
 */

public enum BulkEntityCategory {

    ATTACHMENT("attachment"),
    MILESTONE("milestone"),
    PROJECT("project"),
    RESULT("result"),
    TESTCASE("testcase"),           //The free standing test case used as a template for test entity
    TESTINSTANCE("testinstance"),   //The test entity attached to either run or plan to report against
    TESTPLAN("testplan"),
    TESTRUN("testrun"),
    SECTION("section"),
    TESTRESULT("testresult");

    private static final Logger log = LoggerFactory.getLogger(BulkEntityCategory.class);
    private final String bulkType;

    BulkEntityCategory(String bulkType) {
        this.bulkType = bulkType;
    }

    public String getBulkType() {
        return bulkType;
    }

    public static boolean isBulkCategory(String category) {
        try {
            fromBulkType(category);
            return true;
        } catch (NoSuchElementException ex) {
            log.debug("Non-paged (bulk) object type detected.  '{}'", ex.toString());
            return false;
        }
    }

    public static BulkEntityCategory fromBulkType(String bulkType) {

        return Arrays.stream(BulkEntityCategory.values())
                .filter(t -> t.getBulkType().equals(bulkType))
                .findFirst()
                .orElseThrow();
    }
}
