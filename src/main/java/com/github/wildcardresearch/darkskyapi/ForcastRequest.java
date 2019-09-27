package com.github.wildcardresearch.darkskyapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wildcardresearch.darkskyapi.model.ForcastResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ForcastRequest {

    private static final String KEY_KEY = "#key#";
    private static final String LAT_KEY = "#latitude#";
    private static final String LON_KEY = "#longitude#";
    private static final String URL = "https://api.darksky.net/forecast/" + KEY_KEY + "/" + LAT_KEY + "," + LON_KEY;

    private final String url;
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private ForcastRequest(final String url) {
        this.url = url;
    }

    public ForcastResponse get(double lat, double lon) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(getUri(lat, lon))
                .GET()
                .timeout(Duration.ofMinutes(1))
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return MAPPER.readValue(response.body(), ForcastResponse.class);
    }

    private URI getUri(double lat, double lon) {
        return URI.create(
                this.url.replaceAll(LAT_KEY, String.valueOf(lat))
                .replaceAll(LON_KEY, String.valueOf(lon))
        );
    }

    public static ForcastRequestBuilder builder(String apiKey) {
        return new ForcastRequestBuilder(apiKey);
    }

    public static class ForcastRequestBuilder {

        private String apiKey;

        public ForcastRequestBuilder(String apiKey) { apiKey(apiKey); }

        public ForcastRequestBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public ForcastRequest build() {
            return new ForcastRequest(getUrl());
        }

        private String getUrl() {
            return URL
                    .replaceAll(KEY_KEY, this.apiKey);
        }
    }
}
