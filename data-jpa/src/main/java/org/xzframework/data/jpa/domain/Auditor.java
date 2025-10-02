package org.xzframework.data.jpa.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public record Auditor<UID extends Serializable>(UID userid, String username) implements Serializable {

    @Serial
    private static final long serialVersionUID = 7655207902942591166L;

    public Auditor() {
        this(null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Auditor<?> auditor)) return false;
        return Objects.equals(userid, auditor.userid) && Objects.equals(username, auditor.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, username);
    }

}
