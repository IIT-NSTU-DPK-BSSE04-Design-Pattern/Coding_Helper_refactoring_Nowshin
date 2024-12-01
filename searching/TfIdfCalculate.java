package searching;

import code_clone.getTfIdf;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class TfIdfCalculate {

    ArrayList<String> allterms = new ArrayList<>();
    ArrayList<String> FileWord = new ArrayList<>(); //store all files one by one
    ArrayList<String[]> FilesWords = new ArrayList<>(); //store all files one by one as a String array
    HashMap<String, Double> idfmap = new HashMap<>();
    public static ArrayList<String> queryTerms = new ArrayList<>();
    public static ArrayList<double[]> queryTfIdfVector = new ArrayList<>();
    public static ArrayList<double[]> tfidfvectorProject = new ArrayList<>();

    public void fileRead(String path) throws IOException {

        File directoryPath = new File(path);
        File fileList[] = directoryPath.listFiles();
        for (File file : fileList) {

            if (file.getName().endsWith(".txt")) {

                String filePath = path + "\\" + file.getName();
                Path p = Paths.get(filePath);
                byte[] filecontent = Files.readAllBytes(p);
                String fileContent = new String(filecontent, StandardCharsets.UTF_8).trim();

                String[] allterm = fileContent.split(" ");

                for (String terms : allterm) {

                    if (!allterms.contains(terms)) {
                        allterms.add(terms);
                    }
                }
                FileWord.add(fileContent.trim());
                FilesWords.add(allterm);
            }

        }

    }

    public void Idfcal() {
        double idf;
        for (String term : allterms) {
            idf = new getTfIdf().getIdf(FileWord, term);
            idfmap.put(term, idf);

        }

    }

    public void UniqueQueryTerms(String processQuery) {
        String[] queryTerm = processQuery.trim().split(" ");
        for (String term : queryTerm) {

            if (!queryTerms.contains(term)) {

                queryTerms.add(term);
            }

        }

    }

    public void ProjectTfIdfCal() {

        double tf;
        double idf;
        double tfidf;

        for (String[] fileword : FilesWords) {
            int count = 0;

            double[] tfidfvector = new double[queryTerms.size()];
            for (String term : queryTerms) {
                tf = new getTfIdf().getTf(fileword, term);
                if (idfmap.containsKey(term)) {
                    idf = idfmap.get(term);

                } else {
                    idf = 0;
                }
                tfidf = tf * idf;
                tfidfvector[count] = tfidf;
                count++;

            }
            tfidfvectorProject.add(tfidfvector);
        }

    }

    public void queryTfIdfCal(String processQuery) {
        String[] queryTerm = processQuery.trim().split(" ");
        double Tf;
        double Idf = 0;
        double queryTfIdf = 0;
        int count = 0;
        double[] queryvector = new double[queryTerms.size()];
        for (String q : queryTerms) {
            Tf = new getTfIdf().getTf(queryTerm, q);

            if (idfmap.containsKey(q)) {
                Idf = idfmap.get(q);

            } else {
                Idf = 0;

            }
            queryTfIdf = Tf * Idf;

            queryvector[count] = queryTfIdf;
            count++;
        }
        queryTfIdfVector.add(queryvector);

    }

}
