import dscps.Descriptor;
import dscps.OtherDescriptors;
import dscps.PrimitiveDescriptor;
import dscps.PrimitiveType;

public class Generator_Assignment {
    private final Generator_Arithmetic ga = new Generator_Arithmetic();

    public void assign() {
        Descriptor right = CodeGeneratorImpl.semanticStack.pop();
        Descriptor left = CodeGeneratorImpl.semanticStack.pop();
        if (!left.getClass().equals(right.getClass())) {
            throw new Error(CodeGeneratorImpl.MakeError() + "Assigning inconsistent types.");
        }

        if (left instanceof OtherDescriptors.ArrayDescriptor) {
            OtherDescriptors.ArrayDescriptor leftArray = (OtherDescriptors.ArrayDescriptor) left;
            OtherDescriptors.ArrayDescriptor rightArray = (OtherDescriptors.ArrayDescriptor) right;
            PrimitiveType leftArrayElemType = ((PrimitiveDescriptor) leftArray.elementType).type;
            PrimitiveType rightArrayElemType = ((PrimitiveDescriptor) rightArray.elementType).type;
            if (leftArrayElemType != rightArrayElemType)
                throw new Error(CodeGeneratorImpl.MakeError() + "Cant assign array with " + rightArrayElemType + " element type to array with " + leftArrayElemType + " element type");
            leftArray.setSize(rightArray.getSize());
            leftArray.setStartAddress(rightArray.getStartAddress());
        } else if (left instanceof PrimitiveDescriptor) {
            PrimitiveDescriptor leftPrimitive = (PrimitiveDescriptor) left;
            PrimitiveDescriptor rightPrimitive = (PrimitiveDescriptor) right;
            if (leftPrimitive.type != rightPrimitive.type)
                throw new Error(CodeGeneratorImpl.MakeError() + "assigning " + rightPrimitive.type + " to " + leftPrimitive.type + " is illegal");
            if (leftPrimitive.type == PrimitiveType.STRING_PRIMITIVE) {
                if (leftPrimitive.isConstant())
                    throw new Error(CodeGeneratorImpl.MakeError() + "Assignment to a constant!");
                else {
                    leftPrimitive.setAddress(rightPrimitive.getAddress());
                }
            } else {
                CodeGeneratorImpl.usageCodes.assignAddressLabelsValues(leftPrimitive.getAddress(), rightPrimitive.getAddress(), leftPrimitive.type);
            }
        } else
            throw new Error(CodeGeneratorImpl.MakeError() + "Invalid Types for assignment!");
    }

    public void operateAndAssign(String semantic) {
        Descriptor right = CodeGeneratorImpl.semanticStack.pop();
        Descriptor left = CodeGeneratorImpl.semanticStack.pop();
        CodeGeneratorImpl.semanticStack.push(left);
        CodeGeneratorImpl.semanticStack.push(left);
        CodeGeneratorImpl.semanticStack.push(right);
        twoOperandCheckAndCalculate(semantic);
        assign();
    }

    public void twoOperandCheckAndCalculate(String semantic) {
        PrimitiveDescriptor firstOperand = (PrimitiveDescriptor) CodeGeneratorImpl.semanticStack.pop();
        PrimitiveDescriptor secondOperand = (PrimitiveDescriptor) CodeGeneratorImpl.semanticStack.pop();
        if (firstOperand.type != secondOperand.type) {
            throw new Error(CodeGeneratorImpl.MakeError() + "Inconsistent types for " + semantic + " operation");
        }
        if (firstOperand.isConstant() && secondOperand.isConstant()) {
            twoConstantOperandCheckAndCalculate(semantic, firstOperand, secondOperand);
            return;
        }

        switch (semantic) {
            case "add" -> ga.add(firstOperand, secondOperand);
            case "subtract" -> ga.subtract(firstOperand, secondOperand);
            case "multiply" -> ga.multiply(firstOperand, secondOperand);
            case "divide" -> ga.divide(firstOperand, secondOperand);
            case "bigger_than" -> ga.biggerThan(firstOperand, secondOperand);
            case "bigger_than_equal" -> ga.biggerThanEqual(firstOperand, secondOperand);
            case "less_than" -> ga.lessThan(firstOperand, secondOperand);
            case "less_than_equal" -> ga.lessThanEqual(firstOperand, secondOperand);
            case "equality" -> ga.equality(firstOperand, secondOperand);
            case "inequality" -> ga.inequality(firstOperand, secondOperand);
            case "and" -> ga.and(firstOperand, secondOperand);
            case "or" -> ga.or(firstOperand, secondOperand);
        }
    }

