import dscps.*;

import java.util.*;

public class CodeGeneratorImpl implements CodeGenerator {
    public static RFOSK scanner;
    public static Stack<Descriptor> semanticStack;
    public static UsageCodes usageCodes;
    public static OtherDescriptors.MethodDescriptor currentMethod;
    public static Map<String, Descriptor> GlobalDSCP;
    public static final ArrayList<TokenType> constantTypes = new ArrayList<>(Arrays.asList(TokenType.REAL, TokenType.INTEGER, TokenType.STRING));

    private final Generator_arrays arrays = new Generator_arrays();
    private final Generator_push pg = new Generator_push();
    private final Generator_ReadAndPrint rap = new Generator_ReadAndPrint();
    private final Generator_declare dg = new Generator_declare();
    private final Generator_Assignment ga = new Generator_Assignment();
    private final Generator_Arithmetic gam = new Generator_Arithmetic();
    private final Generator_IF_ELSE gie = new Generator_IF_ELSE();
    private  final Generator_ForLoop gfl = new Generator_ForLoop();
    private final Generator_WhileLoop gwl = new Generator_WhileLoop();

    private void InitializeGlobalDSCP() {
        PrimitiveDescriptor real = new PrimitiveDescriptor("real", "", PrimitiveType.REAL_PRIMITIVE);
        PrimitiveDescriptor integer = new PrimitiveDescriptor("int", "", PrimitiveType.INTEGER_PRIMITIVE);
        PrimitiveDescriptor bool = new PrimitiveDescriptor("bool", "", PrimitiveType.BOOLEAN_PRIMITIVE);
        PrimitiveDescriptor string = new PrimitiveDescriptor("string", "", PrimitiveType.STRING_PRIMITIVE);


        GlobalDSCP.put("real", real);
        GlobalDSCP.put("int", integer);
        GlobalDSCP.put("bool", bool);
        GlobalDSCP.put("string", string);
        GlobalDSCP.put("void", null);
    }

    public CodeGeneratorImpl(RFOSK scanner) {
        this.scanner = scanner;
        semanticStack = new Stack<>();
        usageCodes = new UsageCodes(this);
        GlobalDSCP = new TreeMap<>(String::compareTo);
        InitializeGlobalDSCP();
    }

    @Override
    public void doSemantic(String sem) {
        switch (sem) {
            case "push":
                pg.push();
                break;
            case "trace_object":
                break;
            case "pop":
                semanticStack.pop();
                break;
            case "var_dcl":
                dg.declareVariable();
                break;
            case "array_dcl":
                arrays.declareArray();
                break;
            case "array_dcl_complete":
                arrayDclComplete();
                break;
            case "assign":
                ga.assign();
                break;
            case "divide_assign":
                ga.operateAndAssign("divide");
                break;
            case "multiply_assign":
                ga.operateAndAssign("multiply");
                break;
            case "add_assign":
                ga.operateAndAssign("add");
                break;
            case "subtract_assign":
                ga.operateAndAssign("subtract");
                break;
            case "len":
                len();
                break;
            case "start_be":
                gwl.start_be_while();
                break;
            case "while":
                gwl.while_dcl();
                break;
            case "complete_while":
                gwl.complete_while();
                break;
            case "start_be_for":
                gfl.start_be_for();
                break;
            case "for":
                gfl.for_dcl();
                break;
            case "for_assign_comp":
                gfl.forAssignComp();
                break;
            case "complete_for":
                gfl.complete_for();
                break;
            case "if":
                gie.if_dcl();
                break;
            case "complete_if":
                gie.complete_if();
                break;
            case "add":
            case "subtract":
            case "multiply":
            case "divide":
            case "bigger_than":
            case "bigger_than_equal":
            case "less_than":
            case "less_than_equal":
            case "equality":
            case "inequality":
            case "and":
            case "or":
                ga.twoOperandCheckAndCalculate(sem);
                break;
            case "minus_minus":
            case "plus_plus":
                unaryOperation(sem);
                break;
            case "read_string":
                rap.read_string();
                break;
            case "read_int":
                rap.read_int();
                break;
            case "print_expr":
                rap.print_expr();
                break;
            case "complete_else":
            case "end_if":
                gie.end_if();

        }
    }

