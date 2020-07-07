/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueryProcessing;

import Common.ProcessingFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;

/**
 * @author Hamza
 */
public class FirstSentenceQueryHeuristic {
    private int nMaxSentences = 0;
    private LinkedHashSet<String> oQueries;
    private File oSourceFile;
    private String sSegFilePath = "/SegmentedFiles/";
    private ProcessingFile processingFile;

    public FirstSentenceQueryHeuristic(ProcessingFile processingFile, int maxNoSentence) {
        // initilaze varibles
        oQueries = new LinkedHashSet<>();
        nMaxSentences = maxNoSentence;
        oSourceFile = new File(processingFile.getWebDirAbsolutePath() + sSegFilePath + processingFile.getSegmentedFileName());
    }

    public LinkedHashSet<String> generateQueries() {
        try {
            int nSentences = 0;
            String sCurrentSentence = "";
            String filePath = oSourceFile.getPath();
            // read the sourse file
            FileInputStream inputFile = new FileInputStream(filePath);
            InputStreamReader allFileText = new InputStreamReader(inputFile, "UTF-8");
            BufferedReader buffer = new BufferedReader(allFileText);

            while ((sCurrentSentence = buffer.readLine()) != null && nSentences < this.nMaxSentences) {
                oQueries.add(sCurrentSentence);

                System.out.println(sCurrentSentence);
                nSentences++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return oQueries;
    }
}
