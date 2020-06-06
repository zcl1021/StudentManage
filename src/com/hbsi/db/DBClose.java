package com.hbsi.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//�����ر������ͷ���Դ
public class DBClose {
//�رս��������
	private static void close(ResultSet rs) {
		if(rs !=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private static void close(Statement stat) {
		if(stat !=null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private static void close (Connection conn) {
		if(conn !=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	//���幫�з������ر�ִ����ӡ�ɾ�����޸Ĳ����õ���Դ
	public static void close(Statement stat,Connection conn) {
		close(stat);
		close(conn);
	}
	//���幫�з������ر�ִ�в�ѯ�õ�����Դ
	public static void close(ResultSet rs,Statement stat,Connection conn) {
		close(rs);
		close(stat);
		close(conn);
	}
}
