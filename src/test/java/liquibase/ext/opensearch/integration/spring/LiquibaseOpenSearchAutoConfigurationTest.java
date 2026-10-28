package liquibase.ext.opensearch.integration.spring;

import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LiquibaseOpenSearchAutoConfigurationTest {

    /**
     * The migration is disabled as it would otherwise be executed against the mocked client.
     */
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(LiquibaseOpenSearchAutoConfiguration.class))
            .withBean(OpenSearchClient.class, () -> mock(OpenSearchClient.class))
            .withPropertyValues("opensearch.liquibase.enabled=false");

    @Test
    void isRegisteredAsAutoConfiguration() {
        final var candidates = ImportCandidates.load(AutoConfiguration.class, getClass().getClassLoader());
        assertThat(candidates.getCandidates()).contains(LiquibaseOpenSearchAutoConfiguration.class.getName());
    }

    @Test
    void createsTheBeans() {
        this.contextRunner.run(context -> assertThat(context)
                .hasNotFailed()
                .hasSingleBean(SpringLiquibaseOpenSearch.class)
                .hasSingleBean(SpringLiquibaseOpenSearchProperties.class));
    }

    @Test
    void backsOffWithoutOpenSearchClient() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LiquibaseOpenSearchAutoConfiguration.class))
                .run(context -> assertThat(context)
                        .hasNotFailed()
                        .doesNotHaveBean(SpringLiquibaseOpenSearch.class));
    }

    @Test
    void backsOffIfUserDefinesTheBean() {
        final var userBean = mock(SpringLiquibaseOpenSearch.class);
        this.contextRunner
                .withBean("customSpringLiquibaseOpenSearch", SpringLiquibaseOpenSearch.class, () -> userBean)
                .run(context -> assertThat(context)
                        .hasNotFailed()
                        .hasSingleBean(SpringLiquibaseOpenSearch.class)
                        .getBean(SpringLiquibaseOpenSearch.class).isSameAs(userBean));
    }

    @Test
    void bindsTheDefaultProperties() {
        this.contextRunner.run(context -> {
            final var properties = context.getBean(SpringLiquibaseOpenSearchProperties.class);
            assertThat(properties.changelogFile()).isEqualTo("db/changelog/db.changelog-master.yaml");
            assertThat(properties.contexts()).isNull();
            assertThat(properties.parameters()).isNull();
            assertThat(properties.labelFilterArgs()).isNull();
            assertThat(properties.databaseChangeLogIndex()).isEqualTo("databasechangelog");
            assertThat(properties.databaseChangeLogLockIndex()).isEqualTo("databasechangeloglock");
        });
    }

    @Test
    void isEnabledByDefault() {
        new ApplicationContextRunner()
                .withUserConfiguration(PropertiesOnlyConfiguration.class)
                .run(context -> assertThat(context.getBean(SpringLiquibaseOpenSearchProperties.class).enabled()).isTrue());
    }

    @Test
    void bindsCustomProperties() {
        this.contextRunner
                .withPropertyValues(
                        "opensearch.liquibase.changelog-file=custom/changelog.yaml",
                        "opensearch.liquibase.contexts=ctx1,ctx2",
                        "opensearch.liquibase.parameters.param1=value1",
                        "opensearch.liquibase.parameters.param2=value2",
                        "opensearch.liquibase.label-filter-args=label1 and !label2",
                        "opensearch.liquibase.database-change-log-index=customchangelog",
                        "opensearch.liquibase.database-change-log-lock-index=customchangeloglock")
                .run(context -> {
                    final var properties = context.getBean(SpringLiquibaseOpenSearchProperties.class);
                    assertThat(properties.enabled()).isFalse();
                    assertThat(properties.changelogFile()).isEqualTo("custom/changelog.yaml");
                    assertThat(properties.contexts()).isEqualTo("ctx1,ctx2");
                    assertThat(properties.parameters()).isEqualTo(Map.of("param1", "value1", "param2", "value2"));
                    assertThat(properties.labelFilterArgs()).isEqualTo("label1 and !label2");
                    assertThat(properties.databaseChangeLogIndex()).isEqualTo("customchangelog");
                    assertThat(properties.databaseChangeLogLockIndex()).isEqualTo("customchangeloglock");
                });
    }

    @EnableConfigurationProperties(SpringLiquibaseOpenSearchProperties.class)
    static class PropertiesOnlyConfiguration {
    }

}
