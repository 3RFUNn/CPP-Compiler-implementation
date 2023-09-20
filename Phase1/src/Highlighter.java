import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// This code was written by Erfan Rafiee and Farbod Fooladi

public class Highlighter {
    Map<Integer, ArrayList<RFOSK.Symbol>> SymbolMap;
    RFOSK.Symbol Symbol;
    int lineNumber;

    public void highlightHTML(ArrayList<RFOSK.Symbol> SymList, FileWriter fw) throws IOException {
        SymbolMap = new HashMap<>();

        for (RFOSK.Symbol item : SymList) {
            Symbol = item;
            lineNumber = Symbol.lineNumber;

            if (!SymbolMap.containsKey(lineNumber))
                SymbolMap.put(lineNumber, new ArrayList<>());

            SymbolMap.get(lineNumber).add(Symbol);
        }

        ArrayList<RFOSK.Symbol> currentSym;
        int lineNumber;

        RFOSK.Symbol tempSym;
        String tempType;
        Object tempValue;

        for (int i : SymbolMap.keySet()) {
            currentSym = SymbolMap.get(i);

            StringBuilder htmlCode = new StringBuilder("<p>");
            lineNumber = i + 1;
            htmlCode.append("<div class=\"count\">").append(lineNumber).append(": </div>");

            for (RFOSK.Symbol value : currentSym) {
                tempSym = value;
                tempType = tempSym.type;
                tempValue = tempSym.value;

                if (!tempType.equals("WhiteSpace")) {
                    htmlCode.append("<div class= \"").append(tempType).append("\">").append(tempValue).append("</div>");
                } else {
                    String whiteSpace = "&#160";
                    htmlCode.append("<div>").append(whiteSpace).append("</div>");
                }
            }

            htmlCode.append("</p>");
            fw.write(htmlCode.toString());
        }

        fw.write("</body>");
        fw.write("</html>");
        fw.flush();
        fw.close();
    }

}
