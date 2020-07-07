package Common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wl
 */
public class ProcessingFile {
    private String segmentedFileName = "";
    private String textFileName = "";
    private String originalFileName = "";
    private String originalFileExtension = "";
    private String webDirAbsolutePath = "";
    private String fileText = "";
    private int numberOfWords;
    private int numberOfWordsPlagiarised;
    public List<String> listOfSentences;
    private File convertedFile;

    private ArrayList<ExtractedDataProperties> htmlData = new ArrayList<>();
    private ArrayList<ExtractedDataProperties> extractedTextData = new ArrayList<>();
    private List<SimilarDataProperties> similarTextData = new ArrayList<>();

    public void addHtmlData(ExtractedDataProperties html) {
        htmlData.add(html);
    }

    public void addExtractedTextData(ExtractedDataProperties html) {
        extractedTextData.add(html);
    }

    public String getSegmentedFileName() {
        return segmentedFileName;
    }

    public void setSegmentedFileName(String segmentedFileName) {
        this.segmentedFileName = segmentedFileName;
    }

    public String getTextFileName() {
        return textFileName;
    }

    public void setTextFileName(String textFileName) {
        this.textFileName = textFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getOriginalFileExtension() {
        return originalFileExtension;
    }

    public void setOriginalFileExtension(String originalFileExtension) {
        this.originalFileExtension = originalFileExtension;
    }

    public String getWebDirAbsolutePath() {
        return webDirAbsolutePath;
    }

    public void setWebDirAbsolutePath(String webDirAbsolutePath) {
        this.webDirAbsolutePath = webDirAbsolutePath;
    }

    public String getFileText() {
        return fileText;
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    public ArrayList<ExtractedDataProperties> getHtmlData() {
        return htmlData;
    }

    public ArrayList<ExtractedDataProperties> getExtractedTextData() {
        return extractedTextData;
    }

    public List<SimilarDataProperties> getSimilarTextData() {
        return similarTextData;
    }

    public void setSimilarTextData(List<SimilarDataProperties> similarTextData) {
        this.similarTextData = similarTextData;
    }

    public List<String> getListOfSentences() {
        return listOfSentences;
    }

    public void setListOfSentences(List<String> listOfSentences) {
        this.listOfSentences = listOfSentences;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public int getNumberOfWordsPlagiarised() {
        return numberOfWordsPlagiarised;
    }

    public void setNumberOfWordsPlagiarised(int numberOfWordsPlagiarised) {
        this.numberOfWordsPlagiarised = numberOfWordsPlagiarised;
    }

    public File getConvertedFile() {
        return convertedFile;
    }

    public void setConvertedFile(File convertedFile) {
        this.convertedFile = convertedFile;
    }
}
