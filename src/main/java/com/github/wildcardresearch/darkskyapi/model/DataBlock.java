package com.github.wildcardresearch.darkskyapi.model;

import lombok.Data;

import java.util.List;

@Data
public class DataBlock<T> {
    private String summary;
    private String icon;
    private List<T> data;
}
