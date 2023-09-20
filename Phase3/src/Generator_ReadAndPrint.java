import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_ReadAndPrint {
    public void read_int() {
        CodeGeneratorImpl.semanticStack.push(CodeGeneratorImpl.usageCodes.generateReadInt());
    }

    public void read_string() {
        CodeGeneratorImpl.semanticStack.push(CodeGeneratorImpl.usageCodes.generateReadString());
    }
    
    public void print_expr() {
        PrimitiveDescriptor pd = (PrimitiveDescriptor) CodeGeneratorImpl.semanticStack.pop();
        if (pd.type == PrimitiveType.REAL_PRIMITIVE)
            print_real(pd);
        else if (pd.type == PrimitiveType.STRING_PRIMITIVE)
            print_string(pd);
        else
            print_int(pd);
    }

    public void print_string(PrimitiveDescriptor pd) {
        if (pd.type != PrimitiveType.STRING_PRIMITIVE)
            throw new Error(CodeGeneratorImpl.MakeError() + "Expected String but got " + pd.type);
        CodeGeneratorImpl.usageCodes.writeComment(false, "Printing " + pd.symName);
        CodeGeneratorImpl.usageCodes.writeCommand("li", "$v0", "4");
        CodeGeneratorImpl.usageCodes.writeCommand("la", "$a0", pd.getAddress());
        CodeGeneratorImpl.usageCodes.writeCommand("syscall");

    }

    public void print_real(PrimitiveDescriptor pd) {
        if (pd.type != PrimitiveType.REAL_PRIMITIVE)
            throw new Error(CodeGeneratorImpl.MakeError() + "Expected Real type but got " + pd.type);
        CodeGeneratorImpl.usageCodes.writeComment(false, "Printing " + pd.symName);
        CodeGeneratorImpl.usageCodes.writeCommand("li", "$v0", "2");
        CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f12", pd.getAddress());
        CodeGeneratorImpl.usageCodes.writeCommand("syscall");
    }

    public void print_int(PrimitiveDescriptor pd) {
        if (pd.type != PrimitiveType.INTEGER_PRIMITIVE && pd.type != PrimitiveType.BOOLEAN_PRIMITIVE)
            throw new Error(CodeGeneratorImpl.MakeError() + "Expected Integer or boolean but got " + pd.type);
        CodeGeneratorImpl.usageCodes.writeComment(false, "Printing " + pd.symName);
        CodeGeneratorImpl.usageCodes.writeCommand("li", "$v0", "1");
        CodeGeneratorImpl.usageCodes.writeCommand("lw", "$a0", pd.getAddress());
        CodeGeneratorImpl.usageCodes.writeCommand("syscall");
    }
}
