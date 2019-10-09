package com.github.wildcardresearch.darkskyapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wildcardresearch.darkskyapi.exception.ForcastException;
import com.github.wildcardresearch.darkskyapi.model.ForcastResponse;
import com.github.wildcardresearch.darkskyapi.client.DarkSkyHttpClient;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.wildcardresearch.darkskyapi.util.Assert.notNull;
import static com.github.wildcardresearch.darkskyapi.util.Assert.notNullOrEmpty;

/**
 * Class representing a request for the Dark Sky API. Only able to construct a request through the
 * {@link ForcastRequestBuilder} builder inner class.
 *
 * @author Wildcard Research
 */
public class ForcastRequest {

    private static final String KEY_KEY = "#key#";
    private static final String LAT_KEY = "#latitude#";
    private static final String LON_KEY = "#longitude#";
    private static final String TIME_KEY = "#time#";
    private static final String COMMA_DELIMITER = ",";
    private static final String URL = "https://api.darksky.net/forecast/" + KEY_KEY + "/" + LAT_KEY + COMMA_DELIMITER + LON_KEY + TIME_KEY;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final String url;

    /**
     * Private constructor only used in the builder.
     * @param url Overridden url string with all parameters set except lat, lon, and time
     */
    private ForcastRequest(final String url) {
        this.url = url;
    }

    /**
     * Single HTTP request using a latitude and longitude.
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @return Response body as String
     */
    public String get(double lat, double lon) {
        return get(String.valueOf(lat), String.valueOf(lon), "").body();
    }

    /**
     * Single HTTP request using a latitude, longitude, and time.
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @param time Seconds since midnight GMT on 1 Jan 1970
     * @return Response body as String
     */
    public String get(double lat, double lon, long time) {
        return get(String.valueOf(lat), String.valueOf(lon), String.valueOf(time)).body();
    }

    /**
     * Single HTTP request using a latitude and longitude.
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @return {@link ForcastResponse} for the requested location
     */
    public ForcastResponse getAsForcastResponse(double lat, double lon) {
        return httpResponseToForcastResponse(get(String.valueOf(lat), String.valueOf(lon), ""));
    }

    /**
     * Single HTTP request using a latitude, longitude, and time.
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @param time Seconds since midnight GMT on 1 Jan 1970
     * @return {@link ForcastResponse} for the requested location at the given time
     */
    public ForcastResponse getAsForcastResponse(double lat, double lon, long time) {
        return httpResponseToForcastResponse(get(String.valueOf(lat), String.valueOf(lon), String.valueOf(time)));
    }

    /**
     * Single request URI using a latitude and longitude.
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @return URI for the request
     */
    public URI getUri(double lat, double lon){
        return getUri(String.valueOf(lat), String.valueOf(lon), "");
    }

    /**
     * Single request URI using a latitude and longitude.
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @param time Seconds since midnight GMT on 1 Jan 1970
     * @return URI for the request
     */
    public URI getUri(double lat, double lon, long time){
        return getUri(String.valueOf(lat), String.valueOf(lon), String.valueOf(time));
    }

    /**
     * Creates URI using the overridden URL from the builder and parameters.
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @param time Seconds since midnight GMT on 1 Jan 1970
     * @return URI
     */
    private URI getUri(String lat, String lon, String time) {
        return URI.create(
                this.url.replaceAll(LAT_KEY, lat)
                        .replaceAll(LON_KEY, lon)
                        .replaceAll(TIME_KEY, time.isEmpty() ? time : COMMA_DELIMITER + time)
        );
    }

    /**
     * HTTP GET request using the overridden URL from the builder and parameters.
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @param time Seconds since midnight GMT on 1 Jan 1970
     * @return {@link HttpResponse<String>}
     */
    private HttpResponse<String> get(String lat, String lon, String time) {
        return DarkSkyHttpClient.get(getUri(lat, lon, time));
    }

