# Liquibase Extension for OpenSearch: Spring Boot Starter

This is the [Spring Boot] starter for [`liquibase-opensearch`], which allows you to easily use Liquibase with OpenSearch
clusters in Spring Boot.

Note that the standard [Liquibase Spring Boot integration] does *not* work with OpenSearch. You have to use this
integration here instead.

## Usage

To use this in your java project, add a dependency to `org.liquibase.ext:liquibase-opensearch-spring-boot-starter`.

This starter requires that an [`OpenSearchClient`][opensearch-java-client] is available as a bean. It is generally
recommended that you use [`spring-data-opensearch-starter`] to create this bean, however you can also provide your own.
If no `OpenSearchClient` bean exists, the starter does nothing. You can also replace the auto-configured
`SpringLiquibaseOpenSearch` bean by defining your own.

Unlike `liquibase-opensearch`, this will not attempt to create its own `OpenSearchClient` and consequently it does not
try to read any OpenSearch connection information from the properties.

Once you include this library as a dependency in your project, it will automatically trigger the liquibase migration,
unless you set `opensearch.liquibase.enabled` to `false`.
By default, the integration will look for a changelog file at `db/changelog/db.changelog-master.yaml`.

The migration runs during the startup of the application context. Beans which require the migration to have run
(e.g. because they access indices created by it) must be annotated with
[`@DependsOnDatabaseInitialization`][DependsOnDatabaseInitialization]; they are then only created after the migration.

This starter does not depend on the standard Liquibase Spring Boot integration (`spring-boot-liquibase`), and thus does
not pull in any JDBC dependencies. If your application also uses the standard integration (e.g. for a relational
database), both run independently of each other.
The properties `spring.liquibase.analytics-enabled` and `spring.liquibase.license-key` of the standard integration also
apply to this integration.

See the [properties record] for all supported configuration options.

See the [`liquibase-opensearch`] documentation for further details on the supported change types for OpenSearch.

## OpenSearch Compatibility

Please refer to [`liquibase-opensearch`] to see the current compatibility.

## Versioning

This project follows the [Spring Boot release cycle] and thus **does not** adhere to [Semantic Versioning].

Version 1.0.x is compatible with Spring Boot 4.0.x, major/minor versions increment accordingly from there on out
(i.e. 1.1.x is compatible with 4.1.x, etc.).

## Changelog
For the changelog please see the dedicated [CHANGELOG.md](CHANGELOG.md).

## License
This project is licensed under the Apache License Version 2.0 - see the [LICENSE] file for details.

[Spring Boot]: https://spring.io/projects/spring-boot
[`liquibase-opensearch`]: https://github.com/liquibase/liquibase-opensearch/
[Liquibase Spring Boot integration]: https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/springboot/
[opensearch-java-client]: https://docs.opensearch.org/docs/latest/clients/java/
[`spring-data-opensearch-starter`]: https://github.com/opensearch-project/spring-data-opensearch/tree/main?tab=readme-ov-file#spring-boot-integration
[properties record]: src/main/java/liquibase/ext/opensearch/integration/spring/SpringLiquibaseOpenSearchProperties.java
[DependsOnDatabaseInitialization]: https://docs.spring.io/spring-boot/how-to/data-initialization.html#howto.data-initialization.dependencies
[Spring Boot release cycle]: https://github.com/spring-projects/spring-boot/wiki/Supported-Versions
[Semantic Versioning]: https://semver.org/spec/v2.0.0.html
[LICENSE]: LICENSE
