package liquibase.ext.opensearch.integration.spring;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
class SpringLiquibaseOpenSearchIT {

    public static final String OPENSEARCH_DOCKER_IMAGE_NAME = "opensearchproject/opensearch:3.0.0";

    @Container
    @ServiceConnection
    protected static OpenSearchContainer<?> container = new OpenSearchContainer<>(DockerImageName.parse(OPENSEARCH_DOCKER_IMAGE_NAME));

    @Autowired
    private OpenSearchClient openSearchClient;

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
    @SneakyThrows
    void itRanTheChangelog() {
        assertTrue(openSearchClient.indices().exists(e -> e.index("testindex")).value());
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

}
