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
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String API_SECRET = System.getenv("API_SECRET");
    private static final String ENDPOINT_URL = "https://api.imagga.com/v2/tags";

    public static String getImageTags(String imageUrl) throws Exception {
        String credentialsToEncode = API_KEY + ":" + API_SECRET;
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));
        String url = ENDPOINT_URL + "?image_url=" + imageUrl;
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestProperty("Authorization", "Basic " + basicAuth);
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String jsonResponse = connectionInput.readLine();
            connectionInput.close();
            return jsonResponse;
        } else {
            throw new ImageTaggingException("Imagga API returned an error: " + responseCode);
        }
    }
}