package com.ssafy.cnnect.search.service;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.ssafy.cnnect.search.dto.SearchScriptResponseDto;
import com.ssafy.cnnect.search.dto.SearchTitleResponseDto;
import com.ssafy.cnnect.search.entity.SearchDocument;
import com.ssafy.cnnect.search.repository.SearchElasticsearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchElasticsearchRepository elasticsearchRepository;

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
//        Criteria criteria = new Criteria("nestedPath.field").is("검색어");
//        CriteriaQuery query = new CriteriaQuery(criteria);
        List<SearchDocument> search = elasticsearchRepository.findBySentenceListTextContaining(keyword);
        System.out.println(search.size());


        return list;
    }
}
