package ast;

/**
 * Classe pubblica IntType che estende {@link TypeDescriptor}.
 * Usato per i valori di ritorno int
 */
public class IntType extends TypeDescriptor{

    @Override
    public boolean compatibile(TypeDescriptor tipo) {
        return (tipo instanceof IntType || tipo instanceof VoidType);
    }
}
