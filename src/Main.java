import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;
import java.io.FileWriter;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String nomeFile = args[0];
        NodeProgram nodeProgram;
        TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
        CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();
        try {
            nodeProgram = new Parser(new Scanner(nomeFile)).parse();
        } catch(LexicalException | SyntacticException e) {
            throw new RuntimeException(e);
        }
        nodeProgram.accept(typeCheckingVisitor);
        nodeProgram.accept(codeGeneratorVisitor);

        int indexOfDot = nomeFile.lastIndexOf('.');
        String outFile = nomeFile.substring(0, indexOfDot) + "_out" + nomeFile.substring(indexOfDot);

        try{
            FileWriter writer = new FileWriter(outFile);
            writer.write(codeGeneratorVisitor.getCodiceDc() + '\n');
            writer.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}