package ast;

import visitor.IVisitor;

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
