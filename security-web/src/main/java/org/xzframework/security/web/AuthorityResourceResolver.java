package org.xzframework.security.web;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthorityResourceResolver {

    Collection<WebResource> resolve(GrantedAuthority authority);

}
