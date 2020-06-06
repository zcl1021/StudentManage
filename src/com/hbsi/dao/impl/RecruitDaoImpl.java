package com.hbsi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hbsi.bean.Company;
import com.hbsi.bean.DoPage;
import com.hbsi.bean.Recruit;
import com.hbsi.dao.RecruitDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class RecruitDaoImpl implements RecruitDao {
	//�����������ݿ�Ľӿڶ���
		Connection conn=null;
		PreparedStatement pstat=null;
		ResultSet rs=null;
	//����Ƹ��Ϣ��ӵ����ݱ�
	public boolean addRecruit(Recruit recruit) {
		boolean flag = false;
		conn=ConnectionFactory.getConnection();
		String sql="insert into recruit (cid,companyname,address,postcode,recruitment,workingplace,positiontype,edurequire,description,branch,linkman,telephone,hostpage,email) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			//����Ԥ�������
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, recruit.getCid());
			pstat.setString(2, recruit.getCompanyname());
			pstat.setString(3, recruit.getAddress());
			pstat.setString(4, recruit.getPostcode());
			pstat.setInt(5, recruit.getRecruitment());
			pstat.setString(6, recruit.getWorkingplace());
			pstat.setString(7, recruit.getPositiontype());
			pstat.setString(8, recruit.getEdurequire());
			pstat.setString(9, recruit.getDescription());
			pstat.setString(10, recruit.getBranch());
			pstat.setString(11, recruit.getLinkman());
			pstat.setString(12, recruit.getTelephone());
			pstat.setString(13, recruit.getHostpage());
			pstat.setString(14, recruit.getEmail());
			
		
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
	@Override
	public int doCount(DoPage dopage) {
		//����һ������count�����ܵļ�¼������ʼ��ֵΪ0��
		int count =0;
		//��ȡ�����ݿ������
		conn = ConnectionFactory.getConnection();
		//����sql���,dopage.getsql()�ǲ�ѯ������where�Ӿ�
		String sql ="select count(*) from recruit"+dopage.getSql();
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
	@Override
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
	@Override
	public DoPage doFindAll(DoPage dopage) {
		//����һ��List�������������ѯ����ÿһ����¼��װ�ɵ�company����
		List list = new ArrayList();//�����б�����ʼ��Ϊ��
		//��ȡ���ݿ������
		conn=ConnectionFactory.getConnection();
		//����sql���
		String sql="select * from recruit "+dopage.getSql()+" limit "
				+(dopage.getNowPage()-1)*dopage.getPageSize()+","+dopage.getPageSize();
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement(sql);
			//ִ�в�ѯ���ؽ����
			rs=pstat.executeQuery();
			
			//��������
			while(rs.next()) {
				//����һ��recruit�������Գ�ʼ��ΪĬ��
				Recruit recruit = new Recruit();
				//��ѯ�õ���¼��id�ֶε�ֵ
				recruit.setRid(rs.getInt("rid"));
			    recruit.setCid(rs.getInt("cid"));
				recruit.setCompanyname(rs.getString("companyname"));
				recruit.setRecruitment(rs.getInt("recruitment"));
				recruit.setWorkingplace(rs.getString("workingplace"));
				recruit.setPositiontype(rs.getString("positiontype"));
				recruit.setEdurequire(rs.getString("edurequire"));
				
				//�ѷ�װ�õ�company������ӵ��б������
				list.add(recruit);
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
	//��ѯ��Ƹ��Ϣ
	public Recruit lookRecruit(int rid) {
		 //���������ݿ������
		conn=ConnectionFactory.getConnection();
		//����һ��sql���
		String sql="select * from recruit where rid="+rid;
		//����һ��Company�������Գ�ʼ��ΪĬ��ֵ
		Recruit recruit = new Recruit();
		//����Ԥ�������
		try {
			pstat=conn.prepareStatement(sql);
			//ִ�в�ѯ���ؽ����
			rs=pstat.executeQuery();
			//�жϽ�����Ƿ�Ϊ�գ��ӽ��������ȡ����
			if(rs.next()) {
			 recruit.setRid(rs.getInt("rid"));
			 recruit.setCid(rs.getInt("cid"));
			 recruit.setCompanyname(rs.getString("companyname"));
			 recruit.setAddress(rs.getString("address"));
			 recruit.setPostcode(rs.getString("postcode"));
			 recruit.setRecruitment(rs.getInt("recruitment"));
			 recruit.setWorkingplace(rs.getString("workingplace"));
			 recruit.setPositiontype(rs.getString("positiontype"));
			 recruit.setEdurequire(rs.getString("edurequire"));
			 recruit.setDescription(rs.getString("description"));
			 recruit.setBranch(rs.getString("branch"));
			 recruit.setLinkman(rs.getString("linkman"));
			 recruit.setTelephone(rs.getString("telephone"));
			 recruit.setHostpage(rs.getString("hostpage"));
			 recruit.setEmail(rs.getString("email"));
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);
		}
		return recruit;
	}
	//ɾ����Ƹ��Ϣ
	public boolean deleteRecruit(int rid) {
		boolean flag=false;
		conn = ConnectionFactory.getConnection();
		String sql="delete from recruit where rid ="+rid;
		try {
			pstat=conn.prepareStatement(sql);
			int i=pstat.executeUpdate();
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
	//�޸���Ƹ��Ϣ
	public boolean updateRecruit(Recruit recruit) {
		// TODO Auto-generated method stub
		return false;
	}


}
