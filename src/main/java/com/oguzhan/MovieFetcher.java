package com.oguzhan;

import org.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MovieFetcher {
    private static final String API_KEY = "796764d6";

    public static JSONObject fetchMovieDetails(String movieName) throws Exception {
        String apiUrl = "http://www.omdbapi.com/?t=" + movieName + "&apikey=" + API_KEY;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            HttpResponse response = client.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            return new JSONObject(json);
        }
    }
}
