package br.com.votacao.model.mapper;

import br.com.votacao.model.dto.CriaVotoDto;
import br.com.votacao.model.dto.VotoDto;
import br.com.votacao.model.entity.Voto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VotoMapper {

    Voto toVoto(CriaVotoDto criaVotoDto);
    VotoDto toVotoDto(Voto voto);
}
