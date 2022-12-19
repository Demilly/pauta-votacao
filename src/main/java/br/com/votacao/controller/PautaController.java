package br.com.votacao.controller;

import br.com.votacao.controller.mapper.PautaRequestMapper;
import br.com.votacao.controller.request.CriarPautaRequest;
import br.com.votacao.controller.request.CriarSessaoPautaRequest;
import br.com.votacao.model.dto.PautaDto;
import br.com.votacao.model.dto.VotoDto;
import br.com.votacao.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/pauta/")
@RequiredArgsConstructor
public class PautaController {


    private final PautaService pautaService;
    private final PautaRequestMapper pautaRequestMapper;

    @PostMapping(value = "criar")
    public ResponseEntity<?> criarPauta(@RequestBody @Valid CriarPautaRequest criarPautaRequest) {
        Optional<PautaDto> pautaDto = pautaService.criaPauta(pautaRequestMapper.toPautaDto(criarPautaRequest));
        if (pautaDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(pautaDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "cria-sessao/{idPauta}")
    public ResponseEntity<?> sessaoVotacao(@PathVariable Long idPauta, CriarSessaoPautaRequest criarSessaoPautaRequest) {
        VotoDto votoDto = pautaService.sessaoVotacaoInicio(idPauta,
                pautaRequestMapper.toSessaoPautaCriarDto(criarSessaoPautaRequest));
        if(votoDto.getDataHoraVoto() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

}