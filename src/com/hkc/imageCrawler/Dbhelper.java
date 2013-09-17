package com.hkc.imageCrawler;
import java.sql.*;
import java.util.*;

/**
 * 数据库操作类
 * @author hkc
 *
 */
public class Dbhelper{
    //取得数据库连接
    private  static Connection getConnection(){
    	Connection con=null;
    	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
//    	InputStream fileInputStream=Dbhelper.class.getClassLoader().getResourceAsStream("sql.properties");
//		Properties pp=new Properties();
    	String url="jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=d:\\my.accdb";
//    	String user = "root";
//    	String password="111";
		try {
//			pp.load(fileInputStream);
			Class.forName(driver);
			con=DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return con;
    }
    //针对任何实体的查询方法的封装
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList excuteQuery(String sql,Object[] parameters){
    	ArrayList arrayList=new ArrayList();
    	PreparedStatement preparedStatement=null;
    	ResultSet resultSet=null;
    	Connection connection=null;
        try {
        	connection=getConnection();
			preparedStatement=connection.prepareStatement(sql);
			if (parameters!=null&&parameters.length>0) {
				for (int i = 0; i < parameters.length; i++) {
					preparedStatement.setObject(i+1,parameters[i]);
				}
			}
			resultSet=preparedStatement.executeQuery();
			
			ResultSetMetaData rsmd=resultSet.getMetaData();
			int cloumnnum= 	rsmd.getColumnCount();
			while (resultSet.next()) {
               Object objects[]=new Object[cloumnnum];				
			   for (int i = 0; i < cloumnnum; i++) {
				objects[i]=resultSet.getObject(i+1);
			   }
			   arrayList.add(objects);
			   }
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			if (resultSet!=null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					resultSet=null;
				}
			}
			if (preparedStatement!=null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					preparedStatement=null;
				}
			}
			if (connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					connection=null;
				}
			}
		}     
    	return arrayList;
    }
    //对任何实体的增加，删除，修改操作方法的封装
    public int excuteSql(String sql,Object parameters[]) {
    	Connection conn=getConnection();
    	PreparedStatement ps=null;
    	int result=0;
    	try {
			ps=conn.prepareStatement(sql);
			if (parameters!=null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setObject(i+1, parameters[i]);
				}
			}
			result=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (parameters!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    	
    	return result;
    }
}
