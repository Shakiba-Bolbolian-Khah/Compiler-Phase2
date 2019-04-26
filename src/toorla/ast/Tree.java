package toorla.ast;

import toorla.symbolTable.exceptions.ItemAlreadyExistsException;
import toorla.visitor.Visitor;

public abstract class Tree {
	public int line;
	public int col;
	public abstract <R> R accept(Visitor<R> visitor);
	public abstract String toString();
}