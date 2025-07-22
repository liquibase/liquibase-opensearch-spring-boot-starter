package liquibase.ext.opensearch.integration.spring;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;

@AutoConfiguration
@EnableConfigurationProperties({SpringLiquibaseOpenSearchProperties.class, LiquibaseProperties.class})
@ImportRuntimeHints(LiquibaseOpenSearchAutoConfiguration.LiquibaseAutoConfigurationRuntimeHints.class)
public class LiquibaseOpenSearchAutoConfiguration {
    @Bean
    public SpringLiquibaseOpenSearch springLiquibaseOpenSearch(final OpenSearchClient openSearchClient, final SpringLiquibaseOpenSearchProperties properties, final LiquibaseProperties liquibaseProperties) {
        return new SpringLiquibaseOpenSearch(openSearchClient, properties, liquibaseProperties);
    }

    static class LiquibaseAutoConfigurationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("db/changelog/*");
        }
    }
}
