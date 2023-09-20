import dscps.Descriptor;
import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_WhileLoop {
    public void start_be_while() {
        String startLabel = CodeGeneratorImpl.usageCodes.labelMaker();
        CodeGeneratorImpl.usageCodes.writeComment(false, "start of while loop with label" + startLabel);
        CodeGeneratorImpl.usageCodes.addLabel(startLabel + ":");
        Descriptor label = new Descriptor(startLabel);
        CodeGeneratorImpl.semanticStack.push(label);
    }

    public void while_dcl() {
        String endLabel = CodeGeneratorImpl.usageCodes.labelMaker();
        CodeGeneratorImpl.usageCodes.writeComment(false, "while jz");
        Descriptor condition = CodeGeneratorImpl.semanticStack.pop();
        if (condition instanceof PrimitiveDescriptor) {
            if (((PrimitiveDescriptor) condition).type != PrimitiveType.BOOLEAN_PRIMITIVE) {
                throw new Error(CodeGeneratorImpl.MakeError() + "expr should be a boolean type");
            }
        } else {
            throw new Error(CodeGeneratorImpl.MakeError() + "Wrong expr declaration");
        }

        CodeGeneratorImpl.usageCodes.writeCommand("la", "$t0", ((PrimitiveDescriptor) condition).address);
        CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", "0($t0)");
        CodeGeneratorImpl.usageCodes.writeCommand("beqz", "$t1", endLabel);
        Descriptor endLabelDescriptor = new Descriptor(endLabel);
        CodeGeneratorImpl.semanticStack.push(endLabelDescriptor);
    }

    public void complete_while() {
        String endLabel = CodeGeneratorImpl.semanticStack.pop().symName;
        String startLabel = CodeGeneratorImpl.semanticStack.pop().symName;
        CodeGeneratorImpl.usageCodes.writeComment(false, "complete while");
        CodeGeneratorImpl.usageCodes.writeCommand("j", startLabel);
        CodeGeneratorImpl.usageCodes.addLabel(endLabel + ":");
    }
}
