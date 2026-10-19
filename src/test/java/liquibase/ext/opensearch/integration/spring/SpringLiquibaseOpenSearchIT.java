package liquibase.ext.opensearch.integration.spring;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringLiquibaseOpenSearchIT extends AbstractOpenSearchIT {

    @Test
    void contextLoads() {
    }

    /**
     * Self-test of the test - if this fails something is wrong with the test environment.
     */
    @SneakyThrows
    @Test
    void openSearchIsRunning() {
        assertThat(this.openSearchClient.info().clusterName()).isEqualTo("docker-cluster");
    }

    @Test
    void itRanTheChangelog() {
        assertTrue(indexExists("testindex"));
    }

    @Test
    @SneakyThrows
    void itReplacedVariablesInTheChangelog() {
        assertEquals(1, openSearchClient.count(r -> r.index("testindex")
                .query(q -> q
                        .match(m -> m
                                .field("testfield")
                                .query(q2 -> q2.stringValue("foobar"))))).count());
    }

    @Test
    void itUsesTheDefaultChangelogIndexNames() {
        assertTrue(indexExists("databasechangelog"));
        assertTrue(indexExists("databasechangeloglock"));
    }

}