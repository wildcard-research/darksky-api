package com.github.wildcardresearch.darkskyapi.client;

import com.github.wildcardresearch.darkskyapi.exception.ForcastException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * HTTP Client to send requests to the Dark Sky API.
 */
public class DarkSkyHttpClient {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    /**
     * GET request that verifies the response.
     * @param uri URI to use for the GET request
     * @return HttpResponse
     */
    public static HttpResponse<String> get(URI uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .timeout(Duration.ofMinutes(1))
                .build();

        HttpResponse<String> res = sendToString(request);
        verifyStatus(res);
        return res;
    }

    /**
     * Runs GET request and sends response to a String.
     * @param request {@link HttpRequest}
     * @return HttpResponse
     */
    private static HttpResponse<String> sendToString(HttpRequest request) {
        try {
            return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            throw new ForcastException("HttpClient request failed for " + request.uri().toString(), ex);
        }
    }

    /**
     * Verify HttpResponse.
     * @param res {@link HttpResponse<?>}
     * @return Input parameter
     */
    private static HttpResponse<?> verifyStatus(HttpResponse<?> res) {
        switch(res.statusCode()) {
            case 403:
                throw new ForcastException("Unauthorized request. API Key could be incorrect.");
        }
        return res;
    }
}
