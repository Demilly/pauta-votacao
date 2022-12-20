package br.com.votacao.model.dto;

import br.com.votacao.model.entity.enums.IntencaoVoto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CriaVotoDto {

    private LocalDateTime dataHoraVoto;
    private Long idEleitor;
    private IntencaoVoto intencaoVoto;
}
