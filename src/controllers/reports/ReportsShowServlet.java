package controllers.reports;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import util.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet(name = "reports/show", urlPatterns = { "/reports/show" })
public class ReportsShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());

        Map<String, Long> reaction_map = new HashMap<>();
        Map<String, List<String>> reaction_user_map = new HashMap<>();

        //リアクション
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        long reaction_count = (long)em.createNamedQuery("getReactionCount", Long.class)
        		.setParameter("report_id", r.getId())
        		.setParameter("employee_id", login_employee.getId())
                .getSingleResult();
        	System.out.println("リアクション" + reaction_count);
        if(reaction_count == 0) {
        	request.setAttribute("good", false);
        }else if(reaction_count == 1){
        	request.setAttribute("good", true);
        }

        em.close();

        for(Map.Entry<String, Long> entry : reaction_map.entrySet()) {
        	request.setAttribute(entry.getKey(), entry.getValue());
        }

        for(Map.Entry<String, List<String>> entry : reaction_user_map.entrySet()) {
        	request.setAttribute(entry.getKey(), entry.getValue());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
	}
}