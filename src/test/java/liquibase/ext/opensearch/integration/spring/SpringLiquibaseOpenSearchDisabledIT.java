package liquibase.ext.opensearch.integration.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(properties = {
    "opensearch.liquibase.enabled=false",
    "opensearch.liquibase.parameters.test-index=disabledtestindex",
})
class SpringLiquibaseOpenSearchDisabledIT extends AbstractOpenSearchIT {

    @Test
    void contextLoads() {
    }

    @Test
    void itDidNotRunTheChangelog() {
        assertFalse(indexExists("disabledtestindex"));
    }

}