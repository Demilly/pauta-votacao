package br.com.votacao.service.excpetion;

public class SessaoNaoLocalizadaException extends GlobalException {
    public SessaoNaoLocalizadaException() {
        super("A sessão não foi localizada.");
    }

}
