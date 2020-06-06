package com.hbsi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbsi.bean.Company;
import com.hbsi.bean.DoPage;
import com.hbsi.bean.Recruit;
import com.hbsi.bean.User;
import com.hbsi.dao.RecruitDao;
import com.hbsi.dao.impl.RecruitDaoImpl;



public class RecruitManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		 //��ȡ�������action��ֵ
		String action =request.getParameter("action");
		RecruitDao rd = new RecruitDaoImpl();
		if(action.equals("createRecruit")) {
			int cid=0;
		    cid=Integer.parseInt(request.getParameter("cid"));
			
			String companyname = request.getParameter("companyname");
			String address = request.getParameter("address");
			String postcode = request.getParameter("postcode");
			int recruitment =Integer.parseInt(request.getParameter("recruitment"));
			String workingplace = request.getParameter("workingplace");
			String positiontype = request.getParameter("positiontype");
			String edurequire = request.getParameter("edurequire");
			String description = request.getParameter("description");
			String branch = request.getParameter("branch");
			String linkman = request.getParameter("linkman");
			String telephone = request.getParameter("telephone");
			String hostpage = request.getParameter("hostpage");
			String email = request.getParameter("email");
			Recruit recruit = new Recruit();
			recruit.setCid(cid);
			recruit.setCompanyname(companyname);
			recruit.setAddress(address);
			recruit.setPostcode(postcode);
			recruit.setRecruitment(recruitment);
			recruit.setWorkingplace(workingplace);
			recruit.setPositiontype(positiontype);
			recruit.setEdurequire(edurequire);
			recruit.setDescription(description);
			recruit.setBranch(branch);
			recruit.setLinkman(linkman);
			recruit.setTelephone(telephone);
			recruit.setHostpage(hostpage);
			recruit.setEmail(email);
			boolean flag = rd.addRecruit(recruit);
			if(flag) {
				HttpSession session = request.getSession();
				//�ӶԻ���ȡ��ԭ����½�û�����
				User loginUser=(User)session.getAttribute("user");
				if(loginUser.getUsertypes().equals("admin")) {
				this.gotoPage("/recruitManage?action=recruitlist",request,response);
				}else {
					this.gotoPage("/recruitManage?action=edit",request,response);
				}
				
			}else {
				this.gotoPage("/public/error.jsp",request,response);
			}
			
			
		}
		//������ҵ��Ƹ��Ϣ
		if(action.equals("recruitlist")) {
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
			//String sqlStr = request.getParameter("sql");
			String sqlStr ="";
//			if(sqlStr==null) {//���û�еõ�����sql��ֵ
//				sqlStr="";
//			}else if(sqlStr.equals("1")) {//����õ������������ֵWie1
//				sqlStr="where verify='1'";
//			}else if(sqlStr.equals("2")) {
//				sqlStr="where verify='2'";
//			}else if(sqlStr.equals("3")) {
//				sqlStr="where verify='3'";
//			}else {//����������Ϊ����ֵ
//				sqlStr="";
//			}
			//2.����dopage�����sqlStr��ֵ
			dopage.setSql(sqlStr);
			//3.����dopage�����pageSize������ֵ
			dopage.setPageSize(10);
			//��ȡ�ܵļ�¼��
			int totalCount = rd.doCount(dopage);
			//4.����dopage�����count����ֵ
			dopage.setCount(totalCount);
			//��ȡ��ҳ��
			int totalPage=rd.doTotalPage(dopage);
			//5.����dopage��totalPage����ֵ
			dopage.setTotalPage(totalPage);
			//6.����ǰ�����ã�dopage������4������ֵ����Ϊ����
			dopage=rd.doFindAll(dopage);
			//��6�����Է�װ�õ�dopage��������Ϊ��������
			request.setAttribute("doPage", dopage);
			this.gotoPage("admin/recruitList.jsp", request, response);
		
		
		}
		if(action.equals("show")) {
			//��ȡ�Ự����
			HttpSession session = request.getSession();
			//�ӻỰ������ȡ����½�û�����
			User user=(User)session.getAttribute("user");
			//���½�û�����ҵ����ȡ�û�id��Ϊ��ҵ��cid
//			int cid=0;
//			if(user.getUsertypes().equals("company")) {
//				cid=user.getId();
//			}else {//�����½�û�������ҵ
				//��ȡ��ҵid��ֵ
				int rid=Integer.parseInt(request.getParameter("rid"));
				
//			}
			//������ҵ��cid��ѯ��ҵ�Ļ�����Ϣ
			Recruit recruit = rd.lookRecruit(rid);
			//�Ѳ�ѯ��¼װ�õ�company��������Ϊ��������companyֵ
			request.setAttribute("recruit", recruit);
			//����ת����ҳ��
			this.gotoPage("public/showRecruit.jsp",request,response);
		}
		if(action.equals("edit")) {
			//��ȡ�Ự����
			HttpSession session = request.getSession();
			//�ӻỰ������ȡ����½�û�����
			User user=(User)session.getAttribute("user");
			int cid = user.getId();
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
			//String sqlStr = request.getParameter("sql");
			//if(sqlStr==null) {//���û�еõ�����sql��ֵ
				String sqlStr=" where cid = "+cid;
			//}
			//2.����dopage�����sqlStr��ֵ
			dopage.setSql(sqlStr);
			//3.����dopage�����pageSize������ֵ
			dopage.setPageSize(10);
			//��ȡ�ܵļ�¼��
			int totalCount = rd.doCount(dopage);
			//4.����dopage�����count����ֵ
			dopage.setCount(totalCount);
			//��ȡ��ҳ��
			int totalPage=rd.doTotalPage(dopage);
			//5.����dopage��totalPage����ֵ
			dopage.setTotalPage(totalPage);
			//6.����ǰ�����ã�dopage������4������ֵ����Ϊ����
			dopage=rd.doFindAll(dopage);
			//��6�����Է�װ�õ�dopage��������Ϊ��������
			request.setAttribute("doPage", dopage);
			this.gotoPage("company/CompanyRecruitList.jsp", request, response);
		}
		if(action.equals("delete")) {
			int rid=0;
			String ridStr=request.getParameter("rid");
			try {
				rid=Integer.parseInt(ridStr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean flag =rd.deleteRecruit(rid);
			if(flag) {
				HttpSession session = request.getSession();
				//�ӶԻ���ȡ��ԭ����½�û�����
				User loginUser=(User)session.getAttribute("user");
				if(loginUser.getUsertypes().equals("admin")) {
				this.gotoPage("/recruitManage?action=recruitlist",request,response);
				}else if(loginUser.getUsertypes().equals("company")) {
					this.gotoPage("/recruitManage?action=edit",request,response);
				}else {
					this.gotoPage("/public/success.jsp",request,response);
				}
				
			}else {
				this.gotoPage("/public/error.jsp",request,response);
			}
		}
		if(action.equals("recuit")) {
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
			//String sqlStr = request.getParameter("sql");
			String sqlStr ="";
//			if(sqlStr==null) {//���û�еõ�����sql��ֵ
//				sqlStr="";
//			}else if(sqlStr.equals("1")) {//����õ������������ֵWie1
//				sqlStr="where verify='1'";
//			}else if(sqlStr.equals("2")) {
//				sqlStr="where verify='2'";
//			}else if(sqlStr.equals("3")) {
//				sqlStr="where verify='3'";
//			}else {//����������Ϊ����ֵ
//				sqlStr="";
//			}
			//2.����dopage�����sqlStr��ֵ
			dopage.setSql(sqlStr);
			//3.����dopage�����pageSize������ֵ
			dopage.setPageSize(10);
			//��ȡ�ܵļ�¼��
			int totalCount = rd.doCount(dopage);
			//4.����dopage�����count����ֵ
			dopage.setCount(totalCount);
			//��ȡ��ҳ��
			int totalPage=rd.doTotalPage(dopage);
			//5.����dopage��totalPage����ֵ
			dopage.setTotalPage(totalPage);
			//6.����ǰ�����ã�dopage������4������ֵ����Ϊ����
			dopage=rd.doFindAll(dopage);
			//��6�����Է�װ�õ�dopage��������Ϊ��������
			request.setAttribute("doPage", dopage);
			this.gotoPage("stu/stuRecruitList.jsp", request, response);
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
