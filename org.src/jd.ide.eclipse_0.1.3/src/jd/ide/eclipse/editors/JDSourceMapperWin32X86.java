package jd.ide.eclipse.editors;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;

/**
 * JDSourceMapper
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.2
 * @see     org.eclipse.jdt.internal.core.SourceMapper
 */
@SuppressWarnings("restriction")
public class JDSourceMapperWin32X86 extends JDSourceMapper
{
	@SuppressWarnings("unchecked")
	public JDSourceMapperWin32X86(
		IPath rootPath, IPath sourcePath, String sourceRootPath, Map options) 
	{	
		super(rootPath, sourcePath, sourceRootPath, options);
	}
	
	protected String getLibraryPath() throws IOException
	{
		URL pluginUrl = this.getClass().getResource(
			"/lib/win32/x86/jd-eclipse.dll");
		
		String path = FileLocator.toFileURL(pluginUrl).getFile();
		if ((path != null) && (path.length() > 0) && (path.charAt(0) == '/'))
			path = path.substring(1);
		
		return path;
	}
}
