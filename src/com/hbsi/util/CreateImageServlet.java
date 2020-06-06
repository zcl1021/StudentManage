package com.hbsi.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CreateImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
   //����һ��������������ȡĳһ��ɫ��Χ�ڵ������ɫ
	Color getRandColor(int fc, int bc) {
		Random random = new Random();//�����ȡ����������ѡ��������
	    if(fc>255) {
	    	fc=255;
	    }
	    if(bc>255) {
	    	bc=255;
	    }
	    //r��ʾ��ɫ����ֵ��random.nextInt(bc-fc)�õ���ֵ��0��bc-fc֮������������������bc-fc��ֵ
	    
	    int r=fc+random.nextInt(bc-fc);
	    int g=fc+random.nextInt(bc-fc);//��ɫ����
	    int b=fc+random.nextInt(bc-fc);//��ɫ����
	    //ʹ�ú�����3����ɫ��������һ����ɫ����
	    Color color = new Color(r,g,b);
	    return color;//���ش����õ���ɫ����
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1.������ͻ���д��Ӧ�����������
		response.setContentType("image/jpeg");
		//����ҳ�治���û���
		response.setHeader("Paragma", "No-cache");//��Ӧ��Ϣ���ܻ���
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//2.��ʼ����̬ͼƬ
		//d������֤��ͼƬ��С
		int width=60;
		int height=20;
		//�����ܹ����ڴ����޸ĵ�ͼƬ����
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//��ȡ����ͼƬ��Granphics����(����)
		Graphics g = image.getGraphics();
		//������ͼƬ
		g.setColor(getRandColor(200,250));//���û�����ɫ
		g.fillRect(0, 0, width, height);//������ͼƬ
		//��������
		g.setColor(getRandColor(160,200));//���ø����ߵ���ɫ
		//����һ������������������ø����ߵ���ʼ������
		Random r =new Random();
		for(int i=0;i<100;i++) {//��100��������
			//ȡ0��widthֵ֮�����һ�������
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			//���ø����ߵĽ���������
			int x1=r.nextInt(12);
			int y1=r.nextInt(12);
			//��������
			g.drawLine(x, y, x+x1, y+y1);
			
			
		}
		//�������֤���ַ�
		String codestr="";//�����ַ���������������֤�룬���ûỰ����ֵ
		for(int i=0;i<4;i++) {//ͨ��ѭ������4���ַ�
			String rand="";
			rand= String.valueOf(r.nextInt(10));//���ȥ0��10֮�����
			//����������ɫ
			g.setColor(getRandColor(20,130));
			//�����ַ�
			g.drawString(rand, 13*i+6, 16);
			//ÿһ��ѭ���ѻ����ַ����ӵ�codestr��
			codestr+=rand;
		}
		HttpSession session =request.getSession();
		session.setAttribute("code", codestr);//����֤���ַ�������Ϊ�Ự����code��ֵ
		//4.�ѻ���ͼƬ������ͻ���
		ImageIO.write(image, "JPEG", response.getOutputStream());
		//5.��ջ��棬�ͷ���Դ
		response.getOutputStream().flush();
		response.getOutputStream().close();
		response.flushBuffer();
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		doGet(request, response);
	}

}
