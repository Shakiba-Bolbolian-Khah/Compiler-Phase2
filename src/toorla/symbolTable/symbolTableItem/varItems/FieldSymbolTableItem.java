package toorla.symbolTable.symbolTableItem.varItems;

import toorla.ast.declaration.classDecs.classMembersDecs.AccessModifier;
import toorla.types.AnonymousType;
import toorla.types.Type;

public class FieldSymbolTableItem  extends  VarSymbolTableItem{

    private Type varType;
    private AccessModifier accessModifier;

    public FieldSymbolTableItem(String name,AccessModifier accessModifier){
        this.name=name;
        this.varType = new AnonymousType();
        this.accessModifier = accessModifier;
    };

    public String getKey() {
        return name;
    }

    public Type getVarType() {
        return varType;
    }

    public  AccessModifier getAccessModifier() { return  accessModifier; }

    public void setVarType(Type varType) {
        this.varType = varType;
    }

}
