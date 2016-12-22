package com.rmn.testrail.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Status (often represented as "Verdict" or "Pass/Fail"). By default, this includes only "Passed", "Blocked",
 *  "Failed", "Untested", or "Retest". To represent a custom status (e.g. "skipped", "warning", etc.), invoke the
 *  "addStatus(String, Integer)" method, which will require you to map the String name of the field to the id TestRails
 *  requires in order to set the status
 *
 * @author mmerrell
 */
public class TestStatus {
    private static Map<String, Integer> allStatus = new HashMap<String, Integer>();

    static {
        allStatus.put("Passed", 1);
        allStatus.put("Blocked", 2);
        allStatus.put("Failed", 5);
        allStatus.put("Untested", 6);
        allStatus.put("Retest", 4);
    }

    public static void addStatus(String status, int id) { allStatus.put(status, id); }

    public static int getStatus(String status) {
        if (allStatus.containsKey(status)) {
            return allStatus.get(status);
        }

        if (status.toLowerCase().contains("pass")) {
            return allStatus.get("Passed");
        }

        if (status.toLowerCase().equalsIgnoreCase("fail")) {
            return allStatus.get("Failed");
        }

        throw new IllegalArgumentException(String.format("No status found matching [%s]. Please check to ensure any customizations have been implemented correctly", status));
    }
}
