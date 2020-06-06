package com.hbsi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hbsi.bean.User;
import com.hbsi.dao.UserDao;
import com.hbsi.dao.impl.UserDaoImpl;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String usertypes = request.getParameter("usertypes");
		//����User����,���Գ�ʼ��ΪĬ��ֵ
		User user =new User();
		//�û�ȡ��������ֵ����Ϊuser���������ֵ
		user.setUsername(username);
        user.setPassword(password);
        user.setUsertypes(usertypes);
        //����UserDao����
        UserDao ud = new UserDaoImpl();
        //��������û��ķ���
        User u = ud.addUser(user);
        if(u.getUsertypes().equals("error")) {//˵��û������û��ɹ�
        	//����һ���������ԣ���ʾ����û�û�гɹ�
        	request.setAttribute("regError", "�û�ע��δ�ɹ���������ע��");
        	//����ע��ҳ��
        	this.gotoPage("public/register.jsp", request, response);
        }else {//����û��ɹ�
        	if(u.getUsertypes().equals("admin")) {//���ע���û��ǹ���Ա
        		//�������з�װ��Ϣ
        		request.setAttribute("errorMsg", "����Ա�û�ע��ɹ�������ϵ����Ա�����û�");
        		//����ת����login.jspҳ��
        		this.gotoPage("public/login.jsp", request, response);
        	}
        	if(u.getUsertypes().equals("student")) {
        		//���û�id��װΪ�������Ե�ֵ
        		request.setAttribute("sid", u.getId());
        		request.setAttribute("errorMsg", "ѧ���û�ע��ɹ�������ϵ����Ա�����û�");
        		//����ת����stu/studentInfo.jspҳ��
        		this.gotoPage("stu/studentinfo.jsp", request, response);
        	}
        	if(u.getUsertypes().equals("company")) {
        		request.setAttribute("cid", u.getId());
        		//����ת����companyinfo.jsp
        		request.setAttribute("errorMsg", "��ҵ�û�ע��ɹ�������ϵ����Ա�����û�");
        		this.gotoPage("company/companyinfo.jsp", request, response);
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
