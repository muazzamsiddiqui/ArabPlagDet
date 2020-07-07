/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputData;

import Common.ProcessingFile;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Hamza
 */
public class GenerateExcelFilesForUrls
{
    private String sSourceFileName;
    private String sOutputFileExt = ".xls";
//    private String sOutputFilePath = oGV.getDataFilePath();
    private String sOutputFilePath = "/OutputFiles/";
    
    // This method is used to generate an excel file.
    public void GenerateExcelFile(String sFileName, HashSet<QueryResultsHandler> oListOfUrls, ProcessingFile processingFile)
    {
        sSourceFileName = "QueryResults_" + processingFile.getOriginalFileName();
        File f = new File(processingFile.getWebDirAbsolutePath() + sOutputFilePath);
        f.mkdirs();
        //Path path = Paths.
        sFileName = processingFile.getWebDirAbsolutePath() + sOutputFilePath + sSourceFileName + sOutputFileExt;
        HSSFWorkbook oExcelFile = new HSSFWorkbook();
        HSSFSheet oWorkSheet = oExcelFile.createSheet("Sheet 1");
        
        HSSFRow oHeader = oWorkSheet.createRow(0);
        oHeader.createCell(0).setCellValue("Query");
        oHeader.createCell(1).setCellValue("Urls Extracted Using APIs");
        oHeader.createCell(2).setCellValue("Urls Extracted Manually");
        
        
        String sQuery;
        List<String> oList;
        int nRowCount = 1;
        
        for(QueryResultsHandler oQueryResult : oListOfUrls)
        {
            sQuery = oQueryResult.getsQuery();
            oList = oQueryResult.getoListOfUrls();
            for(String sUrl: oList)
            {
                HSSFRow oRow = oWorkSheet.createRow(nRowCount);
                oRow.createCell(0).setCellValue(sQuery);
                oRow.createCell(1).setCellValue(sUrl);
                
                nRowCount++;
            }            
        }
        
        try
        {
            FileOutputStream oFileOut = new FileOutputStream(sFileName);
            oExcelFile.write(oFileOut);
            oFileOut.close();
        }
        catch (IOException ex) {
            Logger.getLogger(GenerateExcelFilesForUrls.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
}
