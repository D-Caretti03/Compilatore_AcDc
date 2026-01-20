package ast;

/**
 * Classe astratta TypeDescriptor. Indica il tipo del valore di ritorno dalla visita di un nodo
 */
public abstract class TypeDescriptor {
    /**
     * Metodo che indica se due TypeDescriptor sono compatibili tra loro
     * @param tipo  TypeDescriptor da comparare con this
     * @return  True se sono compatibili, false altrimenti
     */
    abstract public boolean compatibile(TypeDescriptor tipo);
}

