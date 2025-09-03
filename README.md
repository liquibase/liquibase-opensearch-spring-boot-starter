# Liquibase Extension for OpenSearch: Spring Boot Starter

This is the [Spring Boot] starter for [liquibase-opensearch], which allows you to easily use Liquibase with OpenSearch
clusters in Spring Boot.

Note that the standard [Liquibase Spring Boot integration] does *not* work with OpenSearch. You have to use this
integration here instead.

## Usage

To use this in your java project, add a dependency to `org.liquibase.ext:liquibase-opensearch-spring-boot-starter`.

This starter requires that an [`OpenSearchClient`][opensearch-java-client] is available as a bean. It is generally
recommended that you use [`spring-data-opensearch-starter`] to create this bean, however you can also provide your own.

Unlike `liquibase-opensearch`, this will not attempt to create its own `OpenSearchClient` and consequently it does not
try to read any OpenSearch connection information from the properties.

Once you include this library as a dependency in your project, it will automatically trigger the liquibase migration,
unless you set `opensearch.liquibase.enabled` to `false`.
By default, the integration will look for a changelog file at `db/changelog/db.changelog-master.yaml`.

Note that the standard Liquibase Spring integration will also be triggered automatically, thus you have to disable it,
unless you have other databases besides OpenSearch, for which you intend to use Liquibase as normally. To disable it,
specify the following Spring configuration:
```yaml
spring.liquibase.enabled=false
```

See the [properties record] for all supported configuration options.

See the [`liquibase-opensearch`] documentation for further details on the supported change types for OpenSearch.

## OpenSearch Compatibility

Please refer to [`liquibase-opensearch`] to see the current compatibility.

## Versioning

This project follows the [Spring Boot release cycle] and thus **does not** adhere to [Semantic Versioning].

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
[Spring Boot release cycle]: https://github.com/spring-projects/spring-boot/wiki/Supported-Versions
[Semantic Versioning]: https://semver.org/spec/v2.0.0.html
[LICENSE]: LICENSE
