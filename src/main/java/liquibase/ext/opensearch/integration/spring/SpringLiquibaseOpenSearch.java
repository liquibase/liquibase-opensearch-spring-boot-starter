package liquibase.ext.opensearch.integration.spring;

import liquibase.Scope;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.analytics.configuration.AnalyticsArgs;
import liquibase.changelog.ChangeLogParameters;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.command.core.helpers.ShowSummaryArgument;
import liquibase.database.ConnectionServiceFactory;
import liquibase.database.DatabaseFactory;
import liquibase.ext.opensearch.database.OpenSearchConnection;
import liquibase.ext.opensearch.database.OpenSearchLiquibaseDatabase;
import liquibase.integration.IntegrationDetails;
import liquibase.logging.Logger;
import liquibase.ui.UIServiceEnum;
import lombok.Getter;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * A Spring wrapper around liquibase-opensearch.
 *
 * <p>
 * This automatically executes the changelog using the provided OpenSearch client.
 * Use this with Spring Boot and the provided {@link LiquibaseOpenSearchAutoConfiguration}.
 * </p>
 */
@Component
public class SpringLiquibaseOpenSearch implements InitializingBean {

    protected final Logger log = Scope.getCurrentScope().getLog(SpringLiquibaseOpenSearch.class);

    private final OpenSearchClient openSearchClient;

    private final SpringLiquibaseOpenSearchProperties properties;
    private final LiquibaseProperties liquibaseProperties;

    @Getter
    protected UIServiceEnum uiService = UIServiceEnum.LOGGER;

    public SpringLiquibaseOpenSearch(final OpenSearchClient openSearchClient, final SpringLiquibaseOpenSearchProperties properties, final LiquibaseProperties liquibaseProperties) {
        this.openSearchClient = openSearchClient;
        this.properties = properties;
        this.liquibaseProperties = liquibaseProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!properties.enabled()) {
            log.info("liquibase-opensearch did not run because it is disabled via configuration (opensearch.liquibase.enabled)");
            return;
        }

        Map<String, Object> scopeVars = new HashMap<>();
        scopeVars.put(Scope.Attr.ui.name(), this.uiService.getUiServiceClass().getDeclaredConstructor().newInstance());
        scopeVars.put(Scope.Attr.integrationDetails.name(), new IntegrationDetails("liquibase-opensearch-spring-boot-starter"));

        scopeVars.put(AnalyticsArgs.ENABLED.getKey(), this.liquibaseProperties.getAnalyticsEnabled());
        scopeVars.put(Scope.Attr.maxAnalyticsCacheSize.name(), 10);

        if (this.liquibaseProperties.getLicenseKey() != null) {
            log.fine("Using PRO licenseKey.");
            scopeVars.put("liquibase.licenseKey", this.liquibaseProperties.getLicenseKey());
        }

        Scope.child(scopeVars,
                () -> {
                    // we want to re-use the connection which spring-data-opensearch (or somebody else) already constructed
                    // => do not rely on liquibase' standard mechanism of constructing a new connection, instead we force it to take our own.
                    final var connection = new OpenSearchConnection(openSearchClient);
                    ConnectionServiceFactory.getInstance().register(connection);
                    final var database = new OpenSearchLiquibaseDatabase();
                    database.setConnection(connection);
                    DatabaseFactory.getInstance().register(database);
                    final var changeLogParameters = new ChangeLogParameters(database);
                    if (properties.parameters() != null) {
                        for (var e : properties.parameters().entrySet()) {
                            changeLogParameters.set(e.getKey(), e.getValue());
                        }
                    }

                    new CommandScope(UpdateCommandStep.COMMAND_NAME)
                            .addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY_OUTPUT, UpdateSummaryOutputEnum.LOG)
                            .addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database)
                            .addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, properties.changelogFile())
                            .addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, changeLogParameters)
                            .addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, properties.contexts())
                            .addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, properties.labelFilterArgs())
                            .execute();
                    connection.close();
                    database.close();
                });
    }

}
