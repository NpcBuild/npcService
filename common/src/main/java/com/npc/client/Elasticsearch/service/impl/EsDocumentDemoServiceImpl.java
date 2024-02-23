package com.npc.client.Elasticsearch.service.impl;

import com.npc.client.Elasticsearch.service.EsDocumentDemoService;
//import jdk.nashorn.internal.ir.ObjectNode;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2023/2/24 22:59
 */
@Service
public class EsDocumentDemoServiceImpl implements EsDocumentDemoService {
    private final RestHighLevelClient client;

    @Autowired
    public EsDocumentDemoServiceImpl(RestHighLevelClient client) throws IOException {
        this.client = client;
    }

    public void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("my_index");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        // 处理查询结果
    }

//    @Override
//    public IndexResponse createByFluentDSL(String idxName, String idxId, Object document) throws Exception {
//        IndexResponse response = elasticsearchClient.index(idx -> idx
//                .index(idxName)
//                .id(idxId)
//                .document(document));
//        return response;
//    }
//
//    @Override
//    public IndexResponse createByBuilderPattern(String idxName, String idxId, Object document) throws Exception {
//        IndexRequest.Builder<Object> indexReqBuilder = new IndexRequest.Builder<>();
//
//        indexReqBuilder.index(idxName);
//        indexReqBuilder.id(idxId);
//        indexReqBuilder.document(document);
//        return elasticsearchClient.index(indexReqBuilder.build());
//    }
//
//    @Override
//    public BulkResponse bulkCreate(String idxName, List<Object> documents) throws Exception {
//        BulkRequest.Builder br = new BulkRequest.Builder();
//
//        // TODO 可以将 Object定义为一个文档基类。比如 ESDocument类
//
//        // 将每一个product对象都放入builder中
//        //documents.stream()
//        //        .forEach(esDocument -> br
//        //                .operations(op -> op
//        //                        .index(idx -> idx
//        //                                .index(idxName)
//        //                                .id(esDocument.getId())
//        //                                .document(esDocument))));
//
//        return elasticsearchClient.bulk(br.build());
//    }
//
//    @Override
//    public Object getById(String idxName, String docId) throws IOException {
//        GetResponse<Object> response = elasticsearchClient.get(g -> g
//                        .index(idxName)
//                        .id(docId),
//                Object.class);
//        return response.found() ? response.source() : null;
//    }
//
//    @Override
//    public ObjectNode getObjectNodeById(String idxName, String docId) throws IOException {
//        GetResponse<ObjectNode> response = elasticsearchClient.get(g -> g
//                        .index(idxName)
//                        .id(docId),
//                ObjectNode.class);
//
//        return response.found() ? response.source() : null;
//    }
//
//    @Override
//    public Boolean deleteById(String idxName, String docId) throws IOException {
//        DeleteResponse delete = elasticsearchClient.delete(d -> d
//                .index(idxName)
//                .id(docId));
//        return delete.forcedRefresh();
//    }
//
//    @Override
//    public BulkResponse bulkDeleteByIds(String idxName, List<String> docIds) throws Exception {
//        BulkRequest.Builder br = new BulkRequest.Builder();
//
//        // 将每一个对象都放入builder中
//        docIds.stream().forEach(id -> br
//                .operations(op -> op
//                        .delete(d -> d
//                                .index(idxName)
//                                .id(id))));
//
//        return elasticsearchClient.bulk(br.build());
//    }
}
