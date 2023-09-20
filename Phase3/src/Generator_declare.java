import dscps.Descriptor;
import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_declare {
    public void declareVariable() {
        Descriptor descriptor = CodeGeneratorImpl.semanticStack.peek();


        if (descriptor instanceof PrimitiveDescriptor) {
            declarePrimitiveVariable((PrimitiveDescriptor) descriptor);
            return;
        }

        throw new Error(CodeGeneratorImpl.MakeError() + "Variable can't be instantiated");

    }

    private void declarePrimitiveVariable(PrimitiveDescriptor pd) {
        Symbol currentSymbol = CodeGeneratorImpl.scanner.currentSymbol;
        String token = currentSymbol.getToken();

        if (CodeGeneratorImpl.GlobalDSCP.containsKey(token)
                || CodeGeneratorImpl.currentMethod.symTable.containsKey(token))
            throw new Error(CodeGeneratorImpl.MakeError() + "Identifier " + token + " has been declared before!");

        String type = stringTypeOfPrimitiveType(pd.type);

        String address = CodeGeneratorImpl.usageCodes.allocateMemory(CodeGeneratorImpl.GlobalDSCP.get(type),
                pd.type == PrimitiveType.REAL_PRIMITIVE ? "0.0" : "0");

        PrimitiveDescriptor creatingVarDescriptor =
                new PrimitiveDescriptor(token, address, pd.type);
        CodeGeneratorImpl.currentMethod.addVariableAndUpdateSymbolTable(token, creatingVarDescriptor);
    }

    private String stringTypeOfPrimitiveType(PrimitiveType pt) {
        String type;
        if (pt == PrimitiveType.INTEGER_PRIMITIVE)
            type = "int";
        else if (pt == PrimitiveType.REAL_PRIMITIVE)
            type = "real";
        else if (pt == PrimitiveType.STRING_PRIMITIVE)
            type = "string";
        else if (pt == PrimitiveType.BOOLEAN_PRIMITIVE)
            type = "bool";
        else
            throw new Error(CodeGeneratorImpl.MakeError() + "Not a valid type!");
        return type;
    }
}
