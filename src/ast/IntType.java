package ast;

public class IntType extends TypeDescriptor{

    @Override
    public boolean compatibile(TypeDescriptor tipo) {
        return (tipo instanceof IntType || tipo instanceof VoidType);
    }
}
