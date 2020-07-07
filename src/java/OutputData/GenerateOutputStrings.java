/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputData;

import Common.DisplayDataProperty;
import Common.ProcessingFile;
import Common.SimilarDataProperties;
import Common.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hamza
 */
public class GenerateOutputStrings {

    private ProcessingFile processingFile;
    private Boolean Change = true;
    private List<DisplayDataProperty> displayDataPropertiesList;
    private String sSuspeciousDocString;
    private String sSourceDocString;
    private int nTempSourceIndex = 0;
    private int nTempSuspeciousIndex = 0;
    private String[] oColors = new String[] {"red", "blue", "seagreen", "yellow", "purple", "orange", "brown", "pink", "tan"};//[10];
//    private int nIndex1;
//    private int nIndex2;
//    private int nLength1;
//    private int nLength2;
//    
//    public StringMatch()
//    {
//        nIndex1 = 0;
//        nIndex2 = 0;
//        nLength1 = 0;
//        nLength2 = 0;
//    }
    
    public GenerateOutputStrings()
    {
        displayDataPropertiesList = new ArrayList<DisplayDataProperty>();
    }


    public GenerateOutputStrings(List<DisplayDataProperty> displayDataPropertiesList, ProcessingFile processingFile)
    {
        this.displayDataPropertiesList = displayDataPropertiesList;
        this.processingFile = processingFile;
    }

    public OutputSentencesStrings GenerateOutputStrings()
    {
        OutputSentencesStrings outputSentencesStrings = new OutputSentencesStrings();
        outputSentencesStrings.setDisplayDataPropertiesList(displayDataPropertiesList);
        
        String sSuspeciousOutput = "";
        String sSourceOutput = "";
        DisplayDataProperty displayDataProperty;
        SimilarDataProperties similarDataProperties;
        List<String> oList = new ArrayList<String>();
        List<StringProperty> stringProperties;
        List<String> processingFileSentences;
        int nColorIndex;
        String sColor;
        
        for(int i = 0; i < displayDataPropertiesList.size(); i ++)
        {
            displayDataProperty = displayDataPropertiesList.get(i);
            similarDataProperties = displayDataProperty.getSimilarDataProperties();
            //oSP = oDDP.getStrProp();
            stringProperties = displayDataProperty.getStringProperties();
            if(stringProperties == null) {
                continue;
            }
            processingFileSentences = processingFile.getListOfSentences();

            nColorIndex = i % oColors.length;
            sColor = oColors[nColorIndex];

            AddHtmlToSuspeciousString(similarDataProperties, stringProperties, processingFileSentences, sColor);

            outputSentencesStrings.AddListToCopiedSentList(processingFileSentences);
        }
        
//        oList.add(sSuspeciousOutput);
//        
//        for(int j = 0; j < oListDDP.size(); j ++)
//        {
//            oDDP = oListDDP.get(j);
//            oSDP = oDDP.getSimDataProp();
//            //oSP = oDDP.getStrProp();
//            nColorIndex = j % oColors.length;
//            sColor = oColors[nColorIndex];
//            //sSourceOutput = AddHtmlToSourceString(oSDP, oSP, sColor, j);
//            oList.add(sSourceOutput);
//        }
//        
        return outputSentencesStrings;
    }
    
//    private String AddHtmlToSourceString(SimilarDataProperties oSDP, ArrayList<StringProperties> oSPList, List<String> oCopiedSent, ArrayList<String> oSourceSent, String sClr, int nCount)
//    {
//        String sTextString = "";
//        String sUrl = "";
//        double dSimilarity = 0.0;
//        int nIndex = 0;
//        int nLength = 0;
//        
//        String sHtmlString = "";
//        String sCopiedText = "";
//        String sTempStr = "";
//        
//        nIndex = oSP.getIndexInSource() + nTempSourceIndex;
//        nLength = oSP.getLengthInSource();
//        
//        sTextString = oSDP.getText();
//        sUrl = oSDP.getUrl();
//        dSimilarity = oSDP.getSimilarityValue();
//        
//        sCopiedText = sTextString.substring(nIndex, nIndex + nLength);
//        
//        sHtmlString = "<a style='color:" + sClr + ";' href='"+ sUrl + "'><b>[" + (nCount+1) + "]</b>" + sCopiedText +" </a>";
//        nTempSourceIndex += sHtmlString.length();
//        
//        sTempStr = sTextString.substring(0, nIndex + 1);	//substring from start to the found index
//	sTempStr += sHtmlString;	//highlight the copied text
//	sTempStr += sTextString.substring(nTempSourceIndex);
//        
//        return sTempStr;
//    }
    
