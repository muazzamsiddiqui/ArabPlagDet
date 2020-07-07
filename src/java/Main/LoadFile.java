package Main;

import Common.ProcessingFile;
import OutputData.OutputSentencesStrings;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author Hamza
 */
@WebServlet(name = "LoadFile", urlPatterns = {"/LoadFile"})
@MultipartConfig
public class LoadFile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        ProcessingFile processingFile = new ProcessingFile();

        MainHandler mainHandler = new MainHandler(request, response, processingFile);
        OutputSentencesStrings outputSentencesStrings = mainHandler.processRequest();

        request.setAttribute("DisplayList", outputSentencesStrings);

        request.setAttribute("FileText", processingFile.getFileText());

        request.setAttribute("WordsInFile", processingFile.getNumberOfWords());

        request.setAttribute("WordsPlagiarised", processingFile.getNumberOfWordsPlagiarised());

        float percent = (processingFile.getNumberOfWordsPlagiarised() * 100.0f) / processingFile.getNumberOfWords();

        DecimalFormat df = new DecimalFormat("0.00");

        request.setAttribute("Ratio", df.format(percent));

        String sFileText = processingFile.getFileText();
        request.setAttribute("OutputData", sFileText);

        RequestDispatcher oRD = request.getServletContext().getRequestDispatcher("/JSP/displayResult.jsp");
        oRD.forward(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
