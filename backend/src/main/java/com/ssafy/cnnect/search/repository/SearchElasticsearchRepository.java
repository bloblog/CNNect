package com.ssafy.cnnect.search.repository;

import com.ssafy.cnnect.search.entity.SearchDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchElasticsearchRepository extends ElasticsearchRepository<SearchDocument, String> {
    List<SearchDocument> findByVideoName(String videoName, Pageable pageable);
}
