/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputData;

import Common.DisplayDataProperty;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hamza
 */
public class OutputSentencesStrings {
 
    private List<List<String>> oCopiedFileList = new ArrayList<>();
    private List<List<String>> oSourceFileList = new ArrayList<>();
    private List<DisplayDataProperty> displayDataPropertiesList;
    private double percent;

    public List<List<String>> getCopiedFileList() {
        return oCopiedFileList;
    }

    public void AddListToCopiedSentList(List<String> oList)
    {
        oCopiedFileList.add(oList);
    }

    public List<List<String>> getSourceFileList() {
        return oSourceFileList;
    }

    public void AddListToSourceSentList(List<String> oList)
    {
        oSourceFileList.add(oList);
    }

    public List<DisplayDataProperty> getDisplayDataPropertiesList() {
        return displayDataPropertiesList;
    }

    public void setDisplayDataPropertiesList(List<DisplayDataProperty> displayDataPropertiesList) {
        this.displayDataPropertiesList = displayDataPropertiesList;
    }
}
