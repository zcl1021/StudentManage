package com.hbsi.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


public class EncodingFilter implements Filter {

    public EncodingFilter() {
        
    }

	
	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
	    //�������������ַ�������utf-8
		request.setCharacterEncoding("utf-8");
		//����д�����ݵ��ַ�������utf-8
//		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//���������Ӧ��ת������һ����Դ
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
