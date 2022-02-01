package de.bdr.elastictest;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.stereotype.Service;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

@Service
@RequiredArgsConstructor
class ElasticIndexer {
    private final RestHighLevelClient elasticClient;

    public void reCreateIndex(String name) {
        dropIndexIfExists(name);
        createIndex(name);
    }

    @SneakyThrows
    private void createIndex(String name) {
        elasticClient.indices().create(new CreateIndexRequest(name), DEFAULT);
    }

    private void dropIndexIfExists(String name) {
        if (hasIndex(name)) {
            deleteIndex(name);
        }
    }

    @SneakyThrows
    private boolean hasIndex(String name) {
        return elasticClient.indices().exists(new GetIndexRequest(name), DEFAULT);
    }

    @SneakyThrows
    private void deleteIndex(String name) {
        elasticClient.indices().delete(new DeleteIndexRequest(name), DEFAULT);
    }
}
