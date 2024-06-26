package com.npc.client.Elasticsearch.service.impl;

//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.mapping.Property;
//import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
//import co.elastic.clients.elasticsearch.indices.*;
//import co.elastic.clients.util.ObjectBuilder;
import com.npc.client.Elasticsearch.service.EsIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;

/**
 * @author NPC
 * @description
 * @create 2023/2/24 22:54
 */
@Service
public class EsIndexServiceImpl implements EsIndexService {
//    @Autowired
//    private ElasticsearchClient elasticsearchClient;
//
//    @Override
//    public void createIndex(String name) throws IOException {
//        CreateIndexResponse response = elasticsearchClient.indices().create(c -> c.index(name));
////        log.info("createIndex方法，acknowledged={}", response.acknowledged());
//    }
//
//    @Override
//    public void createIndex(String name,
//                            Function<IndexSettings.Builder, ObjectBuilder<IndexSettings>> settingFn,
//                            Function<TypeMapping.Builder, ObjectBuilder<TypeMapping>> mappingFn) throws IOException {
//        CreateIndexResponse response = elasticsearchClient
//                .indices()
//                .create(c -> c
//                        .index(name)
//                        .settings(settingFn)
//                        .mappings(mappingFn)
//                );
////        log.info("createIndex方法，acknowledged={}", response.acknowledged());
//    }
//
//    @Override
//    public void deleteIndex(String name) throws IOException {
//        DeleteIndexResponse response = elasticsearchClient.indices().delete(c -> c.index(name));
////        log.info("deleteIndex方法，acknowledged={}", response.acknowledged());
//    }
//
//    @Override
//    public void updateIndexProperty(String name, HashMap<String, Property> propertyMap) throws IOException {
//        PutMappingResponse response = elasticsearchClient.indices()
//                .putMapping(typeMappingBuilder ->
//                        typeMappingBuilder
//                                .index(name)
//                                .properties(propertyMap)
//                );
////        log.info("updateIndexMapping方法，acknowledged={}", response.acknowledged());
//    }
//
//    @Override
//    public GetIndexResponse getIndexList() throws IOException {
//        //使用 * 或者 _all都可以
//        GetIndexResponse response = elasticsearchClient.indices().get(builder -> builder.index("_all"));
////        log.info("getIndexList方法，response.result()={}", response.result().toString());
//        return response;
//    }
//
//    @Override
//    public GetIndexResponse getIndexDetail(String name) throws IOException {
//        GetIndexResponse response = elasticsearchClient.indices().get(builder -> builder.index(name));
////        log.info("getIndexDetail方法，response.result()={}", response.result().toString());
//        return response;
//    }
//
//    @Override
//    public boolean indexExists(String name) throws IOException {
//        return elasticsearchClient.indices().exists(b -> b.index(name)).value();
//    }
}
