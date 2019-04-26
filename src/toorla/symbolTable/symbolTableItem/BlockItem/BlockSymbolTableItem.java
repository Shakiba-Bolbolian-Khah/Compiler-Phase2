package toorla.symbolTable.symbolTableItem.BlockItem;

import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;

public class BlockSymbolTableItem extends SymbolTableItem {

    private  SymbolTable blockSymbolTable;

    public BlockSymbolTableItem(String name,SymbolTable pre){
        this.name = name;
        this.blockSymbolTable = new SymbolTable(pre);
    }

    @Override
    public String getKey() { return name; }

    public SymbolTable getSymbolTable() { return blockSymbolTable; }

    public void setSymbolTable(SymbolTable blockSymbolTable) { this.blockSymbolTable = blockSymbolTable; }
}
