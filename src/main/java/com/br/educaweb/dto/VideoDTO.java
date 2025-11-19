package com.br.educaweb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoDTO {
    private Long id;
    private String link;
    private String descricao;
}