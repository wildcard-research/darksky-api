package com.github.wildcardresearch.darkskyapi.model;

import lombok.Data;

@Data
public class Currently extends DataPoint {
    public Double nearestStormBearing;
    public Double nearestStormDistance;
}
