package com.rmn.testrail.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class MockHTTPUtils extends HTTPUtils {
    private String contentsFromConnection;
    public void setContentsFromConnection( String contentsFromConnection ) { this.contentsFromConnection = contentsFromConnection; }

    @Override
    public String getContentsFromConnection( URLConnection connection ) { return contentsFromConnection; }

    @Override
    public HttpURLConnection getHTTPRequest(String completeUrl, String auth, Map<String, String> headers) throws IOException {
        return new HttpURLConnection( ( URL ) null ) {
            @Override public void connect() throws IOException {}
            @Override public boolean usingProxy() { return false; }
            @Override public void disconnect() {}
        };
    }
}
