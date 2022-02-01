package de.bdr.elastictest;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Value
@Document(indexName = "entity")
class ElasticEntity {

    @Id
    String id;

    @Field(type = FieldType.Text)
    String content;
}
