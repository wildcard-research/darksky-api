package com.github.wildcardresearch.darkskyapi;

import com.github.wildcardresearch.darkskyapi.model.ForcastResponse;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ForcastRequestTests {

    private static final String key = "b1f3229d219d692f2bb50f578d4feb70";

    @Test
    public void test1() throws IOException, InterruptedException {

        ForcastRequest request = ForcastRequest.builder(key).build();

        ForcastResponse response = request.get(37.8267,-122.4233);
        assertNotNull(response);
    }
}
