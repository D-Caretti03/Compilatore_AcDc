package ast;

import visitor.IVisitor;

/**
 * Nodo pubblico NodePrint che estende {@link NodeStat}.
 * Nodo che indica la print di una variabile
 */
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
