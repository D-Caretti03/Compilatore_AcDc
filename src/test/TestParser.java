package test;

import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import ast.NodeProgram;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe TestParser per testare la classe {@link Parser} che tramite lo scanner genera nodi dell'ast
 */
class TestParser {

    @Test
    void parseDich(){
        Scanner s;
        try{
            s = new Scanner("src/test/data/testParser/testSoloDich.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }
        Parser p = new Parser(s);

        assertDoesNotThrow(p::parse);
    }

    @Test
    void parsePrint(){
        Scanner s;
        try{
            s = new Scanner("src/test/data/testParser/testSoloDichPrint.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        assertDoesNotThrow(p::parse);
    }

    @Test
    void parseEcc0(){
        Scanner s;
        try {
            s = new Scanner("src/test/data/testParser/testParserEcc_0.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        SyntacticException se = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Token SEMI unexpected: 1", se.getMessage());
    }

    @Test
    void parseEcc1(){
        Scanner s;
        try {
            s = new Scanner("src/test/data/testParser/testParserEcc_1.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        SyntacticException se = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Token TIMES unexpected: 2", se.getMessage());
    }

    @Test
    void parseEcc2(){
        Scanner s;
        try {
            s = new Scanner("src/test/data/testParser/testParserEcc_2.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        SyntacticException se = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Token INT unexpected: 3", se.getMessage());
    }

    @Test
    void parseEcc3(){
        Scanner s;
        try {
            s = new Scanner("src/test/data/testParser/testParserEcc_3.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        SyntacticException se = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Token PLUS unexpected: 2", se.getMessage());
    }

    @Test
    void parseEcc4(){
        Scanner s;
        try {
            s = new Scanner("src/test/data/testParser/testParserEcc_4.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        SyntacticException se = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected ID: 2", se.getMessage());
    }

    @Test
    void parseEcc5(){
        Scanner s;
        try {
            s = new Scanner("src/test/data/testParser/testParserEcc_5.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        SyntacticException se = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected ID: 3", se.getMessage());
    }

    @Test
    void parseEcc6() {
        Scanner s;
        try {
            s = new Scanner("src/test/data/testParser/testParserEcc_6.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        SyntacticException se = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected ID: 3", se.getMessage());
    }

    @Test
    void parseEcc7() {
        Scanner s;
        try {
            s = new Scanner("src/test/data/testParser/testParserEcc_7.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        SyntacticException se = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected ID: 2", se.getMessage());
    }

    @Test
    void parseCorretto1() {
        Scanner s;
        try {
            s = new Scanner ("src/test/data/testParser/testParserCorretto1.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        assertDoesNotThrow(p::parse);
    }

    @Test
    void parseCorretto2() {
        Scanner s;
        try {
            s = new Scanner ("src/test/data/testParser/testParserCorretto2.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        assertDoesNotThrow(p::parse);
    }

    @Test
    void parseAstFirst(){
        Scanner s;
        try{
            s = new Scanner("src/test/data/testAst/testFirst.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);
        NodeProgram program;

        try {
            program = p.parse();
        } catch (SyntacticException e){
            throw new RuntimeException(e);
        }

        String expected = "<Program, [<ID, <ID, temp>>; <TYPE, Int>; <INIT, null>, <PRINT, <ID, temp>>, <ID, <ID, temp1>>; <TYPE, Float>; <INIT, null>, <PRINT, <ID, temp1>>]>";
        String actual = program.toString();

        assertEquals(expected, actual);
    }

    @Test
    void testAstOp(){
        Scanner s;
        try{
            s = new Scanner("src/test/data/testAst/testOp.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        NodeProgram program;

        try{
            program = p.parse();
        } catch (SyntacticException e){
            throw new RuntimeException(e);
        }

        String expected = "<Program, [<ID, <ID, temp>>; <TYPE, Int>; <INIT, null>, <ASSIGN, <ID, temp>, <OP, Plus:<DEREF, <ID, temp>>, <COST, Int: 7>>>, <ASSIGN, <ID, temp>, <OP, Minus:<OP, Plus:<COST, Int: 3>, <OP, Times:<COST, Int: 7>, <COST, Int: 5>>>, <COST, Int: 6>>>]>";
        String actual = program.toString();

        assertEquals(expected, actual);

        System.out.println(actual);
    }

    @Test
    void testAst1() throws SyntacticException {
        Scanner s;
        try {
            s = new Scanner("src/test/data/testAst/test1.txt");
        } catch (LexicalException e){
            throw new RuntimeException(e);
        }

        Parser p = new Parser(s);

        NodeProgram program = p.parse();

        System.out.println(program.toString());
    }
}