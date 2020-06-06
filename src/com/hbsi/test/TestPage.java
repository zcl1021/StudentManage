package com.hbsi.test;

import java.util.List;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.User;
import com.hbsi.dao.UserDao;
import com.hbsi.dao.impl.UserDaoImpl;

public class TestPage {
    public static void main(String[] args) {
    	//����UserDao����
    	UserDao ud = new UserDaoImpl();
    	//����DoPage��������ֵ��ʼ��ΪĬ��ֵ
    	DoPage dopage = new DoPage();
    	//��ѯuser�������м�¼��
    	dopage.setSql("");
    	int count = ud.doCount(dopage);
    	System.out.print(count);
    	dopage.setPageSize(2);
    	//��ȡ��ҳ��
    	int totalPage = ud.doTotalPage(dopage);
    	System.out.print("��ҳ���ǣ�"+totalPage);
    	//���õ�ǰҳ�ǵ�һҳ
    	dopage.setNowPage(1);
    	//��ѯ��һҳ��Ϣ,��Ϊ������dopage������3������ֵ��sql,pageSize,nowPage����
    	dopage =ud.doFindAll(dopage);
    	System.out.println("��һ���������:");
        showPage(dopage.getList());
    	
    }
    public static void showPage(List<User> list) {
    	for(int i = 0;i<list.size();i++) {
    		User user =list.get(i);
    		String str ="id="+user.getId()+" username= "+user.getUsername()+" password= "+ user.getPassword()+" usertypes="+user.getUsertypes()+" verify="+user.getVerify();
    		System.out.println(str);
    	}
    }
}
