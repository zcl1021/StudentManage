package com.hbsi.dao;

import com.hbsi.bean.Company;
import com.hbsi.bean.DoPage;
import com.hbsi.bean.Recruit;

public interface RecruitDao {
	//���巽��������Ƹ��ӵ����ݱ�recruit
		boolean addRecruit(Recruit recruit);
	//��ȡ�ܼ�¼��
		int doCount(DoPage dopage);
	//��ȡ��ҳ��
		int doTotalPage(DoPage dopage);
	//��ѯ��ǰҳҪ��ʾ������
		DoPage doFindAll(DoPage dopage);
	//���巽����ѯ��Ƹ��Ϣ
		Recruit lookRecruit(int rid);
	//���巽��ɾ����Ƹ��Ϣ
		boolean deleteRecruit(int rid);
	//���巽���޸���Ƹ��Ϣ
		boolean updateRecruit(Recruit recruit);
}
