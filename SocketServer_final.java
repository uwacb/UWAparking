

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SocketServer_sqltest {
	
	public static String url = "jdbc:mysql://localhost:3306/uwaparking"; // ip address:dbname
	public static String driver = "com.mysql.jdbc.Driver";  
	public static String user = "root";  // db user
	public static String password = "";   // db password

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
    	
        System.out.println("123456");
        startService();
    }
    
    
    /**  
    * 启动服务监听，等待客户端连接 
    */  
   private static void startService() {  
       try {  
           // 创建ServerSocket  
           ServerSocket serverSocket = new ServerSocket(9999);  
           System.out.println("--start server, waiting at port 9999--");  
 
           // 监听端口，等待客户端连接  
           while (true) {  
               System.out.println("--wait for client--");  
               Socket socket = serverSocket.accept(); //等待客户端连接  
               System.out.println("client connected：" + socket);  
                 
               startReader(socket);  
           }  
 
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
   }  
 
   /**  
    * 从参数的Socket里获取最新的消息 
    */  
   private static void startReader(final Socket socket) {  
 
       new Thread(){  
           @SuppressWarnings("deprecation")
           @Override  
           public void run() {  
               DataInputStream reader;  
               DataOutputStream out;
               
               try {  
                   reader = new DataInputStream( socket.getInputStream());  
                   out = new DataOutputStream(socket.getOutputStream());
                   while (true) 
                   {  
                       System.out.println("*wait for client input*");  
                       String msg = reader.readUTF();  
                       System.out.println("get client information：" + msg); 
                       
                       int temp = 0;
                       
                       for (int i=0;i<msg.length();i++)
                       {
                    	   if (msg.charAt(i) == '/')
                    	   {
                    		   temp = i;
                    	   }
                       }
                       String name = msg.substring(1, temp);
                       String pass = msg.substring(temp + 1,msg.length());
                       
                       Connection conn = null;
                       PreparedStatement pst = null;
                       ResultSet rs = null;
                       
                       
                       if (msg.charAt(0)=='r')
                       {
                    	   try
                    	   {
	                    	   Class.forName(driver);
	                    	   conn = DriverManager.getConnection(url, user, password);
	                    	   String sql = "insert into user_test(name,password) values(?,?)";
	                    	   pst = conn.prepareStatement(sql);
	                    	   pst.setString(1, name);
	                    	   pst.setString(2, pass);
	                    	   pst.executeUpdate();
                    	   }
                    	   catch(Exception e)
                    	   {	
                    		   	e.printStackTrace();
                    	   }
                       	}
                       	else if (msg.charAt(0)=='l')
                       	{
                     	   try
                     	   {
 	                    	   Class.forName(driver);
 	                    	   conn = DriverManager.getConnection(url, user, password);
 	                    	   String sql = "select password from user_test where name = ?";
 	                    	   pst = conn.prepareStatement(sql);
 	                    	   pst.setString(1, name);
 	                    	   rs = pst.executeQuery();
 	                    	   String pass_temp = "";
 	                    	   while (rs.next())
 	                    	   {
 	                    		   pass_temp = rs.getString(1);
 	                    	   }
 	                    	   
 	                    	   System.out.println(pass_temp);
 	                    	   if (pass_temp.equals(pass))
 	                    	   {
 	                    		  out.writeDouble(1.0);
 	                    	   }
 	                    	   else 
 	                    	   {
 	                    		  out.writeDouble(0.0);
 	                    	   }
 	                    	   
                     	   }
                     	   catch(Exception e)
                     	   {
                     		   e.printStackTrace();
                     	   }
                      
                       	}
                       	else 
                       	{
                            Double lat = Double.parseDouble(name);
                            Double lng = Double.parseDouble(pass);
                            int id = 0;
                            
                            
                            if ( lat<=(-31.979192) && lat>=(-31.978872) && lng<=(115.820110) && lng>=(115.818399)){
                         	   	System.out.println("you are now in Reidpark, send 6.0");
                         	   	id = 1;
                            }
                            else if(lat>=(-31.984) && lat<=(-31.982) && lng<=(115.818) && lng>=(115.816))
                            {
                         	   	System.out.println("you are now in Oaklawn park, send 5.0"); 	   
                        		id = 2;
                            }
                            //else if(lat>=(-31.981313) && lat<=(-31.979265) && lng<=(115.820231) && lng>=(115.820051))
                            //{
                            //	  System.out.println("you are now in Oaklawn park, send 5.0");
                            //     out.writeDouble(5.0);
                            //}
                            else if(lat>=(-31.985531) && lat<=(-31.985147) && lng<=(115.821585) && lng>=(115.820168)){
                         	   System.out.println("you are now in Business park, send 4.0");
                         	   id = 3;
                            }else if(lat>=(-31.977451) && lat<=(-31.977131) && lng<=(115.825889) && lng>=(115.816277)){
                         	   System.out.println("you are now in Csse park, send 3.0");
                         	   id = 4;
                            }else if(lat>=(-31.976332) && lat<=(-31.975558) && lng<=(115.820026) && lng>=(115.818764)){
                         	   System.out.println("you are now in Recreation park, send 2.0");
                         	   id = 5;
                            }else if(lat>=(-31.981828) && lat<=(-31.981022) && lng<=(115.818034) && lng>=(115.817325)){
                         	   System.out.println("you are now in ECM park, send 1.0");
                         	   id = 6;
                            }else{
                         	   System.out.println("you are not in fencing area, send 0.0");
                         	   id = 0;
                            }
                            
                            if (id != 0)
                            {
                       			try
                       			{
                       				Class.forName(driver);
                       				conn = DriverManager.getConnection(url, user, password);
                       				String sql = "select rest_area from parking_status where id = ?";
                       				pst = conn.prepareStatement(sql);
                       				pst.setInt(1, id);
                       				rs = pst.executeQuery();
                           			while (rs.next())
                           			{
                           				temp = rs.getInt(1);
                           			}
                           			if (msg.charAt(0)=='i')
                           			{
                           				temp = temp - 1;
                           			}
                           			else if (msg.charAt(0)=='o')
                           			{
                           				temp = temp + 1;
                           			}
                           			
                       				sql = "update parking_status set rest_area = ? where id = ?";
                       				pst = conn.prepareStatement(sql);
                       				pst.setInt(1, temp);
                       				pst.setInt(2, id);
                       				pst.executeUpdate();
                       			}
                       			catch (Exception e)
                       			{
                       				e.printStackTrace();	
                       			}
                            }                          
                       	}
                       
              			try
              			{
              				conn.close();
              				pst.close();
              				rs.close();
              			}
              			catch (SQLException e)
              			{
              				e.printStackTrace();
              			}                 
                   }
               } catch (IOException e) {  
                   e.printStackTrace();  
               }  
           }  
       }.start();  
   }  

}
