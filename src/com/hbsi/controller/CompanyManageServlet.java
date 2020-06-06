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
import com.hbsi.bean.Student;
import com.hbsi.bean.User;
import com.hbsi.dao.CompanyDao;
import com.hbsi.dao.impl.CompanyDaoImpl;


public class CompanyManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		  //��ȡ�������action��ֵ
			String action =request.getParameter("action");
			CompanyDao cd = new CompanyDaoImpl();
			//���action��ֵ�ǡ�comregister��˵����Դ��companyinfo.jspҳ��
			if(action.equals("comregister")) {
				String id =request.getParameter("cid");
				int cid=0;
				cid=Integer.parseInt(id);
				String companyname =request.getParameter("companyname");
				String unitproperty=request.getParameter("unitproperty");
				String licensenumber = request.getParameter("licensenumber");
				String industry= request.getParameter("industry");
				String unitscale = request.getParameter("unitscale");
				String address =request.getParameter("address");
				String webaddress= request.getParameter("webaddress");
				String linkman = request.getParameter("linkman");
				String telephone=request.getParameter("telephone");
				String email= request.getParameter("email");
				String postcode = request.getParameter("postcode");
				//����һ��company����
				Company company = new Company();//���Գ�ʼ��Ĭ��
				company.setCid(cid);
			    company.setCompanyname(companyname);
			    company.setUnitproperty(unitproperty);
			    company.setLicensenumber(licensenumber);
			    company.setIndustry(industry);
			    company.setUnitscale(unitscale);
			    company.setAddress(address);
			    company.setWebaddress(webaddress);
			    company.setLinkman(linkman);
			    company.setTelephone(telephone);
			    company.setEmail(email);
			    company.setPostcode(postcode);

				//����Dao������ӣ��������
				boolean flag=cd.addCompany(company);
				if(flag) {
					request.setAttribute("errorMsg", "��ҵ�û�ע��ɹ�������ϵ����Ա����");
					this.gotoPage("public/login.jsp", request, response);
				}else {
					request.setAttribute("cid", company.getCid());
					this.gotoPage("company/companyinfo.jsp", request, response);
				}
			}
			//������ҵ��Ϣ
			if(action.equals("companylist")) {
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
//				String sqlStr = request.getParameter("sql");
//				if(sqlStr==null) {//���û�еõ�����sql��ֵ
//					sqlStr="";
//				}else if(sqlStr.equals("1")) {//����õ������������ֵWie1
//					sqlStr="where verify='1'";
//				}else if(sqlStr.equals("2")) {
//					sqlStr="where verify='2'";
//				}else if(sqlStr.equals("3")) {
//					sqlStr="where verify='3'";
//				}else {//����������Ϊ����ֵ
//					sqlStr="";
//				}
				String sqlStr ="";
				//2.����dopage�����sqlStr��ֵ
				dopage.setSql(sqlStr);
				//3.����dopage�����pageSize������ֵ
				dopage.setPageSize(10);
				//��ȡ�ܵļ�¼��
				int totalCount = cd.doCount(dopage);
				//4.����dopage�����count����ֵ
				dopage.setCount(totalCount);
				//��ȡ��ҳ��
				int totalPage=cd.doTotalPage(dopage);
				//5.����dopage��totalPage����ֵ
				dopage.setTotalPage(totalPage);
				//6.����ǰ�����ã�dopage������4������ֵ����Ϊ����
				dopage=cd.doFindAll(dopage);
				//��6�����Է�װ�õ�dopage��������Ϊ��������
				request.setAttribute("doPage", dopage);
				this.gotoPage("admin/companyList.jsp", request, response);
			}
			if(action.equals("show")) {
				//��ȡ�Ự����
				HttpSession session = request.getSession();
				//�ӻỰ������ȡ����½�û�����
				User user=(User)session.getAttribute("user");
				//���½�û�����ҵ����ȡ�û�id��Ϊ��ҵ��cid
				int cid=0;
				if(user.getUsertypes().equals("company")) {
					cid=user.getId();
				}else {//�����½�û�������ҵ
					//��ȡ��ҵid��ֵ
					cid=Integer.parseInt(request.getParameter("cid"));
					
				}
				//������ҵ��cid��ѯ��ҵ�Ļ�����Ϣ
				Company company = cd.lookCompany(cid);
				//�Ѳ�ѯ��¼װ�õ�company��������Ϊ��������companyֵ
				request.setAttribute("company", company);
				//����ת����company/showCompany.jspҳ��
				this.gotoPage("company/showCompany.jsp",request,response);
			}
			if(action.equals("delete")) {
				int cid =0;
				String cidStr=request.getParameter("cid");
				try {
					cid=Integer.parseInt(cidStr);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cd.deleteCompany(cid);
				this.gotoPage("companyManage?action=companylist", request, response);
			}
			if(action.equals("update")) {
				//��ȡ�Ự����
				HttpSession session = request.getSession();
				//�ӻỰ������ȡ����½�û�����
				User user=(User)session.getAttribute("user");
				//���½�û�����ҵ����ȡ�û�id��Ϊ��ҵ��cid
				int cid=0;
				if(user.getUsertypes().equals("company")) {
					cid=user.getId();
				}
				//������ҵ��cid��ѯ��ҵ�Ļ�����Ϣ
				Company company = cd.lookCompany(cid);
				//�Ѳ�ѯ��¼װ�õ�company��������Ϊ��������companyֵ
				request.setAttribute("company", company);
				//����ת����company/showCompany.jspҳ��
				this.gotoPage("company/updateCompany.jsp",request,response);
			}
			if(action.equals("updateCompany")) {
				//��ȡ�������cid��ֵ
				int cid=0;
				//��cid����ֵת��Ϊ������ֵ��cid
				try {
					 cid=Integer.parseInt(request.getParameter("cid"));
					System.out.print(cid);
					
				}catch(NumberFormatException e) {
					e.printStackTrace();
				}
				String companyname = request.getParameter("companyname");
				String unitproperty = request.getParameter("unitproperty");
				String licensenumber = request.getParameter("licensenumber");
				String industry = request.getParameter("industry");
				String unitscale = request.getParameter("unitscale");
				String address = request.getParameter("address");
				String webaddress = request.getParameter("webaddress");
				String linkman = request.getParameter("linkman");
				String telephone = request.getParameter("telephone");
				String email = request.getParameter("email");
				String postcode = request.getParameter("postcode");
				Company company  = new Company();
				company.setCid(cid);
				company.setCompanyname(companyname);
				company.setUnitproperty(unitproperty);
				company.setLicensenumber(licensenumber);
				company.setIndustry(industry);
				company.setUnitscale(unitscale);
				company.setAddress(address);
				company.setWebaddress(webaddress);
				company.setLinkman(linkman);
				company.setTelephone(telephone);
				company.setEmail(email);
				company.setPostcode(postcode);
				boolean flag = cd.updateCompany(company);
				if(flag) {
					request.setAttribute("company", company);
					this.gotoPage("/company/updateCompany.jsp",request,response);
				}else {
					this.gotoPage("/public/error.jsp",request,response);
				}
			}
			if(action.equals("create")) {
				//��ȡ�Ự����
				HttpSession session = request.getSession();
				//�ӻỰ������ȡ����½�û�����
				User user=(User)session.getAttribute("user");
				//���½�û�����ҵ����ȡ�û�id��Ϊ��ҵ��cid
				int cid=0;
				if(user.getUsertypes().equals("company")) {
					cid=user.getId();
				}else {//�����½�û�������ҵ
					//��ȡ��ҵid��ֵ
					cid=Integer.parseInt(request.getParameter("cid"));
				}
				//������ҵ��cid��ѯ��ҵ�Ļ�����Ϣ
				Company company = cd.lookCompany(cid);
				//�Ѳ�ѯ��¼װ�õ�company��������Ϊ��������companyֵ
				request.setAttribute("company", company);
				//����ת����company/showCompany.jspҳ��
				this.gotoPage("public/createRecruit.jsp",request,response);
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
