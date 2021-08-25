package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/pool")
public class DemoServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			//5.1 在web 项目的META-INF 中存放context.xml,在context.xml 编写数据库连接池相关属性
			//5.2 把项目发布到tomcat 中,数据库连接池产生了
			//6 可以在java 中使用jndi 获取数据库连接池中对象
			//6.1 Context:上下文接口.专门获取context.xml文件对象的一个接口
			//6.2 代码,下面3行是JNDI代码:
			Context c = new InitialContext();
			DataSource s =(DataSource) c.lookup("java:comp/env/test");// test为数据库连接池名字
			Connection conn = s.getConnection();
			
			PreparedStatement ps= conn.prepareStatement("select * from flower");
			ResultSet rs = ps.executeQuery();
			
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			while(rs.next()) {
				out.print(rs.getInt(1)+"&nbsp&nbsp&nbsp"+rs.getString(2)+"&nbsp&nbsp&nbsp"+rs.getDouble(3)+"&nbsp&nbsp&nbsp"+rs.getString(4)+"<br/>");
			}
			out.flush();
			out.close();
			rs.close();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 

}
