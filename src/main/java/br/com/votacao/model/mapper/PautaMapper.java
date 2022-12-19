package br.com.votacao.model.mapper;

import br.com.votacao.model.dto.PautaDto;
import br.com.votacao.model.entity.Pauta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    Pauta toPauta(PautaDto pautaDto);
    PautaDto toPautaDto(Pauta pauta);
}
