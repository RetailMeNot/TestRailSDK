package com.rmn.testrail.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HTTPUtils implements Serializable {
    public HTTPUtils() {}

    private static final int REQUEST_TIMEOUT = 1 * 60 * 1000; //minutes * seconds * milliseconds
    private Logger log = LoggerFactory.getLogger(getClass());
    
    /**
     * Prepare everything about a GET request, including adding the headers, composing the URL, establishing the Connection
     * @param completeUrl The complete URL, including the apiCall, to send to the service
     * @param headers A map of header key-value pairs to send along with the HTTP request
     * @return An active, open connection in a post-response state
     * @throws IOException
     */
    public HttpURLConnection getHTTPRequest(String completeUrl, String authentication, Map<String, String> headers) throws IOException {
        //Build the connection, then insert the pid and fp headers
        HttpURLConnection connection = (HttpURLConnection) new URL(completeUrl).openConnection();
        connection.setRequestProperty("Authorization", "Basic " + authentication);

        log.debug("Attempting to get {}", completeUrl);
    
        //Add a new header for each entry in the collection
        if (headers != null) {
            for (String key: headers.keySet()) {
                String value = headers.get(key);
                log.debug("Adding header [{}: {}]", key, value);
                connection.setRequestProperty(key, value);
            }
        }
        return submitRequestFromConnection(connection);
    }
    
    /**
     * Take a fully-baked connection and send it to the server
     * @param connection A composed HTTP request containing the URL and any headers required
     * @return An active, open connection in a post-response state
     * @throws IOException
     */
    private HttpURLConnection submitRequestFromConnection(HttpURLConnection connection) throws IOException {
        //Send the request
        connection.setDoOutput(true);
        connection.setReadTimeout(REQUEST_TIMEOUT);
        connection.setConnectTimeout(REQUEST_TIMEOUT);
    
        log.debug("Sending request...");
        connection.connect();
        log.debug("Response: {}, {}", connection.getResponseCode(), connection.getResponseMessage());
        return connection;
    }
    
    /**
     * Generate the string you'd need to use to re-create this API call. We're not using Curl--this just helps you debug if something goes wrong
     * @param completeUrl The complete URL, including the apiCall, to send to the service
     * @param headers A map of header key-value pairs to send along with the HTTP request
     * @return A String representing the exact curl command needed to reproduce this call outside of this method
     */
    public String getCurlCommandStringGet(String completeUrl, Map<String, String> headers) {
        String curlStr = "curl -v "; //The default is "verbose", but you can remove this if you don't care about the headers
    
        //If there are headers, include them here, otherwise just output the URL
        if (headers != null) {
            for ( Map.Entry<String, String> entry: headers.entrySet() ) {
                curlStr += String.format("-H \"%s: %s\"", entry.getKey(), entry.getValue());
            }
        }
    
        curlStr += " " + completeUrl;
        return curlStr;
    }
    
    /**
     * Generate the string you'd need to use to re-create this API call
     * @param completeUrl The complete URL, including the apiCall, to send to the service
     * @param headers A map of header key-value pairs to send along with the HTTP request
     * @return A String representing the exact curl command needed to reproduce this call outside of this method
     */
    public String getCurlCommandStringPost(String completeUrl, Map<String, String> headers, List<NameValuePair> values) {
        String curlStr = "curl -v"; //The default is "verbose", but you can remove this if you don't care about the headers
    
        //If there are headers, include them here, otherwise just output the URL
        if (headers != null) {
            for (Map.Entry<String, String> entry: headers.entrySet()) {
                curlStr += String.format(" -H \"%s: %s\"", entry.getKey(), entry.getValue());
            }
        }
    
        curlStr += String.format(" -vi -X POST -d \"%s\" \"%s\"", values.toString(), completeUrl);
        return curlStr;
    }

    /**
     * Gathers the contents of a URL Connection and concatenates everything into a String
     * @param connection A URLConnection object that presumably has a getContent() that will have some content to get
     * @return A Concatenated String of the Content contained in the URLConnection
     */
    public String getContentsFromConnection(URLConnection connection) {
        //Get the content from the connection. Since the content could be in many forms, this Java library requires us to marshall it into an InputStream, from which we get a...
        InputStreamReader in;
        try {
            in = new InputStreamReader((InputStream) connection.getContent());
        } catch ( IOException e ) {
            throw new RuntimeException("Could not read contents from connection: " + e.getMessage());
        }
        return getContentsFromInputStream(in);
    }

    /**
     * Returns a concatenated String of the contents of an HttpResponse
     * @param response An HttpResponse
     * @return a String containing the contents of the HttpResponse
     * @throws IOException
     */
    public String getContentsFromHttpResponse(HttpResponse response) throws IOException {
        InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
        return getContentsFromInputStream(in);
    }

    private String getContentsFromInputStream(InputStreamReader in) {
        //...Buffered reader, which we have to parse out in order to get a string we can use
        BufferedReader buff = new BufferedReader( in );
        String text = "";
    
        //The return values all seem to be contained within one line, so we probably don't need this
        String line;
        do {
            try {
                line = buff.readLine();
                text += line + "\n";
            }
            catch (Exception ex) {
                return text;
            }
        } while (line != null);
        return text;
    }

    /**
     * Takes a username and password, then returns a Base64-encoded String that can be sent for Basic Auth
     * @param username the Username to be encoded
     * @param password the Password to be encoded
     * @return a Base64-encoded String representing this key-value pair
     */
    public static String encodeAuthenticationBase64(String username, String password) {
        return Base64.encodeBase64String(String.format("%s:%s", username, password).getBytes());
    }
}

