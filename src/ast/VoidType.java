package ast;

public class VoidType extends TypeDescriptor{

    @Override
    public boolean compatibile(TypeDescriptor tipo) {
        return !(tipo instanceof ErrorType);
    }
}
