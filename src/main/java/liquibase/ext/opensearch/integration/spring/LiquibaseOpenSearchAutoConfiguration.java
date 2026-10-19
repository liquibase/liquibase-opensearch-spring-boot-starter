package liquibase.ext.opensearch.integration.spring;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportRuntimeHints;

@AutoConfiguration(afterName = "org.opensearch.spring.boot.autoconfigure.OpenSearchClientAutoConfiguration")
@ConditionalOnClass(OpenSearchClient.class)
@EnableConfigurationProperties({SpringLiquibaseOpenSearchProperties.class, SharedLiquibaseProperties.class})
@Import(DatabaseInitializationDependencyConfigurer.class)
@ImportRuntimeHints(LiquibaseOpenSearchAutoConfiguration.LiquibaseAutoConfigurationRuntimeHints.class)
public class LiquibaseOpenSearchAutoConfiguration {
    @Bean
    @ConditionalOnBean(OpenSearchClient.class)
    @ConditionalOnMissingBean
    public SpringLiquibaseOpenSearch springLiquibaseOpenSearch(final OpenSearchClient openSearchClient, final SpringLiquibaseOpenSearchProperties properties, final SharedLiquibaseProperties liquibaseProperties) {
        return new SpringLiquibaseOpenSearch(openSearchClient, properties, liquibaseProperties);
    }

    static class LiquibaseAutoConfigurationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("db/changelog/*");
        }
    }
}
