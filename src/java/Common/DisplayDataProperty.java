package Common;

import java.util.List;

public class DisplayDataProperty {

    private SimilarDataProperties similarDataProperties;
    private List<StringProperty> stringProperties;
    private List<String> externalSourceSentences;

    public SimilarDataProperties getSimilarDataProperties() {
        return similarDataProperties;
    }

    public void setSimilarDataProperties(SimilarDataProperties similarDataProperties) {
        this.similarDataProperties = similarDataProperties;
    }

    public List<StringProperty> getStringProperties() {
        return stringProperties;
    }

    public void setStringProperties(List<StringProperty> stringProperties) {
        this.stringProperties = stringProperties;
    }

    public List<String> getExternalSourceSentences() {
        return externalSourceSentences;
    }

    public void setExternalSourceSentences(List<String> externalSourceSentences) {
        this.externalSourceSentences = externalSourceSentences;
    }
}
