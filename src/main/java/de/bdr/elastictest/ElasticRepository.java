package de.bdr.elastictest;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

interface ElasticRepository extends ElasticsearchRepository<ElasticEntity, String> {}
