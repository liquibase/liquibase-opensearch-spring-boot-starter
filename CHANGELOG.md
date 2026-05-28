# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased] - ReleaseDate

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

[Unreleased]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v0.2.1...v1.0.0
[0.2.1]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v0.2.0...v0.2.1
[0.2.0]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/liquibase/liquibase-opensearch-spring-boot-starter/compare/v0.0.1...v0.1.0
[lb-license]: https://www.liquibase.com/blog/liquibase-community-for-the-future-fsl
