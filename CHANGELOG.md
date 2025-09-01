# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased] - ReleaseDate

### Added

* Allow changing the index names for the liquibase-internal indices (defaults: `databasechangelog` and `databasechangeloglock`)

### Changed

* Updated to `spring-data-opensearch` v2 & `opensearch-java` v3 - this is a breaking change for consumers!
  * Require JDK 21 as a minimum due to this update

[Unreleased]: https://github.com/liquibase/liquibase-opensearch/compare/v0.0.1...HEAD
