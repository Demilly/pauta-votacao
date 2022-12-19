package br.com.votacao.service.excpetion;

public class PautaNaoLocalizadaException extends GlobalException {
    public PautaNaoLocalizadaException() {
        super("Pauta não localizada");
    }

}
