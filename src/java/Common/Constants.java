package Common;

/**
 * @author wl
 */
public class Constants {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.94 Safari/537.4";


    public static final String sOutputFileName = "QueryTrainingOutput.txt";
    //final String API_KEY =  "AIzaSyBUYO74Y77j968ijjULfEOHMutxaHc9U5o"; 
    public static final String API_KEY = "AIzaSyDyDk5wf_wyU6TRXMjMW2ygeMRiMG7hVQE"; //Fahad:
    //Imran: AIzaSyBUYO74Y77j968ijjULfEOHMutxaHc9U5o
    //Aqib: AIzaSyDrWdQnvY9ilQpmP-SMZltHG9fMSiECToQ
    //Umer: AIzaSyBsRuhreLa_98c55MXUO8WyGswMyf5koVM
    //Asim: AIzaSyChjdLUXNjsBH4Sp40WQFQj-O7nq6X99DU

    public static final String SEARCH_ENGINE_ID = "010618905545683273698:dxnzryqiofi";
    public static final String APPLICATION_NAME = "KAU-PlagiarsimSearchApp/1.0";
    public static final String RESPONSE_FORMAT = "json";

    public String getApiKey() {
        return API_KEY;
    }

    public String getSearchEngineId() {
        return SEARCH_ENGINE_ID;
    }

    public String getApplicationName() {
        return APPLICATION_NAME;
    }

    public String getResponseFormat() {
        return RESPONSE_FORMAT;
    }

    public String getOutputFileName() {
        return sOutputFileName;
    }

}
