# Error with Elastic Search 7.16

After updating to Elastic Search 7.16 we cannot save entities anymore:

We receive a
**java.lang.NoSuchFieldError: INDEX_CONTENT_TYPE**
for every usage of *org.springframework.data.elasticsearch.repository.ElasticsearchRepository.save() / saveAll()*. 

Debugging this problem shows that the class *org.springframework.data.elasticsearch.core.RequestFactory* wants to access the  static field
*org.elasticsearch.client.Requests.INDEX_CONTENT_TYPE*. 

Even though the class *org.elasticsearch.client.Requests* can be found in the class path, it is not loaded
at runtime and therefore the field *INDEX_CONTENT_TYPE* cannot be found.

To verify this behaviour, just build this project with the command **mvn test**.
