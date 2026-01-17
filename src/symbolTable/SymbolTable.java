package symbolTable;

import ast.LangType;

import java.util.HashMap;
import java.util.Objects;

public class SymbolTable {
    public static HashMap<String, Attributes> table;

    public static class Attributes{
        private LangType tipo;

        public Attributes(LangType tipo){
            this.tipo = tipo;
        }
        public LangType getTipo(){
            return this.tipo;
        }
    }

    public static void init(){
        table = new HashMap<>();
    }

    public static boolean enter(String id, Attributes entry){
        if (table.get(id) != null) return false;
        table.put(id, entry);
        return true;
    }

    public static Attributes lookup(String id){
        return table.get(id);
    }

    public static String toStr(){
        StringBuilder sb = new StringBuilder();
        table.forEach( (id,attr) -> {
            sb.append("ID: ").append(id).append("\tATTR: ").append(attr).append('\n');});
        return sb.toString();
    }

    public static int size(){
        return table.size();
    }
}
