package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

import java.util.ArrayList;

/**
 * Classe TestToken per testare la classe {@link Token}, testando riga, tipo e valore
 */
class TestToken {
    @Test
    void test() {
        ArrayList<Token> t = new ArrayList<>();
        t.add(new Token(1, TokenType.TY_INT));
        t.add(new Token(1, TokenType.ID, "tempa"));
        t.add(new Token(1, TokenType.SEMI));

        Token exp = new Token(1, TokenType.TY_INT);
        assertEquals(exp.toString(), t.get(0).toString());

        exp = new Token(1, TokenType.ID, "tempa");
        assertEquals(exp.toString(), t.get(1).toString());

        exp = new Token(1, TokenType.SEMI);
        assertEquals(exp.toString(), t.get(2).toString());
    }

}