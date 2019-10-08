package com.github.wildcardresearch.darkskyapi.model;

import lombok.Data;

@Data
public class DataPoint {
    private Double apparentTemperature;
    private Double precipAccumulation;
    private Double cloudCover;
    private Double dewPoint;
    private Double humidity;
    private String icon;
    private Double ozone;
    private Double precipIntensity;
    private Double precipIntensityError;
    private Double precipProbability;
    private String precipType;
    private Double pressure;
    private String summary;
    private Double temperature;
    private Long time;
    private Double uvIndex;
    private Double visibility;
    private Double windBearing;
    private Double windGust;
    private Double windSpeed;
}
