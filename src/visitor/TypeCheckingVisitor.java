package visitor;

import ast.*;
import symbolTable.SymbolTable;

/**
 * Classe pubblica TypeCheckingVisitor che implementa {@link IVisitor}.
 * Effettua la visit sui nodi concreti dell'ast ed effettua il typechecking
 */
public class TypeCheckingVisitor implements IVisitor{
    private TypeDescriptor resType;

    /**
     * Metodo per inizializzare la symbolTable
     */
    public void init(){
        SymbolTable.init();
    }

    /**
     * Metodo per restituire l'attuale resType
     * @return  L'attuale resType
     */
    public TypeDescriptor GetResType(){
        return this.resType;
    }

    /**
     * Effettua la visita sul NodeProgram per poi iterare sui suoi figli
     * @param node  Nodo root dell'ast
     */
    @Override
    public void visit(NodeProgram node) {
        init();
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

    /**
     * Metodo per visitare NodeId, effettua la lookup nella symbolTable per evitare l'uso di variabile non definite
     * @param node  NodeId da visitare
     */
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

    /**
     * Metodo per visitare NodeDecl, effettua la lookup nella symbolTable per evitare la dichiarazione di una variabile già dichiarata
     * @param node  NodeDecl da visitare
     */
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
            resType = new ErrorType();
        }
    }

    /**
     * Metodo per visitare NodePrint
     * @param node NodePrint da visitare
     */
    @Override
    public void visit(NodePrint node) {
        node.getId().accept(this);
    }

    /**
     * Metodo per visitare NodeAssign, verifica la compatibilità tra tipi
     * @param node  NodeAssign da visitare
     */
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

    /**
     * Metodo per visitare NodeBinOp, verifica la compatibilità tra tipi
     * @param node  NodeBinOp da visitare
     */
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
            if (node.getOp() == LangOper.Divide) node.setOp(LangOper.Divide_float);
        } else {
            resType = new IntType();
        }
    }

    /**
     * Metodo per visitare NodeCost
     * @param node  NodeCost da visitare
     */
    @Override
    public void visit(NodeCost node) {
        resType = (node.getType() == LangType.Int) ? new IntType() : new FloatType();
    }

    /**
     * Metodo per visitare NodeDeref
     * @param node  NodeDeref da visitare
     */
    @Override
    public void visit(NodeDeref node) {
        node.getId().accept(this);
    }
}
