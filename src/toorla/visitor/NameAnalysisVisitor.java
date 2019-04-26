package toorla.visitor;

import toorla.ast.Program;
import toorla.ast.declaration.classDecs.ClassDeclaration;
import toorla.ast.declaration.classDecs.EntryClassDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.ClassMemberDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.FieldDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.MethodDeclaration;
import toorla.ast.declaration.localVarDecs.ParameterDeclaration;
import toorla.ast.expression.*;
import toorla.ast.expression.binaryExpression.*;
import toorla.ast.expression.unaryExpression.Neg;
import toorla.ast.expression.unaryExpression.Not;
import toorla.ast.expression.value.BoolValue;
import toorla.ast.expression.value.IntValue;
import toorla.ast.expression.value.StringValue;
import toorla.ast.statement.*;
import toorla.ast.statement.localVarStats.LocalVarDef;
import toorla.ast.statement.localVarStats.LocalVarsDefinitions;
import toorla.ast.statement.returnStatement.Return;
import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.exceptions.ItemAlreadyExistsException;
import toorla.symbolTable.exceptions.ItemNotFoundException;
import toorla.symbolTable.symbolTableItem.BlockItem.BlockSymbolTableItem;
import toorla.symbolTable.symbolTableItem.ClassItem.ClassSymbolTableItem;
import toorla.symbolTable.symbolTableItem.MethodItem.MethodSymbolTableItem;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.FieldSymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.LocalVariableSymbolTableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NameAnalysisVisitor implements Visitor {
    private int num;
    private int index;

    public void setClassParents(SymbolTable s){
        Map<String, SymbolTableItem> items = s.getItems();

        for (Map.Entry<String,SymbolTableItem> item : items.entrySet()){
            ClassSymbolTableItem cst = (ClassSymbolTableItem) item.getValue();
            String parName = cst.getParentName();
            if(parName != null){
                if(items.containsKey(parName)){
                    SymbolTable parentSymbolTable = ((ClassSymbolTableItem) items.get(parName)).getSymbolTable();
                    cst.setPre(parentSymbolTable);
                }
            }
        }
    }

    public NameAnalysisVisitor(){
        num = 0;
        index = 0;
    }

    @Override
    public Object visit(PrintLine printStat){
        return null;
    }

    @Override
    public Object visit(Assign assignStat){
        return null;
    }

    @Override
    public Object visit(Block block){
        BlockSymbolTableItem blockSymbol = new BlockSymbolTableItem(Integer.toString(num++) + "-block",SymbolTable.top);

        try {
            SymbolTable.top.put(blockSymbol);
        }
        catch (ItemAlreadyExistsException e){
        }

        SymbolTable.push(blockSymbol.getSymbolTable());

        List<Statement> body = block.body;

        for(int i = 0; i < body.size(); i++){
            body.get(i).accept(this);
        }

        blockSymbol.setSymbolTable(SymbolTable.top);
        SymbolTable.pop();

        return null;
    }

    @Override
    public Object visit(Conditional conditional){
        conditional.getCondition().accept(this);

        BlockSymbolTableItem thenBlock = new BlockSymbolTableItem(Integer.toString(num++) + "-block",SymbolTable.top);
        BlockSymbolTableItem elseBlock = new BlockSymbolTableItem(Integer.toString(num++) + "-block",SymbolTable.top);

        try {
            SymbolTable.top.put(thenBlock);
            SymbolTable.top.put(elseBlock);
        }
        catch (ItemAlreadyExistsException e){
        }

        SymbolTable.push(thenBlock.getSymbolTable());

        conditional.getThenStatement().accept(this);

        thenBlock.setSymbolTable(SymbolTable.top);
        SymbolTable.pop();

        SymbolTable.push(elseBlock.getSymbolTable());

        conditional.getElseStatement().accept(this);

        elseBlock.setSymbolTable(SymbolTable.top);
        SymbolTable.pop();

        return null;
    }

    @Override
    public Object visit(While whileStat){
        whileStat.expr.accept(this);

        BlockSymbolTableItem whileBlock = new BlockSymbolTableItem(Integer.toString(num++) + "-block",SymbolTable.top);

        try {
            SymbolTable.top.put(whileBlock);
        }
        catch (ItemAlreadyExistsException e){
        }

        SymbolTable.push(whileBlock.getSymbolTable());

        whileStat.body.accept(this);

        whileBlock.setSymbolTable(SymbolTable.top);
        SymbolTable.pop();

        return null;
    }

    @Override
    public Object visit(Return returnStat){ return null; }

    @Override
    public Object visit(Break breakStat) { return null; }

    @Override
    public Object visit(Continue continueStat) { return null; }

    @Override
    public Object visit(Skip skip) { return null; }

    @Override
    public Object visit(LocalVarDef localVarDef){
        String varName = localVarDef.getLocalVarName().getName() + "localVar";
        localVarDef.getLocalVarName().setIndex(index);
        LocalVariableSymbolTableItem varItem = new LocalVariableSymbolTableItem(varName ,index++);

        try {
            SymbolTable.top.put(varItem);
        }
        catch (ItemAlreadyExistsException e1){
            varItem.setName(localVarDef.getLocalVarName().getName() + "$" + Integer.toString(varItem.getIndex()) + "-localVar");
            localVarDef.setLocalVarName(localVarDef.getLocalVarName().getName() + "$" + Integer.toString(varItem.getIndex()));

            try {
                SymbolTable.top.put(varItem);
            }
            catch (ItemAlreadyExistsException e2){
            }

        }

        localVarDef.getInitialValue().accept(this);

        return null;
    }

    @Override
    public Object visit(IncStatement incStatement){
        return null;
    }

    @Override
    public Object visit(DecStatement decStatement) {
        return null;
    }

    @Override
    public Object visit(Plus plusExpr) {
        return null;
    }

    @Override
    public Object visit(Minus minusExpr) {
        return null;
    }

    @Override
    public Object visit(Times timesExpr) {
        return null;
    }

    @Override
    public Object visit(Division divExpr) {
        return null;
    }

    @Override
    public Object visit(Modulo moduloExpr) {
        return null;
    }

    @Override
    public Object visit(Equals equalsExpr) {
        return null;
    }

    @Override
    public Object visit(GreaterThan gtExpr) {
        return null;
    }

    @Override
    public Object visit(LessThan lessThanExpr) {
        return null;
    }

    @Override
    public Object visit(And andExpr) {
        return null;
    }

    @Override
    public Object visit(Or orExpr) {
        return null;
    }

    @Override
    public Object visit(Neg negExpr) {
        return null;
    }

    @Override
    public Object visit(Not notExpr) {
        return null;
    }

    @Override
    public Object visit(MethodCall methodCall) {
        return null;
    }

    @Override
    public Object visit(Identifier identifier) {
        return null;
    }

    @Override
    public Object visit(Self self) {
        return null;
    }

    @Override
    public Object visit(IntValue intValue) {
        return null;
    }

    @Override
    public Object visit(NewArray newArray) {
        return null;
    }

    @Override
    public Object visit(BoolValue booleanValue) {
        return null;
    }

    @Override
    public Object visit(StringValue stringValue) {
        return null;
    }

    @Override
    public Object visit(NewClassInstance newClassInstance) {
        return null;
    }

    @Override
    public Object visit(FieldCall fieldCall) {
        return null;
    }

    @Override
    public Object visit(ArrayCall arrayCall) {
        return null;
    }

    @Override
    public Object visit(NotEquals notEquals) {
        return null;
    }

    @Override
    public Object visit(ClassDeclaration classDeclaration) {
        String name = classDeclaration.getName().getName() + "-classItem";
        String parName = classDeclaration.getParentName().getName() + "-classItem";
        boolean isEntry = false;

        ClassSymbolTableItem classItem = new ClassSymbolTableItem(name, parName, isEntry, SymbolTable.top);

        try{
            SymbolTable.top.put(classItem);
        }
        catch (ItemAlreadyExistsException e1){
            name = classDeclaration.getName().getName() + "$" + Integer.toString(num) + "-classItem" ;
            classItem.setName(name);

            classDeclaration.setName(classDeclaration.getName().getName() + "$" + Integer.toString(num++));

            try{
                SymbolTable.top.put(classItem);
            }
            catch (ItemAlreadyExistsException e2){
            }
        }

        SymbolTable.push(classItem.getSymbolTable());

        ArrayList<ClassMemberDeclaration> members = classDeclaration.getClassMembers();

        for( int i = 0 ; i < members.size() ; i++){
            members.get(i).accept(this);
        }

        classItem.setSymbolTable(SymbolTable.top);

        SymbolTable.pop();

        return null;
    }

    @Override
    public Object visit(EntryClassDeclaration entryClassDeclaration) {

        String name = entryClassDeclaration.getName().getName() + "-classItem";
        String parName = entryClassDeclaration.getParentName().getName() + "-classItem";
        boolean isEntry = true;

        ClassSymbolTableItem classItem = new ClassSymbolTableItem(name, parName, isEntry, SymbolTable.top);

        try{
            SymbolTable.top.put(classItem);
        }
        catch (ItemAlreadyExistsException e1){
            name = entryClassDeclaration.getName().getName() + "$" + Integer.toString(num) + "-classItem" ;
            classItem.setName(name);

            entryClassDeclaration.setName(entryClassDeclaration.getName().getName() + "$" + Integer.toString(num++));

            try{
                SymbolTable.top.put(classItem);
            }
            catch (ItemAlreadyExistsException e2){
            }
        }

        SymbolTable.push(classItem.getSymbolTable());

        ArrayList<ClassMemberDeclaration> members = entryClassDeclaration.getClassMembers();

        for( int i = 0 ; i < members.size() ; i++){
            members.get(i).accept(this);
        }

        classItem.setSymbolTable(SymbolTable.top);

        SymbolTable.pop();

        return null;
    }

    @Override
    public Object visit(FieldDeclaration fieldDeclaration) {
        String name = fieldDeclaration.getIdentifier().getName() + "-fieldItem";
        FieldSymbolTableItem fieldSymbolTableItem = new FieldSymbolTableItem(name, fieldDeclaration.getAccessModifier());

        try {
            SymbolTable.top.put(fieldSymbolTableItem);
        } catch (ItemAlreadyExistsException e1) {
            name = fieldDeclaration.getIdentifier().getName() + "$" + Integer.toString(num) + "-fieldItem";
            fieldSymbolTableItem.setName(name);

            fieldDeclaration.setName(fieldDeclaration.getIdentifier().getName() + "$" + Integer.toString(num++));

            try {
                SymbolTable.top.put(fieldSymbolTableItem);
            } catch (ItemAlreadyExistsException e2) {
            }
        }

        return null;
    }

    @Override
    public Object visit(ParameterDeclaration parameterDeclaration) {
        String name = parameterDeclaration.getIdentifier().getName() + "-localVar";
        parameterDeclaration.getIdentifier().setIndex(index);
        LocalVariableSymbolTableItem localVariableSymbolTableItem = new LocalVariableSymbolTableItem(name, index++);
        localVariableSymbolTableItem.setVarType(parameterDeclaration.getType());
        try{
            SymbolTable.top.put(localVariableSymbolTableItem);
        }
        catch (ItemAlreadyExistsException e1){
            name = parameterDeclaration.getIdentifier().getName() + "$" + Integer.toString(index-1) + "-localVar";
            localVariableSymbolTableItem.setName(name);

            parameterDeclaration.setName(parameterDeclaration.getIdentifier().getName() + "$" + Integer.toString(index-1));

            try{
                SymbolTable.top.put(localVariableSymbolTableItem);
            }
            catch (ItemAlreadyExistsException e2){
            }
        }
        return null;
    }

    @Override
    public Object visit(MethodDeclaration methodDeclaration) {
        String name = methodDeclaration.getName().getName() + "-methodItem";
        MethodSymbolTableItem methodSymbolTableItem = new MethodSymbolTableItem(name, methodDeclaration.getAccessModifier(), methodDeclaration.getReturnType(), SymbolTable.top);
        try{
            SymbolTable.top.put(methodSymbolTableItem);
        }
        catch (ItemAlreadyExistsException e1){
            name = methodDeclaration.getName().getName() + "$" + Integer.toString(num)+"-methodItem";
            methodSymbolTableItem.setName(name);

            methodDeclaration.setName(methodDeclaration.getName().getName() + "$" + Integer.toString(num++));
            try{
                SymbolTable.top.put(methodSymbolTableItem);
            }
            catch (ItemAlreadyExistsException e2) {
            }
        }
        SymbolTable.push(methodSymbolTableItem.getSymboleTable());
        ArrayList<ParameterDeclaration> args = methodDeclaration.getArgs();

        index = 1;

        for( int i = 0 ; i < args.size() ; i++){
            args.get(i).accept(this);
        }

        methodSymbolTableItem.setLastArgIndex(index);

        ArrayList<Statement> body = methodDeclaration.getBody();

        for(int i = 0; i < body.size(); i++){
            body.get(i).accept(this);
        }

        methodSymbolTableItem.setSymboleTable(SymbolTable.top);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Object visit(LocalVarsDefinitions localVarsDefinitions) {
        List<LocalVarDef> varDefinitions = localVarsDefinitions.getVarDefinitions();

        for(int i = 0; i < varDefinitions.size(); i++){
            varDefinitions.get(i).accept(this);
        }

        return null;
    }

    @Override
    public Object visit(Program program) {
        SymbolTable symbolTable = new SymbolTable();
        SymbolTable.push(symbolTable);

        ClassSymbolTableItem anyClass = new ClassSymbolTableItem("Any-classItem","",false,SymbolTable.top);
        try {
            SymbolTable.top.put(anyClass);
        }
        catch (Exception ignored){
        }

        SymbolTable.push(anyClass.getSymbolTable());

        List<ClassDeclaration> classes = program.getClasses();

        for(int i = 0; i < classes.size(); i++){
            classes.get(i).accept(this);
        }

        anyClass.setSymbolTable(SymbolTable.top);
        setClassParents(SymbolTable.top);

        SymbolTable.pop();

        SymbolTable.root = SymbolTable.top;
        SymbolTable.pop();

        return null;
    }
}
