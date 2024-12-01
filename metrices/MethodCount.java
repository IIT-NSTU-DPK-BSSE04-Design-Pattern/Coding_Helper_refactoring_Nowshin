package metrices;

import IO.ProjectReader;
import searching.Search;
import java.io.IOException;

public class MethodCount {

    public void getTotalMethods(String projectPath, String projectName) {
        try {
            processProjectFiles(projectPath, projectName);
            int totalMethods = ProjectReader.count;
            displayTotalMethods(totalMethods);
        } catch (Exception e) {
            handleError(e);
        } finally {
            cleanupResources();
        }
    }

    private void processProjectFiles(String projectPath, String projectName) throws IOException {
        Search search = new Search();
        if (isJavaFile(projectName)) {
            search.getFile(projectPath, projectName);
        } else {
            search.processProject(projectPath, projectName);
        }
    }

    private boolean isJavaFile(String projectName) {
        final String JAVA_EXTENSION = ".java";
        return projectName.endsWith(JAVA_EXTENSION);
    }

    private void displayTotalMethods(int totalMethods) {
        System.out.println("\tTotal methods: " + totalMethods);
    }

    private void handleError(Exception e) {
        System.err.println("An error occurred while counting methods: " + e.getMessage());
        e.printStackTrace();
    }

    private void cleanupResources() {
        ProjectReader.count = 0;
        Search.ProjectFileName.clear();
    }
}
