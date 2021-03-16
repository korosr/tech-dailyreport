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

        //リアクション機能
        List<Integer> reaction_id_list = em.createNamedQuery("getReactions", Integer.class)
                .setParameter("report_id", r.getId())
                .getResultList();

        Map<String, Long> reaction_map = new HashMap<>();
        Map<String, List<String>> reaction_user_map = new HashMap<>();

        for(int reaction_id : reaction_id_list) {
        	//リアクション毎のカウント取得
	        long reaction_count = (long)em.createNamedQuery("getReactionsCount", Long.class)
	        		.setParameter("report_id", r.getId())
	        		.setParameter("reaction_id", reaction_id)
	                .getSingleResult();

	        //リアクションしてくれたユーザを取得
	        List<String> reaction_user_list = em.createNamedQuery("getReactionsUserName", String.class)
	                .setParameter("report_id", r.getId())
	                .setParameter("reaction_id", reaction_id)
	                .getResultList();

	        //各リアクション数取得
	        if(reaction_id == 1) {
	        	reaction_map.put("iine_count", reaction_count);
	        	reaction_user_map.put("iine_users", reaction_user_list);
	        }else if(reaction_id == 2){
	        	reaction_map.put("smile_count", reaction_count);
	        	reaction_user_map.put("smile_users", reaction_user_list);
	        }else if(reaction_id == 3){
	        	reaction_map.put("big_count", reaction_count);
	        	reaction_user_map.put("big_users", reaction_user_list);
	        }else if(reaction_id == 4){
	        	reaction_map.put("sweat_count", reaction_count);
	        	reaction_user_map.put("sweat_users", reaction_user_list);
	        }else if(reaction_id == 5){
	        	reaction_map.put("bad_count", reaction_count);
	        	reaction_user_map.put("bad_users", reaction_user_list);
	        }
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