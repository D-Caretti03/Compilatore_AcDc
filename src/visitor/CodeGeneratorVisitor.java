package visitor;

import ast.*;
import registers.Registers;
import symbolTable.SymbolTable;

/**
 * Classe pubblica CodeGeneratorVisitor che implementa {@link IVisitor}.
 * Genera il codice dc.
 */
public class CodeGeneratorVisitor implements IVisitor {
    private String codiceDc;
    private String log;

    public String getCodiceDc(){
        return codiceDc;
    }

    public String getLog(){
        return log;
    }

    @Override
    public void visit(NodeProgram node) {
        codiceDc = "";
        log = "";
        for (NodeDecSt n: node.getDecSts()){
            n.accept(this);
            if (!log.isEmpty()) return;
        }
    }

    @Override
    public void visit(NodeId node) {
        if (!log.isEmpty()) return;
        codiceDc += node.getAttributes().getRegister() + " ";
    }

    @Override
    public void visit(NodeDecl node) {
        if (!log.isEmpty()) return;
        SymbolTable.Attributes newAttributes = node.getId().getAttributes();
        char newRegister = Registers.newRegister();
        if (newRegister >= Registers.maxRegister){
            log += "Exceeded max register number\n";
            return;
        }
        newAttributes.setRegister(newRegister);
        node.getId().setAttributes(newAttributes);

        if (node.getInit() != null){
            node.getInit().accept(this);
            codiceDc += "s";
            node.getId().accept(this);
        }
    }

    @Override
    public void visit(NodePrint node) {
        if (!log.isEmpty()) return;
        codiceDc += "l";
        node.getId().accept(this);
        codiceDc += "p P ";
    }

    @Override
    public void visit(NodeAssign node) {
        if (!log.isEmpty()) return;
        node.getExpr().accept(this);

        codiceDc += "s";
        node.getId().accept(this);
    }

    @Override
    public void visit(NodeBinOp node) {
        if (!log.isEmpty()) return;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        switch(node.getOp()){
            case Plus -> {
                codiceDc += "+ ";
            }
            case Minus -> {
                codiceDc += "- ";
            }
            case Times -> {
                codiceDc += "* ";
            }
            case Divide -> {
                codiceDc += "/ ";
            }
            case Divide_float -> {
                codiceDc += "5 k / 0 k ";
            }
        }
    }

    @Override
    public void visit(NodeCost node) {
        if (!log.isEmpty()) return;
        codiceDc += node.getValue() + " ";
    }

    @Override
    public void visit(NodeDeref node) {
        if (!log.isEmpty()) return;
        codiceDc += "l";
        node.getId().accept(this);
    }
}
