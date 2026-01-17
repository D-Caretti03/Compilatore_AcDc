package visitor;

import ast.*;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor{
    private TypeDescriptor resType;

    public void init(){
        SymbolTable.init();
    }

    public TypeDescriptor GetResType(){
        return this.resType;
    }

    @Override
    public void visit(NodeProgram node) {
        ErrorType error = new ErrorType();
        for (NodeDecSt n: node.getDecSts()){
            n.accept(this);
            TypeDescriptor retType = resType;
            if (retType.compatibile(new ErrorType())){
                error.merge(retType);
            }
        }

        if (error.getMsg().isEmpty()){
            resType = new VoidType();
        }
    }

    @Override
    public void visit(NodeId node) {
        String name = node.GetName();
        if (SymbolTable.lookup(name) == null){
            resType = ErrorType.variableNotDefined(name);
            return;
        }
        node.setAttributes(SymbolTable.lookup(name));
        resType = (node.getAttributes().getTipo() == LangType.Int) ? new IntType() : new FloatType();
    }

    @Override
    public void visit(NodeDecl node) {
        String name = node.getId().GetName();
        if (SymbolTable.lookup(name) != null){
            resType = ErrorType.variableRedefinition(name);
            return;
        }

        TypeDescriptor initTD;
        if (node.getInit() != null) {
            node.getInit().accept(this);
            initTD = resType;
        } else {
            initTD = new VoidType();
        }
        TypeDescriptor langTD = (node.getType() == LangType.Int) ? new IntType() : new FloatType();
        if (initTD.compatibile(new ErrorType())){
            resType = initTD;
        } else if (langTD.compatibile(initTD)){
            SymbolTable.Attributes attributesEntry = new SymbolTable.Attributes(node.getType());
            SymbolTable.enter(name, attributesEntry);
            node.getId().setAttributes(attributesEntry);
            resType = new VoidType();
        } else {
            ErrorType error = new ErrorType();
            resType = error;
        }
    }

    @Override
    public void visit(NodePrint node) {
        node.getId().accept(this);
    }

    @Override
    public void visit(NodeAssign node) {
        node.getId().accept(this);
        TypeDescriptor idTD = resType;
        node.getExpr().accept(this);
        TypeDescriptor exprTD = resType;

        if (idTD.compatibile(new ErrorType()) || exprTD.compatibile(new ErrorType())){
            ErrorType error = new ErrorType();
            error.merge(idTD);
            error.merge(exprTD);
            resType = error;
            return;
        }

        if (!idTD.compatibile(exprTD)){
            resType = ErrorType.typeNotCompatible();
            return;
        }

        resType = new VoidType();
    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        TypeDescriptor leftTD = resType;
        node.getRight().accept(this);
        TypeDescriptor rightTD = resType;
        if (leftTD.compatibile(new ErrorType()) || rightTD.compatibile(new ErrorType())){
            ErrorType error = new ErrorType();
            error.merge(leftTD);
            error.merge(rightTD);
            resType = error;
            return;
        }

        if (leftTD.compatibile(new FloatType()) || rightTD.compatibile(new FloatType())){
            resType = new FloatType();
        } else {
            resType = new IntType();
        }
    }

    @Override
    public void visit(NodeCost node) {
        resType = (node.getType() == LangType.Int) ? new IntType() : new FloatType();
    }

    @Override
    public void visit(NodeDeref node) {
        node.getId().accept(this);
    }
}
