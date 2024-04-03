package com.ssafy.cnnect.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.InnerHits;
import co.elastic.clients.elasticsearch.core.search.ScoreMode;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
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
import org.springframework.data.domain.Sort;
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

    public List<SearchTitleResponseDto> searchByTitle(String title){
        // 정렬 정보 설정
        Sort sort = Sort.by(Sort.Direction.DESC, "video_date");

        // 페이지 요청 설정 (페이지 번호는 0부터 시작)
        Pageable pageable = PageRequest.of(0, 100, sort);

        List<SearchDocument> searchVideo = elasticsearchRepository.findByVideoName(title, pageable);
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
                .withQuery(QueryBuilders.nested().innerHits(new InnerHits.Builder().source(SourceConfig.of(sc -> sc.filter(f -> f.includes(List.of("sentence_list.text"))))).build())
                        .path("sentence_list")
                        .query(QueryBuilders.match().field("sentence_list.text").query(keyword).build()._toQuery())
                        .scoreMode(ChildScoreMode.None).build()._toQuery())
                .withSourceFilter(new FetchSourceFilter(new String[]{"video_id", "video_name", "video_date", "video_level", "category_id"}, null))
                .withPageable(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "video_date")))
                .build();
        SearchHits<SearchDocument> hits  = elasticsearchTemplate.search(nativeQuery, SearchDocument.class);


        for (SearchHit<SearchDocument> hit : hits) {
            SentenceDocument sd = (SentenceDocument) hit.getInnerHits().get("sentence_list").getSearchHits().get(0).getContent();
            System.out.println(sd.getText());
            System.out.println(hit.getContent().getVideoName());
            SearchScriptResponseDto searchScriptResponseDto = SearchScriptResponseDto.builder()
                    .videoId(hit.getContent().getVideoId())
                    .videoName(hit.getContent().getVideoName())
                    .videoDate(hit.getContent().getVideoDate())
                    .videoLevel(hit.getContent().getVideoLevel())
                    .categoryId(hit.getContent().getCategoryId())
                    .sentence(sd.getText())
                    .build();
            list.add(searchScriptResponseDto);
        }
        return list;
    }
}
