/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineSearch;

import Common.Constants;
import Common.ExtractedDataProperties;
import Common.ProcessingFile;
import OutputData.GenerateExcelFilesForUrls;
import OutputData.HTMLExtractor;
import OutputData.QueryResultsHandler;
import OutputData.TXTFileReaderWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Hamza
 */
public class SearchQueries {
    HashSet<String> queriesList = new LinkedHashSet<String>();

    HashSet<String> allSearchResults;
    List<String> tempSearchResults;
    HTMLExtractor htmlExtractor;
    String uniqueFileID;

    String outputFileName;
    String API_KEY;

    String SEARCH_ENGINE_ID;
    String APPLICATION_NAME;
    String responseFormat;
    String sHtmlFilesPath;
    private ProcessingFile processingFile;

    public SearchQueries(ProcessingFile processingFile, LinkedHashSet oQueriesList) {
        this.processingFile = processingFile;
        queriesList = oQueriesList;
        outputFileName = Constants.sOutputFileName;
        API_KEY = Constants.API_KEY;

        SEARCH_ENGINE_ID = Constants.SEARCH_ENGINE_ID;
        APPLICATION_NAME = Constants.APPLICATION_NAME;
        responseFormat = Constants.RESPONSE_FORMAT;
        sHtmlFilesPath = processingFile.getWebDirAbsolutePath() + "/HtmlFiles/";
        File f = new File(sHtmlFilesPath);
        f.mkdirs();
    }


    public void SearchUsingGoogleAPI() {
        GoogleCSEClient oGCSEC = new GoogleCSEClient(APPLICATION_NAME, API_KEY, SEARCH_ENGINE_ID, responseFormat);

        //The object below of class QueryResultsHandler will be used to store all the urls obtained against a query for comparison (manually) purpose.
        QueryResultsHandler oQueryResult;
        HashSet<QueryResultsHandler> oListOfResults = new LinkedHashSet<QueryResultsHandler>();
        GenerateExcelFilesForUrls oGenOutput = new GenerateExcelFilesForUrls();
        //Now search through Google API for the all the Given queries one by one
        allSearchResults = new LinkedHashSet<String>();
        //long asciiValue;
        for (String query : queriesList) {
            oQueryResult = new QueryResultsHandler();
            String[] Splitt = query.split("\\s+"); // Split the query on the basis of space characters. \s is used for one space character and \s+ is for multiple space characters.
            if (Splitt.length > 4) {
                query = "";
                for (int i = 0; i < 5; i++) {
                    query += Splitt[i] + " ";
                }
            }
            //Adding Double Quotes to Query for Exact Match
            query = "\"" + query + "\"";

            tempSearchResults = oGCSEC.Search(query);
            allSearchResults.addAll(tempSearchResults);
            //asciiValue = ConverttoAscii(query);
            // add results in QueryResultsHandler object
            oQueryResult.setsQuery(query);
            oQueryResult.setoListOfUrls(tempSearchResults);
            oListOfResults.add(oQueryResult);
        }

        oGenOutput.GenerateExcelFile("", oListOfResults, processingFile);

        // Now get the HTMLs from web for all the Searched Results
        // and save the HTML for each file in seperate files with a Unique ID one by one
        String sExtractedHtml = "";
        for (String resultURL : allSearchResults) {
            ExtractedDataProperties oEDP = new ExtractedDataProperties();
            sExtractedHtml = "";

            htmlExtractor = new HTMLExtractor(resultURL);
            htmlExtractor.executeURLRequest();
            ArrayList<String> html = new ArrayList<>();
            sExtractedHtml = htmlExtractor.getExtractedHTML();
            html.add(sExtractedHtml);
            uniqueFileID = UUID.randomUUID().toString();

            oEDP.setText(sExtractedHtml);
            oEDP.setUrl(resultURL);
            processingFile.addHtmlData(oEDP);

            try {
                TXTFileReaderWriter.writeLargerTextFile(sHtmlFilesPath + uniqueFileID + ".txt", html);
            } catch (IOException ex) {
                Logger.getLogger(SearchQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

//        JOptionPane.showMessageDialog(null, "HTMLs are written to Text Files with Unique IDs" + outputFileName);
    }


    public static long ConverttoAscii(String sQuery) {
        StringBuilder sb = new StringBuilder();
        String ascString = null;
        long asciiInt;
        for (int i = 0; i < sQuery.length(); i++) {
            sb.append((int) sQuery.charAt(i));
            char c = sQuery.charAt(i);
        }
        ascString = sb.toString();
        asciiInt = Long.parseLong(ascString);
        return asciiInt;
    }


}
