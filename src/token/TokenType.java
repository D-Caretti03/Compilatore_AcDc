package token;

/**
 *  Enumerato usato per identificare i tipi di token che riconosciamo all'interno del nostro compilatore
 */
public enum TokenType {
    INT,
    FLOAT,
    ID,
    TY_INT,
    TY_FLOAT,
    PRINT,
    OP_PLUS_ASSIGN,
    OP_MINUS_ASSIGN,
    OP_MULTIPLY_ASSIGN,
    OP_DIVIDE_ASSIGN,
    ASSIGN,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    SEMI,
    EOF;
}
