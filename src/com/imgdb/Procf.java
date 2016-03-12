package com.imgdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.commons.fileupload.FileItemStream;



public class Procf {
	public static boolean procesarF(String path,FileItemStream item) {
		

		try {
			File f = new File(path+File.separator+"Images");			
			File guardar = new File(f.getAbsolutePath()+File.separator+item.getName());
			FileOutputStream fos = new FileOutputStream(guardar);			
			InputStream is = item.openStream();			
			int x = 0;
			byte[] b=new byte[1024];
			while((x=is.read(b))!=-1){ 
				fos.write(b,0,x);// b son los datos , 0 sin desplazamiento  y x el numero de bytes a escribir
			}
			fos.flush();
			fos.close();
			
			FileInputStream fis = new FileInputStream(guardar);		
			ConDB db = new ConDB();
			db.estableceConexion();
			db.registrar(item.getName(), fis, guardar.length());
			db.cerrarConexion();
			
			return true;
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
