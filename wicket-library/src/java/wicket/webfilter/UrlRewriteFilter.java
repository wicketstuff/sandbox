/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.webfilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Because Tuckey UrlServletFilter is not able to convert /addon/Test.html to 
 * addon.Test without a map, I descided to write my own litte filter.
 * Though it works well, you run into another problem: Absolute
 * URLs like /addon/Home.html MUST include the servlet context path
 * (in the example above: addon). This is really akward. JSP etc.
 * use $contextPath variable, which we do not have with Wicket.
 * Until now, I do not have an elegant solution for that. Sounds
 * like Wicket's approach of using bookmarkable is a good one.
 * 
 * @author Juergen Donnerstag
 */
public final class UrlRewriteFilter implements Filter 
{
    /** Logging */
    private static Log log = LogFactory.getLog(UrlRewriteFilter.class);

    private String servletPath;

    /** for the sanity of speed */
    private String servletPathWithSlash;

    private boolean isForward = true;
    private boolean isTemporaryRedirect = false;
    private boolean isPermanentRedirect = false;

    /**
     *
     */
    private ServletContext context = null;

    /**
     * Init is called automatically by the application server when it creates this filter.
     *
     * @param filterConfig The config of the filter
     */
    public void init(final FilterConfig filterConfig) 
    {
        context = filterConfig.getServletContext();
        
        servletPath = filterConfig.getInitParameter("ServletPath");
        if ((servletPath == null) || (servletPath.trim().length() == 0))
        {
            log.error("web.xml init param missing: name=ServletPath");
            return;
        }

        servletPath = servletPath.trim();
        
        if (servletPath.startsWith("/") == false)
        {
            log.error("web.xml init param 'servletPath' must start with '/': " + servletPath);
        }
        
        if (servletPath.endsWith("/") == false)
        {
            servletPathWithSlash = servletPath + "/";
        }
        else
        {
            servletPathWithSlash = servletPath;
            servletPath = servletPath.substring(0, servletPath.length() - 1);
        }

        if (filterConfig.getInitParameter("forward") != null)
        {
            isForward = Boolean.valueOf(filterConfig.getInitParameter("forward")).booleanValue();
        }

        if (filterConfig.getInitParameter("temporaryRedirect") != null)
        {
            isTemporaryRedirect = Boolean.valueOf(filterConfig.getInitParameter("temporaryRedirect")).booleanValue();
        }

        if (filterConfig.getInitParameter("permanentRedirect") != null)
        {
            isPermanentRedirect = Boolean.valueOf(filterConfig.getInitParameter("permanentRedirect")).booleanValue();
        }
    }

    /**
     * Destroy is called by the application server when it unloads this filter.
     */
    public void destroy() 
    {
        context = null;
    }

    /**
     * The main method called for each request that this filter is mapped for.
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException 
    {
        final HttpServletRequest servletRequest = (HttpServletRequest) request;
        final HttpServletResponse servletResponse = (HttpServletResponse) response;

        String path = servletRequest.getServletPath();
        
        try 
        {
            path = URLDecoder.decode(path, "utf-8");
        } 
        catch (UnsupportedEncodingException e) 
        {
            log.error("the JVM doesn't seem to support decoding utf-8, matches may not occur correctly.");
        }

        if (path.startsWith(servletPath) == true)
        {
            // remove context path and ".html"
            path = path.substring(servletPathWithSlash.length(), path.length() - 5);
            path = path.replaceAll("/", ".");
            
            String toUrl = servletPath + "?bookmarkablePage=" + path;
            if (servletRequest.getQueryString() != null)
            {
                toUrl += "&" + servletRequest.getQueryString();
            }
            
            log.debug("toUrl=" + toUrl);

            final RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher(toUrl);
            if (requestDispatcher == null)
            {
                // todo: figure out if this is 404???
                throw new ServletException("unable to get request dispatcher for " + toUrl);
            }
            
            boolean isForward = true;
            boolean isTemporaryRedirect = false;
            boolean isPermanentRedirect = false;
            
            if (isForward) 
            {
                requestDispatcher.forward(request, response);
            }
            else 
            {
                if (isTemporaryRedirect)
                {
                    servletResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                }
                else if (isPermanentRedirect)
                {
                    servletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                }
                
                servletResponse.sendRedirect(toUrl);
            } 
        }
        else
        {
            log.debug("Request does not match the servlet path provided: request=" + path + "; servletPath=" + servletPath);

            chain.doFilter(request, response);
        }
    }
}
