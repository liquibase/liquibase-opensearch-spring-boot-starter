package liquibase.ext.opensearch.integration.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
    "opensearch.liquibase.database-change-log-index=testchangelogindex",
    "opensearch.liquibase.database-change-log-lock-index=testchangeloglockindex",
    "opensearch.liquibase.parameters.test-index=customindicestestindex",
})
class SpringLiquibaseOpenSearchCustomIndicesIT extends AbstractOpenSearchIT {

    @Test
    void itRanTheChangelog() {
        assertTrue(indexExists("customindicestestindex"));
    }

    @Test
    void itUsesCustomChangelogIndexNames() {
        assertTrue(indexExists("testchangelogindex"));
        assertTrue(indexExists("testchangeloglockindex"));
    }

}