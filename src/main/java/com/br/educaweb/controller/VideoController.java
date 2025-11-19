package com.br.educaweb.controller;

import com.br.educaweb.dto.VideoDTO;
import com.br.educaweb.model.Video;
import com.br.educaweb.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<VideoDTO> criar(@Valid @RequestBody Video video) {
        Video salvo = videoService.save(video);
        return ResponseEntity.ok(videoService.toDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<Page<VideoDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(videoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> buscar(@PathVariable Long id) {
        return videoService.findById(id)
                .map(video -> ResponseEntity.ok(videoService.toDTO(video)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Video novo) {

        return videoService.findById(id)
                .map(video -> {
                    video.setLink(novo.getLink());
                    video.setDescricao(novo.getDescricao());
                    Video atualizado = videoService.save(video);
                    return ResponseEntity.ok(videoService.toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        var videoOpt = videoService.findById(id);

        if (videoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        videoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}