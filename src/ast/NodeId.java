package ast;

import symbolTable.SymbolTable;
import visitor.IVisitor;

public class NodeId extends NodeAst{
    private String name;
    private SymbolTable.Attributes attributes;

    public NodeId(String name){
        super();
        this.name = name;
    }

    public String GetName(){
        return this.name;
    }

    public SymbolTable.Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(SymbolTable.Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString(){
        return "<ID, " + name + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
