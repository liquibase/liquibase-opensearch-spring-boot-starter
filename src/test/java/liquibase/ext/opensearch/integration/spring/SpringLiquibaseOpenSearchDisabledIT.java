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
import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
@SpringBootTest(properties = {
    "opensearch.liquibase.enabled=false",
})
class SpringLiquibaseOpenSearchDisabledIT {

    @Container
    @ServiceConnection
    protected static OpenSearchContainer<?> container = new OpenSearchContainer<>(DockerImageName.parse(OPENSEARCH_DOCKER_IMAGE_NAME));

    @Autowired
    private OpenSearchClient openSearchClient;

    @Test
    void contextLoads() {
    }

    @Test
    @SneakyThrows
    void itDidNotRunTheChangelog() {
        assertFalse(openSearchClient.indices().exists(e -> e.index("testindex")).value());
    }

}
