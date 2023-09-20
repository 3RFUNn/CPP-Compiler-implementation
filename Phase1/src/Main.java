import java.io.*;
import java.util.*;
// This code was written by Erfan Rafiee and Farbod Fooladi
public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<RFOSK.Symbol> symbols = new ArrayList<>();
        Highlighter H1 = new Highlighter();
        FileWriter fileWriter =
            new FileWriter
                ("D:\\InteliJ Projects\\Compiler_Phase1\\src\\Output\\Output.html" , true);
        try {
            RFOSK scanner =
                new RFOSK
                    (new FileReader("D:\\InteliJ Projects\\Compiler_Phase1\\src\\input.txt"));

            while (true) {
                RFOSK.Symbol currentsymbol = scanner.next_symbol();
                if (scanner.yyatEOF()) {
                    break;
                }
                symbols.add(currentsymbol);
                System.out.println(currentsymbol.type + ": " + currentsymbol.value);
            }

            H1.highlightHTML(symbols,fileWriter);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}
