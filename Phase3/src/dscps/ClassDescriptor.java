package dscps;

import java.util.Map;
import java.util.TreeMap;

public class ClassDescriptor extends Descriptor {
    public Map<String ,Field> fields;
    public ClassDescriptor(String symName){
        super(symName);
        this.fields = new TreeMap<>(String::compareTo);
    }
}

class Field {
    public Descriptor descriptor;
    public String name;
    public FieldType fieldType;
    public Field(Descriptor descriptor, FieldType fieldType, String name){
        this.descriptor = descriptor;
        this.fieldType = fieldType;
        this.name = name;
    }
}

enum FieldType {
    FIELD_METHOD,
    FIELD_NON_METHOD
}
