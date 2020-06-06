package com.hbsi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbsi.bean.User;
import com.hbsi.dao.UserDao;
import com.hbsi.dao.impl.UserDaoImpl;


@WebServlet("/LoginServet")
public class LoginServet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public LoginServet() {
        super();
   
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//��ȡ�ͻ����ύ�ı�����
		String name =request.getParameter("username");
		String passwd =request.getParameter("password");
		String usertype =request.getParameter("usertypes");
		//��ȡ�û��ĻỰ����
		HttpSession session = request.getSession();
		//�Ѵ������л�ȡ���û��������롢�û������Ϣ��װΪUser��������ֵ
		User user = new User();
		user.setUsername(name);
		user.setPassword(passwd);
		user.setUsertypes(usertype);
		//��UserDaoʵ���ഴ��UserDao����
		UserDao ud = new UserDaoImpl();
		//���ýӿڶ����lookUser������֤�û�����ֵ�Ƿ���user����
		User u = ud.lookUser(user);
		//������صĶ���u��usertypes����ֵ��error��˵������û���û��������Ϣ
		if(u.getUsertypes().equals("error")) {
			request.setAttribute("errorMsg","�û����������������������");
			//Ȼ������ת�����½ҳ��
			this.gotoPage("public/login.jsp",request,response);
			
		}else {
			if(u.getVerify().equals("1")) {
				request.setAttribute("errorMag","�û�δ�������ϵ����Ա����");
				//Ȼ������ת�����½ҳ��
				this.gotoPage("public/login.jsp",request,response);
			}
			if(u.getVerify().equals("2")) {
				//���û���������Ϊ�Ự����ֵ
				session.setAttribute("user", u);
				if(u.getUsertypes().equals("admin")) {
					//�����½�û��ǹ���Ա���������Ա��ҳ��
					this.gotoPage("admin/index.jsp", request, response);
				
				}
				if(u.getUsertypes().equals("student")) {
					//�����½�û���ѧ�����������Ա��ҳ��
					this.gotoPage("stu/stuIndex.jsp", request, response);
				}
				if(u.getUsertypes().equals("company")) {
					//�����½�û���ҵ���������Ա��ҳ��
					this.gotoPage("company/companyIndex.jsp", request, response);
				}
			}
            if(u.getVerify().equals("3")) {
            	request.setAttribute("errorMsg","�û����δͨ����������ע�ᣬ��ʵ��д��Ϣ");
    			//Ȼ������ת�����½ҳ��
    			this.gotoPage("public/login.jsp",request,response);
			}
			
		}
	}
  //����˽�з�����ʵ������ת��
	private void gotoPage(String url,HttpServletRequest request,HttpServletResponse response)
	  throws ServletException,IOException{
		RequestDispatcher dispatcher=request.getRequestDispatcher(url);
		dispatcher.forward(request,response);
	}
}
