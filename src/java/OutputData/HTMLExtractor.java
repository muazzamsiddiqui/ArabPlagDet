/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputData;

import Common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Hamza
 */
public class HTMLExtractor
{
    private URL url;
    private InputStream data;
    private String URLString;
    private String ExtractedHTML;

    public HTMLExtractor(String URLString) {
        this.URLString = URLString;
    }

    public String getExtractedHTML() {
        return ExtractedHTML;
    }

    public void executeURLRequest() {
        ExtractedHTML = getURLPage(URLString, Constants.DEFAULT_ENCODING);
    }

    public String getURLPage(String urlPage, String encoding) {

        String text = null;
        try {
            URL url = new URL(urlPage);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", Constants.USER_AGENT);
            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            conn.setConnectTimeout(10000);

            text = readResponse(conn.getInputStream(), encoding);
            if (text == null)
                text = urlPage;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }

    public String readResponse(InputStream is, String encoding) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
        StringBuffer response = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        br.close();

        return response.toString();
    }

}
