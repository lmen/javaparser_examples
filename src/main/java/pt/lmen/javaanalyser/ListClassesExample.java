package pt.lmen.javaanalyser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;
import pt.lmen.javaanalyser.support.DirExplorer;

import java.io.File;
import java.io.IOException;

public class ListClassesExample {

    public static void main(String[] args) {

        File projectDir = new File("C:\\Users\\lmen\\IdeaProjects\\teste_javaparser\\src\\main\\java");

        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {

        System.out.println(path);
        System.out.println(Strings.repeat("=", path.length()));
        try {

            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                    super.visit(n, arg);
                    System.out.println(" * " + n.getName());
                }
            }.visit(StaticJavaParser.parse(file), null);
            System.out.println(); // empty line
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }).explore(projectDir);

    }
}
