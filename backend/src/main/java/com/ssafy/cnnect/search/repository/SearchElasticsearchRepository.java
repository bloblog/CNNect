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
    List<SearchDocument> findByVideoName(String videoName);


            @Query("{\n" +
            "    \"nested\": {\n" +
            "      \"path\": \"sentence_list\",\n" +
            "      \"query\": {\n" +
            "        \"match\": {\n" +
            "          \"sentence_list.text\": \"?0\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"inner_hits\": {\n" +
            "        \"_source\": [\"sentence_list.text\"]\n" +
            "      },\n" +
                    "  \"size\" : ?1,\n" +
                    "  \"_source\": [\"video_id\"],\n" +
            "    }\n"+
            "  }\n")
    List<SearchDocument> findBySentenceListTextContaining(String keyword, int size);
}
