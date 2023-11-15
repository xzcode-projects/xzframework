package org.xzframework.activity.impl.embedded;

import jakarta.persistence.Embeddable;
import org.xzframework.activity.define.model.User;

@Embeddable
public class EmbeddedUser implements User {

    private Long id;

    private String name;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public Long getName() {
        return null;
    }
}
