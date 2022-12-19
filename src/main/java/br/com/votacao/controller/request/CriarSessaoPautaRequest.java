package br.com.votacao.controller.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriarSessaoPautaRequest {

    private LocalDateTime dataHoraFechamentoSessao;
}
