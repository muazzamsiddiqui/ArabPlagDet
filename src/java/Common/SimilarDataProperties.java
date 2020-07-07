/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author Hamza
 */
public class SimilarDataProperties {
   
    private String sText;
    private String sUrl;
    private double dSimilarity;
    
    public SimilarDataProperties()
    {
        this.sText = "";
        this.sUrl = "";
        this.dSimilarity = 0.0;
    }

    public String getText() {
        return sText;
    }

    public void setText(String sText) {
        this.sText = sText;
    }

    public String getUrl() {
        return sUrl;
    }

    public void setUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public double getSimilarityValue() {
        return dSimilarity;
    }

    public void setSimilarityValue(double dSimilarity) {
        this.dSimilarity = dSimilarity;
    }
    
    
}
