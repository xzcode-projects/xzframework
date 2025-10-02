package org.xzframework.data.jpa.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Auditor<UID extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7655207902942591166L;

    private UID userid;

    private String username;

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

    private void setUserid(UID userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
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
