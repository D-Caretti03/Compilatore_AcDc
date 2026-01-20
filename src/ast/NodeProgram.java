package ast;

import visitor.IVisitor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe pubblica NodeProgram che estende {@link NodeAst}.
 * Nodo che indica la root dell'ast
 */
public class NodeProgram extends NodeAst{
    private ArrayList<NodeDecSt> decSts;

    public NodeProgram(ArrayList<NodeDecSt> decSts){
        super();
        this.decSts = decSts;
    }

    public ArrayList<NodeDecSt> getDecSts(){
        return this.decSts;
    }

    @Override
    public String toString(){
        return "<Program, " + decSts + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
