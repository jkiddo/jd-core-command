package jd.ide.eclipse.editors;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jd.ide.eclipse.JavaDecompilerPlugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.internal.core.SourceMapper;


/**
 * JDSourceMapper
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.3
 * @see     org.eclipse.jdt.internal.core.SourceMapper
 */
@SuppressWarnings("restriction")
public abstract class JDSourceMapper extends SourceMapper 
{
	private final static String JAVA_CLASS_SUFFIX         = ".class";
	private final static String JAVA_SOURCE_SUFFIX        = ".java";
	private final static int    JAVA_SOURCE_SUFFIX_LENGTH = 5;
	private final static String JAR_SUFFIX                = ".jar";
	private final static String ZIP_SUFFIX                = ".zip";
	
	private static boolean loaded = false;

	private IPath classePath;
	
	@SuppressWarnings("unchecked")
	public JDSourceMapper(
		IPath classePath, IPath sourcePath, String sourceRootPath, Map options) 
	{	
		super(sourcePath, sourceRootPath, options);
		this.classePath = classePath;
	}
	
	@SuppressWarnings("unchecked")
	public char[] findSource(String javaSourcePath) 
	{
		char[] source = null;
		
		// Search source file
		if (this.rootPaths == null)
		{
			source = super.findSource(javaSourcePath);
		}
		else
		{
			Iterator iterator = this.rootPaths.iterator();
			while (iterator.hasNext() && (source == null))
			{
				String sourcesRootPath = (String)iterator.next();				
				source = super.findSource(
					sourcesRootPath + IPath.SEPARATOR + javaSourcePath);
			}
		}
				
		if (source == null)
		{
			// Decompile class file
			String javaClassPath = getJavaClassPath(javaSourcePath);
			if (javaClassPath != null)
				source = findSource(this.classePath, javaClassPath);
		}

		return source;
	}
	
	private static String getJavaClassPath(String javaSourcePath)
	{
		int index = javaSourcePath.length() - JAVA_SOURCE_SUFFIX_LENGTH;
			
		if (javaSourcePath.regionMatches(
				true, index, JAVA_SOURCE_SUFFIX, 
				0, JAVA_SOURCE_SUFFIX_LENGTH))
			return javaSourcePath.substring(0, index) + JAVA_CLASS_SUFFIX;
		
		return null;
	}
	
	public char[] findSource(IPath path, String javaClassPath) 
	{
		if (path == null)
			return null;
				
		IResource resource = 
			ResourcesPlugin.getWorkspace().getRoot().findMember(path);
		path = (resource == null) ? path : resource.getRawLocation();
		File baseFile = path.toFile();	
		
		if (!checkBaseFile(baseFile, javaClassPath))
			return null;
		
		try
		{
			loadLibrary();
			String baseName = baseFile.getAbsolutePath();
			String result = decompile(baseName, javaClassPath);
			if (result != null)
				return result.toCharArray();
		}
		catch (Exception e)
		{
			JavaDecompilerPlugin.getDefault().getLog().log(new Status(
				Status.ERROR, JavaDecompilerPlugin.PLUGIN_ID, 
				0, e.getMessage(), e));
		}
		
		return null;
	}
	
	private static boolean checkBaseFile(File baseFile, String javaClassPath)
	{
		if (! baseFile.exists())
			return false;
		
		if (baseFile.isDirectory())
		{
			File file = new File(baseFile, javaClassPath);
			return file.exists() && file.isFile();			
		}
		
		if (baseFile.isFile())
		{
			String absolutePath = baseFile.getAbsolutePath();	
			
			if (endsWithIgnoreCase(absolutePath, JAR_SUFFIX) ||
				endsWithIgnoreCase(absolutePath, ZIP_SUFFIX))
			{
				ZipFile zipFile = null;
				
				try
				{
					String zipEntryPath = 
						javaClassPath.replace(File.separatorChar, '/');
					zipFile = new ZipFile(baseFile);					
					ZipEntry zipEntry = zipFile.getEntry(zipEntryPath);
					return (zipEntry != null) && (!zipEntry.isDirectory());					
				}
				catch (IOException e)
				{
					JavaDecompilerPlugin.getDefault().getLog().log(new Status(
						Status.ERROR, JavaDecompilerPlugin.PLUGIN_ID, 
						0, e.getMessage(), e));
				}
				finally
				{
					if (zipFile != null)
						try { zipFile.close(); } catch (IOException ignore) {}
				}
			}
		}

		return false;
	}
	
	protected void loadLibrary() throws IOException
	{
		if (loaded == false)
		{
			System.load(getLibraryPath());			
			loaded = true;	
		}
	}	
	
	private static boolean endsWithIgnoreCase(String s, String suffix)
	{
		int suffixLength = suffix.length();
		int index = s.length() - suffixLength;			
		return (s.regionMatches(true, index, suffix, 0, suffixLength));
	}
	
	protected abstract String getLibraryPath() throws IOException;
		
	protected native String decompile(String baseName, String qualifiedName);
}
