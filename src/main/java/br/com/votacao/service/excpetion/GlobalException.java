package br.com.votacao.service.excpetion;

public abstract class GlobalException extends RuntimeException {

    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
    }
}
