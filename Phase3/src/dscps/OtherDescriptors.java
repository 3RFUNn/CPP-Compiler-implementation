package dscps;

import java.util.Map;
import java.util.TreeMap;

public class OtherDescriptors {
    public static class ArrayDescriptor extends Descriptor {
        public Descriptor elementType;
        private String startAddress;
        private int size;

        public ArrayDescriptor(String symName, Descriptor elementType) {
            super(symName);
            if (!(elementType instanceof PrimitiveDescriptor))
                throw new Error("Creating Array of non-primitives are not supported yet");
            startAddress = "";
            size = -1;
            this.elementType = elementType;
        }

        public String getStartAddress() {
            return startAddress;
        }

        public void setStartAddress(String startAddress) {
            this.startAddress = startAddress;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Descriptor getElementType() {
            return elementType;
        }

        public void setElementType(Descriptor elementType) {
            this.elementType = elementType;
        }
    }

    public static class MethodDescriptor extends Descriptor {
        public Map<String, Descriptor> symTable;
        public ClassDescriptor parentClass;

        public MethodDescriptor(String symName, ClassDescriptor parent) {
            super(symName);
            this.symTable = new TreeMap<>(String::compareTo);
            this.parentClass = parent;
        }

        public void addVariableAndUpdateSymbolTable(String name, Descriptor descriptor) {
            this.symTable.put(name, descriptor);
        }
    }

    public static class ObjectDescriptor extends Descriptor {
        public Map<String, Descriptor> attributes;
        public ClassDescriptor relatedClass;

        public ObjectDescriptor(String symName, ClassDescriptor relatedClass) {
            super(symName);
            this.attributes = new TreeMap<>(String::compareTo);
            this.relatedClass = relatedClass;
        }
    }

    public static class PrimitiveDescriptor extends Descriptor {
        public PrimitiveType type;
        public String address;
        private boolean isConstant;

        public PrimitiveDescriptor(String symName, String address, PrimitiveType type) {
            super(symName);
            this.type = type;
            this.address = address;
            isConstant = false;
        }


    }
}
