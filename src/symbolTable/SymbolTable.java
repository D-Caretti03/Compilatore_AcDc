package symbolTable;

import ast.LangType;

import java.util.HashMap;

/**
 * Classe pubblica per gestire la symbolTable del compilatore, la mappa e dichiarata statica in modo che sia accessibile e
 * comune alle classi che importano la symbolTable
 */
public class SymbolTable {
    public static HashMap<String, Attributes> table;

    /**
     * Classe pubblica Attributes che indica gli attributi di un id
     */
    public static class Attributes{
        private LangType tipo;
        private char register;

        public Attributes(LangType tipo){
            this.tipo = tipo;
        }
        public LangType getTipo(){
            return this.tipo;
        }
        public char getRegister() {return this.register;}
        public void setRegister(char r) {this.register = r;}
    }

    /**
     * Inizializza la mappa
     */
    public static void init(){
        table = new HashMap<>();
    }

    /**
     * Inserisce un id e suoi attributi nella symbolTable
     * @param id    Nome dell'id
     * @param entry Attributi dell'id
     * @return  True se viene inserito correttamente false altrimenti
     */
    public static boolean enter(String id, Attributes entry){
        if (table.get(id) != null) return false;
        table.put(id, entry);
        return true;
    }

    /**
     * Effettua il lookup nella symbolTable dato un id
     * @param id    Nome dell'id
     * @return  L'Attributes collegato all'id
     */
    public static Attributes lookup(String id){
        return table.get(id);
    }

    /**
     * Metodo toString per la symbolTable
     * @return  Stringa contenente i campi e i valori della symbolTable
     */
    public static String toStr(){
        StringBuilder sb = new StringBuilder();
        table.forEach( (id,attr) -> {
            sb.append("ID: ").append(id).append("\tATTR: ").append(attr).append('\n');});
        return sb.toString();
    }

    /**
     * Ottieni la dimensione della symnolTable
     * @return  Int che indica la dimensione della mappa
     */
    public static int size(){
        return table.size();
    }
}
