package test;

import ast.NodeProgram;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe TestCodeGenerator che testa la classe {@link CodeGeneratorVisitor}, testando la visita con code generator
 */
class TestCodeGeneratorVisitor {

    @Test
    void testAssign() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testCodeGenerator/1_assign.txt")).parse();
        TypeCheckingVisitor tVisitor = new TypeCheckingVisitor();
        CodeGeneratorVisitor cVisitor = new CodeGeneratorVisitor();
        nP.accept(tVisitor);
        nP.accept(cVisitor);
        assertEquals("1 6 / sa la p P ", cVisitor.getCodiceDc());
    }

    @Test
    void testDivisioni() throws LexicalException, SyntacticException{
        NodeProgram nP = new Parser(new Scanner("src/test/data/testCodeGenerator/2_divisioni.txt")).parse();
        TypeCheckingVisitor tVisitor = new TypeCheckingVisitor();
        CodeGeneratorVisitor cVisitor = new CodeGeneratorVisitor();
        nP.accept(tVisitor);
        nP.accept(cVisitor);
        assertEquals("0 sa la 1 + sa 6 sb 1.0 6 5 k / 0 k la lb / + sc la p P lb p P lc p P ", cVisitor.getCodiceDc());
    }

    @Test
    void testGenerale() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testCodeGenerator/3_generale.txt")).parse();
        TypeCheckingVisitor tVisitor = new TypeCheckingVisitor();
        CodeGeneratorVisitor cVisitor = new CodeGeneratorVisitor();
        nP.accept(tVisitor);
        nP.accept(cVisitor);
        assertEquals("5 3 + sa la 0.5 + sb la p P lb 4 5 k / 0 k sb lb p P lb 1 - sc lc lb * sc lc p P ", cVisitor.getCodiceDc());
    }

    @Test
    void testRegistriFiniti() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testCodeGenerator/4_registriFiniti.txt")).parse();
        TypeCheckingVisitor tVisitor = new TypeCheckingVisitor();
        CodeGeneratorVisitor cVisitor = new CodeGeneratorVisitor();
        nP.accept(tVisitor);
        nP.accept(cVisitor);
        assertEquals("Exceeded max register number\n", cVisitor.getLog());
    }

}