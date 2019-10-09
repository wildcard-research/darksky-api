package com.github.wildcardresearch.darkskyapi;

import com.github.wildcardresearch.darkskyapi.exception.ForcastException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExceptionTests {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test(expected = AssertionError.class)
    public void nullApiKey() {
        ForcastRequest.builder(null).build();
    }

    @Test
    public void incorrectApiKey() {
        expectedEx.expect(ForcastException.class);
        expectedEx.expectMessage("Unauthorized request. API Key could be incorrect.");

        ForcastRequest request = ForcastRequest.builder("incorrect").build();
        request.get(0,0);
    }

    @Test
    public void nullExcludeBlock() {
        expectedEx.expect(AssertionError.class);
        expectedEx.expectMessage("Exclusion blocks cannot be null in ForcastRequestBuilder.");

        ForcastRequest.builder("testkey").exclude(null).build();
    }

    @Test
    public void nullExcludeBlocks() {
        expectedEx.expect(AssertionError.class);
        expectedEx.expectMessage("Exclusion blocks cannot be null in ForcastRequestBuilder.");

        ForcastRequest.builder("testkey").exclude(Block.alerts, null).build();
    }

    @Test
    public void nullLang() {
        expectedEx.expect(AssertionError.class);
        expectedEx.expectMessage("Language cannot be null in ForcastRequestBuilder.");

        ForcastRequest.builder("testkey").lang(null).build();
    }

    @Test
    public void nullUnits() {
        expectedEx.expect(AssertionError.class);
        expectedEx.expectMessage("Units cannot be null in ForcastRequestBuilder.");

        ForcastRequest.builder("testkey").units(null).build();
    }

}
