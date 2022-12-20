package br.com.votacao.service;

import br.com.votacao.model.dto.CriaVotoDto;
import br.com.votacao.model.dto.PautaDto;
import br.com.votacao.model.dto.SessaoPautaCriarDto;
import br.com.votacao.model.dto.VotoDto;
import br.com.votacao.model.entity.enums.IntencaoVoto;

import java.util.Map;
import java.util.Optional;

public interface PautaService {

    Optional<PautaDto> criaPauta(PautaDto pautaDto);

    VotoDto sessaoVotacaoInicio(Long idPauta, SessaoPautaCriarDto sessaoPautaCriarDto);

    VotoDto votarInicio(Long idPauta, CriaVotoDto criaVotoDto);

    Map<IntencaoVoto, Long> contabilizarVotos(Long idpauta);
}
