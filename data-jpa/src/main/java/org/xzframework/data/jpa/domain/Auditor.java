package org.xzframework.data.jpa.domain;

import java.io.Serial;
import java.io.Serializable;

public class Auditor<UID extends Serializable> implements Serializable {
    @Serial
    private static final long serialVersionUID = 7655207902942591166L;

    private final UID userid;

    private final String username;

    public Auditor(UID userid, String username) {
        this.userid = userid;
        this.username = username;
    }

    public Auditor() {
        this(null, null);
    }

    public UID getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }
}
