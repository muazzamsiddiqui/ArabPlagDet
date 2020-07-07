/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputData;

import Common.ProcessingFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Hamza
 */
public class InputFileHandler {

    private String sourceFileAbsolutePath;
    private String sourceFileName;
    private String sourceFileExtension;
    private ProcessingFile processingFile;

    public InputFileHandler(ProcessingFile processingFile) {
        this.processingFile = processingFile;
    }

    public void uploadFile(HttpServletRequest request, HttpServletResponse response) {
        OutputStream outStream = null;
        InputStream filecontent = null;
        String sFilePath = "";

        try {

            try {
                Part filePart = request.getPart("file");
                String uploadedFileName = getFileNameWeb(filePart);

                File parentDir = (File) request.getServletContext().getAttribute(ServletContext.TEMPDIR);
                File file = initFile(parentDir, uploadedFileName);

                outStream = new FileOutputStream(file);
                filecontent = filePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[4096];

                while ((read = filecontent.read(bytes)) != -1) {
                    outStream.write(bytes, 0, read);
                }
                System.out.println("New file " + uploadedFileName + " created at " + parentDir);
            } catch (FileNotFoundException fne) {
                //writer.println("You either did not specify a file to upload or are "
                //    + "trying to upload a file to a protected or nonexistent "
                //    + "location.");
                //writer.println("<br/> ERROR: " + fne.getMessage());

            } finally {
                if (filecontent != null)
                    filecontent.close();

                if (outStream != null)
                    outStream.close();
            }
        } catch (IOException | ServletException me) {
            // Add code here
        }

    }

    private File initFile(File parentDir, String uploadedFileName) {
        File file = new File(parentDir, uploadedFileName);

        sourceFileName = uploadedFileName;
        sourceFileAbsolutePath = file.getAbsolutePath();
        sourceFileExtension = getSourceFileExtension(sourceFileAbsolutePath);

        processingFile.setWebDirAbsolutePath(parentDir.getAbsolutePath());
        processingFile.setOriginalFileName(sourceFileName);
        processingFile.setOriginalFileExtension(sourceFileExtension);

        return file;
    }

    private String getFileNameWeb(final Part part) {
        final String partHeader = part.getHeader("content-disposition");

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }

    public void GetTextData() {

        InputDataProcessor inputDataProcessor = new InputDataProcessor();

        File oInputFile = new File(sourceFileAbsolutePath);
        if (!oInputFile.isFile())
            return;

        processingFile.setTextFileName(oInputFile.getName());

        if(!sourceFileExtension.equals("txt")) {
            oInputFile = inputDataProcessor.convertFileToTxt(oInputFile, sourceFileExtension);
        }

        processingFile.setConvertedFile(oInputFile);

        inputDataProcessor.getTextFromFile(oInputFile, processingFile);
    }

    public String getSourceFileExtension(String sPath) {
        return sPath.substring(sPath.lastIndexOf(".") + 1, sPath.length());
    }

    public String getSourceFileAbsolutePath() {
        return sourceFileAbsolutePath;
    }
}
