package SimilarityComputation;

import Common.DisplayDataProperty;
import Common.ProcessingFile;
import Common.SimilarDataProperties;
import Common.StringProperty;
import Common.Util;
import OutputData.GenerateOutputStrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author University
 */
public class StringMatch {
    private List<CommonProperty> AllComoon;
    private List<List<Integer>> repeatedSubString1;
    private List<List<Integer>> repeatedSubString2;
    private int countAdd;
    public List<String> listOfSentences2;
    public List<Integer> indexesOfSentencesPlagiarised;

    private GenerateOutputStrings oSM = new GenerateOutputStrings();
    private final boolean DEBUG = true;

    private ProcessingFile processingFile;

    private List<DisplayDataProperty> displayDataProperties;

    private final double thresholdDocuments = 0.0;// 0.15;
    private final double thresholdSentences = 0.0;//0.15;
    private final int thresholdOfCommonWord = 3;

    public StringMatch(ProcessingFile processingFile) {
        this.processingFile = processingFile;
        this.displayDataProperties = new ArrayList<>();
        this.indexesOfSentencesPlagiarised = new ArrayList<>();
    }

    public List<DisplayDataProperty> compareStrings() {

        List<SimilarDataProperties> oSimilarTextList = processingFile.getSimilarTextData();

        String processingFileDocument = processingFile.getFileText() + " ";
        processingFileDocument = processingFileDocument.replaceAll("[^\\S\\n]+", " ");

        SimilarDataProperties similarDataProperties;
        DisplayDataProperty displayDataProperty;

        if (oSimilarTextList != null) {
            if (oSimilarTextList.size() > 0) {
                for (int nCount = 0; nCount < oSimilarTextList.size(); nCount++) {
                    similarDataProperties = oSimilarTextList.get(nCount);
                    if(DEBUG) {
                        System.out.println(similarDataProperties.getUrl());
                    }

                    displayDataProperty = new DisplayDataProperty();
                    displayDataProperty.setSimilarDataProperties(similarDataProperties);

                    String document2 = similarDataProperties.getText() + " ";
                    document2 = document2.replaceAll("[^\\S\\n]+", " ");

                    compareMatchingStrings(processingFileDocument, document2, displayDataProperty);
                    displayDataProperties.add(displayDataProperty);
                }
            }
        }

        return displayDataProperties;
    }

    private DisplayDataProperty compareMatchingStrings(String document1, String document2, DisplayDataProperty displayDataProperty) {

        double jaccardSimility = JaccardSimilarity.ComputeJaccardSimility(document1, document2);
        List<List<Double>> simility = new ArrayList<List<Double>>();
        AllComoon = new ArrayList<CommonProperty>();
        if (jaccardSimility >= thresholdDocuments) {

            listOfSentences2 = new ArrayList<String>(Arrays.asList(document2.split("[\n.]", -1)));
            listOfSentences2 = Util.removeEmptyString(listOfSentences2);

            for (int i = 0; i < processingFile.getListOfSentences().size(); i++) {
                simility.add(new ArrayList<Double>());
                for (int j = 0; j < listOfSentences2.size(); j++) {

                    double temp = JaccardSimilarity.ComputeJaccardSimility(processingFile.getListOfSentences().get(i), listOfSentences2.get(j));
                    simility.get(i).add(temp);
                    if (temp >= thresholdSentences) {

                        String s1 = processingFile.getListOfSentences().get(i) + " ";
                        String s2 = listOfSentences2.get(j) + " ";
                        List<String> ListOFWords1 = Arrays.asList(s1.split("\\s+"));
                        List<String> ListOFWords2 = Arrays.asList(s2.split("\\s+"));

                        CommonProperty commonProperty = new CommonProperty();
                        commonProperty.setIndexOfSentenceInSourceText(i);
                        commonProperty.setIndexOfSentenceInInternetText(j);
                        returnAllCommonSubString(ListOFWords1, ListOFWords2, commonProperty, s1);
                    }
                }
            }

            if(AllComoon.size() > 0) {
                displayDataProperty.setExternalSourceSentences(listOfSentences2);
                int biggestNumberOfWords = 0;
                int indexWithBiggestNumberOfWords = 0;
                for (int i = 0; i < AllComoon.size(); i++) {
                    if(biggestNumberOfWords < AllComoon.get(i).getNumberOfWords()) {
                        biggestNumberOfWords = AllComoon.get(i).getNumberOfWords();
                        indexWithBiggestNumberOfWords = i;
                    }
                }
                List<CommonProperty> temp = new ArrayList<>();
                temp.add(AllComoon.get(indexWithBiggestNumberOfWords));
                //System.out.println(temp.get(0).getCommonText());
                AllComoon = new ArrayList<>();
                AllComoon = temp;
            }

            List<StringProperty> stringProperties = getStringProperties();
            displayDataProperty.setStringProperties(stringProperties);
        }

        return displayDataProperty;
    }

