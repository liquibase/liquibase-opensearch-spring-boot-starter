package liquibase.ext.opensearch.integration.spring;

import liquibase.integration.spring.SpringLiquibase;
import liquibase.ui.UIServiceEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseProperties;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Ensures that {@link SharedLiquibaseProperties} uses the same keys and defaults as Spring Boot's
 * {@link LiquibaseProperties}.
 */
class SharedLiquibasePropertiesTest {

    @Test
    void usesTheSameKeysAsSpringBoot() {
        final var binder = new Binder(new MapConfigurationPropertySource(Map.of(
                "spring.liquibase.analytics-enabled", "false",
                "spring.liquibase.license-key", "test-license-key",
                "spring.liquibase.ui-service", "console")));

        final var springBootProperties = binder.bind("spring.liquibase", LiquibaseProperties.class).get();
        final var sharedProperties = binder.bind("spring.liquibase", SharedLiquibaseProperties.class).get();

        assertThat(springBootProperties.getAnalyticsEnabled()).isFalse();
        assertThat(springBootProperties.getLicenseKey()).isEqualTo("test-license-key");
        assertThat(springBootProperties.getUiService()).isEqualTo(LiquibaseProperties.UiService.CONSOLE);
        assertThat(sharedProperties.analyticsEnabled()).isEqualTo(springBootProperties.getAnalyticsEnabled());
        assertThat(sharedProperties.licenseKey()).isEqualTo(springBootProperties.getLicenseKey());
        assertThat(sharedProperties.uiService().name()).isEqualTo(springBootProperties.getUiService().name());
    }

    @Test
    void usesTheSameDefaultsAsSpringBoot() {
        final var binder = new Binder(new MapConfigurationPropertySource());

        final var springBootProperties = binder.bindOrCreate("spring.liquibase", LiquibaseProperties.class);
        final var sharedProperties = binder.bindOrCreate("spring.liquibase", SharedLiquibaseProperties.class);

        assertThat(sharedProperties.analyticsEnabled()).isEqualTo(springBootProperties.getAnalyticsEnabled());
        assertThat(sharedProperties.licenseKey()).isEqualTo(springBootProperties.getLicenseKey());
        // if not set, Spring Boot keeps the default of `SpringLiquibase`
        final var expectedUiService = springBootProperties.getUiService() != null
                ? UIServiceEnum.valueOf(springBootProperties.getUiService().name())
                : new SpringLiquibase().getUiService();
        assertThat(sharedProperties.uiService()).isEqualTo(expectedUiService);
    }

}
