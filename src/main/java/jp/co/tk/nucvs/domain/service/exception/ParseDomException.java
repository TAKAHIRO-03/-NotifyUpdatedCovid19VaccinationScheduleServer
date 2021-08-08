package jp.co.tk.nucvs.domain.service.exception;

public final class ParseDomException extends RuntimeException {

    public ParseDomException(String message){
        super(message);
    }

    public ParseDomException(String message, Throwable cause){
        super(message, cause);
    }

}
