package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.DoubleSummaryStatistics;

/**
 * Classe TestScanner per testare la classe {@link Scanner}, dove scannerizzo i token
 */
class TestScanner {

    @Test
    void testEOF() {
        try {
            Scanner s = new Scanner("src/test/data/testScanner/testEOF.txt");
            try{
                Token t = new Token(6, TokenType.EOF);

                String expected = t.toString();
                String actual = s.nextToken().toString();

                assertEquals(expected, actual);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        } catch (LexicalException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSkip(){
        try{
            Scanner s = new Scanner("src/test/data/testScanner/caratteriSkip");
            try{
                Token t = new Token(8, TokenType.EOF);

                String expected = t.toString();
                String actual = s.nextToken().toString();

                assertEquals(expected, actual);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testID() {
        try {
            Scanner s = new Scanner("src/test/data/testScanner/testId.txt");
            try{
                Token t = new Token(1, TokenType.ID, "jskjdsf2jdshkf");

                String expected = t.toString();
                String actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(2, TokenType.ID, "printl");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(4, TokenType.ID, "ffloat");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.ID, "hhhjj");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(7, TokenType.EOF);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }catch (LexicalException e){
            throw new RuntimeException();
        }
    }

    @Test
    void testKeyWords() {
        try {
            Scanner s = new Scanner("src/test/data/testScanner/testKeywords.txt");
            try{
                Token t = new Token(2, TokenType.PRINT);
                String expected = t.toString();
                String actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(2, TokenType.TY_FLOAT);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.TY_INT);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.EOF);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }catch (LexicalException e){
            throw new RuntimeException();
        }
    }

    @Test
    void testInt(){
        try{
            Scanner s = new Scanner("src/test/data/testScanner/testInt.txt");
            try{
                Token t;
                String expected;
                String actual;

                LexicalException le = assertThrows(LexicalException.class, s::nextToken);
                assertEquals("ERROR: numbers cannot start with 0: 1:6", le.getMessage());

                t = new Token(2, TokenType.INT, "698");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(4, TokenType.INT, "560099");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.INT, "1234");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.EOF);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);
            } catch(Exception e){
                throw new Exception(e);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFloat(){
        try{
            Scanner s = new Scanner("src/test/data/testScanner/testFloat.txt");
            try{
                Token t;
                String expected;
                String actual;

                LexicalException le = assertThrows(LexicalException.class, s::nextToken);
                assertEquals("ERROR: numbers cannot start with 0: 1:11", le.getMessage());

                t = new Token(2, TokenType.FLOAT, "0.");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(3, TokenType.FLOAT, "98.");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.FLOAT, "89.99999");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.EOF);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);
            } catch(Exception e){
                throw new Exception(e);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testOps(){
        try{
            Scanner s = new Scanner("src/test/data/testScanner/testOpsDels.txt");
            try{
                Token t = new Token(1, TokenType.PLUS);
                String expected = t.toString();
                String actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(1, TokenType.OP_DIVIDE_ASSIGN);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(2, TokenType.MINUS);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(2, TokenType.TIMES);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(3, TokenType.DIVIDE);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.OP_PLUS_ASSIGN);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.ASSIGN);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.OP_MINUS_ASSIGN);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(8, TokenType.MINUS);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(8, TokenType.ASSIGN);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(8, TokenType.OP_MULTIPLY_ASSIGN);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(10, TokenType.SEMI);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(10, TokenType.EOF);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);
            } catch(Exception e){
                throw new Exception(e);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGenerale(){
        try{
            Scanner s = new Scanner("src/test/data/testScanner/testGenerale.txt");
            try{
                Token t = new Token(1, TokenType.TY_INT);
                String expected = t.toString();
                String actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(1, TokenType.ID, "temp");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(1, TokenType.SEMI);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(2, TokenType.TY_INT);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(2, TokenType.ID, "temp1");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(2, TokenType.SEMI);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(3, TokenType.ID, "temp");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(3, TokenType.OP_PLUS_ASSIGN);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(3, TokenType.FLOAT, "5.");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(3, TokenType.SEMI);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.TY_FLOAT);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.ID, "b");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(5, TokenType.SEMI);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.ID, "b");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.ASSIGN);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.ID, "temp1");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.PLUS);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.FLOAT, "3.2");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(6, TokenType.SEMI);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(7, TokenType.PRINT);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(7, TokenType.ID, "b");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(7, TokenType.SEMI);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                t = new Token(8, TokenType.EOF);
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

            } catch (Exception e){
                throw new RuntimeException(e);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testcaratteriNonCaratteri(){
        try{
            Scanner s = new Scanner("src/test/data/testScanner/caratteriNonCaratteri.txt");
            try{
                LexicalException le = assertThrows(LexicalException.class, s::nextToken);
                assertEquals("ERROR: no token starts with '^': 1:4", le.getMessage());
                s.nextToken();

                le = assertThrows(LexicalException.class, s::nextToken);
                assertEquals("ERROR: no token starts with '&': 1:8", le.getMessage());
                s.nextToken();

                Token t = new Token(2, TokenType.SEMI);
                String expected = t.toString();
                String actual = s.nextToken().toString();
                assertEquals(expected, actual);

                le = assertThrows(LexicalException.class, s::nextToken);
                assertEquals("ERROR: no token starts with '|': 2:8", le.getMessage());
                s.nextToken();

                t = new Token(3, TokenType.PLUS);
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);
            } catch(Exception e){
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testIdKeyWords(){
        try {
            Scanner s = new Scanner("src/test/data/testScanner/testIdKeyWords.txt");
            try{
                Token t = new Token(1, TokenType.TY_INT);
                String expected = t.toString();
                String actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(1, TokenType.ID, "inta");
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(2, TokenType.TY_FLOAT);
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(3, TokenType.PRINT);
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(4, TokenType.ID, "nome");
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(5, TokenType.ID, "intnome");
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(6, TokenType.TY_INT);
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(6, TokenType.ID, "nome");
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(6, TokenType.EOF);
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testErroriNumbers(){
        try{
            Scanner s = new Scanner("src/test/data/testScanner/erroriNumbers.txt");
            try{
                Token t = new Token(1, TokenType.INT, "0");
                String expected = t.toString();
                String actual = s.nextToken().toString();
                assertEquals(expected, actual);

                t = new Token(1, TokenType.INT, "33");
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);

                LexicalException le = assertThrows(LexicalException.class, s::nextToken);
                assertEquals("ERROR: too much decimal digits: 3:11", le.getMessage());

                t = new Token(5, TokenType.FLOAT, "123.123");
                expected = t.toString();
                actual = s.nextToken().toString();

                assertEquals(expected, actual);

                le = assertThrows(LexicalException.class, s::nextToken);
                assertEquals("ERROR: no token starts with '.': 5:9", le.getMessage());
                s.nextToken();

                t = new Token(7, TokenType.EOF);
                expected = t.toString();
                actual = s.nextToken().toString();
                assertEquals(expected, actual);
            } catch (Exception e){

            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testPeekToken(){
        try {
            Scanner s = new Scanner("src/test/data/testScanner/testGenerale.txt");
            try{
                Token t1 = s.peekToken();
                Token t2 = s.peekToken();

                assertEquals(t1, t2);

                Token t3 = s.peekToken();

                assertEquals(t1, t3);

                Token t4 = s.nextToken();

                assertEquals(t1, t4);

                Token t5 = s.peekToken();

                assertNotEquals(t1, t5);

                Token t6 = s.nextToken();

                assertEquals(t5, t6);
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}