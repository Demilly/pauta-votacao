package br.com.votacao.controller.mapper;

import br.com.votacao.controller.request.CriaVotoRequest;
import br.com.votacao.model.dto.CriaVotoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VotoRequestMapper {

    CriaVotoDto toCriaVotoDto(CriaVotoRequest criaVotoRequest);
}
