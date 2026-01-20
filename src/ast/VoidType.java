package ast;

/**
 * Classe pubblica VoidType che estende {@link TypeDescriptor}.
 * Nodo che indica che la visita attuale non ha valore di ritorno
 */
public class VoidType extends TypeDescriptor{

    @Override
    public boolean compatibile(TypeDescriptor tipo) {
        return !(tipo instanceof ErrorType);
    }
}
