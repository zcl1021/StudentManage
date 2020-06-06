package com.hbsi.dao;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.User;

public interface UserDao {
   //���巽������ѯ�û����У�user���Ƿ����ض���Ϣ
	User lookUser(User user);
	//���巽������������ӵ�user��
	User addUser(User user);
	//����û����Ƿ����
	boolean checkUsername(String username);
	//�޸�����
	boolean updatePwd(User user);
	//��ȡ�ܼ�¼��
	int doCount(DoPage dopage);
	//��ȡ��ҳ��
	int doTotalPage(DoPage dopage);
	//��ѯ��ǰҳҪ��ʾ������
	DoPage doFindAll(DoPage dopage);
	//���巽������id��ѯ�û�
	User lookUserById(int id);
	//ɾ���û�
	boolean deleteUser(int id);
	//�����û�
	boolean disableUser(int id);
	//�����û����δͨ����
	boolean invalidUser(int id);
	//�����û����ͨ��
	boolean activeUser(int id);
}
