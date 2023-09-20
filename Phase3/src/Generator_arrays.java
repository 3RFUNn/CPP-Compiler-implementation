import dscps.Descriptor;
import dscps.OtherDescriptors;
import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_arrays {
    /**
     * We cannot specify memory for arrays in declaration, allocating memory is done in assignment section for arrays.
     */
    public void declareArray() {
        Descriptor arrayElementTypeDescriptor = CodeGeneratorImpl.semanticStack.peek();
        String creatingArrayName = CodeGeneratorImpl.scanner.currentSymbol.getToken();

        if (isDeclaredToken(creatingArrayName))
            throw new Error(CodeGeneratorImpl.MakeError() + "Identifier is declared before!");

        OtherDescriptors.ArrayDescriptor arrayDescriptor = new OtherDescriptors.ArrayDescriptor(creatingArrayName, arrayElementTypeDescriptor);
        CodeGeneratorImpl.currentMethod.addVariableAndUpdateSymbolTable(creatingArrayName, arrayDescriptor);
    }

    private boolean isDeclaredToken(String token) {
        return (CodeGeneratorImpl.currentMethod.symTable.containsKey(token) ||
                CodeGeneratorImpl.GlobalDSCP.containsKey(token));
    }
}
