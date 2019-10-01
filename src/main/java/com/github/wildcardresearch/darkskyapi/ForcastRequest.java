package com.github.wildcardresearch.darkskyapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wildcardresearch.darkskyapi.exception.ForcastException;
import com.github.wildcardresearch.darkskyapi.model.ForcastResponse;
import com.github.wildcardresearch.darkskyapi.client.DarkSkyHttpClient;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.wildcardresearch.darkskyapi.util.Assert.notNull;
import static com.github.wildcardresearch.darkskyapi.util.Assert.notNullOrEmpty;

public class ForcastRequest {

    private static final String KEY_KEY = "#key#";
    private static final String LAT_KEY = "#latitude#";
    private static final String LON_KEY = "#longitude#";
    private static final String TIME_KEY = "#time#";
    private static final String COMMA_DELIMITER = ",";
    private static final String URL = "https://api.darksky.net/forecast/" + KEY_KEY + "/" + LAT_KEY + COMMA_DELIMITER + LON_KEY + TIME_KEY;

    private final String url;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private ForcastRequest(final String url) {
        this.url = url;
    }

    public ForcastResponse get(double lat, double lon) {
        return get(String.valueOf(lat), String.valueOf(lon), "");
    }

    public ForcastResponse get(double lat, double lon, long time) {
        return get(String.valueOf(lat), String.valueOf(lon), String.valueOf(time));
    }

    public URI getUri(double lat, double lon){
        return getUri(String.valueOf(lat), String.valueOf(lon), "");
    }

    public URI getUri(double lat, double lon, long time){
        return getUri(String.valueOf(lat), String.valueOf(lon), String.valueOf(time));
    }

    private URI getUri(String lat, String lon, String time) {
        return URI.create(
                this.url.replaceAll(LAT_KEY, lat)
                        .replaceAll(LON_KEY, lon)
                        .replaceAll(TIME_KEY, time.isEmpty() ? time : COMMA_DELIMITER + time)
        );
    }

    private ForcastResponse get(String lat, String lon, String time) {
        try {
            HttpResponse<String> httpResponse = DarkSkyHttpClient.get(getUri(lat, lon, time));
            return MAPPER.readValue(httpResponse.body(), ForcastResponse.class);
        } catch (JsonProcessingException ex) {
            throw new ForcastException("Cannot convert HttpResponse body to ForcastResponse", ex);
        }
    }

    public static ForcastRequestBuilder builder(String apiKey) {
        return new ForcastRequestBuilder(apiKey);
    }

    public static class ForcastRequestBuilder {

        private String apiKey;
        private Set<Block> excludeBlocks;
        private Boolean extend;
        private Language language;
        private Units units;

        public ForcastRequestBuilder(String apiKey) { apiKey(apiKey); }

        public ForcastRequestBuilder apiKey(String apiKey) {
            notNullOrEmpty(apiKey);
            this.apiKey = apiKey;
            return this;
        }

        public ForcastRequestBuilder exclude(Block... blocks){
            notNull(blocks, "Exclusion blocks cannot be null in ForcastRequestBuilder");
            this.excludeBlocks = Set.of(blocks);
            return this;
        }

        public ForcastRequestBuilder extendHourly() {
            this.extend = true;
            return this;
        }

        public ForcastRequestBuilder lang(Language lang) {
            notNull(lang, "Language cannot be null in ForcastRequestBuilder");
            this.language = lang;
            return this;
        }

        public ForcastRequestBuilder units(Units units) {
            notNull(units, "Units cannot be null in ForcastRequestBuilder");
            this.units = units;
            return this;
        }

        public ForcastRequest build() {
            return new ForcastRequest(getUrl());
        }

        private String getUrl() {
            return URL
                    .replaceAll(KEY_KEY, this.apiKey)
                    + parameters();
        }

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

    public enum Block { currently, minutely, hourly, daily, alerts, flags }
    public enum Language { ar, az, be, bg, gn, bs, ca, cs, da, de, el, en, eo, es, et, fi, fr, he, hi, hr, hu, id, is, it, ja, ka, kn, ko, kw, lv, ml, mr, nb, nl, no, pa, pl, pt, ro, ru, sk, sl, sr, sv, ta, te, tet, tr, uk, ur,  zh}
    public enum Units { auto, ca, uk2, us, si }

}
