package org.xzframework.data.jpa.domain;

import jakarta.persistence.Embeddable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Auditor<UID extends Serializable & Comparable<UID>> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7655207902942591166L;

    private UID userid;

    private String username;

    public Auditor(@NonNull UID userid, @NonNull String username) {
        this.userid = userid;
        this.username = username;
    }

    protected Auditor() {
    }

    @Nullable
    public UID getUserid() {
        return userid;
    }

    private void setUserid(@NonNull UID userid) {
        this.userid = userid;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    private void setUsername(@NonNull String username) {
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
