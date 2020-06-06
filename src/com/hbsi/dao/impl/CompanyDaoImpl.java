package com.hbsi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hbsi.bean.Company;
import com.hbsi.bean.DoPage;
import com.hbsi.bean.Student;
import com.hbsi.bean.User;
import com.hbsi.dao.CompanyDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class CompanyDaoImpl implements CompanyDao {
	Connection conn=null;
	PreparedStatement pstat=null;
	ResultSet rs=null;
	@Override
	public boolean addCompany(Company company) {
		//����һ������ֵ����ʾ��������Ƿ�ɹ�
		boolean flag=false;
		//�������ݿ�
		conn=ConnectionFactory.getConnection();
	    //����sql���
		String sql="insert into company values(?,?,?,?,?,?,?,?,?,?,?,?)";
	    //����Ԥ�������
			try {
				pstat= conn.prepareStatement(sql);
				//��ֵ
				pstat.setInt(1, company.getCid());
				pstat.setString(2, company.getCompanyname());
			    pstat.setString(3, company.getUnitproperty());
				pstat.setString(4, company.getLicensenumber());
				pstat.setString(5, company.getIndustry());
				pstat.setString(6, company.getUnitscale());
				pstat.setString(7, company.getAddress());
				pstat.setString(8, company.getWebaddress());
				pstat.setString(9, company.getLinkman());
				pstat.setString(10, company.getTelephone());
				pstat.setString(11, company.getEmail());
				pstat.setString(12, company.getPostcode());
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
				return flag;//���ز���ֵflagֵ
	}
	@Override
	public int doCount(DoPage dopage) {
		//����һ������count�����ܵļ�¼������ʼ��ֵΪ0��
		int count =0;
		//��ȡ�����ݿ������
		conn = ConnectionFactory.getConnection();
		//����sql���,dopage.getsql()�ǲ�ѯ������where�Ӿ�
		String sql ="select count(*) from company"+dopage.getSql();
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
				String sql="select * from company "+dopage.getSql()+" limit "
						+(dopage.getNowPage()-1)*dopage.getPageSize()+","+dopage.getPageSize();
				try {
					//����Ԥ�������
					pstat=conn.prepareStatement(sql);
					//ִ�в�ѯ���ؽ����
					rs=pstat.executeQuery();
					
					//��������
					while(rs.next()) {
						//����һ��company�������Գ�ʼ��ΪĬ��
					    Company company = new Company();
						//��ѯ�õ���¼��id�ֶε�ֵ
					    company.setCid(rs.getInt("cid"));
						company.setCompanyname(rs.getString("companyname"));
						company.setUnitproperty(rs.getString("unitproperty"));
						company.setLicensenumber(rs.getString("licensenumber"));
						company.setIndustry(rs.getString("industry"));
						
						//�ѷ�װ�õ�company������ӵ��б������
						list.add(company);
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
	@Override
	public Company lookCompany(int cid) {
		       //���������ݿ������
				conn=ConnectionFactory.getConnection();
				//����һ��������ѯ�û��������롢�û������Ƿ���ڵ�sql���
				String sql="select * from company where cid="+cid;
				//����һ��Company�������Գ�ʼ��ΪĬ��ֵ
				Company company = new Company();
				//����Ԥ�������
				try {
					pstat=conn.prepareStatement(sql);
					//ִ�в�ѯ���ؽ����
					rs=pstat.executeQuery();
					//�жϽ�����Ƿ�Ϊ�գ��ӽ��������ȡ����
					if(rs.next()) {
					 company.setCid(rs.getInt("cid"));
				     company.setCompanyname(rs.getString("companyname"));
				     company.setUnitproperty(rs.getString("unitproperty"));
					 company.setLicensenumber(rs.getString("licensenumber"));
					 company.setIndustry(rs.getString("industry"));
					 company.setUnitscale(rs.getString("unitscale"));
					 company.setAddress(rs.getString("address"));
					 company.setWebaddress(rs.getString("webaddress"));
					 company.setLinkman(rs.getString("linkman"));
					 company.setTelephone(rs.getString("telephone"));
					 company.setEmail(rs.getString("email"));
					 company.setPostcode(rs.getString("postcode"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					DBClose.close(rs,pstat,conn);
				}
				return company;
	}
	//ɾ����ҵ��Ϣ
	public boolean deleteCompany(int cid) {
	boolean flag = false;
	conn=ConnectionFactory.getConnection();
	//����һ��������ѯ�û��������롢�û������Ƿ���ڵ�sql���
	String sql="delete from user where id="+cid;
	try {
		pstat=conn.prepareStatement(sql);
		int i=pstat.executeUpdate();
		if(i>0) {
			flag=true;
		}
		pstat = conn.prepareStatement("delete from company where cid="+cid);//ɾ����ҵ������Ϣ
		pstat.executeUpdate();
		pstat = conn.prepareStatement("delete from recruit where cid="+cid);//����Ƹ����ɾ����Ϣ
		pstat.executeUpdate();
		pstat = conn.prepareStatement("delete from recruitresume where cid="+cid);//��ӦƸ��������ɾ����Ϣ
		pstat.executeUpdate();
		pstat = conn.prepareStatement("delete from message where cid="+cid);//��������Ϣ����ɾ����Ϣ
		pstat.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		DBClose.close(pstat, conn);
	}
	
	return flag;
	}
	//�޸���ҵ��Ϣ
	public boolean updateCompany(Company company) {
		boolean flag=false;
		//��ȡ�����ݿ������
		conn=ConnectionFactory.getConnection();
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement("update company set companyname=?,unitproperty=?,licensenumber=?,industry=?,unitscale=?,address=?,webaddress=?,linkman=?,telephone=?,email=?,postcode=? where cid=?");
		    pstat.setString(1, company.getCompanyname());
		    pstat.setString(2, company.getUnitproperty());
		    pstat.setString(3, company.getLicensenumber());
		    pstat.setString(4, company.getUnitscale());
		    pstat.setString(5, company.getIndustry());
		    pstat.setString(6, company.getAddress());
		    pstat.setString(7, company.getWebaddress());
		    pstat.setString(8, company.getLinkman());
		    pstat.setString(9, company.getTelephone());
		    pstat.setString(10, company.getEmail());
		    pstat.setString(11, company.getPostcode());
		    pstat.setInt(12, company.getCid());
			//ִ���޸Ĳ���
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

}