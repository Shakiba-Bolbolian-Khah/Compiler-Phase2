package toorla.symbolTable.symbolTableItem.MethodItem;

import toorla.ast.declaration.classDecs.classMembersDecs.AccessModifier;
import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.LocalVariableSymbolTableItem;
import toorla.types.Type;

import java.util.ArrayList;

public class MethodSymbolTableItem extends SymbolTableItem {

    private Type returnType;
    private AccessModifier accessModifier;
    private int lastArgIndex;
    private SymbolTable methodSymboleTable;

    public MethodSymbolTableItem(String name,AccessModifier accessModifier,Type returnType,SymbolTable pre){
        this.name = name;
        this.accessModifier = accessModifier;
        this.returnType = returnType;
        this.methodSymboleTable = new SymbolTable(pre);
    }

    @Override
    public String getKey() { return name;}

    public Type getReturnType() {
        return returnType;
    }

    public  AccessModifier getAccessModifier() { return  accessModifier; }

    public SymbolTable getSymboleTable() { return methodSymboleTable; }

    public int getLastArgIndex() { return lastArgIndex; }

    public void setLastArgIndex(int lastArgIndex) { this.lastArgIndex = lastArgIndex; }

    public void setSymboleTable(SymbolTable methodSymboleTable) { this.methodSymboleTable = methodSymboleTable; }
}
