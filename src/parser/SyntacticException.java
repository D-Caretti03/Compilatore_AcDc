package parser;

import scanner.LexicalException;

public class SyntacticException extends Exception{
    public SyntacticException() {};

    public SyntacticException(final String message){
        super(message);
    }

    public SyntacticException(String message, Exception e) {
        super(message, e);
    }
}
