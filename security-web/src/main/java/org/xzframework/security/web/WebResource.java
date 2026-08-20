package org.xzframework.security.web;

import org.springframework.http.HttpMethod;

import java.io.Serializable;

public record WebResource(MatchType match, String url, HttpMethod method) implements Serializable {

    public WebResource() {
        this(MatchType.ANT_PATH, null, null);
    }

}
