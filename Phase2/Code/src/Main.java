import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputCoolFilePath = "";
        String outputFilePath = "";
        String tablePath = "";
        if (args.length >= 6)
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--input"))
                    inputCoolFilePath = args[i + 1];
                if (args[i].equals("--output"))
                    outputFilePath = args[i + 1];
                if (args[i].equals("--table"))
                    tablePath = args[i + 1];
            }
        else return;

        RFOSK scanner = new RFOSK(new FileReader(inputCoolFilePath));
        codeGenIMP codeGenerator = new codeGenIMP();
        Parser parser = new Parser(scanner, codeGenerator, tablePath);

        FileWriter fileWriter = new FileWriter(outputFilePath);
        try {
            parser.parse();
            fileWriter.write("Syntax is correct!");
        } catch (Exception e) {
            fileWriter.write("Syntax is wrong!");
        }
        fileWriter.flush();
        fileWriter.close();
    }
}