    private void len() {
        Descriptor descriptor = semanticStack.pop();
        if (descriptor instanceof OtherDescriptors.ArrayDescriptor) {
            OtherDescriptors.ArrayDescriptor ad = (OtherDescriptors.ArrayDescriptor) descriptor;
            PrimitiveDescriptor val = new PrimitiveDescriptor(usageCodes.getTempName(),
                    "", PrimitiveType.INTEGER_PRIMITIVE);
            String allocatedMemoryAddress = usageCodes.allocateMemory(val, String.valueOf(ad.getSize()));
            val.setAddress(allocatedMemoryAddress);
            semanticStack.push(val);
        } else
            throw new Error(MakeError() + "len function for given type is not supported!");
    }

    private void arrayDclComplete() {
        Descriptor rightExpr = semanticStack.pop();
        Descriptor rightType = semanticStack.pop();
        Descriptor left = semanticStack.pop();

        if (!(left instanceof OtherDescriptors.ArrayDescriptor))
            throw new Error(MakeError() + "Left value is not an array type!");
        OtherDescriptors.ArrayDescriptor leftArray = (OtherDescriptors.ArrayDescriptor) left;

        if (!(rightType instanceof PrimitiveDescriptor))
            throw new Error(MakeError() + "invalid Element type for array");
        PrimitiveDescriptor pdRight = (PrimitiveDescriptor) rightExpr;
        if (pdRight.type != PrimitiveType.INTEGER_PRIMITIVE)
            throw new Error(MakeError() + "invalid type for array size");

        PrimitiveType leftArrayElemType = ((PrimitiveDescriptor) leftArray.elementType).type;
        PrimitiveType rightElemType = ((PrimitiveDescriptor) rightType).type;
        if (leftArrayElemType != rightElemType)
            throw new Error(MakeError() + "cant assign array with element type " + rightElemType + " to array with element type " + leftArrayElemType);


        PrimitiveDescriptor rightExprPd = (PrimitiveDescriptor) rightExpr;
        if (!rightExprPd.isConstant())
            throw new Error(MakeError() + "Dynamic array size not supported!");

        int size = Integer.parseInt(rightExpr.symName);
        if (size <= 0)
            throw new Error(MakeError() + "Size of an array should be a natural number!");

        leftArray.setSize(size);

        String allocatedMemoryStart = usageCodes.allocateMemory(leftArray);
        leftArray.setStartAddress(allocatedMemoryStart);
    }


    private void unaryOperation(String semantic) {
        Descriptor descriptor = semanticStack.pop();
        if (!(descriptor instanceof PrimitiveDescriptor))
            throw new Error(MakeError() + "Unary Operators only accept primitives.");
        PrimitiveDescriptor pd = (PrimitiveDescriptor) descriptor;
        switch (semantic) {
            case "not":
                if (pd.type != PrimitiveType.BOOLEAN_PRIMITIVE)
                    throw new Error(MakeError() + "not operator is only applicable on booleans!");
                gam.not(pd);
                break;

            case "minus_minus":
            case "plus_plus":
                if (pd.type != PrimitiveType.REAL_PRIMITIVE && pd.type != PrimitiveType.INTEGER_PRIMITIVE)
                    throw new Error(MakeError() + semantic + " is only applicable on REALs and INTEGERs!");
                if (pd.isConstant())
                    throw new Error(MakeError() + semantic + " is not applicable on constants");
                gam.minusMinusOrPlusPlus(pd, semantic);
                break;
            default:
                throw new Error(MakeError() + "can't Recognize the operation");

        }

    }

    public static String MakeError() {
        return
                "on line " + scanner.currentSymbol.line + " near " + scanner.currentSymbol.getToken() + " error:\n";
    }

    public StringBuilder generateCode() {
        return usageCodes.generatedCode.append('\n').append(usageCodes.dataCode);
    }
}
