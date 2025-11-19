package com.br.educaweb.service;

import com.br.educaweb.dto.VideoDTO;
import com.br.educaweb.model.Video;
import com.br.educaweb.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoDTO toDTO(Video v) {
        return VideoDTO.builder()
                .id(v.getId())
                .link(v.getLink())
                .descricao(v.getDescricao())
                .build();
    }

    public Video save(Video video) {
        return videoRepository.save(video);
    }

    public Page<VideoDTO> findAll(Pageable pageable) {
        return videoRepository.findAll(pageable).map(this::toDTO);
    }

    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }

    public void delete(Long id) {
        videoRepository.deleteById(id);
    }
}