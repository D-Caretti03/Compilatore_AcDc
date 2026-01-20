package ast;

/**
 * Classe pubblica ErrorType che estende {@link TypeDescriptor}.
 * Indica che il tipo di ritorno dalla visita del nodo è Error
 */
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

    /**
     * Effettua il merge dei messaggi di errore di due ErrorType, viene effettuato un controllo per il tipo dell'oggetto
     * @param error Oggetto di cui eseguire il merge
     */
    public void merge(TypeDescriptor error){
        if (this.compatibile(error)){
            this.msg += ((ErrorType) error).getMsg();
        }
    }

    /**
     * Metodo statico usato per costruire un oggetto ErrorType per una variabile ridefinita
     * @param varName   Nome della variabile ridefinita
     * @return  Un nuovo oggetto ErrorType con messaggio di errore
     */
    public static ErrorType variableRedefinition(String varName){
         return new ErrorType(String.format("Error:\n\tRedefinition of the variable %s.\n", varName));
    }

    /**
     * Metodo statico usato per costruire un oggetto ErrorType per una variabile usata ma non definita
     * @param varName   Nome della variabile
     * @return  Un nuovo oggetto ErrorType con messaggio di errore
     */
    public static ErrorType variableNotDefined(String varName){
        return new ErrorType(String.format("Error:\n\tUse of undefined variable %s.\n", varName));
    }

    /**
     * Metodo statico usato per costruire un oggetto ErrorType per la non compatibilità tra tipi
     * @return  Un nuovo oggetto ErrorType con messaggio di errore
     */
    public static ErrorType typeNotCompatible(){
        return new ErrorType("Error:\n\tCannot convert float to int.\n");
    }

    @Override
    public boolean compatibile(TypeDescriptor tipo) {
        return tipo instanceof ErrorType;
    }

    /**
     * Ottiene il messaggio di errore
     * @return  Stringa contenente il messaggio di errore
     */
    public String getMsg(){
        return this.msg;
    }
}
