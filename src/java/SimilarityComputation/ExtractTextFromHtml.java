/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimilarityComputation;

import Common.ExtractedDataProperties;
import Common.ProcessingFile;

import java.util.ArrayList;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;


/**
 *
 * @author Hamza
 */
public class ExtractTextFromHtml {

    /**
     * @param args the command line arguments
     */
    /*public String readFile(File f) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                new FileInputStream(f), "UTF-8"));
        StringBuilder str = new StringBuilder();

        String line;
        while ((line = in.readLine()) != null) {
            str.append(line);
        }

        in.close();
        return (str.toString());
    }

    public void writeFile(File f, String str) {

        try {
            FileOutputStream fout = new FileOutputStream(f);
            byte[] bytes = str.getBytes("UTF-8");
            fout.write(bytes);
            fout.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }*/

    

    boolean validHTML(String str) {

        int index = 0;
        if ((index = str.indexOf("<", index)) == -1) {
            return false;
        }
        if ((index = str.toLowerCase().indexOf("html", index)) == -1) {
            return false;
        }
        return true;
    }

    ParseText parseHTML(String content) {

        ParseText p = new ParseText();
        int index = 0;
        int lastIndex = 0;
        int tagIndex = 0;
        boolean title = false;
        boolean meta = false;
        boolean body = false;
        boolean other = false;
        boolean paragraph = false;
        String tag = "";//new String();
        String str = "";//new String();
        String textStr = "";//new String();
        String lowerCaseContent = content.toLowerCase();
        String newLine = System.getProperty("line.separator");

        if (!validHTML(content)) {
            return null;
        }

        while (index != -1) {

            lastIndex = content.indexOf(">", index);
            tag = lowerCaseContent.substring(index, lastIndex + 1);
            index = content.indexOf("<", lastIndex);

            if (tag.startsWith("<title")) {
                title = true;
            } else if (tag.endsWith("/title>")) {
                title = false;
            } else if (tag.startsWith("<meta")) {
                meta = true;
            } else if (tag.startsWith("<body")) {
                body = true;
            } else if (tag.endsWith("/body>")) {
                body = false;
            } else if (tag.startsWith("<script")) {
                other = true;
            } else if (tag.endsWith("/script>")) {
                other = false;
            } else if (tag.startsWith("<form")) {
                other = true;
            } else if (tag.endsWith("/form>")) {
                other = false;
            } else if (tag.startsWith("<style")) {
                other = true;
            } else if (tag.endsWith("/style>")) {
                other = false;
            } else if (tag.startsWith("<p")) {
                paragraph = true;
            } else if (tag.endsWith("/p>")) {
                paragraph = false;
            }

            /*
            if(Math.abs(index) > 330400 || Math.abs(lastIndex)>330400) {
                System.out.println("---------------");
            }
            */

            if (body && !other && index != -1) {
                str = content.substring(lastIndex + 1, index);
                if (!str.trim().equals("")) {
                    str = str.trim();
                    if (paragraph) {
                        str += " ";
                    } else {
                        str += newLine;
                    }
                    textStr += str;
                }

            } else if (title) {
                p.setTitle(content.substring(lastIndex + 1, index));
            } else if (meta) {
                if ((tagIndex = tag.indexOf("keywords")) != -1) {
                    tagIndex = tag.indexOf("content");
                    str = tag.substring(tagIndex + 8, tag.length() - 2);
                    p.setMeta(str.replace(',', ' '));
                }
                meta = false;
            }

        }

        p.setText(textStr);

        return p;

    }

    public static void main(String[] args) {
        String s = "القاهرة - ويكيبيديا، الموسوعة الحرة";
        System.out.println(s.length());
    }

    /*public void extractText(File in, File out) throws IOException
    {
        
        String html = readFile(in);
        Parse p = parseHTML(html);
        if(p != null)
            writeFile(out, p.getText());
    }*/
    
    public void extractText(ProcessingFile processingFile)// throws IOException
    {
        ArrayList<ExtractedDataProperties> oHtml = processingFile.getHtmlData();
        String sText = "";
        String sHtmlContents = "";
        String sUrl = "";
        ExtractedDataProperties oDP;
        if(oHtml.size() > 0)
        {
            for(int nCount = 0; nCount < oHtml.size(); nCount++)
            {
                oDP = oHtml.get(nCount);
                sHtmlContents = oDP.getText();
                sUrl = oDP.getUrl();
                
                if(sHtmlContents != null)
                {
                    ParseText oP = null;
                    try {
                        oP = parseHTML(sHtmlContents);
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                    if(oP != null)
                    {
                        sText = oP.getText();
                        
                        //Save text in array with url
                        ExtractedDataProperties oEDPT = new ExtractedDataProperties();
                        oEDPT.setText(sText);
                        oEDPT.setUrl(sUrl);
                        processingFile.addExtractedTextData(oEDPT);
                    }
                }                                
            }
        }
    }

    /*public static void main(String[] args) throws Exception{
        // TODO code application logic here
        String path = "//Research//Plagiarism Detection//Corpora//CPIT 100//txt//Web sources//";
        
        System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
        System.setProperty("sun.net.client.defaultReadTimeout", "60000");

        long startTime = System.currentTimeMillis();
        
        ExtractTextFromHtml e = new ExtractTextFromHtml();

        File [] files = new File(path + "//Doc//").listFiles();
        for(int i=0; i<files.length; i++)
        {
            String inputFilename = files[i].getName();
            String outputFilename = path + "//txt//" + inputFilename;
            System.out.println("Converting " + inputFilename);
            e.extractText(files[i], new File(outputFilename));
        }

        long endTime = System.currentTimeMillis();
        double execTime = (double) (endTime - startTime) / 1000;

        System.out.println("Program took " + execTime + " seconds");

    }*/
    
}
