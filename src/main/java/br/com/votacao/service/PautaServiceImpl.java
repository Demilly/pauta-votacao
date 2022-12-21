package br.com.votacao.service;

import br.com.votacao.cliente.RestClientValidaCPF;
import br.com.votacao.cliente.dto.ResponseStatusCpfDto;
import br.com.votacao.model.dto.CriaVotoDto;
import br.com.votacao.model.dto.PautaDto;
import br.com.votacao.model.dto.SessaoPautaCriarDto;
import br.com.votacao.model.dto.VotoDto;
import br.com.votacao.model.entity.Pauta;
import br.com.votacao.model.entity.Voto;
import br.com.votacao.model.entity.VotoSessao;
import br.com.votacao.model.entity.enums.IntencaoVoto;
import br.com.votacao.model.mapper.PautaMapper;
import br.com.votacao.model.mapper.VotoMapper;
import br.com.votacao.repository.PautaRepository;
import br.com.votacao.repository.VotoRepository;
import br.com.votacao.repository.VotoSessaoRepository;
import br.com.votacao.service.excpetion.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final VotoMapper votoMapper;
    private final VotoRepository votoRepository;
    private final RestClientValidaCPF restClientValidaCPF;

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

        Optional<VotoSessao> votoSessao = votoSessaoRepository.findByPauta(idPauta);
        if (votoSessao.isPresent()) {
            throw new JaExisteSessaoParaPautaException();
        }

        if (sessaoPautaCriarDto.getDataHoraFechamentoSessao() != null && LocalDateTime.now().isAfter(sessaoPautaCriarDto.getDataHoraFechamentoSessao())) {
            throw new DataSessaoInferiorException();
        }
        VotoSessao votoSessaoSaved = criaSessaoParaVotacao(pauta, sessaoPautaCriarDto.getDataHoraFechamentoSessao());
        if (votoSessaoSaved != null)
            return VotoDto.builder()
                    .dataHoraVoto(LocalDateTime.now())
                    .build();
        return null;
    }

    @Override
    public VotoDto votarInicio(Long idPauta, CriaVotoDto criaVotoDto) {
        VotoSessao votoSessao = getVotoSessao(idPauta)
                .orElseThrow(() -> new PautaNaoLocalizadaException())
                .orElseThrow(() -> new SessaoNaoLocalizadaException());

        if (LocalDateTime.now().isAfter(votoSessao.getDataFechamento())) {
            throw new SessaoEncerradaExcpetion();
        }
        verificaEleitorJaVotou(votoSessao, criaVotoDto);

        ResponseStatusCpfDto responseStatusCpfDto = restClientValidaCPF.getValidaCPFStatus(criaVotoDto.getCpf());
        if (responseStatusCpfDto.getStatus().equals("ABLE_TO_VOTE")) {
            var voto = votoRepository.save(votoMapper.toVoto(criaVotoDto));
            voto.setVotoSessao(votoSessao);
            return votoMapper.toVotoDto(voto);
        }
        return votoMapper.toVotoDto(new Voto());
    }

    @Override
    public Map<IntencaoVoto, Long> contabilizarVotos(Long idpauta) {
        return getVotoSessao(idpauta)
                .map(vs -> vs.get().getVotos()
                        .stream()
                        .collect(Collectors.groupingBy(Voto::getIntencaoVoto,
                                Collectors.counting()))).orElse(null);
    }

    private Optional<Optional<VotoSessao>> getVotoSessao(Long idPauta) {
        return Optional.ofNullable(votoSessaoRepository.findByPauta(idPauta));
    }

    private void verificaEleitorJaVotou(VotoSessao votoSessao, CriaVotoDto criaVotoDto) {
        Optional<Voto> voto = votoRepository.findById(criaVotoDto.getIdEleitor());
        if (voto.isPresent()) {
            votoSessao.getVotos().stream().forEach(votoEleitorSessao -> {
                if (votoEleitorSessao.getIdEleitor() == voto.get().getIdEleitor()) {
                    throw new EleitorJaVotouExcpetion();
                }
            });
        }
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
