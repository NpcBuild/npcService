package com.npc.client.Elasticsearch.utils;

//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.FieldValue;
//import co.elastic.clients.elasticsearch._types.SortOptions;
//import co.elastic.clients.elasticsearch._types.SortOrder;
//import co.elastic.clients.elasticsearch._types.query_dsl.Query;
//import co.elastic.clients.elasticsearch.core.CountResponse;
//import co.elastic.clients.elasticsearch.core.GetResponse;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson.JSON;
import com.npc.client.Elasticsearch.dto.EsQueryDTO;
import com.npc.core.utils.StringUtils;

import java.util.*;

/**
 * @author NPC
 * @description ES工具类
 * @create 2023/2/24 22:21
 */
public class ElasticClientUtils<T> {
    /**
     * 根据关键字查询
     * @param client
     * @param dto
     * @param target
     * @return
     * @throws Exception
     */
//    public List<T> queryByFiled(ElasticsearchClient client, EsQueryDTO dto, Class<T> target) throws Exception {
//        List<T> result = new ArrayList<>();
//        List<SortOptions> sorts = new ArrayList<>();
//        if (StringUtils.isNotBlank(dto.getOrder())) {
//            SortOptions sortOptions = SortOptions.of(s -> s.field(f -> f.field(dto.getOrder()).order(SortOrder.valueOf(dto.getOrderType()))));
//            sorts.add(sortOptions);
//        }
//        SearchResponse<HashMap> search = client.search(s -> s
//                        .index(dto.getIndexName())
//                        .query(q -> q.term(t -> t
//                                .field(dto.getField())
//                                .value(dto.getWord())
//                        )).sort(sorts),
//                HashMap.class);
//        return getResult(target, result, search);
//    }
//
//
//    /**
//     * 根据关键字查询，基于游标查询scroll
//     * @param client
//     * @param dto
//     * @param queries
//     * @param target
//     * @return
//     * @throws Exception
//     */
//    // fixme 错误
////    public List<T> queryByFileds(ElasticsearchClient client, EsQueryDTO dto, List<Query> queries, Class<T> target) throws Exception {
////        List<T> result = new ArrayList<>();
////        List<SortOptions> sorts = new ArrayList<>();
////        if (StringUtils.isNotBlank(dto.getOrder())) {
////            SortOptions sortOptions = SortOptions.of(s -> s.field(f -> f.field(dto.getOrder()).order(SortOrder.valueOf(dto.getOrderType()))));
////            sorts.add(sortOptions);
////        }
////        getFieldValues(dto, queries);
////        //使用scroll深度分页查询
////        SearchResponse<HashMap> search = client.search(s -> s
////                        .index(dto.getIndexName()).query(q -> q.bool(b -> b.must(queries))).size(5000).scroll(t -> t.time("5s"))
////                        .sort(sorts),
////                HashMap.class);
////        StringBuffer scrollId = new StringBuffer(search.scrollId());
////        //循环查询，直到查不到数据
////        do {
////            getResult(target, result, search);
////            StringBuffer finalScrollId = scrollId;
////            search = client.scroll(s -> s.scrollId(finalScrollId.toString()).scroll(t -> t.time("5s")), HashMap.class);
//////            search = client.scroll(s -> s.scroll("5s")).scrollId(finalScrollId.toString());
////            scrollId = new StringBuffer(search.scrollId());
////        } while (!search.hits().hits().isEmpty());
////        //getResult(target, result, search)
////        return result;
////    }
//
//
//    /**
//     * 根据关键字分页查询
//     * @param client
//     * @param dto
//     * @param target
//     * @return
//     * @throws Exception
//     */
//    public List<T> queryByFiledWithPage(ElasticsearchClient client, EsQueryDTO dto, Class<T> target) throws Exception {
//        List<T> result = new ArrayList<>();
//        List<SortOptions> sorts = new ArrayList<>();
//        if (StringUtils.isNotBlank(dto.getOrder())) {
//            SortOptions sortOptions = SortOptions.of(s -> s.field(f -> f.field(dto.getOrder()).order(SortOrder.valueOf(dto.getOrderType()))));
//            sorts.add(sortOptions);
//        }
//        SearchResponse<HashMap> search = client.search(s -> s
//                        .index(dto.getIndexName())
//                        .query(q -> q.term(t -> t
//                                .field(dto.getField())
//                                .value(dto.getWord())
//                        )).sort(sorts).from(dto.getFrom()).size(dto.getSize()),
//                HashMap.class);
//        return getResult(target, result, search);
//    }
//
//
//    private List<T> getResult(Class<T> target, List<T> result, SearchResponse<HashMap> search) {
//        List<Hit<HashMap>> hits = search.hits().hits();
//        Iterator<Hit<HashMap>> iterator = hits.iterator();
//        while (iterator.hasNext()) {
//            Hit<HashMap> decodeBeanHit = iterator.next();
//            Map<String, Object> docMap = decodeBeanHit.source();
//            docMap.put("id", decodeBeanHit.id());
//            String json = JSON.toJSONString(docMap);
//            T obj = JSON.parseObject(json, target);
//            result.add(obj);
//        }
//        return result;
//    }
//
//
//    /**
//     * 根据关键字查询总条数
//     * @param client
//     * @param dto
//     * @return
//     * @throws Exception
//     */
//    public static long queryCountByFiled(ElasticsearchClient client, EsQueryDTO dto) throws Exception {
//        CountResponse count = client.count(c -> c.index(dto.getIndexName()).query(q -> q.term(t -> t
//                .field(dto.getField())
//                .value(dto.getWord())
//        )));
//        long total = count.count();
//        return total;
//    }
//
//
//    /**
//     * 根据关键字查询总条数-复合查询
//     * @param client
//     * @param queries
//     * @param dto
//     * @return
//     * @throws Exception
//     */
//    public static long queryCountByFileds(ElasticsearchClient client, List<Query> queries, EsQueryDTO dto) throws Exception {
//        getFieldValues(dto, queries);
//        CountResponse count = client.count(c -> c.index(dto.getIndexName()).query(q -> q.bool(b -> b.must(queries))));
//        long total = count.count();
//        return total;
//    }
//
//
//    /**
//     * 根据关键字分页查询- 复合查询  must
//     * @param client
//     * @param dto
//     * @param queries
//     * @param target
//     * @return
//     * @throws Exception
//     */
//    public List<T> queryMustByFiledsWithPage(ElasticsearchClient client, EsQueryDTO dto, List<Query> queries, Class<T> target) throws Exception {
//        List<T> result = new ArrayList<>();
//        List<SortOptions> sorts = new ArrayList<>();
//        if (StringUtils.isNotBlank(dto.getOrder())) {
//            SortOptions sortOptions = SortOptions.of(s -> s
//                    .field(f -> f.field(dto.getOrder()).order(SortOrder.valueOf(dto.getOrderType()))));
//            sorts.add(sortOptions);
//        }
//        SearchResponse<HashMap> search = client.search(s -> s
//                        .index(dto.getIndexName())
//                        .query(q -> q.bool(b -> b.must(queries)))
//                        .sort(sorts).from(dto.getFrom()).size(dto.getSize()),
//                HashMap.class);
//        return getResult(target, result, search);
//    }
//
//
//    /**
//     * 根据关键字分页查询- 复合查询  should
//     * @param client
//     * @param dto
//     * @param queries
//     * @param target
//     * @return
//     * @throws Exception
//     */
//    public List<T> queryShouldByFiledsWithPage(ElasticsearchClient client, EsQueryDTO dto, List<Query> queries, Class<T> target) throws Exception {
//        List<T> result = new ArrayList<>();
//        List<SortOptions> sorts = new ArrayList<>();
//        if (StringUtils.isNotBlank(dto.getOrder())) {
//            SortOptions sortOptions = SortOptions.of(s -> s
//                    .field(f -> f.field(dto.getOrder()).order(SortOrder.valueOf(dto.getOrderType()))));
//            sorts.add(sortOptions);
//        }
//        SearchResponse<HashMap> search = client.search(s -> s
//                        .index(dto.getIndexName())
//                        .query(q -> q.bool(b -> b.should(queries)))
//                        .sort(sorts).from(dto.getFrom()).size(dto.getSize()),
//                HashMap.class);
//        return getResult(target, result, search);
//    }
//
//
//    /**
//     * 构件复合查询条件
//     * @param dto
//     * @param queries
//     */
//    private static void getFieldValues(EsQueryDTO dto, List<Query> queries) {
//        List<FieldValue> fieldValues = new ArrayList<>();
//        //根据关键字列表构件复合查询的值
//        dto.getWords().stream().forEach(word -> fieldValues.add(FieldValue.of(word)));
//        //查询条件列表
//        queries.add(Query.of(q -> q.terms(t -> t.field(dto.getField()).terms(v -> v.value(fieldValues)))));
//    }
//
//
//    /**
//     * 根据文档id查询
//     * @param client
//     * @param dto
//     * @param target
//     * @return
//     * @throws Exception
//     */
//    public Object queryByDocumentId(ElasticsearchClient client, EsQueryDTO dto, Class<T> target) throws Exception {
//        GetResponse<HashMap> getResponse = client.get(s -> s
//                        .index(dto.getIndexName()).id(dto.getWord()),
//                HashMap.class);
//        getResponse.source();
//        Map<String, Object> docMap = getResponse.source();
//        String json = JSON.toJSONString(docMap);
//        T obj = JSON.parseObject(json, target);
//        return obj;
//    }
}
