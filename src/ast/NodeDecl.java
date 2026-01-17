package ast;

import visitor.IVisitor;

public class NodeDecl extends NodeDecSt{
    private NodeId id;
    private LangType type;
    private NodeExpr init;

    public NodeDecl(NodeId id, LangType type, NodeExpr init){
        super();
        this.id = id;
        this.type = type;
        this.init = init;
    }

    public NodeId getId(){
        return this.id;
    }

    public LangType getType(){
        return this.type;
    }

    public NodeExpr getInit(){
        return this.init;
    }

    @Override
    public String toString(){
        return "<ID, " + id + ">; <TYPE, " + type + ">; <INIT, " + init + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
