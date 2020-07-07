package Common;

/**
 *
 */
public class StringProperty {
    
    private int indexOfSentenceInConvertedFile;
    private int indexOfSentenceInInternetFile;
    private int lengthInConvertedFile;
    private int lengthInInternetFile;
    private String color;
    private int numberOfWords;
    private int indexOfSuspiciousTextInSentence;

    public int getIndexOfSentenceInConvertedFile() {
        return indexOfSentenceInConvertedFile;
    }

    public void setIndexOfSentenceInConvertedFile(int indexOfSentenceInConvertedFile) {
        this.indexOfSentenceInConvertedFile = indexOfSentenceInConvertedFile;
    }

    public int getIndexOfSentenceInInternetFile() {
        return indexOfSentenceInInternetFile;
    }

    public void setIndexOfSentenceInInternetFile(int indexOfSentenceInInternetFile) {
        this.indexOfSentenceInInternetFile = indexOfSentenceInInternetFile;
    }

    public int getLengthInConvertedFile() {
        return lengthInConvertedFile;
    }

    public void setLengthInConvertedFile(int lengthInConvertedFile) {
        this.lengthInConvertedFile = lengthInConvertedFile;
    }

    public int getLengthInInternetFile() {
        return lengthInInternetFile;
    }

    public void setLengthInInternetFile(int lengthInInternetFile) {
        this.lengthInInternetFile = lengthInInternetFile;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public int getIndexOfSuspiciousTextInSentence() {
        return indexOfSuspiciousTextInSentence;
    }

    public void setIndexOfSuspiciousTextInSentence(int indexOfSuspiciousTextInSentence) {
        this.indexOfSuspiciousTextInSentence = indexOfSuspiciousTextInSentence;
    }
}
