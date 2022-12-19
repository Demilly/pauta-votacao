package br.com.votacao.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@Entity
@Table(name = "votos")
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voto")
    private Long idVoto;

    @Column(name = "data_hora_voto")
    private LocalDateTime dataHoraVoto;

    @Column(name = "id_eleitor")
    private Long idEleitor;

    @ManyToOne
    @JoinColumn(name = "id_voto_sessao")
    private VotoSessao votoSessao;
}