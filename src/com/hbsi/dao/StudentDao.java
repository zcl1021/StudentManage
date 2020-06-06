package com.hbsi.dao;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.Student;
import java.util.List;

public interface StudentDao {
//���巽������ѧ����Ϣ��ӵ����ݱ�Student
	boolean addStudent(Student student);
//���巽����ѯѧ��������Ϣ
	Student lookStudent(int sid);
//���巽����ѯ����ѧ����Ϣ
	List<Student> doFindAll();
//���巽��ɾ��ѧ����Ϣ
	boolean deleteStudent(int sid);
//��ȡ�ܼ�¼��
	int doCount(DoPage dopage);
//��ȡ��ҳ��
	int doTotalPage(DoPage dopage);
//��ѯ��ǰҳҪ��ʾ������
	DoPage doFindAll(DoPage dopage);
}
