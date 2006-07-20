package wicket.contrib.jooreports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import wicket.Page;
import wicket.WicketRuntimeException;
import wicket.markup.MarkupStream;
import wicket.model.IModel;
import wicket.protocol.http.WebResponse;

import net.sf.jooreports.converter.DocumentConverter;
import net.sf.jooreports.openoffice.connection.OpenOfficeConnection;
import net.sf.jooreports.openoffice.connection.SocketOpenOfficeConnection;
import net.sf.jooreports.openoffice.converter.OpenOfficeDocumentConverter;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.ZippedDocumentTemplate;


/**
 * @author Jordi Deu-Pons (http://www.jordeu.net)
 *
 * Page class that that generates a report from an OpenOffice document and 
 * the page data Model. It uses jooreports (http://jooreports.sourceforge.net) 
 * library to generate the document and convert it with OpenOffice (working as a
 * service) into one of the posible openoffice export formats. (pdf, word, excel...)
 *  
 */
public abstract class OOPage extends Page 
{	

	// Temporal files
	private File templateFile = null;  
	private File inputFile = null;
	private File outputFile = null;
	
	// Automatic Table filter
	private boolean automaticTables = false;  // By default is disabled.
	
	/**
	 *  Default constructor
	 */
	protected OOPage() {
		super();
	}
	
	/**
	 * Default constructor, with the model.
	 * 
	 * @param model
	 */
	protected OOPage(wicket.model.IModel model) {
		super(model);
	}
	
	
	/**
	 * Constructor for Bookmarkable pages.
	 * 
	 * @param params
	 */
	protected OOPage(wicket.PageParameters params) {
		this((IModel)null);
	}
	
	/* 
	 * Configure the HTTP Headear.
	 * 
	 * @see wicket.Page#configureResponse()
	 */
	protected void configureResponse()
	{
		final String encoding = getRequestCycle().getApplication().getRequestCycleSettings().getResponseRequestEncoding();
		getResponse().setContentType(getContentType()+"; charset=" + encoding);
		((WebResponse)getResponse()).getHttpServletResponse().addHeader("Content-Disposition", "attachment; filename="+getFileName());
		((WebResponse)getResponse()).getHttpServletResponse().addHeader("Content-Transfer-Encoding", "binary");
	}	
		
	
	/* 
	 * Renders and convert the report with jooreport. 
	 * And returns it to the web client.
	 * 
	 */
	protected void onRender(final MarkupStream markupStream) {
		byte[] buf = new byte[1024];
        int len;
        
        //TODO Avoid the convertion and the temporal file creation if there is nothing to convert.  
        String filename = getFileName();
		String extension = filename.substring(filename.lastIndexOf(".")+1, filename.length());
		
		inputFile = new File(getTempDir()+"/wicket_odfpage_in_"+System.currentTimeMillis()+".odt");
        outputFile = new File(getTempDir()+"/wicket_odfpage_out_"+System.currentTimeMillis()+"."+extension);
         
        // Configure HTTP Header Response object with the correct locale and content type
		configureResponse();

		// Build the report with the Template and the data of the model.
		try {
			DocumentTemplate odtTemplate = new ZippedDocumentTemplate(addTableMarks(getClass().getResourceAsStream(getClass().getSimpleName()+"."+getMarkupType())));
	        odtTemplate.createDocument(getModelObject(), new FileOutputStream(inputFile));
		} catch (IOException e) {
			throw new WicketRuntimeException(e);
		} catch (DocumentTemplateException e) {
			throw new WicketRuntimeException(e);
		} 
		
		
		// Create the connection to the OpenOffice service. OpenOffice must be
		// listening on port 8100.
		OpenOfficeConnection connection = new SocketOpenOfficeConnection();
		
		try {
            connection.connect();
        } catch (ConnectException officeNotRunning) {
            throw new WicketRuntimeException("ERROR: OpenOffice.org not running on port "+ SocketOpenOfficeConnection.DEFAULT_PORT);
        }
        
        // Convert the OpenDocument source to the output file.
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);
        
        
        // Send the file to the browser
        try {
        OutputStream outStream = getResponse().getOutputStream();
        FileInputStream inStream = new FileInputStream(outputFile);
        	          
        while ((len = inStream.read(buf)) > 0) {
            outStream.write(buf, 0, len);
        }
        
        inStream.close();
        outStream.close();
        
        } catch (FileNotFoundException e) {
        	throw new WicketRuntimeException(e);
        } catch (IOException e) {
        	throw new WicketRuntimeException(e);
        }
        
