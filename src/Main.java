import ast.ErrorType;
import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Expected file in args");
            System.exit(1);
        }
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
        if (typeCheckingVisitor.GetResType() instanceof ErrorType){
            System.out.println(((ErrorType) typeCheckingVisitor.GetResType()).getMsg());
            System.exit(2);
        }
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
