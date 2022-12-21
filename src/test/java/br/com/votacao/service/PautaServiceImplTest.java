package br.com.votacao.service;


import br.com.votacao.model.dto.PautaDto;
import br.com.votacao.model.entity.Pauta;
import br.com.votacao.model.mapper.PautaMapper;
import br.com.votacao.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class PautaServiceImplTest {

    @InjectMocks
    private PautaServiceImpl pautaService;
    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private PautaMapper pautaMapper;

    @Test
    public void criarPautaTest() {

        PautaDto pautaDto = new PautaDto();
        pautaDto.setDescricao("teste");
        pautaDto.setTitulo("testeTitulo");

        Pauta pauta = new Pauta();
        pauta.setIdPauta(123L);
        pauta.setDescricao("teste");
        pauta.setTitulo("testeTitulo");

        Mockito.when(pautaMapper.toPauta(pautaDto)).thenReturn(pauta);
        Mockito.when(pautaMapper.toPautaDto(pauta)).thenReturn(pautaDto);
        Mockito.when(pautaRepository.save(pauta)).thenReturn(pauta);

        Optional<PautaDto> pautaDtoSaved = pautaService.criaPauta(pautaDto);

        Mockito.verify(pautaRepository, Mockito.times(1)).save(pauta);
        Mockito.verify(pautaMapper, Mockito.times(1)).toPautaDto(pauta);
        Mockito.verify(pautaMapper, Mockito.times(1)).toPauta(pautaDto);

        assertEquals(pautaDto.getDescricao(), pautaDtoSaved.get().getDescricao());
        assertEquals(pautaDto.getTitulo(), pautaDtoSaved.get().getTitulo());
    }
}
