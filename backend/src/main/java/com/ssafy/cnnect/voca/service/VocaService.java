package com.ssafy.cnnect.voca.service;

import com.ssafy.cnnect.voca.dto.VocaListResponseDto;
import com.ssafy.cnnect.voca.dto.VocaRequestDto;
import com.ssafy.cnnect.voca.entity.Voca;
import com.ssafy.cnnect.voca.repository.VocaHistoryRepository;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VocaService {
    private final CustomUserDetailsService customUserDetailsService;
    private final VocaHistoryRepository wordHistoryRepository;

    public List<VocaListResponseDto> getWordList(){
        User user = customUserDetailsService.getUserByAuthentication();
        List<Voca> wordList = wordHistoryRepository.findAllByUser(user);

        List<VocaListResponseDto> vocaList = wordList.stream()
                .sorted((o1, o2) -> o2.getWordDate().compareTo(o1.getWordDate()))
                .map(voca -> VocaListResponseDto.builder()
                        .wordListId(voca.getWordListId())
                        .wordContent(voca.getWordContent())
                        .wordMean(voca.getWordMean())
                        .build())
                .collect(Collectors.toList());

        return vocaList;
    }

    @Transactional
    public boolean saveWord(VocaRequestDto word) {
        User user = customUserDetailsService.getUserByAuthentication();

        Voca study = wordHistoryRepository.findByWordContentAndUser(word.getWordContent() , user);

        if(study == null) {
            Voca voca = Voca.builder()
                    .user(user)
                    .wordContent(word.getWordContent())
                    .wordMean(word.getWordMean())
                    .wordDate(LocalDate.now())
                    .build();
            wordHistoryRepository.save(voca);
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public void deleteWord(Long wordListId){
        wordHistoryRepository.deleteByWordListId(wordListId);
    }

}
