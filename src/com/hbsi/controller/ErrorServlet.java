package com.hbsi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	     //��ȡ�û��ĻỰ����
			HttpSession session = request.getSession();
			//�ӻỰ������ȡ����װ������
			String username = (String)session.getAttribute("username");
			String usertype = (String)session.getAttribute("usertype");
			//ȡ�������Ե�ֵд���ͻ���
			PrintWriter out = response.getWriter();
			out.append(usertype+"�˺�"+username+"��½��Ϣ��������������");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		doGet(request, response);
	}

}
