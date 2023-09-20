import dscps.Descriptor;
import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_push {
    public void push() {
        String symName = CodeGeneratorImpl.scanner.currentSymbol.getToken();
        TokenType tokenType = CodeGeneratorImpl.scanner.currentSymbol.getType();

        if (CodeGeneratorImpl.constantTypes.contains(tokenType)) {
            push_constant(symName, tokenType);
            return;
        }

        if (CodeGeneratorImpl.currentMethod.symTable.containsKey(symName)) {
            Descriptor descriptor = CodeGeneratorImpl.currentMethod.symTable.get(symName);
            CodeGeneratorImpl.semanticStack.push(descriptor);

        } else if (CodeGeneratorImpl.GlobalDSCP.containsKey(symName)) {
            Descriptor descriptor = CodeGeneratorImpl.GlobalDSCP.get(symName);
            CodeGeneratorImpl.semanticStack.push(descriptor);
        }
        else
            throw new Error(CodeGeneratorImpl.MakeError() + "Literal " + symName + " is not declared within current scope!");
    }

    public void push_constant(String token, TokenType type) {
        if (CodeGeneratorImpl.GlobalDSCP.containsKey(token)) {
            CodeGeneratorImpl.semanticStack.push(CodeGeneratorImpl.GlobalDSCP.get(token));
            return;
        }

        PrimitiveType constantType;
        switch (type) {
            case INTEGER:
                constantType = PrimitiveType.INTEGER_PRIMITIVE;
                break;
            case REAL:
                constantType = PrimitiveType.REAL_PRIMITIVE;
                break;
            case STRING:
                constantType = PrimitiveType.STRING_PRIMITIVE;
                break;
            default:
                throw new Error(CodeGeneratorImpl.MakeError() + "Not a valid constant type");
        }
        PrimitiveDescriptor pd = new PrimitiveDescriptor(token, "", constantType);
        pd.address = CodeGeneratorImpl.usageCodes.allocateMemory(pd, token);
        pd.activeIsConstant();

        CodeGeneratorImpl.GlobalDSCP.put(token, pd);
        CodeGeneratorImpl.semanticStack.push(pd);
    }
}
