package ast;

import visitor.IVisitor;

/**
 * Classe pubblica NodeBinOp che estende {@link NodeExpr}.
 * Nodo che indica un operazione binaria
 */
public class NodeBinOp extends NodeExpr{
    private LangOper op;
    private NodeExpr left;
    private NodeExpr right;

    public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right){
        super();
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public LangOper getOp(){
        return this.op;
    }
    public void setOp(LangOper op) {this.op = op;}

    public NodeExpr getLeft(){
        return this.left;
    }

    public NodeExpr getRight(){
        return this.right;
    }

    @Override
    public String toString(){
        return "<OP, " + op + ":" + left + ", " + right + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
