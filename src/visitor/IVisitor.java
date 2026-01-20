package visitor;

import ast.*;

/**
 * Interfaccia pubblica IVisitor, che tramite il pattern Visitor permette di effettuare la visita sui nodi concreti dell'albero
 * e di poter essere implementato da più classi per un comportamento diverso
 */
public interface IVisitor {
    public abstract void visit (NodeProgram node);
    public abstract void visit (NodeId node);
    public abstract void visit (NodeDecl node);
    public abstract void visit (NodePrint node);
    public abstract void visit (NodeAssign node);
    public abstract void visit (NodeBinOp node);
    public abstract void visit (NodeCost node);
    public abstract void visit (NodeDeref node);
}
