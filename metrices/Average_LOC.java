package metrices;

import IO.ProjectReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class AverageLOC {

    public void calculateAverageLOC(String path) {
        try {
            prepareProjectData(path);
            double average = calculateAverage(ProjectReader.filename.size(), LineOfCode.totalLineOfProject);
            System.out.println("\tAverage LOC in a class: " + formatToTwoDecimalPlaces(average));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resetProjectData();
        }
    }

    private void prepareProjectData(String path) throws Exception {
        ProjectReader.fileRead(path, 0);
        for (String filename : ProjectReader.filename) {
            new LineOfCode().countLines(filename);
        }
    }

    private double calculateAverage(int totalClasses, int totalLines) {
        return (double) totalLines / totalClasses;
    }

    private String formatToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

    private void resetProjectData() {
        ProjectReader.classCount = 0;
        ProjectReader.filename.clear();
        LineOfCode.totalLineOfProject = 0;
    }
}
