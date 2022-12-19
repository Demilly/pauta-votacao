package br.com.votacao.service.excpetion;

public class SessaoEncerradaExcpetion extends GlobalException {
    public SessaoEncerradaExcpetion() {
        super("A sessão já está encerrada.");
    }

}
