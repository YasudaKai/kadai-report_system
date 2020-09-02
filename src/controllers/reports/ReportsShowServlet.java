package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
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

        //ここから

        //すでにデータベースにfollowとfollowedの同じ組み合わせが存在しているかチェックする。


        //Reportクラスのemployeeのインスタンスを取得。FollowクラスのEmployee型のインスタンスに格納。
        Employee followed = r.getEmployee();

        //セッションスコープからとってきた、ログインしてる人のインスタンスを取得。
        Employee follow = (Employee)request.getSession().getAttribute("login_employee");




        //フォローされる人(日報の作成者）のインスタンスをセット。それが、今ログインしてる人との組み合わせが
        //データベースに存在しているかチェック。
        long follow_count = (long)em.createNamedQuery("checkFollow", Long.class)
                                     .setParameter("followed", followed)
                                     .setParameter("follow", follow)
                                     .getSingleResult();

        //もしfollow_countに１件でもデータが入っていたら、

        if(follow_count > 0){

        request.setAttribute("follow_count", follow_count);

        }


        //ここまで


        em.close();


        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
