package scanner;

public class LexicalException extends Exception {

    public LexicalException(){}

    // Costruttori
    public LexicalException(final String message){
        super(message);
    }

    public LexicalException(String message, Exception e){
        super(message, e);
    }

}