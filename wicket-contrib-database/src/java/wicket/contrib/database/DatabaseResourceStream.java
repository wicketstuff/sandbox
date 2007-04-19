/*
 * $Id$
 * $Revision$ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

public abstract class DatabaseResourceStream extends AbstractResourceStream
{
	Blob blob;
	InputStream in;
	
	public DatabaseResourceStream(Blob blob)
	{
		this.blob = blob;
	}

	public abstract String getContentType();

	public long length()
	{
		try
		{
			return blob.length();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	public InputStream getInputStream() throws ResourceStreamNotFoundException
	{
		try
		{
			return in = blob.getBinaryStream();
		}
		catch (SQLException e)
		{
			throw new ResourceStreamNotFoundException(e);
		}
	}

	public void close() throws IOException
	{
		if (in != null)
		{
			in.close();
		}
	}

	public abstract Time lastModifiedTime();
}
