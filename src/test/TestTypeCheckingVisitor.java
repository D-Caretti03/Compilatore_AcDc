package test;

import ast.ErrorType;
import ast.NodeProgram;
import ast.TypeDescriptor;
import ast.VoidType;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import visitor.TypeCheckingVisitor;

import static org.junit.jupiter.api.Assertions.*;

class TestTypeCheckingVisitor {
    @Test
    void testDicRipetute() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/1_dicRipetute.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        TypeDescriptor retType = tcVisit.GetResType();
        assertInstanceOf(ErrorType.class, retType);
        assertEquals("Error:\n\tRedefinition of the variable a.\n", ((ErrorType)retType).getMsg());
    }

    @Test
    void testIdNonDec() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/2_idNonDec.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        TypeDescriptor retType = tcVisit.GetResType();
        assertInstanceOf(ErrorType.class, retType);
        assertEquals("Error:\n\tUse of undefined variable b.\n", ((ErrorType)retType).getMsg());
    }

    @Test
    void testIdNonDec2() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/3_idNonDec")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        TypeDescriptor retType = tcVisit.GetResType();
        assertInstanceOf(ErrorType.class, retType);
        assertEquals("Error:\n\tUse of undefined variable c.\n", ((ErrorType)retType).getMsg());
    }

    @Test
    void testTipoNonCompatibile() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/4_tipoNonCompatibile.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        TypeDescriptor retType = tcVisit.GetResType();
        assertInstanceOf(ErrorType.class, retType);
        assertEquals("Error:\n\tCannot convert float to int.\n", ((ErrorType)retType).getMsg());
    }

    @Test
    void testCorretto1() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/5_corretto.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        TypeDescriptor retType = tcVisit.GetResType();
        assertInstanceOf(VoidType.class, retType);
    }

    @Test
    void testCorretto2() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/6_corretto.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        TypeDescriptor retType = tcVisit.GetResType();
        assertInstanceOf(VoidType.class, retType);
    }

    @Test
    void testCorretto3() throws LexicalException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/7_corretto.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        TypeDescriptor retType = tcVisit.GetResType();
        assertInstanceOf(VoidType.class, retType);
    }
}