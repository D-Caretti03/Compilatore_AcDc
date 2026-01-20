package ast;

/**
 * Classe pubblica FloatType che estende {@link TypeDescriptor}.
 * Usato per i valori di ritorno float
 */
public class FloatType extends TypeDescriptor{

    @Override
    public boolean compatibile(TypeDescriptor tipo) {
        return (tipo instanceof FloatType || tipo instanceof IntType || tipo instanceof VoidType);
    }
}
