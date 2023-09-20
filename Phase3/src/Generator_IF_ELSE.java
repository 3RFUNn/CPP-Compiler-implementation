import dscps.Descriptor;
import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_IF_ELSE {
    public void if_dcl() {
        CodeGeneratorImpl.usageCodes.writeComment(false, "if dcl");

        String elseStart = CodeGeneratorImpl.usageCodes.labelMaker();
        Descriptor elseStartLabel = new Descriptor(elseStart);


        Descriptor condition = CodeGeneratorImpl.semanticStack.pop();
        if (condition instanceof PrimitiveDescriptor) {
            if (((PrimitiveDescriptor) condition).type != PrimitiveType.BOOLEAN_PRIMITIVE) {
                throw new Error(CodeGeneratorImpl.MakeError() + "expr should be a boolean type");
            }
        } else {
            throw new Error(CodeGeneratorImpl.MakeError() + "Wrong expr decleration");
        }

        CodeGeneratorImpl.semanticStack.push(elseStartLabel);

        CodeGeneratorImpl.usageCodes.writeCommand("la", "$t0", ((PrimitiveDescriptor) condition).address);
        CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", "0($t0)");
        CodeGeneratorImpl.usageCodes.writeCommand("beqz", "$t1", elseStart);
    }

    public void complete_if() {
        CodeGeneratorImpl.usageCodes.writeComment(false, "if cjz");
        String endOfElse = CodeGeneratorImpl.usageCodes.labelMaker();
        Descriptor endOfElseDescriptor = new Descriptor(endOfElse);


        CodeGeneratorImpl.usageCodes.writeCommand("j", endOfElse);
        CodeGeneratorImpl.usageCodes.addLabel(CodeGeneratorImpl.semanticStack.pop().symName + ":");
        CodeGeneratorImpl.semanticStack.push(endOfElseDescriptor);
    }

    public void end_if() {
        CodeGeneratorImpl.usageCodes.addLabel(CodeGeneratorImpl.semanticStack.pop().symName + ":");
    }
}
