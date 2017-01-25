//
package br.banco.bancoDeDados;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
  
public class Conexao {  
      
    public static Connection conexao;  
    static String url = "jdbc:mysql://sql5.freemysqlhosting.net:3306/sql597954";  
    static String user = "sql597954";  
    static String pass = "yY6%yF4!";  
  
       public Conexao(){    
             
       }  
    
        public static Connection getConexao(){  
  
            try{  
           
               Class.forName("org.gjt.mm.mysql.Driver");  
            
               conexao = DriverManager.getConnection(url,user,pass);  
            
               //System.out.println("Conexao realizada com sucesso.");  
                 
             }  
           
             catch(SQLException ex){  
              System.out.println("Problemas na conexao com o banco de dados."+ex);  
                
             }  
  
             catch(ClassNotFoundException ex){  
               System.out.println("Driver JDBC-ODBC nao encontrado: "+ ex);   
             }  
      
             return conexao;  
          
        }  
  
        public static void Fecha(){  
            
               //System.out.println("Conexao finalizada com sucesso");  
          
        }  
}  