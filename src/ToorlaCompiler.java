import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import toorla.ast.Program;
import toorla.visitor.ErrorHandler;
import toorla.visitor.NameAnalysisVisitor;
import toorla.visitor.TreePrinter;
import toorla.visitor.Visitor;

public class ToorlaCompiler {
    public void compile(CharStream textStream) {
        ToorlaLexer toorlaLexer = new ToorlaLexer( textStream );
        CommonTokenStream tokenStream = new CommonTokenStream( toorlaLexer );
        ToorlaParser toorlaParser = new ToorlaParser( tokenStream );
        Program toorlaASTCode = toorlaParser.program().mProgram;
        Visitor<Void> nameAnalysor = new NameAnalysisVisitor();
        Visitor<Void> errorHandler = new ErrorHandler();
        Visitor<Void> treePrinter = new TreePrinter();

        toorlaASTCode.accept(nameAnalysor);
        toorlaASTCode.accept(errorHandler);

        if(! ((ErrorHandler) errorHandler).hasError())
            toorlaASTCode.accept( treePrinter );
    }
}
