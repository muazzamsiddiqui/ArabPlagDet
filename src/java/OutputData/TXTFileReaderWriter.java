/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hamza
 */
public class TXTFileReaderWriter
{
    /**
     * Constructor.
     *
     * @param aFileName full name of an existing, readable file.
     */
    public TXTFileReaderWriter(String aFileName) {
        fFilePath = Paths.get(aFileName);
    }

    /**
     * Takes the Document Name and returns the URL list for that Document.
     */
    public final ArrayList<String> processLineByLine(String suspiciousDocName) throws IOException {

        ArrayList<String> urlList = new ArrayList<>();
        String tempURL;

        try (Scanner scanner = new Scanner(fFilePath, ENCODING.name())) {
            while (scanner.hasNextLine()) {
                tempURL = processLine(scanner.nextLine(), suspiciousDocName);

                if (!tempURL.equals("")) {
                    urlList.add(tempURL);
                }
            }
        }

        return urlList;
    }

    /**
     * Returns the Full Document Text in a String (Not Accurate, Not Tested) Not
     * Recommended Without Testing
     *
     * @return
     * @throws IOException
     */
    public final String getFullDocumentText() throws IOException {
        String tempText = "";

        try (Scanner scanner = new Scanner(fFilePath, ENCODING.name())) {
            while (scanner.hasNextLine()) {
                tempText = tempText.concat(scanner.nextLine());
            }
        }

        return tempText;
    }

    /**
     * Returns Full Document Text in Lines in an ArrayList
     *
     * @param suspiciousDocName
     * @return
     * @throws IOException
     */
    public final ArrayList<String> getFullDocumentTextLines() throws IOException {

        ArrayList<String> documentLines = new ArrayList<>();
        String tempLine;

        try (Scanner scanner = new Scanner(fFilePath, ENCODING.name())) {
            while (scanner.hasNextLine()) {
                tempLine = scanner.nextLine();
                documentLines.add(tempLine);
            }
        }

        return documentLines;
    }

    /**
     * Overridable method for processing lines in different ways.
     */
    protected String processLine(String aLine, String fileName) {
        //use a second Scanner to parse the content of each line 
        Scanner scanner = new Scanner(aLine);
        scanner.useDelimiter("\t");

        String url = "";

        if (scanner.hasNext()) {
            //assumes the line has a certain structure
            String fName = scanner.next();
            if (fName.equals(fileName)) {
                url = scanner.next();
            }

        }
        return url;
    }

    /**
     * This method reads labels.txt file (Specified Format) and gets all the
     * File Names from that file.
     *
     * @return
     * @throws IOException
     */
    public final ArrayList<String> parseFileNamesFromExcelFile() throws IOException {

        ArrayList<String> fileNamesList = new ArrayList<>();
        int total = 0;

        try (Scanner scanner = new Scanner(fFilePath, ENCODING.name())) {
            while (scanner.hasNextLine()) {

                Scanner scannerTwo = new Scanner(scanner.nextLine());
                scannerTwo.useDelimiter("\t");

                if (scannerTwo.hasNext()) {
                    //assumes the line has a certain structure
                    String fName = scannerTwo.next();
                    if (!fileNamesList.contains(fName)) {
                        fileNamesList.add(fName);
                        total++;
                        //log(fName);
                    }
                }
            }
        }

        log(total + "File Names Added");
        return fileNamesList;
    }

    /**
     * THis method takes the Text as String and Extracts the URL from it based
     * on a Regular Expression
     *
     * @param sourceText
     * @return
     */
    public ArrayList<String> extractUrlsLessAccurate() {

        ArrayList<String> result = new ArrayList<>();
        try {
            ArrayList<String> documentLines = getFullDocumentTextLines();
            for (String sourceText : documentLines) {
                String urlPattern = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
                Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(sourceText);
                while (m.find()) {
                    result.add(sourceText.substring(m.start(0), m.end(0)));
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(TXTFileReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public ArrayList<String> extractUrlsMoreAccurate() {
        ArrayList<String> result = new ArrayList<>();

        try {
            ArrayList<String> documentLines = getFullDocumentTextLines();
            for (String sourceText : documentLines) {
                String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(sourceText);
                while (m.find()) {
                    String urlStr = m.group();
                    if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                        urlStr = urlStr.substring(1, urlStr.length() - 1);
                    }
                    result.add(urlStr);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TXTFileReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // PRIVATE 
    private final Path fFilePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private static void log(Object aObject) {
        System.out.println(String.valueOf(aObject));
    }

    private String quote(String aText) {
        String QUOTE = "'";
        return QUOTE + aText + QUOTE;
    }

    public static void writeLargerTextFile(String aFileName, ArrayList<String> aLines) throws IOException {
        //createOutputFile(aFileName);
        Path path = Paths.get(aFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)) {
            for (String line : aLines) 
            {
                if(line != null)
                {
                    writer.write(line);
                    writer.newLine();
                }                
            }
            writer.close();
        }
//        try 
//        {
//            BufferedWriter writer = Files.newBufferedWriter(path, ENCODING);
//            for (String line : aLines) {
//                writer.write(line);
//                writer.newLine();
//            }
//        }catch(IOException e)
//        {
//            System.out.print(aFileName + " does not contain any kind of data.");
//        }
    }

    private static void createOutputFile(String aFileName) throws IOException {
        File f = new File(aFileName);
        f.mkdirs();
        f.createNewFile();
    }
}
