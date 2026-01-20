package scanner;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import token.*;

/**
 * Classe pubblica Scanner per gestire lo scanner del programma da compilare, usa un automa a stati finiti per emettere il token giusto.
 * Per leggere il file in input carattere per carattere usiamo un buffer di tipo PushbackReader.
 */
public class Scanner {
    final char EOF = (char) -1;
    private int riga;
    private int colonna;
    private final PushbackReader buffer;
    private Token currToken;

    // skpChars: insieme caratteri di skip (include EOF) e inizializzazione
    private final ArrayList<Character> skipChars = new ArrayList<>(Arrays.asList(EOF, '\n', '\r', '\t', ' '));

    // letters: insieme lettere
    private final ArrayList<Character> letters = new ArrayList<>();

    // digits: cifre
    private final ArrayList<Character> digits = new ArrayList<>();

    // operTkType: mapping fra caratteri '+', '-', '*', '/'  e il TokenType corrispondente
    private final Map<Character, TokenType> operTkType = new HashMap<>();

    // delimTkType: mapping fra caratteri '=', ';' e il e il TokenType corrispondente
    private final Map<Character, TokenType> delimTkType = new HashMap<>();

    // keyWordsTkType: mapping fra le stringhe "print", "float", "int" e il TokenType  corrispondente
    private final Map<String, TokenType> keyWordsTkType = new HashMap<>();

    public Scanner(String fileName) throws LexicalException {

        try{
            this.buffer = new PushbackReader(new FileReader(fileName));
        } catch(FileNotFoundException e){
            throw new LexicalException("FileNotFoundException", e);
        }
        riga = 1;
        colonna = 1;
        currToken = null;
        // inizializzare campi che non hanno inizializzazione
        for (char c = 'a'; c <= 'z'; ++c){
            letters.add(c);
        }
        for (char c = 'A'; c <= 'Z'; ++c){
            letters.add(c);
        }

        for (char i = '0'; i <= '9'; ++i){
            digits.add(i);
        }

        keyWordsTkType.put("print", TokenType.PRINT);
        keyWordsTkType.put("int", TokenType.TY_INT);
        keyWordsTkType.put("float", TokenType.TY_FLOAT);

        delimTkType.put(';', TokenType.SEMI);
        delimTkType.put('=', TokenType.ASSIGN);

        operTkType.put('+', TokenType.PLUS);
        operTkType.put('-', TokenType.MINUS);
        operTkType.put('/', TokenType.DIVIDE);
        operTkType.put('*', TokenType.TIMES);
    }

    /**
     * Restituisce il l'ultimo tokeno generato, fino a quando non si chiama la nextToken() che aggiornerà currToken.
     * @return Il Token salvato in cache tramite currToken
     * @throws LexicalException Eccezione lessicale se la nextToken lancia un'eccezione lessicale
     */
    public Token peekToken() throws LexicalException{
        if (currToken == null){
            currToken = nextToken();
        }

        return currToken;
    }

    /**
     * Tramite un automa a stati finiti genera un token prendendo in input carattere per carattere il file da compilare.
     * @return Il Token emesso dato l'attuale input nell'automa a stati finiti
     * @throws LexicalException Eccezione lessicale per errori nel linguaggio
     */
    public Token nextToken() throws LexicalException{
        if (currToken != null){
            Token temp = currToken;
            currToken = null;
            return temp;
        }

        // nextChar contiene il prossimo carattere dell'input (non consumato).
        //Catturate l'eccezione IOException e
        // ritornate una LexicalException che la contiene
        char nextChar = peekChar();

        // Avanza nel buffer leggendo i carattere in skipChars
        // incrementando riga se leggi '\n'.
        // Se raggiungi la fine del file ritorna il Token EOF
        while(this.skipChars.contains(nextChar)){
            nextChar = readChar();
            if (nextChar == '\n') {
                riga++;
                colonna = 1;
            }
            if (nextChar == EOF) return new Token(riga, TokenType.EOF);
            nextChar = peekChar();
        }

        // Se nextChar e' in letters
        if (letters.contains(nextChar)) {
            return scanId();
        }
        // return scanId()
        // che deve generare o un Token ID o parola chiave

        // Se nextChar e' o in operators oppure delimitatore
        // ritorna il Token associato con l'operatore o il delimitatore
        // Attenzione agli operatori di assegnamento!
        nextChar = peekChar();
        if (operTkType.containsKey(nextChar)){
            return scanOperator();
        }


        nextChar = peekChar();
        // Se nextChar e' ; o =
        // ritorna il Token associato
        if (delimTkType.containsKey(nextChar)){
            nextChar = readChar();
            return new Token(riga, delimTkType.get(nextChar));
        }

        // Se nextChar e' in numbers
        // return scanNumber()
        // che legge sia un intero che un float e ritorna il Token INUM o FNUM
        // i caratteri che leggete devono essere accumulati in una stringa
        // che verra' assegnata al campo valore del Token
        nextChar = peekChar();
        if (digits.contains(nextChar)){
            return scanNumber();
        }

        // Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
        // eccezione lessicale dicendo la riga e il carattere che la hanno
        // provocata.

        throw new LexicalException("ERROR: no token starts with '" + nextChar + "': " + riga + ":" + colonna);
    }