    private void twoConstantOperandCheckAndCalculate(String semantic, PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand) {
        PrimitiveType type = firstOperand.type;
        String resValue;
        PrimitiveType resType;
        switch (semantic) {
            case "add":
                if (type == PrimitiveType.INTEGER_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) + Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) + Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.REAL_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "Addition is not applicable on given operand types.");
                break;
            case "subtract":
                if (type == PrimitiveType.INTEGER_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(secondOperand.symName) - Integer.parseInt(firstOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(secondOperand.symName) - Float.parseFloat(firstOperand.symName)
                    );
                    resType = PrimitiveType.REAL_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "Subtraction is not applicable on given operand types.");
                break;
            case "multiply":
                if (type == PrimitiveType.INTEGER_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) * Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) * Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.REAL_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "Multiplication is not applicable on given operand types.");
                break;
            case "divide":
                if (secondOperand.symName.charAt(0) == '0')
                    throw new Error(CodeGeneratorImpl.MakeError() + "Can't divide by zero");
                if (type == PrimitiveType.INTEGER_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(secondOperand.symName) / Integer.parseInt(firstOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(secondOperand.symName) / Float.parseFloat(firstOperand.symName)
                    );
                    resType = PrimitiveType.REAL_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "Division is not applicable on given operand types.");
                break;
            case "bigger_than":
                if (type == PrimitiveType.INTEGER_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(secondOperand.symName) > Integer.parseInt(firstOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(secondOperand.symName) > Float.parseFloat(firstOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "biggerThan operator is not applicable on given operand types.");
                break;
            case "bigger_than_equal":
                if (type == PrimitiveType.INTEGER_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(secondOperand.symName) >= Integer.parseInt(firstOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(secondOperand.symName) >= Float.parseFloat(firstOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "biggerThanEqual operator is not applicable on given operand types.");
                break;
            case "less_than":
                if (type == PrimitiveType.INTEGER_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(secondOperand.symName) < Integer.parseInt(firstOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(secondOperand.symName) < Float.parseFloat(firstOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "lessThan operator is not applicable on given operand types.");
                break;
            case "less_than_equal":
                if (type == PrimitiveType.INTEGER_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(secondOperand.symName) <= Integer.parseInt(firstOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) <= Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "lessThanEqual operator is not applicable on given operand types.");
                break;
            case "equality":
                if (type == PrimitiveType.INTEGER_PRIMITIVE || type == PrimitiveType.BOOLEAN_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) == Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) == Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "Equality operator is not applicable on given operand types.");
                break;
            case "inequality":
                if (type == PrimitiveType.INTEGER_PRIMITIVE || type == PrimitiveType.BOOLEAN_PRIMITIVE) {
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) != Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else if (type == PrimitiveType.REAL_PRIMITIVE) {
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) != Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                } else
                    throw new Error(CodeGeneratorImpl.MakeError() + "Inequality operator is not applicable on given operand types.");
                break;
            case "and":
            case "or":
                throw new Error(CodeGeneratorImpl.MakeError() + "Invalid boolean operation!");
            default:
                throw new Error(CodeGeneratorImpl.MakeError() + "Operation is not supported");
        }

        if (CodeGeneratorImpl.GlobalDSCP.containsKey(resValue)) {
            CodeGeneratorImpl.semanticStack.push(CodeGeneratorImpl.GlobalDSCP.get(resValue));
            return;
        }
        PrimitiveDescriptor pd = new PrimitiveDescriptor(resValue, "", resType);
        String allocatedAddress = CodeGeneratorImpl.usageCodes.allocateMemory(pd, resValue);
        pd.setAddress(allocatedAddress);
        pd.activeIsConstant();
        CodeGeneratorImpl.semanticStack.push(pd);
        CodeGeneratorImpl.GlobalDSCP.put(resValue, pd);
    }
}
