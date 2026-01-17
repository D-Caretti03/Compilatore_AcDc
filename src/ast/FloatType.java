package ast;

public class FloatType extends TypeDescriptor{

    @Override
    public boolean compatibile(TypeDescriptor tipo) {
        return (tipo instanceof FloatType || tipo instanceof IntType || tipo instanceof VoidType);
    }
}
