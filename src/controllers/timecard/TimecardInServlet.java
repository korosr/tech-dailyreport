package controllers.timecard;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.TimeCard;
import util.DBUtil;

/**
 * Servlet implementation class TimecardInServlet
 */
@WebServlet(name = "timecards/in", urlPatterns = { "/timecards/in" })
public class TimecardInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimecardInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager em = DBUtil.createEntityManager();

		Employee e = (Employee) request.getSession().getAttribute("login_employee");
		//ログインユーザのID
		int loginUserId = e.getId();

		Date date = new Date();
		TimeCard tc = new TimeCard();
		tc.setIn_time(date);
		tc.setEmployee_id(loginUserId);

		em.getTransaction().begin();
        em.persist(tc);
        em.getTransaction().commit();

        response.sendRedirect(request.getContextPath() + "/index.html");
	}
}