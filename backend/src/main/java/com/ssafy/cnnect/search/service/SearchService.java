package com.ssafy.cnnect.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.ScoreMode;
import co.elastic.clients.util.ObjectBuilder;
import com.ssafy.cnnect.search.dto.SearchScriptResponseDto;
import com.ssafy.cnnect.search.dto.SearchTitleResponseDto;
import com.ssafy.cnnect.search.entity.SearchDocument;
import com.ssafy.cnnect.search.entity.SearchResultDocument;
import com.ssafy.cnnect.search.entity.SentenceDocument;
import com.ssafy.cnnect.search.repository.SearchElasticsearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchElasticsearchRepository elasticsearchRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ElasticsearchClient elasticsearchClient;

    public List<SearchTitleResponseDto> searchByTitle(String title){
        List<SearchDocument> searchVideo = elasticsearchRepository.findByVideoName(title);
        List<SearchTitleResponseDto> list = new ArrayList<>();

        for(SearchDocument sd : searchVideo){
            SearchTitleResponseDto searchTitleResponseDto = SearchTitleResponseDto.builder()
                    .videoId(sd.getVideoId())
                    .videoDate(sd.getVideoDate())
                    .videoLevel(sd.getVideoLevel())
                    .videoName(sd.getVideoName())
                    .videoThumbnail(sd.getVideoThumbnail())
                    .build();
            list.add(searchTitleResponseDto);
        }

        return list;
    }

    public List<SearchScriptResponseDto> searchByScript(String keyword){
        List<SearchScriptResponseDto> list = new ArrayList<>();
        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(QueryBuilders.nested()
                        .path("sentence_list")
                        .query(QueryBuilders.match().field("sentence_list.text").query(keyword).build()._toQuery())
                        .scoreMode(ChildScoreMode.None).build()._toQuery())
                .withSourceFilter(new FetchSourceFilter(new String[]{"video_id", "video_name", "video_date"}, null))
                .withPageable(PageRequest.of(0, 15))
                .build();
        List<SearchResultDocument> hits  = elasticsearchTemplate.search(nativeQuery, SearchResultDocument.class)
                        .stream()
                        .map(SearchHit::getContent)
                        .collect(Collectors.toList());;
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.nestedQuery(
//                        "sentence_list",
//                        QueryBuilders.matchQuery("sentence_list.text", "security"),
//                        ScoreMode.None))
//                .withSourceFilter(new FetchSourceFilter(new String[]{"video_id"}, null))
//                .withPageable(PageRequest.of(0, 15))
//                .build();
//        SearchHits<SearchDocument> searchHits = elasticsearchTemplate.search(searchQuery, MyDocument.class);
//        Pageable pageable = PageRequest.of(1,10000);
//        List<SearchDocument> searchList = elasticsearchRepository.findBySentenceListTextContaining(keyword, 27);
        for (SearchResultDocument hit : hits) {
            System.out.println(hit.getVideo_id());
            System.out.println(hit.getVideo_name());
            System.out.println(hit.getText());

        }
        return list;
    }
}
