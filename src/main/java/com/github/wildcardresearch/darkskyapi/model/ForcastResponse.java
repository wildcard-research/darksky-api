package com.github.wildcardresearch.darkskyapi.model;

import lombok.Data;

import java.util.List;

@Data
public class ForcastResponse {
    private double latitude;
    private double longitude;
    private String timezone;
    private Currently currently;
    private DataBlock<Minutely> minutely;
    private DataBlock<Hourly> hourly;
    private DataBlock<Daily> daily;
    private List<Alert> alerts;
    private Flag flags;
    private Integer offset;
}
