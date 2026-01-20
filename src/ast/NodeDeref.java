package ast;

import visitor.IVisitor;

/**
 * Classe pubblica NodeDeref che estende {@link NodeExpr}.
 * Nodo che indica la dereferenziazione di una variabile
 */
public class NodeDeref extends NodeExpr{
    private NodeId id;

    public NodeDeref(NodeId id){
        super();
        this.id = id;
    }

    public NodeId getId(){
        return this.id;
    }

    @Override
    public String toString(){
        return "<DEREF, " + id + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
