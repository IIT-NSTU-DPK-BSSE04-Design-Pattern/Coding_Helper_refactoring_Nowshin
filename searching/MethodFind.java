package searching;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodFind {

    public void getMethod(String filename, String fileContent, String path, String processfilePath) throws FileNotFoundException, IOException {

        Scanner scan = new Scanner(fileContent);
        int linenumber = 0;
        String file = scan.useDelimiter("\\Z").next().trim();             //^\w+\W\w+\.(\w+){3}\s*\{?+[^;]$ dotall
        Matcher methodMatcher = Pattern.compile(pattern, Pattern.MULTILINE).matcher(file);
        while (methodMatcher.find()) {
            String method = new GrepContent().findBetweenBraces(methodMatcher.start(), file);
            String methodname = methodMatcher.group().replaceAll("\\{", "").replaceAll("[\r\n]+", " ").trim();
            linenumber = new GrepContent().getLineNumber(methodname, path, linenumber);
            String processFilename = methodMatcher.group(3) + "-" + linenumber + "-" + filename;;

            if (linenumber != 0) {
                new ProcessSearchFile().processMethod(processFilename, method, path, processfilePath);
            }
        }
    }

    public void getConstructor(String filename, String fileContent, String path, String processfilePath) throws FileNotFoundException, IOException {
        int linenumber = 0;
        Pattern classpattern = Pattern.compile("class\\s+([a-zA-Z]+).*");
        Matcher classMatcher = classpattern.matcher(fileContent);
        while (classMatcher.find()) {
            String className = classMatcher.group(1);
            String classContent = new GrepContent().findBetweenBraces(classMatcher.start(), fileContent);
            Pattern constructorFind = Pattern.compile("(\\b" + classMatcher.group(1) + "\\b)\\s*\\(.*\\)\\s*[^;].*$", Pattern.MULTILINE);

            Matcher consMatch = constructorFind.matcher(classContent);
            while (consMatch.find()) {
                String cons = consMatch.group();
                String constructor = new GrepContent().findBetweenBraces(consMatch.start(), classContent);
                String ConsName = consMatch.group().replaceAll("\\{", "").replaceAll("[\r\n]+", " ").trim();
                linenumber = new GrepContent().getLineNumber(ConsName, path, linenumber);
                String processFilename = consMatch.group(1) + "-" + linenumber + "-" + filename;
                if (linenumber != 0) {
                    new ProcessSearchFile().processMethod(processFilename, constructor, path, processfilePath);
                }

            }
        }
    }
}
