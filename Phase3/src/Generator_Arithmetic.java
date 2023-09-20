import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_Arithmetic {
    private final Generator_Assignment ga = new Generator_Assignment();

    public void minusMinusOrPlusPlus(PrimitiveDescriptor pd, String op) {
        String addingVal = op.contains("plus_plus") ?
                pd.type == PrimitiveType.REAL_PRIMITIVE ? "1.0" : "1"
                :
                pd.type == PrimitiveType.REAL_PRIMITIVE ? "-1.0" : "-1";

        PrimitiveDescriptor oneDescriptor = new PrimitiveDescriptor(addingVal, "", pd.type);
        oneDescriptor.setAddress(CodeGeneratorImpl.usageCodes.allocateMemory(oneDescriptor, addingVal));

        CodeGeneratorImpl.semanticStack.push(pd);
        add(pd, oneDescriptor);
        ga.assign();
        if (op.contains("expr"))
            CodeGeneratorImpl.semanticStack.push(pd);
    }

    public void biggerThan(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Comparison : BiggerThan");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("c.lt.s", "$f0", "$f1");
            CodeGeneratorImpl.usageCodes.writeCommand("cfc1", "$t0", "$25");
            CodeGeneratorImpl.usageCodes.writeCommand("andi", "$t0", "1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);
            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Comparison : BiggerThan");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("sgt", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types to compare (>)");
    }

    public void lessThan(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Comparison : LessThan");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("c.lt.s", "$f1", "$f0");
            CodeGeneratorImpl.usageCodes.writeCommand("cfc1", "$t0", "$25");
            CodeGeneratorImpl.usageCodes.writeCommand("andi", "$t0", "1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Comparison : LessThan");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("slt", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types to compare (<)");

    }

    public void lessThanEqual(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Comparison : LessThanEqual");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("c.le.s", "$f1", "$f0");
            CodeGeneratorImpl.usageCodes.writeCommand("cfc1", "$t0", "$25");
            CodeGeneratorImpl.usageCodes.writeCommand("andi", "$t0", "1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Comparison : LessThanEqual");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("sle", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types to compare (<=)");
    }

    public void equality(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Comparison : equality");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("c.eq.s", "$f1", "$f0");
            CodeGeneratorImpl.usageCodes.writeCommand("cfc1", "$t0", "$25");
            CodeGeneratorImpl.usageCodes.writeCommand("andi", "$t0", "1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE || firstOperand.type == PrimitiveType.BOOLEAN_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Comparison : equality");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("seq", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types to compare ==");
    }

    public void inequality(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Comparison : inequality");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("c.eq.s", "$f0", "$f1");
            CodeGeneratorImpl.usageCodes.writeCommand("cfc1", "$t0", "$25");
            CodeGeneratorImpl.usageCodes.writeCommand("andi", "$t0", "1");
            CodeGeneratorImpl.usageCodes.writeCommand("seq", "$t0", "$t0", "0");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE || firstOperand.type == PrimitiveType.BOOLEAN_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Comparison : inequality");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("sne", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types to compare !=");
    }

    public void biggerThanEqual(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Comparison : BiggerThanEqual");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("c.le.s", "$f0", "$f1");
            CodeGeneratorImpl.usageCodes.writeCommand("cfc1", "$t0", "$25");
            CodeGeneratorImpl.usageCodes.writeCommand("andi", "$t0", "1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Comparison : BiggerThanEqual");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("sge", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types to compare (>=)");
    }

    public void add(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Addition");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("add.s", "$f0", "$f0", "$f1");
            CodeGeneratorImpl.usageCodes.writeCommand("s.s", "$f0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Addition");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("add", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types for addition");
    }

    public void subtract(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Subtraction");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("sub.s", "$f0", "$f1", "$f0");
            CodeGeneratorImpl.usageCodes.writeCommand("s.s", "$f0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Subtraction");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("sub", "$t0", "$t1", "$t0");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types for Subtraction");
    }

    public void multiply(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Multiplication");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("mul.s", "$f0", "$f1", "$f0");
            CodeGeneratorImpl.usageCodes.writeCommand("s.s", "$f0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Multiplication");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("mult", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("mflo", "$t0");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types for Multiplication");
    }

    public void divide(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Real Division");
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("l.s", "$f1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("div.s", "$f0", "$f1", "$f0");
            CodeGeneratorImpl.usageCodes.writeCommand("s.s", "$f0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Integer Division");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("div", "$t1", "$t0");
            CodeGeneratorImpl.usageCodes.writeCommand("mflo", "$t0"); //q
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "invalid types for Subtraction");
    }

    public void and(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.BOOLEAN_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "And");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("and", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "Not valid type for bitwise operation");
    }

    public void or(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        if (firstOperand.type == PrimitiveType.BOOLEAN_PRIMITIVE) {
            PrimitiveDescriptor pd = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", firstOperand.type);
            String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd);
            pd.setAddress(allocatedAddress);
            CodeGeneratorImpl.usageCodes.writeComment(false, "Or");
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", firstOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t1", secondOperand.getAddress());
            CodeGeneratorImpl.usageCodes.writeCommand("or", "$t0", "$t0", "$t1");
            CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", allocatedAddress);

            CodeGeneratorImpl.semanticStack.push(pd);
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "Not valid type for bitwise operation");
    }


    public void not(PrimitiveDescriptor pd) {
        PrimitiveDescriptor negatedBoolean = new PrimitiveDescriptor(CodeGeneratorImpl.usageCodes.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
        String address = CodeGeneratorImpl.usageCodes.allocateMemory(negatedBoolean);
        negatedBoolean.setAddress(address);
        CodeGeneratorImpl.usageCodes.writeComment(false, "Not operator");
        CodeGeneratorImpl.usageCodes.writeCommand("lw", "$t0", pd.getAddress());
        CodeGeneratorImpl.usageCodes.writeCommand("not", "$t0", "$t0");
        CodeGeneratorImpl.usageCodes.writeCommand("add", "$t0", "$t0", "2");
        CodeGeneratorImpl.usageCodes.writeCommand("sw", "$t0", address);
        CodeGeneratorImpl.semanticStack.push(negatedBoolean);
    }

}
