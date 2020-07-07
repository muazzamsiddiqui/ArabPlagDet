package SimilarityComputation;

import java.util.List;

/**
 * @author wl
 */
public class CommonProperty {

    private int indexOfSentenceInSourceText;
    private int indexOfSentenceInInternetText;
    private int numberOfWords;
    private String commonText;
    private int indexOfCommonText;
    private List<List<Integer>> repeatedSubString1;
    private List<List<Integer>> repeatedSubString2;

    public int getIndexOfSentenceInSourceText() {
        return indexOfSentenceInSourceText;
    }

    public void setIndexOfSentenceInSourceText(int indexOfSentenceInSourceText) {
        this.indexOfSentenceInSourceText = indexOfSentenceInSourceText;
    }

    public int getIndexOfSentenceInInternetText() {
        return indexOfSentenceInInternetText;
    }

    public void setIndexOfSentenceInInternetText(int indexOfSentenceInInternetText) {
        this.indexOfSentenceInInternetText = indexOfSentenceInInternetText;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public String getCommonText() {
        return commonText;
    }

    public void setCommonText(String commonText) {
        this.commonText = commonText;
    }

    public List<List<Integer>> getRepeatedSubString1() {
        return repeatedSubString1;
    }

    public void setRepeatedSubString1(List<List<Integer>> repeatedSubString1) {
        this.repeatedSubString1 = repeatedSubString1;
    }

    public List<List<Integer>> getRepeatedSubString2() {
        return repeatedSubString2;
    }

    public void setRepeatedSubString2(List<List<Integer>> repeatedSubString2) {
        this.repeatedSubString2 = repeatedSubString2;
    }

    public int getIndexOfCommonText() {
        return indexOfCommonText;
    }

    public void setIndexOfCommonText(int indexOfCommonText) {
        this.indexOfCommonText = indexOfCommonText;
    }
}
