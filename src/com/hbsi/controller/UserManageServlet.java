package com.hbsi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.User;
import com.hbsi.dao.UserDao;
import com.hbsi.dao.impl.UserDaoImpl;



public class UserManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	      //����UserDao����
		UserDao ud = new UserDaoImpl();
		String action = request.getParameter("action");
		if(action.equals("disable")) {
			int id=0;//�������id��ʼֵΪ0
			//��ȡ���������id��ֵ
			String idStr= request.getParameter("id");
			try {
				id=Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��idΪʵ�Σ����÷��������û�
			ud.disableUser(id);
			this.gotoPage("userManage?action=list", request, response);
		}
		//ɾ���û�
		if(action.equals("delete")) {
			int id=0;//�������id��ʼֵΪ0
			//��ȡ���������id��ֵ
			String idStr= request.getParameter("id");
			try {
				id=Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��idΪʵ�Σ����÷���ɾ���û�
			ud.deleteUser(id);
			this.gotoPage("userManage?action=list", request, response);
		}
		//���δͨ��
		if(action.equals("invalid")) {
			int id =0;
			String idStr=request.getParameter("id");
			try {
				id=Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ud.invalidUser(id);
			this.gotoPage("userManage?action=list", request, response);
		}
		//���ͨ��
		if(action.equals("active")) {
			int id =0;
			String idStr=request.getParameter("id");
			try {
				id=Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ud.activeUser(id);
			this.gotoPage("userManage?action=list", request, response);
		}
		if(action.equals("list")) {//˵���ǹ����û�����
			//����Dopage���󣬳�ʼ�����������ΪĬ��ֵ
			DoPage dopage = new DoPage();
			//��ȡ��ǰҳ���������ȡ�����еĵ�ǰҳ�룩
			String pageNum=request.getParameter("page");
			int pageNo=0;//�������pageNo��ʾ��ǰ�ǵڼ�ҳ
			if(pageNum==null) {//���û�д�������ܻ�ȡ��page������ֵ
				pageNo=1;
			}else {//����������л�ȡ���˲���page��ֵ
				pageNo=Integer.parseInt(pageNum);//�ѵõ��Ĳ���ֵת��Ϊ�������Ƹ�pageNum
				
			}
			//1.����dopage�����nowpage����ֵΪpageNo
			dopage.setNowPage(pageNo);
			//�������л�ȡ�������sql��ֵ
			String sqlStr = request.getParameter("sql");
			if(sqlStr==null) {//���û�еõ�����sql��ֵ
				sqlStr="";
			}else if(sqlStr.equals("1")) {//����õ������������ֵWie1
				sqlStr=" where verify= 1";
			}else if(sqlStr.equals("2")) {
				sqlStr=" where verify= 2";
			}else if(sqlStr.equals("3")) {
				sqlStr=" where verify= 3";
			}else {//����������Ϊ����ֵ
				sqlStr="";
			}
			//2.����dopage�����sqlStr��ֵ
			dopage.setSql(sqlStr);
			//3.����dopage�����pageSize������ֵ
			dopage.setPageSize(10);
			//��ȡ�ܵļ�¼��
			int totalCount = ud.doCount(dopage);
			//4.����dopage�����count����ֵ
			dopage.setCount(totalCount);
			//��ȡ��ҳ��
			int totalPage=ud.doTotalPage(dopage);
			//5.����dopage��totalPage����ֵ
			dopage.setTotalPage(totalPage);
			//6.����ǰ�����ã�dopage������5������ֵ����Ϊ����
			dopage=ud.doFindAll(dopage);
			//��6�����Է�װ�õ�dopage��������Ϊ��������
			request.setAttribute("doPage", dopage);
			this.gotoPage("admin/listUser.jsp", request, response);
		}
		if(action.equals("update")) {//˵�����޸����������
			//��ȡ�������id��ֵ
			int id=0;
			//��id����ֵת��Ϊ������ֵ��id
			try {
				id=Integer.parseInt(request.getParameter("id"));
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
			//��ȡ�û������������
			String pwd = request.getParameter("password");
			//��Ĭ�Ϲ��췽������user�������Գ�ʼ��Ĭ��ֵ
			User user = new User();
			//�û�ȡ�Ĳ���id��ֵ����Ϊuser�����id����ֵ
			user.setId(id);
			user.setPassword(pwd);
			boolean flag=ud.updatePwd(user);
			if(flag) {//˵���޸ĳɹ�
				//��ȡ�Ự����
				HttpSession session = request.getSession();
				//�ӶԻ���ȡ��ԭ����½�û�����
				User loginUser=(User)session.getAttribute("user");
				if(loginUser.getUsertypes().equals("admin")) {
					this.gotoPage("userManage?action=list", request, response);
				}else {//������ǹ���Ա
					loginUser.setPassword(pwd);
					//�������ûỰ�еĵ�½�û�
					session.setAttribute("user", loginUser);
					this.gotoPage("public/success.jsp", request, response);
				}
				
			}else {
				this.gotoPage("public/error.jsp", request, response);
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}
	 //����˽�з�����ʵ������ת��
		private void gotoPage(String url,HttpServletRequest request,HttpServletResponse response)
		  throws ServletException,IOException{
			RequestDispatcher dispatcher=request.getRequestDispatcher(url);
			dispatcher.forward(request,response);
		}

}
