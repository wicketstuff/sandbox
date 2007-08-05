package org.wicketstuff.pickwick.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.wicket.request.RequestParameters;



public class ReceiverServlet extends HttpServlet {
    
    private String uploadDir = "/Users/doume/uploads/";
    private String file = "";
    private String currentDirectory = "";
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // add connection closed header
        response.setHeader("Connection","close");        
        // Create PrintWriter object to write to
        PrintWriter out = response.getWriter();
        uploadDir = request.getParameter("uploadDir");
        try {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            
            
            // Parse the request, we got the list of items present in POST header
            List items = upload.parseRequest(request);
            
            for (int i = 0; i < items.size(); i++) {
                DiskFileItem item = (DiskFileItem) items.get(i);
                // As we are interested not in regular form fields, we filter only files
                if (!item.isFormField()) {
                    // the name of file to save
                    String fileName = item.getName();
                    //Split file name and directory name when this is specified
                    splitFileFromDirectory(fileName);
                    //creates the directory
                    createDirectory(this.currentDirectory);
                    // concat the default directory, the specified directory and the file name 
                    String completeName = (this.currentDirectory.equals("")?
                            uploadDir + this.file:
                                uploadDir + this.currentDirectory +"\\"+ this.file);
                    
                    // save file
                    File uploadedFile = new File(completeName);
                    item.write(uploadedFile);
                    //if succeeded, print out the "success" code to notify applet the file was uploaded 
                    out.print("RESP.100");
                    //flush the stream to speed up applet notification
                    out.flush();
                }
            }
        } catch (FileUploadException e) {
            //if failed for some reason, print out the "failed" code to notify applet the file wasn't uploaded 
            out.print("RESP.200");
            // flush the stream to speed up applet notification
            out.flush();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Separates the file name and the directory name if the directory is specified
     * 
     * @param fileName The file name which is sent from the client
     * 
     */
    private void splitFileFromDirectory(String fileName){
        this.currentDirectory = "";
        this.file ="";
        String[] data = fileName.split("\\\\");
        if(data.length!=1){
            int index = fileName.lastIndexOf("\\");
            this.file = fileName.substring(index+1, fileName.length());
            this.currentDirectory = fileName.substring(0, index);
        }
        else{
            this.file = fileName;
        }
        
    }
    
    /**
     * Creates a new directory name if it does not exist
     * 
     * @param directory The name of the directory
     */
    private void createDirectory(String directory){
        String newDirectory = uploadDir + directory;
        File directoryFile = new File(newDirectory);
        if(!directoryFile.isDirectory()){
            directoryFile.mkdirs();
        }
    }
}