/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueryProcessing;

import Common.ProcessingFile;
import Common.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author Hamza
 */
public class FileSegmentation {
    private String sFolderPath = "/SegmentedFiles/";
    private String sEncoding = "UTF-8";
    private String sSegmentedFileName = "";
    private ProcessingFile processingFile;

    public FileSegmentation(ProcessingFile processingFile) {
        this.processingFile = processingFile;
    }

    public void segmentFileData() {
        String outputFile = "";
        try {
//            FileWriter fWriter = null;
//            BufferedWriter writer = null;
//            String sTextFileName = oGV.getTextFileName();
//            outputFile =  "Segmentation_"+ sTextFileName;
//            oGV.setSegmentedFileName(outputFile);
//            
//            
//            fWriter = new FileWriter(oGV.getWebDirAbsolutePath() + sFolderPath + outputFile);
//            writer = new BufferedWriter(new OutputStreamWriter(FileOutputStream(oGV.getWebDirAbsolutePath() + sFolderPath + outputFile, sEncoding)));
//
//            FileInputStream inputFile = new FileInputStream (oGV.getWebDirAbsolutePath() + "WEB-INF/Data/" + sTextFileName);
//            InputStreamReader allFileText = new InputStreamReader ( inputFile, "UTF-8" );
//            BufferedReader buffer = new BufferedReader(allFileText);       
//            String currentSentence="";
//            // read the  source file line by line and store it in currentSentence varible
//            while ( ( currentSentence = buffer.readLine()) != null  )
//            {   
//                //remove unorganzied charchter
//                currentSentence= removeUnrocoginizedCharachter(currentSentence);
//                if (currentSentence.length() == 0)
//                {
//                    continue;
//                }
//                //split the current line by Spacees
//                String[] listOfWords = currentSentence.split("\\s+");
//                if (listOfWords.length < 4)
//                {
//                    continue;
//                }
//                String [] spliteStringPeriod = currentSentence.split("\\.");
//                if (spliteStringPeriod.length == 1)
//                {   
//                     writer.write(currentSentence+"\r\n");
//                }
//                else
//                {
//                    String tempBuffer = "";
//                    for (int j = 0; j < spliteStringPeriod.length; j++)
//                    {
//                        if (spliteStringPeriod.length > 1)
//                        {
//                            if (j != spliteStringPeriod.length - 1)
//                            {
//                                tempBuffer += spliteStringPeriod[j] + ".";
//                            }
//                            else
//                            {
//                                tempBuffer += spliteStringPeriod[j];
//                            }
//                            writer.write(tempBuffer+"\r\n");
//                            tempBuffer = "";
//                        }                        
//                    }
//                }
//            }
//            writer.close();
//            fWriter.close();
//            allFileText.close();
//            inputFile.close();
            //read the souce file
            FileWriter fWriter = null;
            BufferedWriter writer = null;
            String sTextFileName = processingFile.getTextFileName();
            outputFile = "Segmentation_" + sTextFileName;
            processingFile.setSegmentedFileName(outputFile);

            File segmentedDir = new File(processingFile.getWebDirAbsolutePath() + sFolderPath);
            segmentedDir.mkdirs();
            //f.createNewFile();
            //fWriter = new FileWriter(oGV.getWebDirAbsolutePath() + sFolderPath + outputFile);//set new file name and path here
            //writer = new BufferedWriter(fWriter);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(segmentedDir, outputFile)), sEncoding));

            String absoluteFilePath = processingFile.getConvertedFile().getAbsolutePath();
            FileInputStream inputFile = new FileInputStream(absoluteFilePath);// set .txt file name path here
            InputStreamReader allFileText = new InputStreamReader(inputFile, sEncoding);
            BufferedReader buffer = new BufferedReader(allFileText);
            String currentSentence = "";
            // read the  source file line by line and store it in currentSentence varible
            while ((currentSentence = buffer.readLine()) != null) {
                //remove unorganzied charchter
                currentSentence = Util.removeUnrocoginizedCharachter(currentSentence);
                if (currentSentence.length() == 0) {
                    continue;
                }
                //split the current line by Spacees
                String[] listOfWords = currentSentence.split("\\s+");
                if (listOfWords.length < 4) {
                    continue;
                }
                String[] spliteStringPeriod = currentSentence.split("\\.");
                if (spliteStringPeriod.length == 1) {
                    writer.write(currentSentence + "\r\n");
                } else {
                    String tempBuffer = "";
                    for (int j = 0; j < spliteStringPeriod.length; j++) {
                        if (spliteStringPeriod.length > 1) {
                            if (j != spliteStringPeriod.length - 1) {
                                tempBuffer += spliteStringPeriod[j] + ".";
                            } else {
                                tempBuffer += spliteStringPeriod[j];
                            }
                            writer.write(tempBuffer + "\r\n");
                            tempBuffer = "";
                        }
                    }
                }
            }
            writer.close();
            //fWriter.close();
            allFileText.close();
            inputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
