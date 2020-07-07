/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputData;

import Common.Constants;
import Common.ProcessingFile;
import Common.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Hamza
 */
public class InputDataProcessor
{
    //private String sInputFolderPath = oGV.getDataFilePath();
    private String sParentDirectoryPath = "";//oGV.getDataFileParentPath();

    public void WriteTextData(File oSourceData) {
        InputStream inStream = null;
        OutputStream outStream = null;

        try {

            inStream = new FileInputStream(oSourceData);
            outStream = new FileOutputStream(sParentDirectoryPath + oSourceData.getName());

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            inStream.close();
            outStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File convertFileToTxt(File inputFile, String fileExt) {
        File newFile = null;
        try {
            String fileName = inputFile.getName();
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));

            newFile = new File(inputFile.getParent(), fileName + ".txt");

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile), "UTF-8"));
            String text = null;
            if (fileExt.equals("docx")) {
                text = Util.getTextFromDocx(inputFile);
            } else if (fileExt.equals("doc")) {
                text = Util.getTextFromDoc(inputFile);
            } else if (fileExt.equals("pdf")) {
                text = Util.getTextFromPdf(inputFile);
                text = text.replaceAll("\\s+", " ");
            }

            out.write(text);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newFile;
    }

    //Get text from input file and save it as a string
    public void getTextFromFile(File inputFile, ProcessingFile processingFile) {
        String sFileData = "";
        try {
            String sTextFileName = processingFile.getTextFileName();
            String sEncoding = Constants.DEFAULT_ENCODING;

            FileInputStream fis = new FileInputStream(inputFile);// set .txt file name path here
            InputStreamReader inputStreamReader = new InputStreamReader(fis, sEncoding);
            BufferedReader buffer = new BufferedReader(inputStreamReader);
            String currentSentence = "";

            // read the  source file line by line and store it in currentSentence varible
            while ((currentSentence = buffer.readLine()) != null) {
                if (currentSentence.length() == 0) {
                    continue;
                }
                sFileData += currentSentence + "\r\n";
            }
            inputStreamReader.close();
            fis.close();

            processingFile.setFileText(sFileData);

            int numberOfWords = Util.countWords(sFileData);

            processingFile.setNumberOfWords(numberOfWords);

            List<String> listOfSentences = new ArrayList<String>(Arrays.asList(sFileData.split("[\n.]", -1)));
            listOfSentences = Util.removeEmptyString(listOfSentences);

            processingFile.setListOfSentences(listOfSentences);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return sFileData;
    }

}
