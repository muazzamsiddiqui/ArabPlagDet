/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineSearch;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 *
 * @author Hamza
 */
public class GoogleCSEClient
{
    /**
     * Be sure to specify the name of your application. If the application name
     * is {@code null} or blank, the application will log a warning. Suggested
     * format is "MyCompany-ProductName/1.0".
     */
    private static String APPLICATION_NAME;

    /**
     * API Key for the registered developer project for your application.
     */
    private static String API_KEY;

    private static String SEARCH_ENGINE_ID;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport httpTransport;

    private String responseFormat;

    @SuppressWarnings("unused")

    private static Customsearch client;
    private Customsearch customSearch;
    private Customsearch.Cse customSearchEngine;

    private int totalSearchCallsExecuted;

    /**
     * Default Constructor, It will Initialize the API Key and Application Name
     */
    public GoogleCSEClient() {

        Initialize();

        API_KEY = "AIzaSyDRmVYnVAE4w0kKKYuw48LkKC2VK2vhqCM";
        SEARCH_ENGINE_ID = "010618905545683273698:dxnzryqiofi";
        APPLICATION_NAME = "KAU-PlagiarsimSearchApp/1.0";
        responseFormat = "json";

        totalSearchCallsExecuted = 0;
    }

    /**
     * Parametrised Constructor for Giving the API Key of Your Choice along with
     * the Application Name
     *
     * @param applicationName
     * @param APIKey
     * @param searchEngineID
     * @param responseType
     */
    public GoogleCSEClient(String applicationName, String APIKey, String searchEngineID, String responseType) {

        Initialize();

        API_KEY = APIKey;
        SEARCH_ENGINE_ID = searchEngineID;
        APPLICATION_NAME = applicationName;

        //If the response type given in the constructor is not json or atom then set it to json (Default)
        responseFormat = responseType == "json" || responseType == "atom" ? responseType : "json";

        totalSearchCallsExecuted = 0;
    }

    /**
     * Method to Initialize the required Google Clinet properties (This method
     * should always be called in the GoogleCSEngineClient constructor)
     */
    private void Initialize() {
        try {
            // initialize the transport
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            // set up global Customsearch instance
            client = new Customsearch.Builder(httpTransport, JSON_FACTORY, null)
                    .setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(API_KEY))
                    .setApplicationName(APPLICATION_NAME).build();

            customSearch = new Customsearch(httpTransport, JSON_FACTORY, null);
            customSearchEngine = customSearch.cse();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    /**
     * Searching Method, Taking search Query as input Parameter
     *
     * @param searchQuery
     * @return
     */
    public ArrayList<String> Search(String searchQuery) {

        ArrayList<String> searchResultsURLs = new ArrayList<>();

        //No need to Encode the Query, It gives Wrong Results
        //String encodedQuery = encodeQuery(searchQuery);
        try {

            //Pass the Search Query (Encoded)/(Trimmed) as a Searching Parameter to the List Constructor.
            Customsearch.Cse.List list = customSearchEngine.list(searchQuery);

            //Setting the Custom Search Engine ID, this is to be got from the google after Creating the Custom Search Engine
            list.setCx(SEARCH_ENGINE_ID);

            //Setting the API key, to be registered at google to get the API Key.
            list.setKey(API_KEY);//AIzaSyBUYO74Y77j968ijjULfEOHMutxaHc9U5o

            //Setting the Search Response Format, it can be json or atom
            list.setAlt(responseFormat);

            //This method will Execute the Query that has been created and will return the Response in JSON format.            
            Search googleSearch = list.execute();
            totalSearchCallsExecuted++;

            //Creating Our own list and getting the Result objects in that.
            java.util.List<Result> searchResults = googleSearch.getItems();

            //iterating over the list and getting the ULRs from each link.  
            if (searchResults != null) {
                for (Result result : searchResults) {
                    searchResultsURLs.add(result.getLink());
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return searchResultsURLs;
    }

    public int getTotalSearchQueriesExecuted() {
        return totalSearchCallsExecuted;
    }

    //Method was meant to Encode the Search Query, but Not used now as It gives WRONG results
    private String encodeQuery(String query) {
        String encodedQuery = "";
        try {
            encodedQuery = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encodedQuery;
    }
}