    private void AddHtmlToSuspeciousString(SimilarDataProperties similarDataProperties,
                                           List<StringProperty> stringProperties,
                                           List<String> processingFileSentences, String color)
    {
        String sTextString = "";
        String sUrl = "";
        double dSimilarity = 0.0;
        int nIndex = 0;
        //int nLength = 0;
        
        String sHtmlString = "";
        String sCopiedText = "";
        String sTempStr = "";
        
        sUrl = similarDataProperties.getUrl();
        dSimilarity = similarDataProperties.getSimilarityValue();

//        nIndex = oSP.getIndexInCopiedFile()+ nTempSuspeciousIndex;
//        nLength = oSP.getLengthInCopiedFile();
        
        //sTextString = oSDP.getText();
        
        
        
        //GlobalVariables oGV = new GlobalVariables(); 
        //sTextString = oGV.getFileText();
        
//        if(nIndex >= sTextString.length())
//        {
//            nIndex = oSP.getIndexInCopiedFile();
//        }
        int indexOfSentenceInConvertedFile;
        int nSntNoInSource;
        int nLength;
        int nIndexInSuspecious;
        int nIndexInSource;
        for(int i = 0; i < stringProperties.size(); i++)
        {
            StringProperty stringProperty = stringProperties.get(i);
            stringProperty.setColor(color);
            indexOfSentenceInConvertedFile = stringProperty.getIndexOfSentenceInConvertedFile();
            nLength = stringProperty.getLengthInConvertedFile();
            nIndexInSuspecious = stringProperty.getIndexOfSuspiciousTextInSentence();

            // For Suspecious list
            sTextString = processingFileSentences.get(indexOfSentenceInConvertedFile).toString();
            sCopiedText = sTextString.substring(nIndexInSuspecious, nIndexInSuspecious + nLength);
            sHtmlString = "<a target='_blank' style='font-weight:bold;color:" + color + ";' href='"+ sUrl + "'>" + sCopiedText +" </a>";
            
            sTempStr = sTextString.substring(0, nIndexInSuspecious);	//substring from start to the found index
            sTempStr += sHtmlString;	//highlight the copied text
            sTempStr += sTextString.substring(nIndexInSuspecious + nLength);

            processingFileSentences.set(indexOfSentenceInConvertedFile, sTempStr);

            /*
            sTextString = "";
            sTempStr = "";
            // For Source list
            nSntNoInSource = oSP.getSentenceNoInSource();
            nIndexInSource = Integer.parseInt(oSP.getIndexInSource().get(0).toString());
            sTextString = sourceSentences.get(nSntNoInSource).toString();
            sCopiedText = sTextString.substring(nIndexInSource, nIndexInSource + nLength);
            sHtmlString = "<a target='_blank' style='color:" + sColor + ";' href='"+ sUrl + "'>" + sCopiedText +" </a>";
            
            sTempStr = sTextString.substring(0, nIndexInSource);	//substring from start to the found index
            sTempStr += sHtmlString;	//highlight the copied text
            sTempStr += sTextString.substring(nIndexInSource + nLength);

            sourceSentences.set(nSntNoInSource, sTempStr);
            */
            
            sTextString = "";
            sTempStr = "";
        }

    }

}
