# darksky-api

A Java library for the [Dark Sky API](https://darksky.net/dev). Maintained independently and not associated with Dark Sky. The library is fully implemented except for the following:
* Language `x-pig-latin` for Pig Latin
* Language `zh-tw` for traditional Chinese

### Language requirements
Java 11

### Build
`mvn clean package`

### Maven dependency
Todo

### Usage Examples
Get current cloud cover using built in ForcastResponse class:
```java
ForcastRequest request = ForcastRequest
    .builder("testkey")
    .exclude(ForcastRequest.Block.alerts, ForcastRequest.Block.flags)
    .lang(ForcastRequest.Language.ca)
    .units(ForcastRequest.Units.si)
    .extendHourly()
    .build();

ForcastResponse res = request.getAsForcastResponse(40.7128, -74.006);
System.out.println("Current cloud cover: " + res.getCurrently().getCloudCover());
```

Get raw response body:
```java
ForcastRequest request = ForcastRequest
    .builder("testkey")
    .lang(ForcastRequest.Language.ja)
    .build();

String res = request.get(35.6762, 139.6503);
System.out.println("Raw response: " + res);
```

Get URI for requests:
```java
ForcastRequest request = ForcastRequest
    .builder("testkey")
    .lang(ForcastRequest.Language.es)
    .extendHourly()
    .build();

URI res = request.getUri(40.4637, 3.7492, 255657600);
System.out.println("We can make a request using " + res.toString());
```

### Documentation
JavaDocs located on [Github](https://wildcard-research.github.io/darksky-api/)

### Issues
Report any issues in [Github](https://github.com/wildcard-research/darksky-api/issues).

### Acknowledgments 
[![Powered by Dark Sky](https://darksky.net/dev/img/attribution/poweredby.png)](https://darksky.net/poweredby/)
To see more about forcast request and response formats, view the Dark Sky API [documentation](https://darksky.net/dev/docs).