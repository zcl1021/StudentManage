package com.hbsi.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.User;
import com.hbsi.dao.UserDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class UserDaoImpl implements UserDao {
	Connection conn =null;
	PreparedStatement pstat=null;
	ResultSet rs = null;
	@Override
	public User lookUser(User user) {
		//��ȡ�����ݿ������
		conn=ConnectionFactory.getConnection();
		//����һ��sql���
		String sql="select * from user where username=? and password=? and usertypes=?";
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement(sql);
			//Ϊsql����е�3 ������ֵ
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			pstat.setString(3, user.getUsertypes());
			//ִ�в�ѯ�����ؽ����
			rs=pstat.executeQuery();
			//��������
			if(rs.next()) {
				//�ѽ�������ֶ���Ϊid���ֶ�ֵȡ������Ϊ��������Ϊuser���������id��ֵ
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUsertypes(rs.getString("usertypes"));
				user.setVerify(rs.getString("verify"));
			}else {
				user.setUsertypes("error");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);
		}
		
		return user;
	}
	/*
	 * ���û������������ӵ����ݱ�user
	 * 1.����user������ֻ��
	 * 2. ��������ݳɹ����ٴΰ��ղ���user����֪����������ֵΪ��������ѯ�������ӳɹ������ؼ�¼��5���ֶζ�����ֵ��
	 * �Ѳ�ѯ�õ���5���ֶ�ֵ��װΪһ��User������Ϊ��������ֵ����;
	 * �Ѳ�ѯ�õ���5���ֶ�ֵ��װΪһ��User������Ϊ��������ֵ����;
	 * ���ݷ��ض����usertypes�ȿ���֪����������Ƿ�ɹ�
	 * */
	@Override
	public User addUser(User user) {
		User u=new User();//����User���󣬳�ʼ������ֵ
		conn=ConnectionFactory.getConnection();
		String sql="insert into user(username,password,usertypes,verify) values(?,?,?,?)";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			pstat.setString(3, user.getUsertypes());
			pstat.setString(4, "1");
			int i = pstat.executeUpdate();
				u=this.lookUser(user);
				
				
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(pstat, conn);
		}
		return u;
	}
	public boolean checkUsername(String username) {
		boolean flag=false;
		//����û��ṩ���û��������ݿ����Ƿ���ڣ��������flag=true,��������ڣ�flag=false
		conn=ConnectionFactory.getConnection();//��ȡ�����ݿ������
		String sql="select * from user where username=?";
		try{
			pstat = conn.prepareStatement(sql);//����Ԥ�������
			pstat.setString(1,username);//�ò���username��ֵ����Ϊsql����е�һ������ֵ
			rs= pstat.executeQuery();//ִ�в�ѯ�����ؽ��������
			if(rs.next()){//����������Ϊ�գ�˵���û������ݿ��д���
				flag = true;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			DBClose.close(rs,pstat,conn);
		}
		return flag;
	}
	@Override
	public boolean updatePwd(User user) {
		boolean flag=false;
		//��ȡ�����ݿ������
		conn=ConnectionFactory.getConnection();
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement("update user set password=? where id=?");
		    //�ò���user�����password����Ϊ��һ������ֵ
			pstat.setString(1, user.getPassword());
			//Ϊ�ڶ����ʺŸ�ֵ
			
			pstat.setInt(2, user.getId());
			//ִ���޸��������
			int i = pstat.executeUpdate();
			if(i>0) {//i>0˵����Ӱ����������0
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
	/*
	 * ��ȡ�ܵļ�¼�� ����֪�����ǲ�ѯ�ı�Ͳ�ѯ������
	 * ���Բ�����dopage���������ֻ��sql��������ֵ�ģ�
	 * ����������Ĭ��ֵ����������0���ַ���ʱnull
	 */
	public int doCount(DoPage dopage) {
		//����һ������count�����ܵļ�¼������ʼ��ֵΪ0��
		int count =0;
		//��ȡ�����ݿ������
		conn = ConnectionFactory.getConnection();
		//����sql���,dopage.getsql()�ǲ�ѯ������where�Ӿ�
		String sql ="select count(*) from user "+dopage.getSql();
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
		String sql="select * from user "+dopage.getSql()+" limit "
				+(dopage.getNowPage()-1)*dopage.getPageSize()+","+dopage.getPageSize();
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement(sql);
			//ִ�в�ѯ���ؽ����
			rs=pstat.executeQuery();
			
			//��������
			while(rs.next()) {
				//����һ��user�������Գ�ʼ��ΪĬ��
				User user = new User();
				//��ѯ�õ���¼��id�ֶε�ֵ
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUsertypes(rs.getString("usertypes"));
				user.setVerify(rs.getString("verify"));
				//�ѷ�װ�õ�user������ӵ��б������
				list.add(user);
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
	//����id��ѯ�û�
	public User lookUserById(int id) {
		//�����û�User���󣬳�ʼ����������ΪĬ��ֵ
		User user = new User();
		//��ȡ���ݿ������
		conn = ConnectionFactory.getConnection();
		//����sql���
		String sql="select * from user where id="+id;
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement(sql);
			//ִ�в�ѯ�����ؽ��������
			rs=pstat.executeQuery();
				//����������Ϊ��
				if(rs.next()) {
					//�ý����id�ֶ�ֵ����Ϊuser����id����ֵ
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setUsertypes(rs.getString("usertypes"));
					user.setVerify(rs.getString("verify"));
				}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(rs, pstat, conn);
		}
		
		return user;//����user����
	}
	@Override
	public boolean deleteUser(int id) {
		//����һ��������������ʼ��Ϊfalse
		boolean flag = false;
		//����id��ѯҪɾ�����û�����
		User user = this.lookUserById(id);
		//��ȡ�����ݿ������
		conn = ConnectionFactory.getConnection();
		try {
			if(user.getUsertypes().equals("admin")) {
				//����ǹ���Ա
				//����Ԥ������󣬴�user����ɾ���û�
				pstat = conn.prepareStatement("delete from user where id="+id);
				int i = pstat.executeUpdate();//ִ��ɾ��������������Ӱ�������
				if(i>0) {	//�����Ӱ�����������0��˵��ɾ���ɹ�
					flag=true;
				}
			}
			if(user.getUsertypes().equals("student")) {
				//�����ѧ��
				//����Ԥ������󣬴�user����ɾ���û�
				pstat = conn.prepareStatement("delete from user where id="+id);
				int i = pstat.executeUpdate();//ִ��ɾ��������������Ӱ�������
				if(i>0) {	//�����Ӱ�����������0��˵��ɾ���ɹ�
					flag=true;
				}
				pstat = conn.prepareStatement("delete from student where sid="+id);//ɾ��ѧ��������Ϣ
				pstat.executeUpdate();
				pstat = conn.prepareStatement("delete from resume where sid="+id);//�Ӽ�������ɾ����Ϣ
				pstat.executeUpdate();
				pstat = conn.prepareStatement("delete from recruitresume where sid="+id);//��ӦƸ��������ɾ����Ϣ
				pstat.executeUpdate();
				pstat = conn.prepareStatement("delete from message where sid="+id);//��������Ϣ����ɾ����Ϣ
				pstat.executeUpdate();
			}
			if(user.getUsertypes().equals("company")) {
				//�������ҵ
				//����Ԥ������󣬴�user����ɾ���û�
				pstat = conn.prepareStatement("delete from user where id="+id);
				int i = pstat.executeUpdate();//ִ��ɾ��������������Ӱ�������
				if(i>0) {	//�����Ӱ�����������0��˵��ɾ���ɹ�
					flag=true;
				}
				pstat = conn.prepareStatement("delete from company where cid="+id);//ɾ����ҵ������Ϣ
				pstat.executeUpdate();
				pstat = conn.prepareStatement("delete from recruit where cid="+id);//����Ƹ����ɾ����Ϣ
				pstat.executeUpdate();
				pstat = conn.prepareStatement("delete from recruitresume where cid="+id);//��ӦƸ��������ɾ����Ϣ
				pstat.executeUpdate();
				pstat = conn.prepareStatement("delete from message where cid="+id);//��������Ϣ����ɾ����Ϣ
				pstat.executeUpdate();
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			DBClose.close(pstat, conn);
		}
		
		return flag;
	}

	//�����û�
	public boolean disableUser(int id) {
		boolean flag = false;
		conn = ConnectionFactory.getConnection();
		try {
			pstat= conn.prepareStatement("update user set verify='1'where id ="+id);
		    int i =pstat.executeUpdate();//�޸Ĳ���
		    if(i>0) {
		    	flag=true;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close( pstat, conn);
		}
		
		return flag;
	}
	//����id�����û����δͨ��
	public boolean invalidUser(int id) {
		boolean flag = false;
		conn = ConnectionFactory.getConnection();
		try {
			pstat= conn.prepareStatement("update user set verify='3'where id ="+id);
		    int i =pstat.executeUpdate();//�޸Ĳ���
		    if(i>0) {
		    	flag=true;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close( pstat, conn);
		}
		return flag;
	}
	//����id�����û����ͨ��
	public boolean activeUser(int id) {
		boolean flag = false;
		conn = ConnectionFactory.getConnection();
		try {
			pstat= conn.prepareStatement("update user set verify='2'where id ="+id);
		    int i =pstat.executeUpdate();//�޸Ĳ���
		    if(i>0) {
		    	flag=true;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close( pstat, conn);
		}
		return flag;
	}

}
