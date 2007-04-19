package wicket.benchmark;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * A filter that adds the execution time to the heading of the HTML.
 * 
 * @author Phil Kulak
 */
public class TimerFilter implements Filter {
	public void init(FilterConfig config) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		long timeStart = System.currentTimeMillis();
		PrintWriter out = response.getWriter();
		CharResponseWrapper wrapper = new CharResponseWrapper(
				   (HttpServletResponse)response);
		
		chain.doFilter(request, wrapper);
		
		if(wrapper.getContentType() != null && wrapper.getContentType().contains("text/html")) {
			long totalMili = System.currentTimeMillis() - timeStart;
			float totalSec = totalMili / 1000.f;
			
			StringBuffer full = wrapper.toStringBuffer();
			full.insert(full.indexOf("</h1>"), " (" + Float.toString(totalSec) + " seconds)");
			response.setContentLength(full.length());
			out.write(full.toString());
		} else {
			out.write(wrapper.toString());
		}
	}

	public void destroy() {}
	
	public static class CharResponseWrapper extends HttpServletResponseWrapper {
	   private CharArrayWriter output;
	   
	   @Override
	   public String toString() {
		   return output.toString();
	   }
	   
	   public StringBuffer toStringBuffer() {
	      return new StringBuffer(toString());
	   }
	   
	   public CharResponseWrapper(HttpServletResponse response){
	      super(response);
	      output = new CharArrayWriter();
	   }
	   
	   @Override
	   public PrintWriter getWriter(){
	      return new PrintWriter(output);
	   }
	}
}
