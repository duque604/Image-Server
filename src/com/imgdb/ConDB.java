package com.imgdb;

import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ConDB {
	
Connection con = null;
	
	public void estableceConexion(){
		 if (con != null)
	            return;
	        String url = "jdbc:postgresql://localhost:5432/imagendb";
		try {
				Class.forName("org.postgresql.Driver");
				
				con = DriverManager.getConnection(url,"postgres","masterkey");
				
				if(con != null){
					System.out.println("conexion ok");
				}
			} catch (Exception e) {
				System.out.println("problema al establecer conexion a la base de datos");
			}			
	}
	
	public void registrar(String nombre,FileInputStream fis,long length) throws SQLException {
		
		PreparedStatement ps = con.prepareStatement("insert into imagen(nombre,imagen)values(?,?)");
		
		
		try {
			ps.setString(1, nombre);
			ps.setBinaryStream(2,fis,length);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ps.close();
		
     			
	}
	
	public ResultSet buscar() throws SQLException{
		PreparedStatement ps = con.prepareStatement("select imagen from imagen order by codigo desc");
		return  ps.executeQuery();
		             
	}
	
	public ResultSet buscar(int a) throws SQLException{
		PreparedStatement ps = con.prepareStatement("select imagen from imagen order by codigo desc limit "+a);
		return  ps.executeQuery();
		             
	}
	
	public void cerrarConexion(){
		try {
			con.close();
		} catch (Exception e) {
			System.out.println("problema para cerrar la conexion");
		}
	}

}
