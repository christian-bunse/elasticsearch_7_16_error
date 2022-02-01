package de.bdr.elastictest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@ContextConfiguration(initializers = ElasticInitializer.class)
class ElasticTest {

    @Autowired
    protected ElasticIndexer elasticSearchIndexer;

    @Autowired
    private ElasticRepository repository;

    @BeforeEach
    void createIndex() {
        elasticSearchIndexer.reCreateIndex("entity");
    }

    @Test
    void entity_should_be_stored() {
        repository.save( new ElasticEntity("id", "content") );

        var result = StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .toList();

        assertThat(result).hasSize(1);
    }
}
