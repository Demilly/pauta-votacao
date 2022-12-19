package br.com.votacao.service.excpetion;

public class EleitorJaVotouExcpetion extends GlobalException {
    public EleitorJaVotouExcpetion() {
        super("O eleitor ja votou.");
    }

}
