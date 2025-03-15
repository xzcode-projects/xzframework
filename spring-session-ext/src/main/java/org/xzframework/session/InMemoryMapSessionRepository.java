package org.xzframework.session;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.session.*;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InMemoryMapSessionRepository implements SessionRepository<MapSession>, FindByIndexNameSessionRepository<MapSession>, InitializingBean {

    private final Map<String, Session> sessions;

    private final Map<String, Set<String>> sessionsPrincipalNameIndex;

    private final IndexResolver<Session> indexResolver = new DelegatingIndexResolver<>(new PrincipalNameIndexResolver<>());

    private Duration defaultMaxInactiveInterval = Duration.ofSeconds(MapSession.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS);

    private SessionIdGenerator sessionIdGenerator = UuidSessionIdGenerator.getInstance();


    /**
     * Creates a new instance backed by the provided {@link Map}. This allows
     * injecting a distributed {@link Map}.
     */
    public InMemoryMapSessionRepository() {
        sessions = new ConcurrentHashMap<>();
        sessionsPrincipalNameIndex = new ConcurrentHashMap<>();
    }

    /**
     * Set the maximum inactive interval in seconds between requests before newly created
     * sessions will be invalidated. A negative time indicates that the session will never
     * time out. The default is 30 minutes.
     *
     * @param defaultMaxInactiveInterval the default maxInactiveInterval
     */
    public void setDefaultMaxInactiveInterval(Duration defaultMaxInactiveInterval) {
        Assert.notNull(defaultMaxInactiveInterval, "defaultMaxInactiveInterval must not be null");
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }

    @Override
    public void save(MapSession session) {
        if (!session.getId().equals(session.getOriginalId())) {
            sessions.remove(session.getOriginalId());
        }
        MapSession saved = new MapSession(session);
        saved.setSessionIdGenerator(sessionIdGenerator);
        sessions.put(session.getId(), saved);
        createIndex(saved);
    }

    @Override
    public MapSession findById(String id) {
        Session saved = sessions.get(id);
        if (saved == null) {
            return null;
        }

        if (saved.isExpired()) {
            deleteById(saved.getId());
            return null;
        }
        MapSession result = new MapSession(saved);
        result.setSessionIdGenerator(sessionIdGenerator);
        return result;
    }

    @Override
    public void deleteById(String id) {
        deleteIndex(findById(id));
        sessions.remove(id);
    }

    @Override
    public MapSession createSession() {
        MapSession result = new MapSession(sessionIdGenerator);
        result.setMaxInactiveInterval(defaultMaxInactiveInterval);
        return result;
    }

    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        Assert.notNull(sessionIdGenerator, "sessionIdGenerator cannot be null");
        this.sessionIdGenerator = sessionIdGenerator;
    }

    private void createIndex(Session session) {
        Map<String, String> indexes = indexResolver.resolveIndexesFor(session);
        String index = indexes.get(PRINCIPAL_NAME_INDEX_NAME);
        if (index != null) {
            Set<String> set = sessionsPrincipalNameIndex.get(index);
            if (set == null) {
                sessionsPrincipalNameIndex.put(index, Set.of(session.getId()));
            } else if (!set.contains(session.getId())) {
                Set<String> newSet = new LinkedHashSet<>(set.size() + 1);
                newSet.addAll(set);
                newSet.add(session.getId());
                sessionsPrincipalNameIndex.put(index, newSet);
            }
        }
    }

    private void deleteIndex(Session session) {
        Map<String, String> indexes = indexResolver.resolveIndexesFor(session);
        String index = indexes.get(PRINCIPAL_NAME_INDEX_NAME);
        Set<String> set = sessionsPrincipalNameIndex.get(index);
        if (set != null) {
            Set<String> newSet = new LinkedHashSet<>(set);
            newSet.remove(session.getId());
            if (newSet.isEmpty()) {
                sessionsPrincipalNameIndex.remove(index);
            } else {
                sessionsPrincipalNameIndex.put(index, newSet);
            }
        }
    }

    @Override
    public Map<String, MapSession> findByIndexNameAndIndexValue(String indexName, String indexValue) {
        if (!PRINCIPAL_NAME_INDEX_NAME.equals(indexName) || indexValue == null || indexValue.isBlank()) {
            return Collections.emptyMap();
        }
        Set<String> sessionIds = sessionsPrincipalNameIndex.get(indexValue);
        if (sessionIds == null) {
            return Collections.emptyMap();
        } else {
            return sessionIds.stream().collect(Collectors.toMap(v -> v, this::findById));
        }
    }

    @Override
    public void afterPropertiesSet() {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            sessions.values().forEach(v -> {
                if (v.isExpired()) {
                    deleteById(v.getId());
                }
            });
        }, 1, 1, TimeUnit.MINUTES);
    }
}
