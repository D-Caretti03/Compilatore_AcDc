package ast;

import visitor.IVisitor;

public class NodePrint extends NodeStat{
    private NodeId id;

    public NodePrint(NodeId id){
        super();
        this.id = id;
    }

    public NodeId getId(){
        return this.id;
    }

    @Override
    public String toString(){
        return "<PRINT, " + id + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
