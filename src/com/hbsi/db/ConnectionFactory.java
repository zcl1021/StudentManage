package com.hbsi.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
   //�����ĸ�˽������
	private static String DRIVER="";
	private static String URL="";
	private static String USERNAME ="";
	private static String PASSWORD="";
	
	//����˽�еĹ��췽������ֹ����������ù��췽���������
	private ConnectionFactory() {}
	//���徲̬��ʼ���飬��������ʱ�����þ�̬��ʼ����
	static {
		getProperties();
	}
	private static void getProperties() {
		//��ȡ��ǰ�������е��̶߳���
		Thread curThread = Thread.currentThread();
		//��ȡ��ǰ�������е��̵߳��������
		ClassLoader loader = curThread.getContextClassLoader();
		//ʹ�����������ȡһ�������ļ���������
		InputStream inStream = loader.getResourceAsStream("jdbc.properties");
		//����һ�����Զ��󣬱����jdbc.properties�ļ���������Ժ����Ե�ֵ
		Properties prop = new Properties();
		try {
			prop.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DRIVER=prop.getProperty("driver");
		URL=prop.getProperty("url");
		USERNAME=prop.getProperty("username");
		PASSWORD =prop.getProperty("password");
	}
	public static Connection getConnection() {
		Connection conn=null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn =DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn; //�������Ӷ���
	}
}
