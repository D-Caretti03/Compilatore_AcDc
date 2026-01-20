package ast;

import visitor.IVisitor;

/**
 * Classe astratta NodeAst. Identifica un nodo dell'albero di parsing
 */
public abstract class NodeAst {
    public abstract void accept (IVisitor visitor);
}
