package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.ReactionManage;
import util.DBUtil;

/**
 * Servlet implementation class ReportsReactionServlet
 */
@WebServlet(name = "reports/reaction", urlPatterns = { "/reports/reaction" })
public class ReportsReactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsReactionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager em = DBUtil.createEntityManager();

		//レポートID取得
		int reportId = Integer.parseInt(request.getParameter("report_id"));

		//リアクション取得
		String good = request.getParameter("good_btn");

		//ログインユーザ取得
		Employee e = (Employee) request.getSession().getAttribute("login_employee");
		int loginUserId = e.getId();

		if("on".equals(good)) {
			ReactionManage r = new ReactionManage();
			r.setEmployee_id(loginUserId);
			r.setReport_id(reportId);
			em.getTransaction().begin();
			em.persist(r);
			em.getTransaction().commit();
		}else if("off".equals(good)){
			em.getTransaction().begin();
			em.createNamedQuery("deleteReaction")
			.setParameter("report_id", reportId)
			.setParameter("employee_id", loginUserId)
			.executeUpdate();
			em.getTransaction().commit();
		}

		em.close();
        response.sendRedirect(request.getContextPath() + "/reports/show?id=" + Integer.toString(reportId) );
	}
}