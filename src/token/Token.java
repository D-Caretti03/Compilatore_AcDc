package token;

public class Token {
    private int riga;
    private TokenType tipo;
    private String valore;

    public Token(int riga, TokenType tipo){
        super();
        this.riga = riga;
        this.tipo = tipo;
        this.valore = "";
    }

    public TokenType GetTipo() {
        return tipo;
    }

    public int GetRiga(){
        return riga;
    }

    public String GetValore(){
        return valore;
    }

    public Token(int riga, TokenType tipo, String valore){
        super();
        this.riga = riga;
        this.tipo = tipo;
        this.valore = valore;
    }

    @Override
    public String toString() {
        return "<" + this.tipo + ", r:" + riga + (valore.isEmpty() ? "" : (", " + valore))+ ">";
    }
}
