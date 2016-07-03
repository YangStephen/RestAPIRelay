package com.RestAPIRelay;

import java.util.*;
import java.io.*;
import java.net.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relay")
public class RelayController {
    @RequestMapping(method = RequestMethod.OPTIONS)
    public String relayOptions(){
        return "";
    }
    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin()
    public String relayGet(@RequestParam(value = "url") String url) {
        System.out.println(url);
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);

            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                response.append(line);
                response.append('\r');
            }
            br.close();
            connection.disconnect();
            return response.toString();
        } catch (Error e) {
            System.out.println("error");
            return null;
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @CrossOrigin()
    public String relayPost(@RequestParam(value = "user", required = false) String username, @RequestParam(value = "pass", required = false) String password, @RequestParam(value = "url") String url, @RequestParam(value = "params", required = false) String params) {
        try {
            String authEncoded = "";
            if (username != null && password != null)
            {
                authEncoded = Base64.getEncoder().encodeToString((username+":"+password).getBytes());
                System.out.println(authEncoded);
            }
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
            if (username != null && password != null) {
                connection.setRequestProperty("Authorization", "Basic " + authEncoded);
            }
            connection.setDoInput(true);
            if (params != null) {
                connection.setDoOutput(true);
            }
            connection.setUseCaches(false);

            connection.connect();
            if (params != null) {
                OutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(params.getBytes());
                os.flush();
            }
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                response.append(line);
                response.append('\r');
            }
            br.close();
            connection.disconnect();
            return response.toString();
        } catch (Error er) {
            System.out.println(er.getMessage());
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @RequestMapping(method = RequestMethod.DELETE)
    @CrossOrigin()
    public String relayDelete(@RequestParam(value = "user", required = false) String username, @RequestParam(value = "pass", required = false) String password, @RequestParam(value = "url", required=false) String url) {
        try {
            String authEncoded = "";
            System.out.println(username+" "+password+" "+url);
            if (username != null && password != null)
            {
                authEncoded = Base64.getEncoder().encodeToString((username+":"+password).getBytes());
                System.out.println(authEncoded);
            }
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
            if (username != null && password != null) {
                connection.setRequestProperty("Authorization", "Basic " + authEncoded);
            }
            connection.setDoInput(true);
            connection.setUseCaches(false);

            connection.connect();
            connection.disconnect();
            return "{}";
        } catch (Error er) {
            System.out.println(er.getMessage());
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @RequestMapping(method = RequestMethod.PUT)
    @CrossOrigin()
    public String relayPut(@RequestParam(value = "user", required = false) String username, @RequestParam(value = "pass", required = false) String password, @RequestParam(value = "url", required=false) String url,@RequestParam(value = "params", required=false) String params) {
        try {
            String authEncoded = "";
            System.out.println(username+" "+password+" "+url);
            if (username != null && password != null)
            {
                authEncoded = Base64.getEncoder().encodeToString((username+":"+password).getBytes());
                System.out.println(authEncoded);
            }
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
            if (username != null && password != null) {
                connection.setRequestProperty("Authorization", "Basic " + authEncoded);
            }
            connection.setDoInput(true);
            connection.setUseCaches(false);

            connection.connect();
            if (params != null) {
                OutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(params.getBytes());
                os.flush();
            }
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                response.append(line);
                response.append('\r');
            }
            br.close();
            connection.disconnect();
            return response.toString();

        } catch (Error er) {
            System.out.println(er.getMessage());
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}