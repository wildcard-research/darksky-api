package com.github.wildcardresearch.darkskyapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flag {
    private List<String> sources;
    private String units;
}
