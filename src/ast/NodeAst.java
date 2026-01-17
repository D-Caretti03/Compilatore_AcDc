package ast;

import visitor.IVisitor;

public abstract class NodeAst {
    public abstract void accept (IVisitor visitor);
}
