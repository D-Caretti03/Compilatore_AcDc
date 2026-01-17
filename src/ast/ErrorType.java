package ast;

public class ErrorType extends TypeDescriptor{
    private String msg;

    public ErrorType(String msg){
        this();
        this.msg = msg;
    }

    public ErrorType(){
        super();
        this.msg = "";
    }

    public void merge(TypeDescriptor error){
        if (this.compatibile(error)){
            this.msg += ((ErrorType) error).getMsg();
        }
    }

    public static ErrorType variableRedefinition(String varName){
         return new ErrorType(String.format("Error:\n\tRedefinition of the variable %s.\n", varName));
    }

    public static ErrorType variableNotDefined(String varName){
        return new ErrorType(String.format("Error:\n\tUse of undefined variable %s.\n", varName));
    }

    public static ErrorType typeNotCompatible(){
        return new ErrorType("Error:\n\tCannot convert float to int.\n");
    }

    @Override
    public boolean compatibile(TypeDescriptor tipo) {
        return tipo instanceof ErrorType;
    }

    public String getMsg(){
        return this.msg;
    }
}
