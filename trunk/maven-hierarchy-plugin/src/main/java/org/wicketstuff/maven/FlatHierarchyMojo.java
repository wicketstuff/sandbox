/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.maven;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * This goal flattens the module hierarchy so that all submodules are children
 * of the root parent project.
 * 
 * @goal flatten
 */
public class FlatHierarchyMojo extends AbstractMojo
{
	/**
	 * The maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;


	/**
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException
	{
		// project is null in validate phase
		// project.getParent() is null if project is root module
		if (project == null || project.getParent() == null)
		{
			return;
		}

		// go up the hierarchy until the root project is reached.
		MavenProject parent = project.getParent();
		while (parent.getParent() != null)
		{
			parent = parent.getParent();
		}
		project.setParent(parent);
		getLog().info(
				"Parent of \"" + project.getArtifactId() + "\" is now \""
						+ project.getParent().getArtifactId() + "\"");
	}
}
