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
package wicket.addons.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import wicket.addons.CategoryRequest;

/**
 * @author Juergen Donnerstag
 */
public class GmailClient
{
    private static final Log log = LogFactory.getLog(CategoryRequest.class);

    private final MailSender mailSender;

    public GmailClient(final MailSender mailSender)
    {
        this.mailSender = mailSender;
    }
    
    public void sendMail(final SimpleMailMessage message)
    {
        //Create a threadsafe "sandbox" of the message
        SimpleMailMessage msg = new SimpleMailMessage(message);
        msg.setTo("juergen.donnerstag@gmail.com");
        msg.setText("Dies ist ein Test!");
  
        if (mailSender instanceof JavaMailSenderImpl)
        {
            final JavaMailSenderImpl sender = (JavaMailSenderImpl) mailSender;

            Properties p = new Properties();
            p.put("mail.smtps.host", sender.getHost());
            p.put("mail.smtps.port", new Integer(sender.getPort()));
            p.put("mail.smtps.user", sender.getUsername());
            p.put("mail.smtps.password", sender.getPassword());
            p.put("mail.smtps.auth", "true");
            p.put("mail.smtps.starttls.enable", "true");

            sender.setSession(Session.getDefaultInstance(p, new Authenticator() 
            {
                protected PasswordAuthentication getPasswordAuthentication() 
                {
                    return new PasswordAuthentication(sender.getUsername(), sender.getPassword());
                }
            }));
        }
        
        try
        {
            mailSender.send(msg);
        }
        catch (MailException ex) 
        {
            log.error(ex.getMessage());
            throw ex;
        }            
    }
}
