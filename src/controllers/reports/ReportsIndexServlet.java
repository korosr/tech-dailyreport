package controllers.reports;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet(name = "reports/index", urlPatterns = { "/reports/index" })
public class ReportsIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		//すべての従業員の日報取得
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }


        List<Report> reports = em.createNamedQuery("getAllReports", Report.class)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        long reports_count = (long)em.createNamedQuery("getReportsCount", Long.class)
                                     .getSingleResult();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);


        //フォローした従業員の日報取得
        int page_follow;
        try{
            page_follow = Integer.parseInt(request.getParameter("page_follow"));
        } catch(Exception e) {
            page_follow = 1;
        }
        //ログインユーザ情報取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        //ログインユーザがフォローしているユーザの従業員IDを取得

        //ログインユーザを基にフォローしているユーザのレポートを取得
        Query query = em.createNamedQuery("getFollowedReports");
        query.setParameter(1, login_employee.getId())
        .setFirstResult(15 * (page - 1))
        .setMaxResults(15);
        List<Report> reports_follow = query.getResultList();

        query = em.createNamedQuery("getCountFollowedReports").setParameter(1, login_employee.getId());

        Long reports_count_follow =((BigInteger) query.getSingleResult()).longValue();

        em.close();

        request.setAttribute("reports_follow", reports_follow);
        request.setAttribute("reports_count_follow", reports_count_follow);
        request.setAttribute("page_follow", page_follow);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
	}
}