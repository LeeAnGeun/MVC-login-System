package bbs;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;
import dto.BbsDto;

public class BbsController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		System.out.println("BbsController doProcess");
		
		String param = req.getParameter("param");
		
		// 게시물 목록으로 이동
		if(param.equals("bbslist")) {
			resp.sendRedirect("bbslist.jsp");
		}
		
		// 게시물로 이동
		if(param.equals("detail")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			System.out.println(seq);
			
			req.setAttribute("seq", seq);
			req.getRequestDispatcher("bbsdetail.jsp").forward(req, resp);
		}
		
		// 게시물 삭제
		if(param.equals("delete")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			System.out.println(seq);
			
			BbsDao dao = BbsDao.getInstance();
			dao.removeBbs(seq);
			
			resp.sendRedirect("bbslist.jsp");
		}
		
		// 게시물 수정 작성
		if(param.equals("update")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			System.out.println(seq);
			
			req.setAttribute("seq", seq);
			req.getRequestDispatcher("updatebbs.jsp").forward(req, resp);
		}
		
		// 게시물 수정 저장
		if(param.equals("updateAf")) {
			int seq = Integer.parseInt( req.getParameter("seq"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			System.out.println(seq + " " + title + " " + content);			
			
			BbsDao dao = BbsDao.getInstance();
			dao.updateBbs(seq, title, content);
			resp.sendRedirect("bbslist.jsp");
		}
		
		// 댓글 작성
		if(param.equals("answer")) {
			int seq = Integer.parseInt( req.getParameter("seq"));
			
			req.setAttribute("seq", seq);
			req.getRequestDispatcher("answer.jsp").forward(req, resp);
		}
		
		// 댓글 저장
		if(param.equals("answerAf")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			BbsDao dao = BbsDao.getInstance();
			dao.answer(seq, new BbsDto(id, title, content));
			resp.sendRedirect("bbslist.jsp");	
		}
		
		// 글쓰기
		if(param.equals("bbswrite")) {
			resp.sendRedirect("bbswrite.jsp");
		}
		
		// 글쓰기 저장
		if(param.equals("bbswriteAf")) {
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			System.out.println(id + title + content);
			BbsDao dao = BbsDao.getInstance();
			dao.addBbslist(new BbsDto(id, title, content));
			
			resp.sendRedirect("bbslist.jsp");
		}
	}
	
}
