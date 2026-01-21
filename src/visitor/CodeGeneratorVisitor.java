package visitor;

import ast.*;
import registers.Registers;
import symbolTable.SymbolTable;

/**
 * Classe pubblica CodeGeneratorVisitor che implementa {@link IVisitor}.
 * Genera il codice dc.
 */
public class CodeGeneratorVisitor implements IVisitor {
    private String codiceDc;
    private String log;

    /**
     * Ottieni il codice generato
     * @return Stringa contenente il codice
     */
    public String getCodiceDc(){
        return codiceDc;
    }

    /**
     * Ottieni il log
     * @return Stringa contenente il log
     **/
    public String getLog(){
        return log;
    }

    /**
     * Visita il NodeProgram root dell'ast e tutti i suoi figli, se all'uscita dell'accept del figlio il log contiene qualcosa
     * c'è stato un errore nei registri allora esco dal metodo
     * @param node  Nodo root dell'ast
     */
    @Override
    public void visit(NodeProgram node) {
        codiceDc = "";
        log = "";
        for (NodeDecSt n: node.getDecSts()){
            n.accept(this);
            if (!log.isEmpty()) return;
        }
    }

    /**
     * Visita il NodeId dell'ast, se log non è vuoto ritorno
     * altrimenti aggiungo al codice il nome del registro dell'id
     * @param node Nodo da visitare
     */
    @Override
    public void visit(NodeId node) {
        if (!log.isEmpty()) return;
        codiceDc += node.getAttributes().getRegister() + " ";
    }

    /**
     * Visita il NodeDecl dell'ast, se il log non è vuoto ritorno
     * altrimenti tento di aggiungere un nuovo registro all'attribute della variabile, se i registri sono finiti
     * scrivo su log l'errore, se la variabile ha un'inizializzazione aggiungo il codice di inizializzazione
     * @param node  Nodo da visitare
     */
    @Override
    public void visit(NodeDecl node) {
        if (!log.isEmpty()) return;
        SymbolTable.Attributes newAttributes = node.getId().getAttributes();
        char newRegister = Registers.newRegister();
        if (newRegister >= Registers.maxRegister){
            log += "Exceeded max register number\n";
            return;
        }
        newAttributes.setRegister(newRegister);
        node.getId().setAttributes(newAttributes);

        if (node.getInit() != null){
            node.getInit().accept(this);
            codiceDc += "s";
            node.getId().accept(this);
        }
    }

    /**
     * Visita il NodePrint dell'ast, se log non è vuoto ritorno
     * altrimenti aggiungo il codice per stampare e svuotare il top dello stack
     * @param node  Nodo da visitare
     */
    @Override
    public void visit(NodePrint node) {
        if (!log.isEmpty()) return;
        codiceDc += "l";
        node.getId().accept(this);
        codiceDc += "p P ";
    }

    /**
     * Visita il NodeAssign dell'ast, se log non è vuoto ritorno
     * altrimenti aggiungo il codice per assegnare un'espressione all'id
     * @param node  Nodo da visitare
     */
    @Override
    public void visit(NodeAssign node) {
        if (!log.isEmpty()) return;
        node.getExpr().accept(this);

        codiceDc += "s";
        node.getId().accept(this);
    }

    /**
     * Visita il NodeBinOp dell'ast, se log non è vuoto ritorno
     * altrimenti aggiungo codice in base all'operazione da eseguire
     * @param node  Nodo da visitare
     */
    @Override
    public void visit(NodeBinOp node) {
        if (!log.isEmpty()) return;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        switch(node.getOp()){
            case Plus -> {
                codiceDc += "+ ";
            }
            case Minus -> {
                codiceDc += "- ";
            }
            case Times -> {
                codiceDc += "* ";
            }
            case Divide -> {
                codiceDc += "/ ";
            }
            case Divide_float -> {
                codiceDc += "5 k / 0 k ";
            }
        }
    }

    /**
     * Visita il NodeCost dell'ast, se log non è vuoto ritorno
     * altrimenti aggiungo al codice il valore della costante
     * @param node  Nodo da visitare
     */
    @Override
    public void visit(NodeCost node) {
        if (!log.isEmpty()) return;
        codiceDc += node.getValue() + " ";
    }

    /**
     * Visita il NodeDeref dell'ast, se log non è vuoto ritorno
     * altrimenti aggiungo il codice per inserire il valore del registro in cima allo stack
     * @param node  Nodo da visitare
     */
    @Override
    public void visit(NodeDeref node) {
        if (!log.isEmpty()) return;
        codiceDc += "l";
        node.getId().accept(this);
    }
}
