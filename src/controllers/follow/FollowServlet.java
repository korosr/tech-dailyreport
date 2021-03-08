package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Relationship;
import util.DBUtil;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet(name = "employees/follow", urlPatterns = { "/employees/follow" })
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager em = DBUtil.createEntityManager();

		//フォローしたい人のID
        int followedId = Integer.parseInt(request.getParameter("empId"));
        int relationId = 0;
        if(request.getParameter("relationId") != null) {
        	relationId = Integer.parseInt(request.getParameter("relationId"));
        }
        String follow = request.getParameter("follow");
		Employee e = (Employee) request.getSession().getAttribute("login_employee");
		//ログインユーザのID
		int loginUserId = e.getId();

		if("true".equals(follow)) {
	        Relationship r = new Relationship();
	        r.setFollowed_id(followedId);
	        r.setFollower_id(loginUserId);

	        em.getTransaction().begin();
	        em.persist(r);
	        em.getTransaction().commit();
	        e = em.find(Employee.class, followedId);
	        request.getSession().setAttribute("flush", e.getName() + "さんをフォローしました。");
		} else {
			String name = em.find(Employee.class, followedId).getName();
			em.getTransaction().begin();
			em.createNamedQuery("deleteRelationship")
			.setParameter("id", relationId)
			.executeUpdate();
			em.getTransaction().commit();

			request.getSession().setAttribute("flush", name + "さんのフォローを解除しました。");
		}
        em.close();
        response.sendRedirect(request.getContextPath() + "/employees/index");
	}
}