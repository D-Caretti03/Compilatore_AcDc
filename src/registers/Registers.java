package registers;

public class Registers {
    private static char nextRegister = 'a';
    public static final char maxRegister = 'z';

    public Registers(){}

    public static void resetRegisters(){
        nextRegister = 'a';
    }

    public static char newRegister(){
        char register = nextRegister;
        nextRegister++;
        return register;
    }
}
