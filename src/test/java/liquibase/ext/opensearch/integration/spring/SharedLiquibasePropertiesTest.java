package liquibase.ext.opensearch.integration.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseProperties;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Ensures that {@link SharedLiquibaseProperties} uses the same keys as Spring Boot's {@link LiquibaseProperties}.
 */
class SharedLiquibasePropertiesTest {

    @Test
    void usesTheSameKeysAsSpringBoot() {
        final var binder = new Binder(new MapConfigurationPropertySource(Map.of(
                "spring.liquibase.analytics-enabled", "false",
                "spring.liquibase.license-key", "test-license-key")));

        final var springBootProperties = binder.bind("spring.liquibase", LiquibaseProperties.class).get();
        final var sharedProperties = binder.bind("spring.liquibase", SharedLiquibaseProperties.class).get();

        assertThat(springBootProperties.getAnalyticsEnabled()).isFalse();
        assertThat(springBootProperties.getLicenseKey()).isEqualTo("test-license-key");
        assertThat(sharedProperties.analyticsEnabled()).isEqualTo(springBootProperties.getAnalyticsEnabled());
        assertThat(sharedProperties.licenseKey()).isEqualTo(springBootProperties.getLicenseKey());
    }

}
