package org.xzframework.activity.impl.embedded;

import jakarta.persistence.Embeddable;
import org.xzframework.activity.define.model.User;

@Embeddable
public class EmbeddedUser implements User {

    private Long id;

    private String name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
