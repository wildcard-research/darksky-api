package com.github.wildcardresearch.darkskyapi;

import com.github.wildcardresearch.darkskyapi.exception.ForcastException;
import com.github.wildcardresearch.darkskyapi.model.ForcastResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ForcastRequestTests {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private static final String key = "b1f3229d219d692f2bb50f578d4feb70";

    @Test(expected = AssertionError.class)
    public void nullKey() {
        ForcastRequest.builder(null).build();
    }

    @Test
    public void incorrectKey() {
        expectedEx.expect(ForcastException.class);
        expectedEx.expectMessage("Unauthorized request");

        ForcastRequest request = ForcastRequest.builder("incorrect").build();
        request.get(0,0);
    }
}
