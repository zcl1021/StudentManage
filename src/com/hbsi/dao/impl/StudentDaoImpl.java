package com.hbsi.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.Student;
import com.hbsi.bean.User;
import com.hbsi.dao.StudentDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class StudentDaoImpl implements StudentDao {
	Connection conn=null;
	PreparedStatement pstat=null;
	ResultSet rs=null;
  //��д�ӿڷ������Ѳ���student���������ֵ��ӵ����ݱ�
	public boolean addStudent(Student student) {
	  //����һ������ֵ����ʾ��������Ƿ�ɹ�
		boolean flag=false;
		//�������ݿ�
		conn=ConnectionFactory.getConnection();
		//����sql���
		String sql="insert into student values(?,?,?,?,?,?,?,?,?,?)";
		//����Ԥ�������
		try {
			pstat= conn.prepareStatement(sql);
			//��ֵ
			pstat.setInt(1, student.getSid());
			pstat.setString(2, student.getSname());
			pstat.setString(3, student.getGender());
			pstat.setString(4, student.getIdnumber());
			pstat.setString(5, student.getSchool());
			pstat.setString(6, student.getDepartment());
			pstat.setString(7, student.getMajor());
			pstat.setString(8, student.getEducation());
			pstat.setString(9, student.getEntrancedate());
			pstat.setString(10, student.getNativeplace());
			//ִ��������ݲ���
			int i=pstat.executeUpdate();
			if(i>0) {//˵��������ݳɹ�
				flag=true;//����콣���ݳɹ����Ѳ���ֵ��Ϊtrue
					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(pstat, conn);
		}
		return flag;//���ز���ֵflagֵ
	}
	@Override
	public Student lookStudent(int sid) {
		//���������ݿ������
		conn=ConnectionFactory.getConnection();
		//����һ��������ѯ�û��������롢�û������Ƿ���ڵ�sql���
		String sql="select * from student where sid="+sid;
		//����һ��Student�������Գ�ʼ��ΪĬ��ֵ
		Student student = new Student();
		//����Ԥ�������
		try {
			pstat=conn.prepareStatement(sql);
			//ִ�в�ѯ���ؽ����
			rs=pstat.executeQuery();
			//�жϽ�����Ƿ�Ϊ�գ��ӽ��������ȡ����
			if(rs.next()) {
			//���ֶ�����Ϊ����ȡ���ֶ�sid��ֵ������Ϊstudent��������sid��ֵ
				student.setSid(rs.getInt("sid"));
		    //���ֶ���ȡ���ֶ�sname��ֵ
				student.setSname(rs.getString("sname"));
			//��װ
				student.setGender(rs.getString("gender"));
				student.setIdnumber(rs.getString("idnumber"));
				student.setSchool(rs.getString("school"));
				student.setDepartment(rs.getString("department"));
				student.setMajor(rs.getString("major"));
				student.setEducation(rs.getString("education"));
				student.setEntrancedate(rs.getString("entrancedate"));
				student.setNativeplace(rs.getString("nativeplace"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);
		}
		return student;
	}
	//��д������ʵ������ѧ����Ϣ��ѯ
	@Override
	public List<Student> doFindAll() {
		List<Student> studentlist = new ArrayList<Student>();//�����б���󣬳�ʼ��Ϊ�յ��б�
		/*
		 * ͨ����ѯ���ݱ��Ѳ�ѯ��������ѧ�����ɣ�ÿһ����¼��װΪһ��ѧ������
		 */
		conn=ConnectionFactory.getConnection();//�������ݿ�
		try {
			pstat=conn.prepareStatement("select * from student");
		    rs=pstat.executeQuery();//ִ�в�ѯ�����ؽ����
		    while(rs.next()) {//ѭ�����������ÿһ����¼
		    	Student student = new Student();//����Studnet����
		    	student.setSid(rs.getInt("sid"));
		    	student.setSname(rs.getString("sname"));
		    	student.setGender(rs.getString("gender"));
		    	student.setIdnumber(rs.getString("idnumber"));
		    	student.setSchool(rs.getString("school"));
		    	student.setDepartment(rs.getString("department"));
		    	student.setMajor(rs.getString("major"));
		    	student.setEducation(rs.getString("education"));
		    	student.setEntrancedate(rs.getString("entrancedate"));
		    	student.setNativeplace(rs.getString("nativeplace"));
		    	//�ѷ�װ�õ�ѧ��������ӵ��б���
		    	studentlist.add(student);
		    	
		    	
		    	
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);
		}
		return studentlist;//�����б����
	}
	//��д������ɾ��ѧ����Ϣ
	@Override
	public boolean deleteStudent(int sid) {
		boolean flag=false;
		//���������ݿ������
		conn=ConnectionFactory.getConnection();
	    //����һ��������ѯ�û��������롢�û������Ƿ���ڵ�sql���
	  String sql1="delete from student where sid="+sid;
	  String sql2="update user set verify=3 where id="+sid;

	  try {
		pstat=conn.prepareStatement(sql1);
		int i=pstat.executeUpdate();
		if(i>0) {
		pstat=conn.prepareStatement(sql2);
		int j=pstat.executeUpdate();
		if(j>0) {
			flag=true;
		}
		}else {
			flag=false;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		DBClose.close(pstat,conn);
	}
		return flag;
	}
	public int doCount(DoPage dopage) {
		//����һ������count�����ܵļ�¼������ʼ��ֵΪ0��
		int count =0;
		//��ȡ�����ݿ������
		conn = ConnectionFactory.getConnection();
		//����sql���,dopage.getsql()�ǲ�ѯ������where�Ӿ�
		String sql ="select count(*) from student "+dopage.getSql();
		try {
			pstat=conn.prepareStatement(sql);//����Ԥ�������
			rs=pstat.executeQuery();//ִ�в�ѯ�����ؽ����
			if(rs.next()) {//����������Ϊ��
				count= rs.getInt(1);//�ѽ�����л�ȡ��������ȡ������ֵ������count
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);//�ͷ���Դ
		}
		return count;
	}
	/*
	 * ��ȡ��ҳ����һֱ����ʱ�ܼ�¼����ÿҳ��ʾ�ļ�¼��
	 * ������dopage�����count���Ժ�pageSize��������֪��
	 * �������Կ���ʹĬ��ֵ
	 * 
	 */
	public int doTotalPage(DoPage dopage) {
		//�������totalPage��ʾ��ҳ��,��ʼֵΪ0
		int totalPage=0;
		//�������m�����ܼ�¼������ÿҳ��ʾ��¼������
		int m = this.doCount(dopage)/dopage.getPageSize();
		//��¼�ܼ�¼������ÿҳ��ʾ��¼��������
		if(this.doCount(dopage)%dopage.getPageSize()>0) {
			totalPage=m+1;//��ҳ�������ܼ�¼������ÿҳ��ʾ��¼�����̼�1
		}else {
			totalPage=m;
		}
		return totalPage;
	}
	/*
	 *  ��ȡ��ǰҳ��Ҫ��ʾ�ļ�¼�ļ���
	 *  ��֪����ʱ:��ǰ�ǵڼ�ҳ��ÿҳ��ʾ�ļ�¼������ѯ����
	 *  ���ݲ���dopage�����nowPage,pageSize,sql����ִ�в�ѯ�����ؽ����������
	 *  ����������Ϊ�գ���ÿһ����ѯ���ļ�¼��װΪһ��user����
	 *  ��ӵ��б�����У����б��������Ϊdopage�����list������ֵ������list����
	 *  
	 */
	public DoPage doFindAll(DoPage dopage) {
		//����һ��List�������������ѯ����ÿһ����¼��װ�ɵ�user����
		List list = new ArrayList();//�����б�����ʼ��Ϊ��
		//��ȡ���ݿ������
		conn=ConnectionFactory.getConnection();
		//����sql���
		String sql="select * from student "+dopage.getSql()+" limit "
				+(dopage.getNowPage()-1)*dopage.getPageSize()+","+dopage.getPageSize();
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement(sql);
			//ִ�в�ѯ���ؽ����
			rs=pstat.executeQuery();
			
			//��������
			while(rs.next()) {
				//����һ��student�������Գ�ʼ��ΪĬ��
				Student student = new Student();
				//��ѯ�õ���¼��id�ֶε�ֵet
				student.setSid(rs.getInt("sid"));
		        student.setSname(rs.getString("sname"));
		        student.setGender(rs.getString("gender"));
		        student.setSchool(rs.getString("school"));
		        student.setMajor(rs.getString("major"));
		        
				//�ѷ�װ�õ�user������ӵ��б������
				list.add(student);
			}
			//���б��������Ϊdopage������
			dopage.setList(list);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);//�ͷ���Դ
		}
		return dopage;
	}

}
