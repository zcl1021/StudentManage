package com.hbsi.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class CheckCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1.ȡ�������еĲ���code��ֵ�����ͻ����������֤�룩
		String checkcode = request.getParameter("code");
		//2.ȡ��������������֤��ʱ�����ڻỰ�е���֤��
		HttpSession session = request.getSession();//��ȡ�Ự����
		String sessioncode = String.valueOf(session.getAttribute("code"));//��ȡ�Ự�����з�װ��code����ֵ
		//3.�Ƚ��û��������֤��ͻỰ�з�װ����֤���Ƿ���ͬ
		if(checkcode.equals(sessioncode)) {
			//�����ͬ��˵���û��������֤������ȷ�ģ�����ת����
			this.gotoPage("test5", request, response);
		}else {
			//����û��������֤�벻��ȷ������������������codeMsg,ֵΪ����֤�����
			request.setAttribute("codeMsg", "��֤���������������");
			//����ת����login.jspҳ��
			this.gotoPage("public/login.jsp", request, response);
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