    /**
     * Nodo dell'automa dove si legge il nome di una variable oppure una parola chiave
     * @return Token generato dal nodo dell'automa
     * @throws LexicalException Eccezione lessicale se l'I/O lancia un'eccezione
     */
    private Token scanId() throws  LexicalException{
        char letter = peekChar();
        StringBuilder word = new StringBuilder();

        while (letters.contains(letter) || digits.contains(letter)){
            letter = readChar();
            word.append(letter);
            letter = peekChar();
        }
        if (keyWordsTkType.containsKey(word.toString())){
            return new Token(riga, keyWordsTkType.get(word.toString()));
        }

        return new Token(riga, TokenType.ID, word.toString());
    }

    /**
     * Nodo dell'automa che legge un operatore binario
     * @return Token generato dal nodo dell'automa
     * @throws LexicalException Eccezione lessicale se l'I/O lancia un'eccezione, oppure se l'operatore non è riconosciuto
     */
    private Token scanOperator() throws LexicalException{
        char letter = readChar();
        char next = peekChar();

        if (next == '='){
            next = readChar();
            TokenType t = switch (letter) {
                case '+' -> TokenType.OP_PLUS_ASSIGN;
                case '-' -> TokenType.OP_MINUS_ASSIGN;
                case '/' -> TokenType.OP_DIVIDE_ASSIGN;
                case '*' -> TokenType.OP_MULTIPLY_ASSIGN;
                default ->
                        throw new LexicalException("ERROR: " + letter + next + " is not an operator: " + riga + ":" + colonna);
            };
            return new Token(riga, t);
        }

        return new Token(riga, operTkType.get(letter));
    }

    /**
     * Nodo dell'automa che legge in input un numero che può essere un intero o un float
     * @return Token generato dal nodo dell'automa
     * @throws LexicalException Eccezione lessicale se l'I/O lancia un'eccezione, oppure se un numero comincia con lo 0
     */
    private Token scanNumber() throws LexicalException {
        char digit = peekChar();
        StringBuilder number = new StringBuilder();

        while(digits.contains(digit)){
            digit = readChar();
            number.append(digit);
            digit = peekChar();
            if (digit == '.'){
                return scanFloat(number);
            }
        }

        String value = number.toString();

        if (value.charAt(0) == '0' && value.length() > 1){
            throw new LexicalException("ERROR: numbers cannot start with 0: " + riga + ':' + colonna);
        }

        return new Token(riga, TokenType.INT, value);
    }

    /**
     *
     * @param number stringa contenente il numero letto fino a questo momento, ovvero la parte intera
     * @return Token generato dal nodo dell'automa
     * @throws LexicalException Eccezione lessicale se l'I/O lancia un eccezione, oppure se il float ha più di 5 cifre decimali
     */
    private Token scanFloat(StringBuilder number) throws LexicalException{
        char digit = readChar();
        number.append(digit);
        int decimals = 0;
        digit = peekChar();

        while(digits.contains(digit)) {
            digit = readChar();
            decimals++;
            if (decimals > 5) {
                colonna--; // Perchè ho letto il carattere di troppo con readChar
                throw new LexicalException("ERROR: too much decimal digits: " + riga + ":" + colonna);
            }
            number.append(digit);
            digit = peekChar();
        }

        String value = number.toString();

        if (value.charAt(0) == '0' && value.indexOf('.') != 1){
            throw new LexicalException("ERROR: numbers cannot start with 0: " + riga + ':' + colonna);
        }

        return new Token(riga, TokenType.FLOAT, value);
    }

    /**
     * Legge in input un carattere e lo consuma
     * @return Carattere letto dall'input
     * @throws LexicalException Eccezione lessicala wrappata, contiene un'eccezione di I/O
     */
    private char readChar() throws LexicalException {
        colonna++;
        char c;
        try {
            c = ((char) this.buffer.read());
        } catch (IOException e){
            throw new LexicalException("IO Exception", e);
        }
        return c;
    }

    /**
     * Sbircia il carattere successivo nell'input senza consumarlo
     * @return Carattere successivo nell'input
     * @throws LexicalException Eccezione lessicale wrappata, contiene un'eccezione di I/O
     */
    private char peekChar() throws LexicalException {
        char c;
        try{
            c = (char) buffer.read();
        } catch (IOException e){
            throw new LexicalException("IO Exception", e);
        }
        try {
            buffer.unread(c);
        } catch(IOException e){
            throw new LexicalException("IO Exception", e);
        }
        return c;
    }
}