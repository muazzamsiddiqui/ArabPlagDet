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
public class ExtractedDataProperties {
 
    private String sText;
    private String sUrl;
    
    public ExtractedDataProperties()
    {
        this.sText = "";
        this.sUrl = "";
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
    
    
}