    private List<StringProperty> getStringProperties() {

        List<StringProperty> stringProperties = new ArrayList<>();

        for (int k = 0; k < AllComoon.size(); k++) {
            CommonProperty commonProperty = AllComoon.get(k);

            if(indexesOfSentencesPlagiarised.contains(commonProperty.getIndexOfSentenceInSourceText())) {
                continue;
            } else {
                indexesOfSentencesPlagiarised.add(commonProperty.getIndexOfSentenceInSourceText());
            }

            StringProperty stringProperty = new StringProperty();
            stringProperty.setIndexOfSentenceInConvertedFile(commonProperty.getIndexOfSentenceInSourceText());
            stringProperty.setIndexOfSentenceInInternetFile(commonProperty.getIndexOfSentenceInInternetText());
            int nLength = commonProperty.getCommonText().length();
            stringProperty.setLengthInConvertedFile(nLength);
            stringProperty.setLengthInInternetFile(nLength);
            stringProperty.setNumberOfWords(commonProperty.getNumberOfWords());
            stringProperty.setIndexOfSuspiciousTextInSentence(commonProperty.getIndexOfCommonText());

            int tempWords = processingFile.getNumberOfWordsPlagiarised();
            tempWords += commonProperty.getNumberOfWords();
            processingFile.setNumberOfWordsPlagiarised(tempWords);

            stringProperties.add(stringProperty);
        }

        return stringProperties;
    }

    private void returnAllCommonSubString(List<String> Liststr1, List<String> Liststr2, CommonProperty commonProperty, String s) {
        String str1 = Util.getConcatenatedString(Liststr1) + " ";
        String str2 = Util.getConcatenatedString(Liststr2) + " ";
        while (true) {
            List<String> temp = Util.longestSubstring(Liststr1, Liststr2, thresholdOfCommonWord);
            if (temp == null)
                break;

            if (temp.get(0).equals(""))
                break;

            CommonProperty tempo = new CommonProperty();
            tempo.setIndexOfSentenceInSourceText(commonProperty.getIndexOfSentenceInSourceText());
            tempo.setIndexOfSentenceInInternetText(commonProperty.getIndexOfSentenceInInternetText());
            tempo.setRepeatedSubString1(commonProperty.getRepeatedSubString1());
            tempo.setRepeatedSubString2(commonProperty.getRepeatedSubString2());
            tempo.setCommonText(temp.get(0));
            tempo.setIndexOfCommonText(s.indexOf(temp.get(0)));
            tempo.setNumberOfWords(Integer.parseInt(temp.get(1)));
            AllComoon.add(tempo);

            if(1==1)
            break;
            List<Integer> tempList = new ArrayList<Integer>();
            repeatedSubString1.add(tempList);
            repeatedSubString2.add(tempList);

            String remove = temp.get(0);
            allSubseqense(remove, str1, str2);
            countAdd++;

            String repeated1 = new String(new char[remove.length()]).replace("\0", "^");
            String repeated2 = new String(new char[remove.length()]).replace("\0", "#");

            System.out.println(remove.substring(0, remove.length() - 1));

            str1 = str1.replaceAll(remove.substring(0, remove.length() - 1), repeated1);
            str2 = str2.replaceAll(remove.substring(0, remove.length() - 1), repeated2);
            try {
                returnAllCommonSubString(Arrays.asList(str1.split("\\s+")), Arrays.asList(str2.split("\\s+")), tempo, s);
            } catch (Exception e) {
                e.printStackTrace();
            }

            break;
        }
    }

    private void allSubseqense(String subString, String str1, String str2) {
        int index = str1.indexOf(subString);
        //  index = str1.indexOf(subString, index + 1);
        while (index >= 0) {
            repeatedSubString1.get(countAdd).add(index);
            index = str1.indexOf(subString, index + 1);
        }

        index = str2.indexOf(subString);
        //  index = str2.indexOf(subString, index + 1);
        while (index >= 0) {
            repeatedSubString2.get(countAdd).add(index);
            index = str2.indexOf(subString, index + 1);
        }

    }

    public static void main(String[] args) {
        String s = new String(new char[8]);
        System.out.println(s);
    }

}