    /**
     * Conversion from HttpResponse to {@link ForcastResponse}
     * @param httpResponse response from Dark Sky API
     * @return {@link ForcastResponse}
     */
    private ForcastResponse httpResponseToForcastResponse(HttpResponse<String> httpResponse) {
        try {
            return MAPPER.readValue(httpResponse.body(), ForcastResponse.class);
        } catch (JsonProcessingException ex) {
            throw new ForcastException("Cannot convert HttpResponse body to ForcastResponse.", ex);
        }
    }

    /**
     * @param apiKey Dark Sky API key for each user
     * @return {@link ForcastRequestBuilder}
     */
    public static ForcastRequestBuilder builder(String apiKey) {
        return new ForcastRequestBuilder(apiKey);
    }

    /**
     * Builder inner class to create a {@link ForcastRequest}
     */
    public static class ForcastRequestBuilder {

        private String apiKey;
        private LinkedHashSet<Block> excludeBlocks;
        private boolean extend = false;
        private Language language;
        private Units units;

        /**
         * Initialize builder with Dark Sky API key.
         * @param apiKey Dark Sky API key for each user
         */
        public ForcastRequestBuilder(String apiKey) { apiKey(apiKey); }

        /**
         * Set request API key.
         * @param apiKey Dark Sky API key for each user that cannot be empty or null
         * @return This builder
         */
        public ForcastRequestBuilder apiKey(String apiKey) {
            notNullOrEmpty(apiKey);
            this.apiKey = apiKey;
            return this;
        }

        /**
         * Set request exclusion blocks. Parameters can not be null.
         * @param block {@link Block} to exclude
         * @param blocks Group of additional {@link Block} values to exclude
         * @return This builder
         */
        public ForcastRequestBuilder exclude(Block block, Block... blocks){
            String messageIfNull = "Exclusion blocks cannot be null in ForcastRequestBuilder.";
            notNull(block, messageIfNull);
            notNull(blocks, messageIfNull);
            List<Block> allBlocks = new ArrayList<>(blocks.length + 1);
            allBlocks.add(block);
            allBlocks.addAll(Arrays.asList(blocks));
            this.excludeBlocks = allBlocks.stream().filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
            return this;
        }

        /**
         * Set request extend hourly.
         * @return This builder
         */
        public ForcastRequestBuilder extendHourly() {
            this.extend = true;
            return this;
        }

        /**
         * Set request language.
         * @param lang {@link Language} for response
         * @return This builder
         */
        public ForcastRequestBuilder lang(Language lang) {
            notNull(lang, "Language cannot be null in ForcastRequestBuilder.");
            this.language = lang;
            return this;
        }

        /**
         * Set request units.
         * @param units {@link Units} for response
         * @return This builder
         */
        public ForcastRequestBuilder units(Units units) {
            notNull(units, "Units cannot be null in ForcastRequestBuilder.");
            this.units = units;
            return this;
        }

        /**
         * Create a request
         * @return {@link ForcastRequest}
         */
        public ForcastRequest build() {
            return new ForcastRequest(getUrl());
        }

        /**
         * Override base URL String with parameters.
         * @return Overridden URL for request
         */
        private String getUrl() {
            return URL
                    .replaceAll(KEY_KEY, this.apiKey)
                    + parameters();
        }

        /**
         * Builder request parameters substring.
         * @return Parameters for request
         */
        private String parameters() {
            Collection<String> params = new LinkedList<>();

            if(this.excludeBlocks != null && !this.excludeBlocks.isEmpty()) {
                params.add("exclude=" + this.excludeBlocks.stream().map(Block::name).collect(Collectors.joining(COMMA_DELIMITER)));
            }

            if(this.extend) {
                params.add("extend=true");
            }

            if(this.language != null) {
                params.add("lang=" + this.language.name());
            }

            if(this.units != null) {
                params.add("units=" + this.units.name());
            }

            return params.isEmpty() ? "" : "?".concat(String.join("&", params));
        }
    }

}
