package org.xzframework.data.jpa.domain;

import java.io.Serial;
import java.io.Serializable;

public class Auditor<ID extends Serializable, NAME extends Serializable> implements Serializable {
    @Serial
    private static final long serialVersionUID = 7655207902942591166L;

    private final ID userid;

    private final NAME username;

    public Auditor(ID userid, NAME username) {
        this.userid = userid;
        this.username = username;
    }

    public Auditor() {
        this(null, null);
    }

    public ID getUserid() {
        return userid;
    }

    public NAME getUsername() {
        return username;
    }
}
