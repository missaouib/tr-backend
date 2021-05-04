package com.main.app.config;

import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CloseIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.main.app.elastic")
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.home}")
    private String esHome;

    @Value("${spring.elasticsearch.host}")
    private String esHost;

    @Value("${spring.elasticsearch.port}")
    private int esPort;

    @Value("${spring.elasticsearch.clustername}")
    private String esClusterName;

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo(esHost+":"+esPort)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws IOException {

        String[] indexesForAnalyzer = new String[] {
                "user", "category", "attribute", "attribute_value", "attribute_category" , "attribute_category_unique" , "brand", "product", "variation", "order"
        };

        for (String indexName : indexesForAnalyzer) {
            if(exists(indexName)){
                CloseIndexRequest requestClose = new CloseIndexRequest(indexName);
                client().indices().close(requestClose, RequestOptions.DEFAULT);

                UpdateSettingsRequest request = new UpdateSettingsRequest(indexName);
                request.settings(Settings.builder()
                        .loadFromSource(Strings.toString(jsonBuilder()
                                .startObject()
                                    .startObject("analysis")
                                        .startObject("analyzer")
                                            .startObject("default")
                                                .field("tokenizer", "keyword")
                                                .field("type", "custom")
                                                .field("filter", new String[]{"lowercase"})
                                            .endObject()
                                        .endObject()
                                    .endObject()
                                .endObject()), XContentType.JSON)
                );

                client().indices().putSettings(request, RequestOptions.DEFAULT);

                OpenIndexRequest requestOpen = new OpenIndexRequest(indexName);
                client().indices().open(requestOpen, RequestOptions.DEFAULT);
            }
        }

        return new ElasticsearchRestTemplate(client());
    }

    private boolean exists(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return client().indices().exists(request, RequestOptions.DEFAULT);
    }


}
