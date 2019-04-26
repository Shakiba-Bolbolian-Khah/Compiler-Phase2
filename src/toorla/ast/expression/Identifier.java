package toorla.ast.expression;

import toorla.visitor.Visitor;

public class Identifier extends Expression {
    private String name;
    private int index;

    public Identifier(String name) {
        this.name = name;
        index = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        if( name != null && index > 0)
            return "(Identifier," + name + "_" + index + ")";
        else if(name != null && index == -1)
            return "(Identifier," + name + ")";
        else return "(Identifier,Dummy)";
    }

    public int getIndex() { return index;}

    public void setIndex(int index) { this.index = index; }
}
