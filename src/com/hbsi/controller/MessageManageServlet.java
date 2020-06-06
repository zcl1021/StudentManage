package com.hbsi.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hbsi.bean.Message;
import com.hbsi.dao.MessageDao;
import com.hbsi.dao.impl.MessageDaoImpl;


public class MessageManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 //��ȡ�������action��ֵ
		String action =request.getParameter("action");
		MessageDao md = new MessageDaoImpl();
		if(action.equals("add")) {
			String idStr=request.getParameter("id");
			int id = Integer.parseInt(idStr);
			String username = request.getParameter("username");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			Date d =new Date();//��ȡ��ǰ���ں�ʱ��
			DateFormat df = DateFormat.getDateTimeInstance();//��ȡ���ں�ʱ��ĸ�ʽ��������
			String time =df.format(d);
			Message message = new Message();//����message���󣬲���ʼ��
			message.setId(id);
			message.setUsername(username);
			message.setContent(content);
			message.setMsgtime(time);
			message.setTitle(title);
			//����Dao������ӣ��������
			boolean flag=md.addMessage(message);
			if(flag) {
				this.gotoPage("public/success.jsp", request, response);
			}else {
				this.gotoPage("public/error.jsp", request, response);
			}
			
			
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	 //����˽�з�����ʵ������ת��
	private void gotoPage(String url,HttpServletRequest request,HttpServletResponse response)
	  throws ServletException,IOException{
		RequestDispatcher dispatcher=request.getRequestDispatcher(url);
		dispatcher.forward(request,response);
	}
}
