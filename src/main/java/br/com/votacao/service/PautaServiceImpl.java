package br.com.votacao.service;

import br.com.votacao.model.dto.PautaDto;
import br.com.votacao.model.dto.SessaoPautaCriarDto;
import br.com.votacao.model.dto.VotoDto;
import br.com.votacao.model.entity.Pauta;
import br.com.votacao.model.entity.VotoSessao;
import br.com.votacao.model.mapper.PautaMapper;
import br.com.votacao.repository.PautaRepository;
import br.com.votacao.repository.VotoSessaoRepository;
import br.com.votacao.service.excpetion.DataSessaoInferiorException;
import br.com.votacao.service.excpetion.JaExisteSessaoParaPautaException;
import br.com.votacao.service.excpetion.PautaNaoLocalizadaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    @Value("${sessao.statica}")
    private Integer sessaoStatica;

    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;
    private final VotoSessaoRepository votoSessaoRepository;

    @Override
    public Optional<PautaDto> criaPauta(PautaDto pautaDto) {
        Optional<PautaDto> pautaSavedOptional =
                Optional.ofNullable(pautaMapper.toPautaDto(pautaRepository.save(pautaMapper.toPauta(pautaDto))));
        return pautaSavedOptional;
    }

    @Override
    public VotoDto sessaoVotacaoInicio(Long idPauta, SessaoPautaCriarDto sessaoPautaCriarDto) {
        Pauta pauta = pautaRepository.findById(idPauta)
                .orElseThrow(PautaNaoLocalizadaException::new);

        Optional<VotoSessao> votoSessao = Optional.ofNullable(votoSessaoRepository.findByPauta(idPauta)
                .orElseThrow(JaExisteSessaoParaPautaException::new));

        if (sessaoPautaCriarDto.getDataHoraFechamentoSessao() != null && LocalDateTime.now().isAfter(sessaoPautaCriarDto.getDataHoraFechamentoSessao())) {
            throw new DataSessaoInferiorException();
        }
        VotoSessao votoSessaoSaved = criaSessaoParaVotacao(pauta, sessaoPautaCriarDto.getDataHoraFechamentoSessao());
        if(votoSessaoSaved != null)
            return VotoDto.builder()
                    .dataHoraVoto(LocalDateTime.now())
                    .build();
        return null;
    }

    private VotoSessao criaSessaoParaVotacao(Pauta pauta, LocalDateTime dataHoraFechamento) {
        return votoSessaoRepository.save(VotoSessao.builder()
                .dataAbertura(LocalDateTime.now())
                .dataFechamento(verificaDataHoraFechamento(dataHoraFechamento))
                .pauta(pauta)
                .build());
    }

    private LocalDateTime verificaDataHoraFechamento(LocalDateTime dataHoraFechamento) {
        return dataHoraFechamento == null ? LocalDateTime.now().plusSeconds(sessaoStatica) : dataHoraFechamento;
    }
}
