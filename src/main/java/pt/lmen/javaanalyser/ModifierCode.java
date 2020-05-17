package pt.lmen.javaanalyser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import java.io.File;
import java.io.FileNotFoundException;

public class ModifierCode {

    /**
     * A utility method to print a CompilationUnit preceeded by a title
     */
    private static void printCompilationUnit(String title,
                                             CompilationUnit compilationUnit) {
        System.out.println(title);
        for (int i=0; i<title.length(); i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.println(compilationUnit);
    }

    public static void main(String[] args) throws FileNotFoundException {
        // The directory where we store the examples
        String pathToExamplesDir = "C:\\Users\\lmen\\IdeaProjects\\teste_javaparser\\src\\main\\java\\pt\\lmen\\javaanalyser\\JavaClassTest.java";
        // Parse the code of an entire source file, a.k.a. a Compilation Unit
        CompilationUnit compilationUnitNode = StaticJavaParser.parse(new File(pathToExamplesDir));
        printCompilationUnit("My original class", compilationUnitNode);

        // Modifying the name of the class
        compilationUnitNode.getClassByName("JavaClassTest").get()
                .setName("MyRenamedClass");
        printCompilationUnit("Renamed class", compilationUnitNode);

        // Adding a method: we add a setter
        MethodDeclaration setter = compilationUnitNode
                .getClassByName("MyRenamedClass").get()
                .addMethod("setAField", Modifier.Keyword.PUBLIC);
        setter.addParameter("boolean", "aField");
        setter.getBody().get().getStatements().add(new ExpressionStmt(
                new AssignExpr(
                        new FieldAccessExpr(new ThisExpr(),"aField"),
                        new NameExpr("aField"),
                        AssignExpr.Operator.ASSIGN
                )));
        printCompilationUnit("With a setter", compilationUnitNode);
    }
}
