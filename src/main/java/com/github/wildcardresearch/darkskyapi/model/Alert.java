package com.github.wildcardresearch.darkskyapi.model;

import lombok.Data;

import java.util.List;

@Data
public class Alert {
    private String description;
    private Long expires;
    private List<String> regions;
    private String severity;
    private Long time;
    private String title;
    private String uri;
}
