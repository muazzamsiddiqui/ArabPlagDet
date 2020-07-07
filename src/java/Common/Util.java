package Common;

import SimilarityComputation.JaccardSimilarity;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author wl
 */
public class Util {

    public static void writeTextToFileNio(String fileName, String text, String charsetName) throws IOException {

        File f = new File(fileName);
        if(!f.exists()) {
            f.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(f, true);

        InputStream is = new ByteArrayInputStream(text.getBytes(charsetName));
        ReadableByteChannel rbc = Channels.newChannel(is);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        rbc.close();
        fos.close();
    }

    public static final List<File> getFilesRecursive( final File basedir, final FileFilter filter, boolean doSubDirs ) {

        List<File> files = new ArrayList<File>();

        if ( basedir != null && basedir.isDirectory() ) {

            if(doSubDirs) {
                for ( File subdir : basedir.listFiles( FileTypeFilter.DIR ) ) {
                    files.addAll(getFilesRecursive(subdir, filter, doSubDirs));
                }
            }
            files.addAll( Arrays.asList(basedir.listFiles(filter)) );
        }

        return files;
    }

    public static String readFileNIO(String dir, String fileName) throws IOException {
        Path path = FileSystems.getDefault().getPath(dir, fileName);
        InputStream is = Files.newInputStream(path);
        Reader r = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(r);
        StringBuilder sb = new StringBuilder();
        String str;
        while((str=br.readLine()) != null){
            sb.append(str);
            sb.append("\n");
        }

        return sb.toString();
    }

    public static void disableSSL() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new javax.net.ssl.X509TrustManager() {

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public static String getTextFromPdf(File pdfFile) throws IOException {
        PDDocument doc = PDDocument.load(pdfFile);

        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(doc);
        doc.close();


        //text = Normalizer.normalize(text, Normalizer.Form.NFKD);

        /*
        BidiBase bidiBase = new BidiBase(text.toCharArray(), 0, null, 0, text.length(), Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < bidiBase.getLength(); ++i) {
            sb.append(' ');
            sb.append(new String(new byte[]{bidiBase.getLevelAt(i)}, "UTF-8"));
        }
        String tt = sb.toString();
        */

        //BiDiClass biDiClass = new BiDiClass();

        //text = biDiClass.englishToArabicNumber(text);
        //text = biDiClass.makeLineLogicalOrder(text, true);
        //text = biDiClass.normalizePres(text);
        //text = biDiClass.normalizeDiac(text);

        return text;
    }

    public static String getTextFromPdf(File pdfFile, int startPage, int endPage) throws IOException {

        PDDocument doc = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();

        //stripper.setStartPage(startPage);
        //stripper.setEndPage(endPage);
        String text = stripper.getText(doc);
        doc.close();

        return text;
    }

    public static String getTextFromDoc(File inputFile) throws IOException {

        FileInputStream fis = new FileInputStream(inputFile.getAbsoluteFile());
        HWPFDocument doc = new HWPFDocument(fis);
        WordExtractor wordExtrator = new WordExtractor(doc);
        String text = wordExtrator.getText();

        return text;
    }

    public static String getTextFromDocx(File inputFile) throws IOException {

        FileInputStream fis = new FileInputStream(inputFile.getAbsoluteFile());
        XWPFDocument doc = new XWPFDocument(fis);
        XWPFWordExtractor wordExtrator = new XWPFWordExtractor(doc);
        String text = wordExtrator.getText();

        return text;
    }

    public static List<String> removeEmptyString(List<String> temp) {
        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).trim().isEmpty()) {
                //do nothing
            } else {
                returnList.add(temp.get(i));
            }
        }
        return returnList;
    }

    public static int countWords(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }

    public static List<String> longestSubstring(List<String> str1, List<String> str2, int thresholdOfCommonWord) {

        List<String> allinfo = new ArrayList<String>();
        allinfo.add("");
        allinfo.add("");

        if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
            return null;

        // ignore case
        //str1 = str1.toLowerCase();
        //str2 = str2.toLowerCase();

        // java initializes them already with 0
        int[][] num = new int[str1.size()][str2.size()];
        int maxlen = 0;
        int lastSubsBegin = -1;

        StringJoiner sj = new StringJoiner(" ");
        for (int i = 0; i < str1.size(); i++) {
            for (int j = 0; j < str2.size(); j++) {
                if (str1.get(i).equals(str2.get(j))) {
                    if ((i == 0) || (j == 0))
                        num[i][j] = 1;
                    else
                        num[i][j] = 1 + num[i - 1][j - 1];


                    if (num[i][j] > maxlen) {
                        maxlen = num[i][j];

                        if (num[i][j] >= thresholdOfCommonWord) {

                            // generate substring from str1 => i
                            int thisSubsBegin = i - num[i][j] + 1;
                            int thisSubsBegin2 = j - num[i][j] + 1;
                            if (lastSubsBegin == thisSubsBegin) {
                                //if the current LCS is the same as the last time this block ran
                                allinfo.set(0, allinfo.get(0) + " " + String.valueOf(str1.get(i)));
                                allinfo.set(1, String.valueOf(num[i][j]));
                            } else {
                                //this block resets the string builder if a different LCS is found
                                lastSubsBegin = thisSubsBegin;
                                sj = new StringJoiner(" ");
                                //String jj =String.valueOf(i)+"   "+String.valueOf(j)+"   "+ String.valueOf(num[i][j])+"   "+str1.substring(lastSubsBegin, i + 1);
                                for (int ik = lastSubsBegin; ik < i + 1; ik++) {
                                    sj.add(str1.get(ik));
                                }

                                allinfo.set(0, sj.toString());
                                allinfo.set(1, String.valueOf(num[i][j]));
                            }
                        }
                    }
                }
            }
        }

        return allinfo;
    }

    public static String getConcatenatedString(List<String> list) {
        return getConcatenatedString(list, " ");
    }

    public static String getConcatenatedString(List<String> list, String concatenateString) {
        StringJoiner sj = new StringJoiner(concatenateString);
        for (String s:list) {
            sj.add(s);
        }

        return sj.toString();
    }

    private StringProperty longestSubstringList(String str1, String str2) {
        boolean Change = false;
        str1 = str1 + " g";
        str2 = str2 + " h";
        str1 = str1.replaceAll("\\s+", " ");
        str2 = str2.replaceAll("\\s+", " ");
        String indexing1 = "";
        String indexing2 = "";
        //List<String> sb = new ArrayList<String>();
        StringProperty oSP = new StringProperty();

        if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
            return null;

        // ignore case
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        List<String> listOfWords1 = Arrays.asList(str1.split("\\s+"));
        List<String> listOfWords2 = Arrays.asList(str2.split("\\s+"));
        String temp = "";
        // java initializes them already with 0
        int[][] num = new int[listOfWords1.size()][listOfWords2.size()];
        // int maxlen = 0;

        for (int i = 0; i < listOfWords1.size(); i++) {
            for (int j = 0; j < listOfWords2.size(); j++) {
                if (listOfWords1.get(i).equals(listOfWords2.get(j))) {
                    if ((i == 0) || (j == 0)) {
                        num[i][j] = 1;
                    } else {
                        num[i][j] = 1 + num[i - 1][j - 1];
                    }


                    if (num[i][j] >= 3) {
                        Change = true;
                        // maxlen = num[i][j];
                        if (num[i][j] == 3) {
                            if (temp == "") {
                                for (int k = i - 2; k <= i; k++) {
                                    temp += listOfWords1.get(k) + " ";
                                }
                                temp = temp.substring(0, temp.length() - 1);

                                int tempcounting = 0;
                                for (int k = 0; k < i - 2; k++) {
                                    tempcounting += listOfWords1.get(k).length() + 1;
                                }

                                //oSP.setIndexInCopiedFile(tempcounting);
                                //indexing1 =String.valueOf(tempcounting);
                                tempcounting = 0;

                                for (int k = 0; k < j - 2; k++) {
                                    tempcounting += listOfWords2.get(k).length() + 1;
                                }

                                //oSP.setIndexInSource(tempcounting);
                                //indexing2 =String.valueOf(tempcounting);
                            }
                        } else {
                            temp += listOfWords1.get(i) + " ";
                        }
//                        generate substring from str1 => i
//                        int thisSubsBegin = i - num[i][j] + 1;
//                        if(lastSubsBegin == thisSubsBegin)
//                        {
//                            //if the current LCS is the same as the last time this block ran
//                            temp += str1.charAt(i);
//                            temp.append(str1.charAt(i));
//                        }
                    }
                } else {
                    if (Change == true && i != 0 && j != 0) {
                        int h = 0;
                        if (listOfWords1.get(i - 1).equals(listOfWords2.get(j - 1)) && num[i - 1][j - 1] >= 3) {
                            Change = false;
                            int length1 = 0;
                            for (int k = i - num[i - 1][j - 1]; k < i; k++) {
                                length1 += listOfWords1.get(k).length() + 1;
                            }
                            length1 = length1 - 1;
                            //oSP.setLengthInCopiedFile(length1);

                            int length2 = 0;
                            for (int k = j - num[i - 1][j - 1]; k < j; k++) {
                                length2 += listOfWords2.get(k).length() + 1;
                            }

                            length2 = length2 - 1;
                            //oSP.setLengthInSource(length2);
                            //sb.add(indexing1+" "+length+" "+indexing2+" "+length2+" "+temp);
                            temp = "";
                        }
                    }
                }
            }
        }
        return oSP;
    }

    public static void computeSimilarityInText(ProcessingFile processingFile) {
        List<ExtractedDataProperties> oText = processingFile.getExtractedTextData();
        List<SimilarDataProperties> oSimilarData = new ArrayList<SimilarDataProperties>();
        String processingFileText = processingFile.getFileText();
        ExtractedDataProperties extractedDataProperties;
        if (oText != null) {
            double dSimilarity = 0;
            double dThreshold = 0.15;

            for (int nCount = 0; nCount < oText.size(); nCount++) {
                extractedDataProperties = oText.get(nCount);
                computeSimilar(oSimilarData, extractedDataProperties, processingFileText, dThreshold);
            }
        }
        processingFile.setSimilarTextData(oSimilarData);
    }

    public static void computeSimilar(List<SimilarDataProperties> oSimilarData, ExtractedDataProperties extractedDataProperties, String sOriginalFileText, double dThreshold) {
        String sDoc1 = extractedDataProperties.getText();
        double dSimilarity = JaccardSimilarity.ComputeJaccardSimility2(sOriginalFileText, sDoc1);

        if (dSimilarity >= dThreshold) {
            List<List<Double>> simility = new ArrayList<List<Double>>();

            List<String> listOfSentences1 = new ArrayList<String>(Arrays.asList(sOriginalFileText.split("[\n.]", -1)));
            listOfSentences1 = Util.removeEmptyString(listOfSentences1);
            List<String> listOfSentences2 = new ArrayList<String>(Arrays.asList(sDoc1.split("[\n.]", -1)));
            listOfSentences2 = Util.removeEmptyString(listOfSentences2);
            for (int i = 0; i < listOfSentences1.size(); i++) {
                simility.add(new ArrayList<Double>());
                for (int j = 0; j < listOfSentences2.size(); j++) {
                    double temp = JaccardSimilarity.ComputeJaccardSimility2(listOfSentences1.get(i), listOfSentences2.get(j));
                    simility.get(i).add(temp);
                }
            }

            SimilarDataProperties similarDataProperties = new SimilarDataProperties();
            similarDataProperties.setSimilarityValue(dSimilarity);
            similarDataProperties.setText(extractedDataProperties.getText());
            similarDataProperties.setUrl(extractedDataProperties.getUrl());
            oSimilarData.add(similarDataProperties);
        }
    }

    public static String repeatString(String s, int n) {
        return new String(new char[n]).replace("\0", s);
    }

    public static String removeUnrocoginizedCharachter (String s) {
        s = s.trim();
        String replaceMe = Character.toString((char)(65279));
        s = s.replace(replaceMe, "");
        replaceMe = Character.toString((char)(160));
        s = s.replace(replaceMe, " ");
        s = s.trim();
        return s;
    }

}
