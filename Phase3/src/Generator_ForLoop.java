import dscps.Descriptor;
import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_ForLoop {
    public void start_be_for() {
        CodeGeneratorImpl.usageCodes.writeComment(false, "for start");
        String topOfBe = CodeGeneratorImpl.usageCodes.labelMaker();
        CodeGeneratorImpl.usageCodes.addLabel(topOfBe + ":");
        Descriptor topOfBeLabel = new Descriptor(topOfBe);
        CodeGeneratorImpl.semanticStack.push(topOfBeLabel);
    }

    public void for_dcl() {
        Descriptor condition = CodeGeneratorImpl.semanticStack.pop();
        if (condition instanceof PrimitiveDescriptor) {
            if (((PrimitiveDescriptor) condition).type != PrimitiveType.BOOLEAN_PRIMITIVE) {
                throw new Error(CodeGeneratorImpl.MakeError() + "expr should be a boolean type");
            }
        } else {
            throw new Error(CodeGeneratorImpl.MakeError() + "Wrong expr declaration");
        }


        String topOfAssign = CodeGeneratorImpl.usageCodes.labelMaker();

        Descriptor topOfAssignLabel = new Descriptor(topOfAssign);
        CodeGeneratorImpl.semanticStack.push(topOfAssignLabel);


        String end = CodeGeneratorImpl.usageCodes.labelMaker();
        Descriptor endLabel = new Descriptor(end);
        CodeGeneratorImpl.semanticStack.push(endLabel);

        String topOfSts = CodeGeneratorImpl.usageCodes.labelMaker();
        Descriptor topOfStsLabel = new Descriptor(topOfSts);
        CodeGeneratorImpl.semanticStack.push(topOfStsLabel);


        CodeGeneratorImpl.usageCodes.writeCommand("la", "$t0", ((PrimitiveDescriptor) condition).address);
        CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", "0($t0)");
        CodeGeneratorImpl.usageCodes.writeCommand("beqz", "$t1", end);
        CodeGeneratorImpl.usageCodes.writeCommand("j", topOfSts);
        CodeGeneratorImpl.usageCodes.addLabel(topOfAssign + ":");


    }

    public void forAssignComp() {

        Descriptor topOfSts = CodeGeneratorImpl.semanticStack.pop();
        Descriptor endOfLoop = CodeGeneratorImpl.semanticStack.pop();
        Descriptor topOfAssign = CodeGeneratorImpl.semanticStack.pop();
        Descriptor topOfBe = CodeGeneratorImpl.semanticStack.pop();
        CodeGeneratorImpl.usageCodes.writeCommand("j", topOfBe.symName);
        CodeGeneratorImpl.usageCodes.addLabel(topOfSts.symName + ":");

        CodeGeneratorImpl.semanticStack.push(topOfAssign);
        CodeGeneratorImpl.semanticStack.push(endOfLoop);
    }

    public void complete_for() {
        Descriptor endOfLoop = CodeGeneratorImpl.semanticStack.pop();
        Descriptor topOfAssign = CodeGeneratorImpl.semanticStack.pop();

        CodeGeneratorImpl.usageCodes.writeCommand("j", topOfAssign.symName);
        CodeGeneratorImpl.usageCodes.addLabel(endOfLoop.symName + ":");
    }
}
