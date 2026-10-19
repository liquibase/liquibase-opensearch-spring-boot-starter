package liquibase.ext.opensearch.integration.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The subset of the properties of the standard Spring Boot Liquibase integration which also apply to this integration.
 *
 * <p>
 * These are bound to the same keys as in Spring Boot's {@code LiquibaseProperties}, so that they only have to be
 * configured once. They are defined here to not depend on the standard Spring Boot Liquibase integration (which would
 * pull in JDBC).
 * </p>
 *
 * @param analyticsEnabled Whether to send product usage data and analytics to Liquibase.
 * @param licenseKey       Liquibase Pro license key.
 */
@ConfigurationProperties("spring.liquibase")
public record SharedLiquibaseProperties(
        Boolean analyticsEnabled,

        String licenseKey
) {
}
