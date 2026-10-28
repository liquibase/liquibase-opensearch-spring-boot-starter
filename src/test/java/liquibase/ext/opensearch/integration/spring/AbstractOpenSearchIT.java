package liquibase.ext.opensearch.integration.spring;

import lombok.SneakyThrows;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.utility.DockerImageName;

/**
 * Base class for all integration tests.
 *
 * <p>
 * All integration tests share a single OpenSearch container (singleton container pattern) to keep the test runtime
 * low. The container is started once and is removed by Testcontainers (Ryuk) when the JVM exits.
 * </p>
 * <p>
 * As the cluster is shared, every test class which runs the changelog must use its own index names (both for the
 * index created by the changelog, via the {@code test-index} changelog parameter, and for the liquibase-internal
 * indices) to not be influenced by other test classes.
 * </p>
 */
abstract class AbstractOpenSearchIT {

    private static final String OPENSEARCH_DOCKER_IMAGE_NAME = "opensearchproject/opensearch:3.0.0";

    @ServiceConnection
    protected static final OpenSearchContainer<?> CONTAINER = new OpenSearchContainer<>(DockerImageName.parse(OPENSEARCH_DOCKER_IMAGE_NAME));

    static {
        CONTAINER.start();
    }

    @Autowired
    protected OpenSearchClient openSearchClient;

    @SneakyThrows
    protected boolean indexExists(final String index) {
        return this.openSearchClient.indices().exists(e -> e.index(index)).value();
    }

}
