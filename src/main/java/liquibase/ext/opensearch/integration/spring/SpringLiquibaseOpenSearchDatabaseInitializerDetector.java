package liquibase.ext.opensearch.integration.spring;

import org.springframework.boot.sql.init.dependency.AbstractBeansOfTypeDatabaseInitializerDetector;
import org.springframework.boot.sql.init.dependency.DatabaseInitializerDetector;

import java.util.Set;

/**
 * {@link DatabaseInitializerDetector} for {@link SpringLiquibaseOpenSearch}, so that beans annotated with
 * {@link org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization @DependsOnDatabaseInitialization}
 * are only created once the OpenSearch migration has run.
 */
class SpringLiquibaseOpenSearchDatabaseInitializerDetector extends AbstractBeansOfTypeDatabaseInitializerDetector {

    @Override
    protected Set<Class<?>> getDatabaseInitializerBeanTypes() {
        return Set.of(SpringLiquibaseOpenSearch.class);
    }

}
