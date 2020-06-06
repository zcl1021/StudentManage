package com.hbsi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.Student;
import com.hbsi.bean.User;
import com.hbsi.dao.StudentDao;
import com.hbsi.dao.impl.StudentDaoImpl;



public class StudentManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	  //��ȡ�������action��ֵ
		String action =request.getParameter("action");
		StudentDao sd=new StudentDaoImpl();
		if(action.equals("studentlist")) {
			//List<Student> students= sd.doFindAll();
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
//			String sqlStr = request.getParameter("sql");
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
			String sqlStr ="";
			//2.����dopage�����sqlStr��ֵ
			dopage.setSql(sqlStr);
			//3.����dopage�����pageSize������ֵ
			dopage.setPageSize(10);
			//��ȡ�ܵļ�¼��
			int totalCount = sd.doCount(dopage);
			//4.����dopage�����count����ֵ
			dopage.setCount(totalCount);
			//��ȡ��ҳ��
			int totalPage=sd.doTotalPage(dopage);
			//5.����dopage��totalPage����ֵ
			dopage.setTotalPage(totalPage);
			//6.����ǰ�����ã�dopage������4������ֵ����Ϊ����
			dopage=sd.doFindAll(dopage);
			//��6�����Է�װ�õ�dopage��������Ϊ��������
			request.setAttribute("doPage", dopage);
			//request.setAttribute("list", students);
			this.gotoPage("admin/studentList.jsp", request, response);
		}
		if(action.equals("show")) {//˵���û�������󵼺����Ӳ鿴���˻�����Ϣ
			//��ȡ�Ự����
			HttpSession session = request.getSession();
			//�ӻỰ������ȡ����½�û�����
			User user=(User)session.getAttribute("user");
			//�����½�û���ѧ������ȡ�û�id��Ϊѧ����sid
			int sid=0;
			if(user.getUsertypes().equals("student")) {
				sid=user.getId();
			}else {//�����½�û�����ѧ��
				//��ȡѧ��id��ֵ
				sid=Integer.parseInt(request.getParameter("sid"));
				
			}
			//����ѧ����sid��ѯѧ���Ļ�����Ϣ
			Student student =sd.lookStudent(sid);
			//�Ѳ�ѯ��¼װ�õ�student��������Ϊ��������studentֵ
			request.setAttribute("studnet", student);
			//����ת����stu/showStudent.jspҳ��
			this.gotoPage("stu/showStudent.jsp",request,response);
		}
		//���action��ֵ�ǡ�sturegister��˵����Դ��studentinfo.jspҳ��
		if(action.equals("sturegister")) {
			String id =request.getParameter("sid");
			int sid=0;
			sid=Integer.parseInt(id);
			String sname=request.getParameter("sname");
			String gender = request.getParameter("gender");
			String idnumber= request.getParameter("idnumber");
			String school = request.getParameter("school");
			String department =request.getParameter("department");
			String major= request.getParameter("major");
			String education = request.getParameter("education");
			String entrancedate=request.getParameter("entrancedate");
			String nativeplace = request.getParameter("nativeplace");
			//����һ��student����
			Student student = new Student();//���Գ�ʼ��Ĭ��ֵ
			//ʹ���û����ύ��sidֵ����Ϊstudent����sid����ֵ
			student.setSid(sid);
			//ʹ���û����ύ��sname��ֵ����Ϊstudent����sname����ֵ
			student.setSname(sname);
			student.setGender(gender);
			student.setIdnumber(idnumber);
			student.setSchool(school);
			student.setDepartment(department);
			student.setMajor(major);
			student.setEducation(education);
			student.setEntrancedate(entrancedate);
			student.setNativeplace(nativeplace);
			//����Dao������ӣ��������
			boolean flag=sd.addStudent(student);
			if(flag) {
				request.setAttribute("errorMsg", "ѧ���û�ע��ɹ�������ϵ����Ա����");
				this.gotoPage("public/login.jsp", request, response);
			}else {
				request.setAttribute("sid", student.getSid());
				this.gotoPage("stu/studentinfo.jsp", request, response);
			}
		}   
		    //ɾ��ѧ����Ϣ
		    if(action.equals("delete")) {
			
			int sid =0;
			String sidStr=request.getParameter("sid");
			try {
				sid=Integer.parseInt(sidStr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sd.deleteStudent(sid);
			this.gotoPage("studentManage?action=studentlist", request, response);
		}
		//����ѧ����sidɾ��ѧ���Ļ�����Ϣ
		//Boolean flag =sd.deleteStudent(sid);
		//.gotoPage("stu/studentinfo.jsp", request, response);
	
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
