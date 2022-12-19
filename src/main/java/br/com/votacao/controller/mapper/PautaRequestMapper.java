package br.com.votacao.controller.mapper;

import br.com.votacao.controller.request.CriarPautaRequest;
import br.com.votacao.controller.request.CriarSessaoPautaRequest;
import br.com.votacao.model.dto.PautaDto;
import br.com.votacao.model.dto.SessaoPautaCriarDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PautaRequestMapper {

    PautaDto toPautaDto(CriarPautaRequest criarPautaRequest);
    SessaoPautaCriarDto toSessaoPautaCriarDto(CriarSessaoPautaRequest criarSessaoPautaRequest);
}
