package com.hbsi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hbsi.bean.Resume;
import com.hbsi.dao.ResumeDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class ResumeDaoImpl implements ResumeDao {

	@Override
	public boolean addResume(Resume resume) {
		Connection conn=null;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		boolean flag = false;
		//�������ݿ�
		conn=ConnectionFactory.getConnection();
	    //����sql���
		String sql="insert into resume values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    //����Ԥ�������
			try {
				pstat= conn.prepareStatement(sql);
				//��ֵ
				pstat.setInt(1, resume.getSid());
				pstat.setString(2, resume.getSname());
			    pstat.setString(3, resume.getGender());
				pstat.setString(4, resume.getBirthdate());
				pstat.setString(5, resume.getNation());
				pstat.setString(6, resume.getPolitics());
				pstat.setString(7, resume.getGraduation());
				pstat.setString(8, resume.getSchool());
				pstat.setString(9, resume.getMajor());
				pstat.setString(10, resume.getEducation());
				pstat.setString(11, resume.getEmail());
				pstat.setString(12, resume.getPhone());
				pstat.setString(13, resume.getForeignlanguage());
				pstat.setString(14, resume.getHobby());
				pstat.setString(15, resume.getPractice());
				pstat.setString(16, resume.getPosition());
				pstat.setString(17, resume.getHonor());
				pstat.setString(18, resume.getResearch());
				pstat.setString(19, resume.getSelfevaluation());
				//ִ��������ݲ���
				int i=pstat.executeUpdate();
					if(i>0) {//˵��������ݳɹ�
					  flag=true;//���������ݳɹ����Ѳ���ֵ��Ϊtrue
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					DBClose.close(pstat, conn);
				}
		return flag;
	}

}
