package liquibase.ext.opensearch.integration.spring;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static liquibase.ext.opensearch.integration.spring.SpringLiquibaseOpenSearchIT.OPENSEARCH_DOCKER_IMAGE_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(properties = {
    "opensearch.liquibase.database-change-log-index=testchangelogindex",
    "opensearch.liquibase.database-change-log-lock-index=testchangeloglockindex"
})
class SpringLiquibaseOpenSearchCustomIndicesIT {

    @Container
    @ServiceConnection
    protected static OpenSearchContainer<?> container = new OpenSearchContainer<>(DockerImageName.parse(OPENSEARCH_DOCKER_IMAGE_NAME));

    @Autowired
    private OpenSearchClient openSearchClient;

    @Test
    @SneakyThrows
    void itUsesCustomChangelogIndexNames() {
        assertTrue(openSearchClient.indices().exists(e -> e.index("testchangelogindex")).value());
        assertTrue(openSearchClient.indices().exists(e -> e.index("testchangeloglockindex")).value());
    }

}
