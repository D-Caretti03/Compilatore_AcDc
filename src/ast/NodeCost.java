package ast;

import visitor.IVisitor;

/**
 * Classe pubblica NodeCost che estende {@link NodeExpr}.
 * Nodo che indica un valore costante
 */
public class NodeCost extends NodeExpr{
    private String value;
    private LangType type;

    public NodeCost(String value, LangType type){
        super();
        this.value = value;
        this.type = type;
    }

    public String getValue(){
        return this.value;
    }

    public LangType getType(){
        return this.type;
    }

    @Override
    public String toString(){
        return "<COST, " + type + ": " + value + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
