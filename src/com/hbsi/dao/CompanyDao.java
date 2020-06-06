package com.hbsi.dao;

import com.hbsi.bean.Company;
import com.hbsi.bean.DoPage;
import com.hbsi.bean.Student;
import com.hbsi.bean.User;

public interface CompanyDao {
	//���巽��������ҵ��Ϣ��ӵ����ݱ�company
		boolean addCompany(Company company);
	//��ȡ�ܼ�¼��
		int doCount(DoPage dopage);
	//��ȡ��ҳ��
		int doTotalPage(DoPage dopage);
	//��ѯ��ǰҳҪ��ʾ������
		DoPage doFindAll(DoPage dopage);
	//���巽����ѯ��ҵ������Ϣ
		Company lookCompany(int cid);
	//���巽��ɾ����ҵ��Ϣ
		boolean deleteCompany(int cid);
	//���巽���޸���ҵ��Ϣ
		boolean updateCompany(Company company);
}
