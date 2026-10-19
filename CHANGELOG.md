# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).
This project follows the Spring Boot release cycle and thus does *not* adhere to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html), see the [README](README.md#versioning) for details.

## [Unreleased] - ReleaseDate

### Added

* Beans annotated with `@DependsOnDatabaseInitialization` are now only created after the migration has run
* `spring.liquibase.ui-service` is now supported (as in the standard Liquibase Spring Boot integration). This replaces
  the non-configurable field `SpringLiquibaseOpenSearch#uiService` (and its getter)

### Changed

* No longer depends on `spring-boot-starter-liquibase`, which pulled in JDBC dependencies (incl. HikariCP). If your
  application needs them, declare them explicitly. `spring.liquibase.analytics-enabled` and
  `spring.liquibase.license-key` are still supported
  * The constructor of `SpringLiquibaseOpenSearch` now takes `SharedLiquibaseProperties` instead of `LiquibaseProperties`
* `SpringLiquibaseOpenSearch` is no longer annotated with `@Component`, it is only created by the auto-configuration
* The auto-configuration now backs off if no `OpenSearchClient` bean exists (instead of failing the application startup)
  or if a custom `SpringLiquibaseOpenSearch` bean is defined

### Fixed

* The jar now contains the Spring Boot configuration metadata (`spring-configuration-metadata.json`), enabling IDE
  auto-completion and documentation for the `opensearch.liquibase.*` properties. `spring-boot-configuration-processor`
  is no longer a transitive dependency
* The connection is now also closed if the migration fails
* The connection and database are no longer registered globally in Liquibase, which leaked an instance on every
  application context start

## [1.1.2] - 2026-10-13

This version is compatible with Spring Boot 4.1.x.

### Changed
* Updated to `liquibase-opensearch:2.1.1`

## [1.1.1] - 2026-09-03

### Changed

* Updated to Liquibase 5.0.4
* Updated to `spring-data-opensearch:3.1.2`
* Updated to `opensearch-java:3.10.0`
* Updated to `liquibase-opensearch:2.1.0`

## [1.1.0] - 2026-06-17

This version is compatible with Spring Boot 4.1.x.

### Changed

* Updated to Spring Boot 4.1.0

## [1.0.0] - 2026-05-28

This version is compatible with Spring Boot 4.0.x.

### Breaking Changes

* Updated to `liquibase-opensearch:2.0.0`
* Updated to Liquibase 5
  * Note the [license change on Liquibase itself][lb-license]. The license of `liquibase-opensearch` and
    `liquibase-opensearch-spring-boot-starter` remain unchanged (ALv2)
* Updated to Spring Boot 4.0

## [0.2.1] - 2026-05-28

This version is compatible with Spring Boot 3.5.x.

### Changed

* Updated to `liquibase-opensearch:1.0.0`

## [0.2.0] - 2026-03-12

This version is compatible with Spring Boot 3.5.x.

### Changed

* Updated to `liquibase-opensearch:0.2.0`

## [0.1.0] - 2025-10-07

### Added

* Allow changing the index names for the liquibase-internal indices (defaults: `databasechangelog` and `databasechangeloglock`)

### Changed

* Updated to `spring-data-opensearch` v2 & `opensearch-java` v3 - this is a breaking change for consumers!
  * Require JDK 21 as a minimum due to this update

[Unreleased]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v1.1.2...HEAD
[1.1.2]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v1.1.1...v1.1.2
[1.1.1]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v1.1.0...v1.1.1
[1.1.0]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v0.2.1...v1.0.0
[0.2.1]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v0.2.0...v0.2.1
[0.2.0]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v0.0.1...v0.1.0
[lb-license]: https://www.liquibase.com/blog/liquibase-community-for-the-future-fsl
