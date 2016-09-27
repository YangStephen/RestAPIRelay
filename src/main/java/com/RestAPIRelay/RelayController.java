package com.RestAPIRelay;

/**
 * Controller to manage REST calls to API
 *
 * @author Stephen Yang
 */

import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@RestController
@RequestMapping("/relay")
public class RelayController {
    /**
     * requestHandler: Generic function to manage the HTTP request for all types (POST, GET, etc.)
     * @param type POST, GET, etc.
     * @param url URL to request
     * @param params Request Body
     * @param user username for base64 encoded authorization
     * @param pass password for base64 encoded authorization
     * @return String response from HTTP request
     */
    public String requestHandler(String type, String url, String params, String user, String pass) {

        String authEncoded = "";

        try {
            // Open Connection to the URL
            if (user != null && pass != null) {
                authEncoded = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
                System.out.println(authEncoded);
            }

            URL urlConnection = new URL(url); // create urlConnection
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection(); // Form HTTPURLConnection from url
            connection.addRequestProperty("User-Agent", "Mozilla/4.76"); // set User-Agent to stop 403 errors
            connection.setRequestMethod(type); // Type Parameter sent from endpoint function

            // Check if the request data is coming in the form of a URLEncoded or a JSON (POST, PUT, and DELETE)
            if (type.equals("GET")) {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // urlencoded
            } else {
                connection.setRequestProperty("Content-Type", "application/json;charset=utf-8"); // JSON
            }

            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);

            // Only execute DoInput if type is not equal to get
            if (!(type.equals("GET"))) {
                connection.setDoInput(true);
            }

            // there are parameters being sent in, setDoOutput becomes true
            if (params != null) {
                connection.setDoOutput(true);
            }

            // Add Authentication Headers encoded in Base64.
            if (user != null && pass != null) {
                connection.setRequestProperty("Authorization", "Basic " + authEncoded);
            }

            //Connect
            connection.connect();

            // Write the Parameters to the output stream of the connection
            if (params != null) {
                OutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(params.getBytes());
                os.flush();
            }

            // Setup the InputStream to take in data;
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;

            // While loop to iterate through all the response data and write it into a StringBuilder object.
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                response.append(line);
                response.append('\r');
            }

            // close BufferedReader.
            br.close();
            // disconnect the connection
            connection.disconnect();

            // return response
            return response.toString();
        } catch (Error e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Relay manager for OPTIONS
     * @return String HTTP response
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    public String relayOptions() {
        return "";
    }

    /**
     * Relay handler for GET
     * @param url Request URL
     * @return HTTP response body
     */
    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin()
    public String relayGet(@RequestParam(value = "url") String url) {
        return requestHandler("GET", url, null, null, null);
    }

    /**
     * Relay handler for POST
     * @param username optional
     * @param password optional
     * @param url Request URL
     * @param params optional
     * @return HTTP response body
     */
    @RequestMapping(method = RequestMethod.POST)
    @CrossOrigin()
    public String relayPost(@RequestParam(value = "user", required = false) String username, @RequestParam(value = "pass", required = false) String password, @RequestParam(value = "url") String url, @RequestParam(value = "params", required = false) String params) {
        return requestHandler("POST", url, params, username, password);
    }

    /**
     * Relay handler for DELETE
     * @param username optional
     * @param password optional
     * @param url requestURL
     * @return HTTP response body
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @CrossOrigin()
    public String relayDelete(@RequestParam(value = "user", required = false) String username, @RequestParam(value = "pass", required = false) String password, @RequestParam(value = "url") String url) {
        return requestHandler("DELETE", url, null, username, password);
    }

    /**
     * Relay handler for PUT
     * @param username
     * @param password
     * @param url
     * @param params
     * @return HTTP response body
     */
    @RequestMapping(method = RequestMethod.PUT)
    @CrossOrigin()
    public String relayPut(@RequestParam(value = "user", required = false) String username, @RequestParam(value = "pass", required = false) String password, @RequestParam(value = "url") String url, @RequestParam(value = "params", required = false) String params) {
        return requestHandler("PUT", url, params, username, password);
    }
}