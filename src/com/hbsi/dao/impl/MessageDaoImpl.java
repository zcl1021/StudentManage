package com.hbsi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hbsi.bean.Message;
import com.hbsi.dao.MessageDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class MessageDaoImpl implements MessageDao {
    //�����������ݿ�Ľӿڶ���
	Connection conn=null;
	PreparedStatement pstat=null;
	ResultSet rs=null;
	@Override
	public boolean addMessage(Message message) {
		boolean flag=false;
		
		conn=ConnectionFactory.getConnection();
		String sql="insert into message(id,username,title,msgtime,content) values(?,?,?,?,?)";
		try {
			//����Ԥ�������
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1,message.getId());
			pstat.setString(2, message.getUsername());
			pstat.setString(3, message.getTitle());
			pstat.setString(4, message.getMsgtime());
			pstat.setString(5, message.getContent());
			//ִ����Ӳ���
			int i = pstat.executeUpdate();
			if(i>0) {
				flag=true;
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
