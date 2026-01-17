package parser;
import org.w3c.dom.Node;
import scanner.*;
import token.Token;
import token.TokenType;
import ast.*;

import java.util.ArrayList;

public class Parser {
    private final Scanner scanner;

    public Parser(Scanner scanner){
        this.scanner = scanner;
    }

    private Token match(TokenType type) throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception", e);
        }
        try {
            if (type.equals(tk.GetTipo())) return scanner.nextToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        throw new SyntacticException("Expected " + type + ": " + tk.GetRiga());
    }

    public NodeProgram parse() throws SyntacticException {
        return this.parsePrg();
    }

    private NodeProgram parsePrg() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception", e);
        }
        switch (tk.GetTipo()) {
            case TY_FLOAT, TY_INT, ID, PRINT, EOF -> { // Prg -> DSs $
                ArrayList<NodeDecSt> decSts = parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(decSts);
            }
            default ->
                    throw new SyntacticException("Token " + tk.GetTipo() + " is not start of program:  " + tk.GetRiga());
        }
    }

    private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }
        switch (tk.GetTipo()){
            case TY_FLOAT, TY_INT -> {// DSs -> Dcl DSs
                NodeDecl dec = parseDcl();
                ArrayList<NodeDecSt> dss = parseDSs();
                dss.addFirst(dec);
                return dss;
            }
            case ID, PRINT -> {// DSs -> Stm DSs
                NodeStat stat = parseStm();
                ArrayList<NodeDecSt> dss = parseDSs();
                dss.addFirst(stat);
                return dss;
            }
            case EOF -> { // DSs -> EOF
                return new ArrayList<>();
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private NodeDecl parseDcl() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch(tk.GetTipo()){
            case TY_FLOAT, TY_INT -> {// Dcl -> Ty id Dclp
                LangType type = parseTy();
                String nome = match(TokenType.ID).GetValore();
                NodeExpr init = parseDclp();
                return new NodeDecl(new NodeId(nome), type, init);
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private LangType parseTy() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch(tk.GetTipo()){
            case TY_FLOAT -> {// Ty -> float
                match(TokenType.TY_FLOAT);
                return LangType.Float;
            }
            case TY_INT -> { // Ty -> int
                match(TokenType.TY_INT);
                return LangType.Int;
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private NodeExpr parseDclp() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch(tk.GetTipo()){
            case SEMI -> { // Dclp -> ;
                match(TokenType.SEMI);
                return null;
            }
            case ASSIGN -> {// Dclp -> = Exp;
                match(TokenType.ASSIGN);
                NodeExpr expr = parseExp();
                match(TokenType.SEMI);
                return expr;
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private NodeStat parseStm() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch(tk.GetTipo()){
            case PRINT -> {// Stm -> print id
                match(TokenType.PRINT);
                String name = match(TokenType.ID).GetValore();
                match(TokenType.SEMI);
                return new NodePrint(new NodeId(name));
            }
            case ID -> {// Stm -> id Op Exp;
                String name = match(TokenType.ID).GetValore();
                AssignOper assign = parseOp();
                NodeExpr expr = parseExp();
                match(TokenType.SEMI);

                switch (assign){
                    case Assign -> {
                        return new NodeAssign(new NodeId(name), expr);
                    }
                    case PlusAssign -> {
                        NodeExpr newExpr = new NodeBinOp(LangOper.Plus, new NodeDeref(new NodeId(name)), expr);
                        return new NodeAssign(new NodeId(name), newExpr);
                    }
                    case MinusAssign -> {
                        NodeExpr newExpr = new NodeBinOp(LangOper.Minus, new NodeDeref(new NodeId(name)), expr);
                        return new NodeAssign(new NodeId(name), newExpr);
                    }
                    case TimesAssign -> {
                        NodeExpr newExpr = new NodeBinOp(LangOper.Times, new NodeDeref(new NodeId(name)), expr);
                        return new NodeAssign(new NodeId(name), newExpr);
                    }
                    case DivideAssign -> {
                        NodeExpr newExpr = new NodeBinOp(LangOper.Divide, new NodeDeref(new NodeId(name)), expr);
                        return new NodeAssign(new NodeId(name), newExpr);
                    }
                }

                return new NodeAssign(new NodeId(name), expr);
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private NodeExpr parseExp() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch (tk.GetTipo()){
            case ID, FLOAT, INT -> {// Exp -> Tr ExpP
                NodeExpr expr = parseTr();
                return parseExpP(expr);
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch(tk.GetTipo()){
            case PLUS -> {// ExpP -> + Tr ExpP
                match(TokenType.PLUS);
                NodeExpr expr = parseTr();
                NodeBinOp op = new NodeBinOp(LangOper.Plus, left, expr);
                return parseExpP(op);
            }
            case MINUS -> {// ExpP -> - Tr ExpP
                match(TokenType.MINUS);
                NodeExpr expr = parseTr();
                NodeBinOp op = new NodeBinOp(LangOper.Minus, left, expr);
                return parseExpP(op);
            }
            case SEMI -> {// ExpP ->
                return left;
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private NodeExpr parseTr() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch(tk.GetTipo()){
            case ID, FLOAT, INT -> {// Tr -> Val TrP
                NodeExpr expr = parseVal();
                return parseTrp(expr);
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private NodeExpr parseTrp(NodeExpr left) throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch(LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch(tk.GetTipo()){
            case TIMES -> {// Trp -> * Val Trp
                match(TokenType.TIMES);
                NodeExpr expr = parseVal();
                NodeBinOp op = new NodeBinOp(LangOper.Times, left, expr);
                return parseTrp(op);
            }
            case DIVIDE -> {// Trp -> / Val Trp
                match(TokenType.DIVIDE);
                NodeExpr expr = parseVal();
                NodeBinOp op = new NodeBinOp(LangOper.Divide, left, expr);
                return parseTrp(op);
            }
            case PLUS, MINUS, SEMI -> {// Trp ->
                return left;
            }
            default ->
                    throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private NodeExpr parseVal() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e){
            throw new SyntacticException("Lexical Exception", e);
        }

        switch(tk.GetTipo()){
            case INT -> {// Val -> intVal
                String value = match(TokenType.INT).GetValore();
                return new NodeCost(value, LangType.Int);
            }
            case FLOAT -> {// Val -> floatVal
                String value = match(TokenType.FLOAT).GetValore();
                return new NodeCost(value, LangType.Float);
            }
            case ID -> {// Val -> id
                String name = match(TokenType.ID).GetValore();
                return new NodeDeref(new NodeId(name));
            }
            default ->
                throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }

    private AssignOper parseOp() throws SyntacticException {
        Token tk;
        try {
            tk = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception", e);
        }

        switch (tk.GetTipo()) {
            case ASSIGN -> {// Op -> =
                match(TokenType.ASSIGN);
                return AssignOper.Assign;
            }
            case OP_PLUS_ASSIGN -> {// Op -> +=
                match(TokenType.OP_PLUS_ASSIGN);
                return AssignOper.PlusAssign;
            }
            case OP_MINUS_ASSIGN -> {// Op -> -=
                match(TokenType.OP_MINUS_ASSIGN);
                return AssignOper.MinusAssign;
            }
            case OP_MULTIPLY_ASSIGN -> {// Op -> *=
                match(TokenType.OP_MULTIPLY_ASSIGN);
                return AssignOper.TimesAssign;
            }
            case OP_DIVIDE_ASSIGN -> {// Op -> /=
                match(TokenType.OP_DIVIDE_ASSIGN);
                return AssignOper.DivideAssign;
            }
            default -> throw new SyntacticException("Token " + tk.GetTipo() + " unexpected: " + tk.GetRiga());
        }
    }
}
