package com.sorbonne.cvsearches.services;

import com.google.gson.Gson;
import com.sorbonne.cvsearches.models.EsResume;
import com.sorbonne.cvsearches.resources.MySearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ResumeService {

    @Autowired
    Gson gson;

    @Autowired
    RestHighLevelClient client;

    public boolean insertResumeES(EsResume parsed, String filename) throws IOException {
        IndexRequest req = new IndexRequest("resumes");
        parsed.setFileName(filename);
        req.source(gson.toJson(parsed), XContentType.JSON);
        IndexResponse response = client.index(req, RequestOptions.DEFAULT);
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        return shardInfo.getFailed() == 0;
    }

    public List<EsResume> searchResume(MySearchRequest search) throws IOException {
        BoolQueryBuilder filter = QueryBuilders.boolQuery();
        if (!search.getSkills().isEmpty()) {
            search.getSkills().forEach(skill -> {
                filter.should(QueryBuilders.termQuery("competences", skill));
            });
            filter.minimumShouldMatch(search.getMinimumMatches());
        }

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .size(5000)
                .query(filter);
        return executeQuery(searchSourceBuilder);
    }


    private List<EsResume> executeQuery(SearchSourceBuilder searchSourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest("resumes");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return Stream.of(searchResponse.getHits().getHits())
                .map(hit -> hit.getSourceAsString())
                .map(site -> gson.fromJson(site, EsResume.class))
                .collect(Collectors.toList());
    }


}
