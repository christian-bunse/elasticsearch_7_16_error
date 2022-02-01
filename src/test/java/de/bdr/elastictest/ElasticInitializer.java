package de.bdr.elastictest;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
class ElasticInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static GenericContainer<?> container;

    @Override
    public synchronized void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        if ( notYetInitialized() ) {
            container = createContainer();
            container.start();
        }

        setProperties(applicationContext);
    }

    private static boolean notYetInitialized() {
        return container == null;
    }

    private static GenericContainer<?> createContainer() {
        DockerImageName image = DockerImageName
                .parse("elasticsearch:7.16.2")
                .asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch");

        return new ElasticsearchContainer(image);
    }

    private static void setProperties(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of("spring.elasticsearch.uris=" + containerUrl()).applyTo(applicationContext);
    }

    private static String containerUrl() {
        return "http://localhost:" + container.getMappedPort(9200);
    }
}
