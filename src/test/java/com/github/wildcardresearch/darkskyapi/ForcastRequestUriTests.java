package com.github.wildcardresearch.darkskyapi;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class ForcastRequestUriTests {

    @Test
    public void noTimeNoParams(){
        ForcastRequest request = ForcastRequest.builder("testkey").build();
        URI uri = request.getUri(1.23, 4.56);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56" , uri.toString());
    }

    @Test
    public void NoParams(){
        ForcastRequest request = ForcastRequest.builder("testkey").build();
        URI uri = request.getUri(1.23, 4.56, 9876);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56,9876" , uri.toString());
    }

    @Test
    public void noTimeExcludeBlock(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .exclude(Block.flags)
                .build();
        URI uri = request.getUri(1.23, 4.56);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56?exclude=flags" , uri.toString());
    }

    @Test
    public void noTimeExcludeBlocks(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .exclude(Block.flags, Block.alerts)
                .build();
        URI uri = request.getUri(1.23, 4.56);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56?exclude=flags,alerts" , uri.toString());
    }

    @Test
    public void excludeBlocks(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .exclude(Block.flags, Block.alerts)
                .build();
        URI uri = request.getUri(1.23, 4.56, 9876);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56,9876?exclude=flags,alerts" , uri.toString());
    }

    @Test
    public void noTimeExtendsHourly(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .extendHourly()
                .build();
        URI uri = request.getUri(1.23, 4.56);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56?extend=true" , uri.toString());
    }

    @Test
    public void extendsHourly(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .extendHourly()
                .build();
        URI uri = request.getUri(1.23, 4.56, 9876);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56,9876?extend=true" , uri.toString());
    }

    @Test
    public void noTimeLang(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .lang(Language.az)
                .build();
        URI uri = request.getUri(1.23, 4.56);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56?lang=az" , uri.toString());
    }

    @Test
    public void lang(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .lang(Language.ro)
                .build();
        URI uri = request.getUri(1.23, 4.56, 9876);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56,9876?lang=ro" , uri.toString());
    }

    @Test
    public void noTimeUnits(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .units(Units.auto)
                .build();
        URI uri = request.getUri(1.23, 4.56);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56?units=auto" , uri.toString());
    }

    @Test
    public void units(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .units(Units.uk2)
                .build();
        URI uri = request.getUri(1.23, 4.56, 9876);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56,9876?units=uk2" , uri.toString());
    }

    @Test
    public void noTimeAllParams(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .exclude(Block.alerts, Block.hourly)
                .extendHourly()
                .lang(Language.bg)
                .units(Units.auto)
                .build();
        URI uri = request.getUri(1.23, 4.56);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56?exclude=alerts,hourly&extend=true&lang=bg&units=auto" , uri.toString());
    }

    @Test
    public void allParams(){
        ForcastRequest request = ForcastRequest
                .builder("testkey")
                .exclude(Block.alerts, Block.currently)
                .lang(Language.ca)
                .units(Units.si)
                .extendHourly()
                .build();
        URI uri = request.getUri(1.23, 4.56, 9876);
        assertEquals("https://api.darksky.net/forecast/testkey/1.23,4.56,9876?exclude=alerts,currently&extend=true&lang=ca&units=si" , uri.toString());
    }
}
