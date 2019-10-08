package com.github.wildcardresearch.darkskyapi.model;

import lombok.Data;

@Data
public class Daily extends DataPoint {
    private Double apparentTemperatureHigh;
    private Long apparentTemperatureHighTime;
    private Double apparentTemperatureLow;
    private Long apparentTemperatureLowTime;
    private Double apparentTemperatureMax;
    private Long apparentTemperatureMaxTime;
    private Double apparentTemperatureMin;
    private Long apparentTemperatureMinTime;
    private Double moonPhase;
    private Double precipAccumulation;
    private Double precipIntensityMax;
    private Double precipIntensityMaxTime;
    private Long sunriseTime;
    private Long sunsetTime;
    private Double temperatureHigh;
    private Long temperatureHighTime;
    private Double temperatureLow;
    private Long temperatureLowTime;
    private Double temperatureMax;
    private Long temperatureMaxTime;
    private Double temperatureMin;
    private Long temperatureMinTime;
    private Long uvIndexTime;
    private Double windGustTime;
}
