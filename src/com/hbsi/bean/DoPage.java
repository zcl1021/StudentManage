package com.hbsi.bean;

import java.util.List;

/*
 * ����Bean���װ��ҳ����
 */
public class DoPage {
     private int count;//�ܼ�¼��
     private int pageSize;//ÿҳ��ʾ������
     private int totalPage;//��ҳ��
     private int nowPage;//��ǰ�ǵڼ�ҳ
     private List list;//������װ��ǰҳҪ��ʾ�ļ�¼
     private String sql;//�߼���ѯʱ�õ����Ӳ�ѯ����
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
     
}
