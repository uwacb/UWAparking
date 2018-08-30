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

public class SocketServer_user {

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
                   while (true) {  
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
                       
                       System.out.println("get /：" + temp); 
			
                       String name = msg.substring(1, temp);
                       String pass = msg.substring(temp + 1,msg.length());
                       
                       System.out.println("get name：" + name); 
                       System.out.println("get pass：" + pass); 
                       
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
                       
              			try
              			{
              				conn.close();
              				pst.close();

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
