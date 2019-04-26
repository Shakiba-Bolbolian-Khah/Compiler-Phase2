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
import toorla.symbolTable.exceptions.ItemNotFoundException;
import toorla.symbolTable.symbolTableItem.ClassItem.ClassSymbolTableItem;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorHandler implements Visitor {

    private  boolean hasError;

    public boolean hasError() {return hasError; }

    @Override
    public Object visit(PrintLine printStat) {
        return null;
    }

    @Override
    public Object visit(Assign assignStat) {
        return null;
    }

    @Override
    public Object visit(Block block) {

        List<Statement> body = block.body;

        for(int i = 0; i < body.size(); i++){
            body.get(i).accept(this);
        }

        return null;
    }

    @Override
    public Object visit(Conditional conditional) {

        conditional.getCondition().accept(this);
        conditional.getThenStatement().accept(this);
        conditional.getElseStatement().accept(this);

        return null;
    }

    @Override
    public Object visit(While whileStat) {

        whileStat.body.accept(this);

        return null;
    }

    @Override
    public Object visit(Return returnStat) {
        return null;
    }

    @Override
    public Object visit(Break breakStat) {
        return null;
    }

    @Override
    public Object visit(Continue continueStat) {
        return null;
    }

    @Override
    public Object visit(Skip skip) {
        return null;
    }

    @Override
    public Object visit(LocalVarDef localVarDef) {

        String name = localVarDef.getLocalVarName().getName();

        if(name.contains("$")){
            int dollarIndex = name.indexOf("$");
            name = name.substring(0,dollarIndex);//TODO:check
            System.out.println("Error:Line:" + localVarDef.line + ":Redefinition of Local Variable " + name +  " in current scope");

            hasError = true;
        }

        return null;
    }

    @Override
    public Object visit(IncStatement incStatement) {
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

        String name = classDeclaration.getName().getName();

        if(name.equals("Any")){
            System.out.println("Error:Line:" + classDeclaration.getName().line + ":Redefinition of Class " + name);
            hasError = true;
        }
        else if(name.contains("$")){
            int dollarIndex = name.indexOf("$");
            name = name.substring(0,dollarIndex);//TODO:check

            System.out.println("Error:Line:" + classDeclaration.line + ":Redefinition of Class " + name);
            hasError = true;

        }

        ArrayList<ClassMemberDeclaration> members = classDeclaration.getClassMembers();

        for(int i = 0; i < members.size(); i++){
            members.get(i).accept(this);
        }


        return null;
    }

    @Override
    public Object visit(EntryClassDeclaration entryClassDeclaration) {

        String name = entryClassDeclaration.getName().getName();

        if(name.contains("$")){
            int dollarIndex = name.indexOf("$");
            name = name.substring(0,dollarIndex);//TODO:check

            System.out.println("Error:Line:" + entryClassDeclaration.getName().line + ":Redefinition of Class " + name);
            hasError = true;

        }

        ArrayList<ClassMemberDeclaration> members = entryClassDeclaration.getClassMembers();

        for(int i = 0; i < members.size(); i++){
            members.get(i).accept(this);
        }
        return null;
    }

    @Override
    public Object visit(FieldDeclaration fieldDeclaration) {

        String name = fieldDeclaration.getIdentifier().getName();

        boolean reDifined = true;

        if(name.startsWith("length$") || name.equals("length")) {
            System.out.println("Error:Line:" + fieldDeclaration.line + ":Definition of length as field of a class");
            hasError = true;
        }
        else if(name.contains("$")) {
            int dollarIndex = name.indexOf("$");
            name = name.substring(0,dollarIndex);//TODO:check

            System.out.println("Error:Line:" + fieldDeclaration.line + ":Redefinition of Field " + name);

            hasError = true;
        }
        else{
            try{
                SymbolTable.top.getPreSymbolTable().get(name+"-fieldItem");
            }
            catch (ItemNotFoundException e){
                reDifined = false;
            }

            if(reDifined) {
                System.out.println("Error:Line:" + fieldDeclaration.line + ":Redefinition of Field " + fieldDeclaration.getIdentifier().getName());
                hasError = true;
            }

        }


        return null;
    }

    @Override
    public Object visit(ParameterDeclaration parameterDeclaration) {

        String name = parameterDeclaration.getIdentifier().getName();

        if(name.contains("$"))
        {
            int dollarIndex = name.indexOf("$");
            name = name.substring(0,dollarIndex);//TODO:check
            System.out.println("Error:Line:" + parameterDeclaration.line + ":Redefinition of Local Variable" + name + "in current scope");
            hasError = true;
        }

        return null;
    }

    @Override
    public Object visit(MethodDeclaration methodDeclaration) {

        String name = methodDeclaration.getName().getName();
        boolean reDifined = true;

        //System.out.println("name:"+name);

        if(name.contains("$")) {
            int dollarIndex = name.indexOf("$");
            name = name.substring(0,dollarIndex);//TODO:check
            System.out.println("Error:Line:" + methodDeclaration.getName().line +":Redefinition of Method " + name);

            hasError = true;
        }
        else {
            try{
                SymbolTable.top.getPreSymbolTable().get(name+"-methodItem");
            }
            catch (ItemNotFoundException e){
                reDifined = false;
            }

            if(reDifined) {
                System.out.println("Error:Line:" + methodDeclaration.getName().line + ":Redefinition of Method " + name);
                hasError = true;
            }
        }

        ArrayList<ParameterDeclaration> args = methodDeclaration.getArgs();

        for(int i = 0; i < args.size(); i++)
            args.get(i).accept(this);

        ArrayList<Statement> body = methodDeclaration.getBody();

        for(int i = 0; i < body.size(); i++)
            body.get(i).accept(this);

        return null;
    }

    @Override
    public Object visit(LocalVarsDefinitions localVarsDefinitions) {

        List<LocalVarDef> localVarDefs = localVarsDefinitions.getVarDefinitions();

        for(int i = 0; i < localVarDefs.size(); i++)
            localVarDefs.get(i).accept(this);

        return null;
    }

    @Override
    public Object visit(Program program) {

        List<ClassDeclaration> classes = program.getClasses();

        Map<String, SymbolTableItem> programItems = SymbolTable.root.getItems();
        ClassSymbolTableItem anyClassItem = (ClassSymbolTableItem) programItems.get("Any-classItem");
        SymbolTable.push(anyClassItem.getSymbolTable());

        Map<String,SymbolTableItem> anyItems = SymbolTable.top.getItems();

        for(int i = 0; i < classes.size(); i++){
            ClassSymbolTableItem classItem = (ClassSymbolTableItem) anyItems.get(classes.get(i).getName().getName() + "-classItem");
            SymbolTable.push(classItem.getSymbolTable());
            classes.get(i).accept(this);
            SymbolTable.pop();
        }

        return null;
    }
}
