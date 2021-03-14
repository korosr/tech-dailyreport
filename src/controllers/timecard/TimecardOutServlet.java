package controllers.timecard;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.TimeCard;
import util.DBUtil;

/**
 * Servlet implementation class TimecardInServlet
 */
@WebServlet(name = "timecards/out", urlPatterns = { "/timecards/out" })
public class TimecardOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimecardOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager em = DBUtil.createEntityManager();

		int tc_id =  Integer.parseInt(request.getParameter("tc_id"));

		//idからデータ取得
		TimeCard tc = em.find(TimeCard.class, tc_id);
		Date date = new Date();
		//勤怠日時セット
		tc.setOut_time(date);

		// データベースを更新
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        SimpleDateFormat sdf = new SimpleDateFormat("h時m分");
        String time = sdf.format(date);
        request.getSession().setAttribute("flush", "お疲れ様でした！本日は" + time + "に退勤しました！");

		response.sendRedirect(request.getContextPath() + "/index.html");
	}
}