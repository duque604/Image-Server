package com.imgdb;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       

    public Servlet() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			  		       
		InputStream is2=null;
		OutputStream os = response.getOutputStream();
		
		boolean esmulti = ServletFileUpload.isMultipartContent(request);
		if(esmulti){
			ServletFileUpload upload = new ServletFileUpload();
			
			try {
				
				FileItemIterator  iter = upload.getItemIterator(request);
				while(iter.hasNext()){
					
					FileItemStream item = (FileItemStream) iter.next();
					if(item.isFormField()){
						String fieldName = item.getFieldName();
						InputStream is = item.openStream();
						byte[] b=new byte[is.available()];
						is.read(b);
						String value = new String(b);
						System.out.println(fieldName+": "+value);						
					}else{
						String path = "C:/Users/Andres/workspace/";
						if(Procf.procesarF(path,item)){
							try {								
								response.setContentType("image/jpeg");
								ConDB db = new ConDB();
								
								db.estableceConexion();								
								ResultSet rs = db.buscar();
								rs.next();
								is2 = rs.getBinaryStream(1);
								int i;
					            while ((i = is2.read()) != -1) {
					                os.write(i);
					            }
					            is2.close();						
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}					
						}else{
							System.out.println("no puede procesar");
						}
					}
				}
				os.flush();
	            os.close();
				
				
			} catch (FileUploadException e) {
				
				e.printStackTrace();
				
			}
		}
		else{
			
			int c = Integer.parseInt(request.getParameter("cantidad"));
			int p = Integer.parseInt(request.getParameter("posicion"));
			response.setContentType("image/jpeg");
								
			ResultSet rs;
			
					
				try {
					ConDB db = new ConDB();		
					db.estableceConexion();
					rs = db.buscar(c);
					InputStream is=null;
					int j = 0;
					
					while(rs.next()){
						
						if(j==p){
						
							is = rs.getBinaryStream(1);
							int i;
				            while ((i = is.read()) != -1) {
				                os.write(i);
				            }
						}
						j+=1;
					}

			        is.close();
			        os.flush();
			        os.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}
	}
}
