/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputData;

import java.util.List;

/**
 *
 * @author Hamza
 */
public class QueryResultsHandler
{
    private String sQuery;

    /**
     * Get the value of sQuery
     *
     * @return the value of sQuery
     */
    public String getsQuery()
    {
        return sQuery;
    }

    /**
     * Set the value of sQuery
     *
     * @param sQuery new value of sQuery
     */
    public void setsQuery(String sQuery)
    {
        this.sQuery = sQuery;
    }

    private List<String> oListOfUrls;

    /**
     * Get the value of oListOfUrls
     *
     * @return the value of oListOfUrls
     */
    public List getoListOfUrls()
    {
        return oListOfUrls;
    }

    /**
     * Set the value of oListOfUrls
     *
     * @param oListOfUrls new value of oListOfUrls
     */
    public void setoListOfUrls(List oListOfUrls)
    {
        this.oListOfUrls = oListOfUrls;
    }
}
