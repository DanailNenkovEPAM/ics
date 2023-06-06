package com.vmware.ics.service;

import com.vmware.ics.exception.ImageTaggingException;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ImageTaggingService {
   //private static final String API_KEY = System.getenv("API_KEY");

    private static final String API_KEY = "acc_02cb9e80cd84cae";
    private static final String API_SECRET = "e00c2e15ac529bd9d2e0472c29075625";
    private static final String ENDPOINT_URL = "https://api.imagga.com/v2/tags";

    public static String getImageTags(String imageUrl) throws Exception {
        String credentialsToEncode = "acc_02cb9e80cd84cae" + ":" + "e00c2e15ac529bd9d2e0472c29075625";
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

        String endpoint_url = "https://api.imagga.com/v2/tags";
        //String image_url = "http://playground.imagga.com/static/img/example_photos/japan-605234_1280.jpg";

        String url = endpoint_url + "?image_url=" + imageUrl;
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        connection.setRequestProperty("Authorization", "Basic " + basicAuth);

        int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonResponse = connectionInput.readLine();

        connectionInput.close();

        System.out.println(jsonResponse);

        return jsonResponse;
    }
}