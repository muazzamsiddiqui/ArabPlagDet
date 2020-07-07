package Main;

import Common.DisplayDataProperty;
import Common.ExtractedDataProperties;
import Common.ProcessingFile;
import Common.Util;
import InputData.InputFileHandler;
import OnlineSearch.SearchQueries;
import OutputData.GenerateOutputStrings;
import OutputData.HTMLExtractor;
import OutputData.OutputSentencesStrings;
import QueryProcessing.FileSegmentation;
import QueryProcessing.FirstSentenceQueryHeuristic;
import SimilarityComputation.ExtractTextFromHtml;
import SimilarityComputation.StringMatch;
import SimilarityComputation.SubString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hamza
 */
public class MainHandler {
    HttpServletRequest oRequest;
    HttpServletResponse oResponse;
    private ProcessingFile processingFile;
    private final boolean TEST = false;

    MainHandler(HttpServletRequest request, HttpServletResponse response, ProcessingFile processingFile) {
        oRequest = request;
        oResponse = response;
        this.processingFile = processingFile;
    }

    public OutputSentencesStrings processRequest() {

        OutputSentencesStrings outputSentencesStrings = null;
        try {
            InputFileHandler inputFileHandler = new InputFileHandler(processingFile);
            inputFileHandler.uploadFile(oRequest, oResponse);
            inputFileHandler.GetTextData();

            FileSegmentation fileSegmentation = new FileSegmentation(processingFile);
            fileSegmentation.segmentFileData();

            FirstSentenceQueryHeuristic firstSentenceQueryHeuristic = new FirstSentenceQueryHeuristic(processingFile, 5);

            searchForPlagiarism(firstSentenceQueryHeuristic, TEST);

            Util.computeSimilarityInText(processingFile);

            //StringMatch oSM = new StringMatch();
            //oDisplayList = oSM.CompareStrings();
            StringMatch stringMatch = new StringMatch(processingFile);
            List<DisplayDataProperty> displayDataProperties = stringMatch.compareStrings();

            GenerateOutputStrings generateOutputStrings = new GenerateOutputStrings(displayDataProperties, processingFile);
            outputSentencesStrings = generateOutputStrings.GenerateOutputStrings();
        } catch (Exception x) {
            x.printStackTrace();
        }

        return outputSentencesStrings;
    }

    private void searchForPlagiarism(FirstSentenceQueryHeuristic firstSentenceQueryHeuristic, boolean isTest) {

        SearchQueries searchQueries = new SearchQueries(processingFile, firstSentenceQueryHeuristic.generateQueries());
        searchQueries.SearchUsingGoogleAPI();

        ExtractTextFromHtml extractTextFromHtml = new ExtractTextFromHtml();
        extractTextFromHtml.extractText(processingFile);

    }

}
