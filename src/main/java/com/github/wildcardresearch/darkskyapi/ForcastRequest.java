package com.github.wildcardresearch.darkskyapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wildcardresearch.darkskyapi.exception.ForcastException;
import com.github.wildcardresearch.darkskyapi.model.ForcastResponse;
import com.github.wildcardresearch.darkskyapi.client.DarkSkyHttpClient;

import java.net.URI;
import java.net.http.HttpResponse;

import static com.github.wildcardresearch.darkskyapi.util.Assert.notNullOrEmpty;

public class ForcastRequest {

    private static final String KEY_KEY = "#key#";
    private static final String LAT_KEY = "#latitude#";
    private static final String LON_KEY = "#longitude#";
    private static final String URL = "https://api.darksky.net/forecast/" + KEY_KEY + "/" + LAT_KEY + "," + LON_KEY;

    private final String url;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private ForcastRequest(final String url) {
        this.url = url;
    }

    public ForcastResponse get(double lat, double lon) {
        try {
            HttpResponse<String> httpResponse = DarkSkyHttpClient.get(getUri(lat, lon));
            return MAPPER.readValue(httpResponse.body(), ForcastResponse.class);
        } catch (JsonProcessingException ex) {
            throw new ForcastException("Cannot convert HttpResponse body to ForcastResponse", ex);
        }
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
            notNullOrEmpty(apiKey);
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
