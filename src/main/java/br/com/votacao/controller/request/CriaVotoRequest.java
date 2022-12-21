package br.com.votacao.controller.request;

import br.com.votacao.model.entity.enums.IntencaoVoto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class CriaVotoRequest {

    @NotNull
    private LocalDateTime dataHoraVoto;
    @NotNull
    private IntencaoVoto intencaoVoto;
    @NotBlank
    private Long idEleitor;
    @NotEmpty
    private String cpf;
}
