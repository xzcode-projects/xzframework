package org.xzframework.data.autoconfigure;

import org.hibernate.annotations.EmbeddedTable;
import org.hibernate.boot.models.annotations.internal.EmbeddedTableAnnotation;
import org.hibernate.event.spi.PreFlushEventListener;
import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.*;

import java.util.Set;

public class Hibernate72RuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
        registerLoggerHints(hints.reflection());
        registerEventHints(hints.reflection());
        registerAnnotationHints(hints.reflection());
    }

    private void registerLoggerHints(ReflectionHints reflection) {
        reflection.registerTypes(Set.of(TypeReference.of("org.hibernate.action.internal.ActionLogging_$logger"),
                        TypeReference.of("org.hibernate.boot.BootLogging_$logger"),
                        TypeReference.of("org.hibernate.boot.beanvalidation.BeanValidationLogger_$logger"),
                        TypeReference
                                .of("org.hibernate.bytecode.enhance.spi.interceptor.BytecodeInterceptorLogging_$logger"),
                        TypeReference.of("org.hibernate.collection.internal.CollectionLogger_$logger"),
                        TypeReference.of("org.hibernate.context.internal.CurrentSessionLogging_$logger"),
                        TypeReference.of("org.hibernate.engine.internal.NaturalIdLogging_$logger"),
                        TypeReference.of("org.hibernate.engine.internal.PersistenceContextLogging_$logger"),
                        TypeReference.of("org.hibernate.engine.internal.SessionMetricsLogger_$logger"),
                        TypeReference.of("org.hibernate.engine.jdbc.JdbcLogging_$logger"),
                        TypeReference.of("org.hibernate.engine.jdbc.batch.JdbcBatchLogging_$logger"),
                        TypeReference
                                .of("org.hibernate.engine.jdbc.connections.internal.ConnectionProviderLogging_$logger"),
                        TypeReference.of("org.hibernate.engine.jdbc.env.internal.LobCreationLogging_$logger"),
                        TypeReference.of("org.hibernate.engine.jdbc.spi.SQLExceptionLogging_$logger"),
                        TypeReference.of("org.hibernate.event.internal.EntityCopyLogging_$logger"),
                        TypeReference.of("org.hibernate.event.internal.EventListenerLogging_$logger"),
                        TypeReference.of("org.hibernate.id.UUIDLogger_$logger"),
                        TypeReference.of("org.hibernate.id.enhanced.OptimizerLogger_$logger"),
                        TypeReference.of("org.hibernate.internal.SessionFactoryLogging_$logger"),
                        TypeReference.of("org.hibernate.internal.SessionLogging_$logger"),
                        TypeReference.of("org.hibernate.internal.log.StatisticsLogger_$logger"),
                        TypeReference.of("org.hibernate.jpa.internal.JpaLogger_$logger"),
                        TypeReference.of("org.hibernate.loader.ast.internal.MultiKeyLoadLogging_$logger"),
                        TypeReference.of("org.hibernate.metamodel.mapping.MappingModelCreationLogging_$logger"),
                        TypeReference.of("org.hibernate.query.QueryLogging_$logger"),
                        TypeReference.of("org.hibernate.query.hql.HqlLogging_$logger"),
                        TypeReference.of("org.hibernate.resource.jdbc.internal.LogicalConnectionLogging_$logger"),
                        TypeReference.of("org.hibernate.resource.transaction.backend.jta.internal.JtaLogging_$logger"),
                        TypeReference.of("org.hibernate.resource.transaction.internal.SynchronizationLogging_$logger"),
                        TypeReference.of("org.hibernate.service.internal.ServiceLogger_$logger"),
                        TypeReference.of("org.hibernate.sql.model.ModelMutationLogging_$logger"),
                        TypeReference.of("org.hibernate.sql.results.graph.embeddable.EmbeddableLoadingLogger_$logger")),
                (hint) -> hint.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                        MemberCategory.INVOKE_PUBLIC_METHODS));
    }

    private void registerEventHints(ReflectionHints reflection) {
        reflection.registerType(PreFlushEventListener.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                MemberCategory.INVOKE_PUBLIC_METHODS);
        reflection.registerType(PreFlushEventListener[].class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                MemberCategory.INVOKE_PUBLIC_METHODS);
    }

    private void registerAnnotationHints(ReflectionHints reflection) {
        reflection.registerType(EmbeddedTable.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        reflection.registerType(EmbeddedTableAnnotation.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                MemberCategory.INVOKE_PUBLIC_METHODS);
    }

}
