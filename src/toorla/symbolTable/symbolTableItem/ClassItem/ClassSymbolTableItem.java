package toorla.symbolTable.symbolTableItem.ClassItem;

import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;
import toorla.types.Type;

public class ClassSymbolTableItem extends SymbolTableItem {

    private String parentName;
    private boolean isEntry;
    private SymbolTable classSymbolTable;

    public ClassSymbolTableItem(String name,String parentName,boolean isEntry,SymbolTable pre){
        this.name = name;
        this.parentName = parentName;
        this.isEntry = isEntry;
        this.classSymbolTable = new SymbolTable(pre);
    }

    @Override
    public String getKey() { return name; }

    public String getParentName() { return parentName;}

    public boolean IsEntry(){ return  isEntry;}

    public SymbolTable getSymbolTable() { return classSymbolTable; }

    public void setSymbolTable(SymbolTable classSymbolTable) {
        this.classSymbolTable = classSymbolTable;
    }

    public void setPre(SymbolTable pre){ this.classSymbolTable.setPre(pre);}
}
