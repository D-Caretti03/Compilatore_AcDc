package ast;

import visitor.IVisitor;

/**
 * Classe pubblica NodeAssign che estende {@link NodeStat}.
 * Nodo che rappresenta l'assegnamento di un'espressione a una variabile
 */
public class NodeAssign extends NodeStat{
    private NodeId id;
    private NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr){
        super();
        this.id = id;
        this.expr = expr;
    }

    public NodeId getId(){
        return this.id;
    }

    public NodeExpr getExpr(){
        return this.expr;
    }

    @Override
    public String toString(){
        return "<ASSIGN, " + id + ", " + expr + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
