package com.marisareis.dal;

import java.sql.*;

public class ModuloConexao {
    public static Connection conector(){
        
        Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/bd_jogodaforca";
        String user = "root";
        String password = "";
        
        try{
            conexao = DriverManager.getConnection(url, user, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        
        try{
            Class.forName(driver);
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } 
        
        return conexao;
    }
    
}