        // Delete temporal files.
        deleteFiles();
		
	}
	
	
	/**
	 * @return Returns the output filename. If you don't overide this method it will return
	 * 		   the name of the class as the filename and the getMarkupType as the extension.
	 */
	public String getFileName() {
		return getClass().getSimpleName()+"."+getMarkupType();
	}

	/**
	 * @return - The output contentType. If you don't overide the method  
	 * 		will deduce the content type from the filename extension. 
	 *      If it isn't posible the default application/octet-stream
	 *      contentType is returned.
	 *       
	 * 		The contentTypes automaticaly detected:
	 * 			application/pdf  (.pdf)
	 * 			text/rtf (.rtf)
	 * 			text/plain (.txt)
	 * 			application/vnd.oasis.opendocument.text (.odt)  
	 * 			application/vnd.oasis.opendocument.presentation (.odp)
	 * 			application/vnd.oasis.opendocument.spreadsheet (.ods)
	 * 			application/msword (.doc)
	 * 			application/vnd.ms-excel (.xsl)
	 * 			application/vnd.ms-powerpoint (.ppt)
	 * 			application/octet-stream (default)
	 * 
	 *     Look for more types in 
	 *          http://www.iana.org/assignments/media-types/
	 *          
	 */
	public String getContentType() {
		String filename = getFileName();
		String extension = filename.substring(filename.lastIndexOf(".")+1, filename.length());
		
		if (extension.equals("pdf")) {
			return "application/pdf";
		} else if (extension.equals("rtf")) {
			return "application/rtf";
		} else if (extension.equals("odt")) {
			return "application/vnd.oasis.opendocument.text";
		} else if (extension.equals("doc")) {
			return "application/msword";
		} else if (extension.equals("xsl")) {
			return "application/vnd.ms-excel";
		} else if (extension.equals("ods")) {
			return "application/vnd.oasis.opendocument.spreadsheet";
		} else if (extension.equals("ppt")) {
			return "application/vnd.ms-powerpoint";
		} else if (extension.equals("odp")) {
			return "application/vnd.oasis.opendocument.presentation";
		} else if (extension.equals("txt")) {
			return "text/plain";
		} else {
			return "application/octet-stream";
		}
		
	}
	
	/* 
	 * return - The markupType of the template. It must be and OpenDocument Format extension. 
	 *          The template must be at the same directory of the class, with the same name
	 *          and with markupType as extension. The default markupType is odt.
	 */
	public String getMarkupType() {
		return "odt";
	}

	
	
	/**
	 * @return - The system temporal directory were your webapplication and your OpenOffice 
	 * 			service can read and write. If you don't overide this method it returns 
	 * 			/tmp the temporal directory for most of unix-like OS.
	 */
	protected String getTempDir() {
		return "/tmp";
	}
	
	/**
	 * @param input
	 * @return
	 * 
	 * This function filters the .odt resource template and adds freemarkers in some tables. Have
	 * a look at setAutomaticTables description.
	 *
	 */
	private InputStream addTableMarks(InputStream input) {
		if (isAutomaticTables()) {
			int len=0;
			try {
				templateFile = new File(getTempDir()+"/wicket_odfpage_mark_"+System.currentTimeMillis()+".odt");
				ZipOutputStream zipOutput = new ZipOutputStream(new FileOutputStream(templateFile));
				ZipInputStream zipInput = new ZipInputStream(input);
			
				ZipEntry entry;
				byte[] buf = new byte[40092];
				int value; 
			
				String table_name = "";
				String last30 = "                           ";
						
				while((entry = zipInput.getNextEntry())!=null) {
					if("content.xml".equals(entry.getName())) {
						zipOutput.putNextEntry(entry);
						while ((value = zipInput.read()) != -1) {
							zipOutput.write(value);
							last30 = last30.substring(1) + String.valueOf((char)(value & 0xff));
							
							if (last30.endsWith("<table:table table:name=\"")) {
							
								table_name = "";
								while ((value = zipInput.read()) != (int) '\"') {
									zipOutput.write(value);
									table_name = table_name + String.valueOf((char)(value & 0xff));
								}
								zipOutput.write(value);
														
								if (table_name.indexOf("_")!=-1) {
									while((value=zipInput.read()) != -1) {
										zipOutput.write(value);
										
										last30 = last30.substring(1) + String.valueOf((char)(value & 0xff));
																		
										if (last30.endsWith("</table:table-header-rows>")) {
										
											String iniStr = "[#list " + table_name.replaceFirst("_", " as ") + "]";
											zipOutput.write(iniStr.getBytes());
										
											while((value=zipInput.read())!=-1) {
												zipOutput.write(value);
											
												last30 = last30.substring(1) + String.valueOf((char)(value & 0xff));
																						
												if (last30.endsWith("</table:table-row>")) {
													iniStr = "[/#list]";
													zipOutput.write(iniStr.getBytes());
												
													break;
												}
											}
											break;
										}
									}
								}
							} 
						}
						zipOutput.closeEntry();
					} else {
						zipOutput.putNextEntry(entry);
						while ((len = zipInput.read(buf)) > 0) {
							zipOutput.write(buf, 0, len);
						}
						zipOutput.closeEntry();
					}
				}
				zipOutput.close();
				return new FileInputStream(templateFile);
			} catch (Exception e) {
				throw new WicketRuntimeException(e);
			}
		} else {
			return input;
		}
	}
	
	/**
	 *  Delete all temporal files created. 
	 */
	private void deleteFiles() {
		
		if (templateFile != null) 
			templateFile.delete();
		
		if (inputFile != null) 
			inputFile.delete();
		
		if (outputFile != null) 
			outputFile.delete();
		
	}

	/**
	 * @param automaticTables - If you are using tables with multiple rows and don't want
	 *  to open the .odt and modify the content.xml adding freemarkers marks everytime you
	 *  modify the .odt template, then you can set automaticTables to true.
	 * 
	 *  All tables with a name like "friends_friend" would be procesed. And a freemarker like 
	 *  [#list friends as friend] would be added in front of the first row (without count the 
	 *  header row).
	 *  
	 *  If you are looking for performance then is better to add the freemarker mark by
	 *  hand and set automaticTable to false to disable the table filter.
	 */
	public void setAutomaticTables(boolean automaticTables) {
		this.automaticTables = automaticTables;
	}
	
	public boolean isAutomaticTables() {
		return automaticTables;
	}
}
