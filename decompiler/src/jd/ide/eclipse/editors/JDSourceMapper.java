package jd.ide.eclipse.editors;

import java.io.File;

public class JDSourceMapper
{
	private final static String JAVA_CLASS_SUFFIX = ".class";
	private final static String JAVA_SOURCE_SUFFIX = ".java";
	private final static int JAVA_SOURCE_SUFFIX_LENGTH = 5;
	
	private static boolean loaded = false;

	public static void main(String[] args)
	{
		JDSourceMapper mapper = new JDSourceMapper();
		String source = mapper.decompileClassFile("testser.class");
		System.out.println(source);
	}

	public JDSourceMapper()
	{
		loadLibrary();
	}

	public String decompileClassFile(String baseName, String qualifiedName)
	{
		return decompile(baseName, qualifiedName);
	}
	
	public String decompileClassFile(String qualifiedName)
	{
		return decompileClassFile(getWorkingDirectory(), qualifiedName);
	}

	private void loadLibrary()
	{
		System.load(getLibraryPath());
		loaded = true;
	}

	protected String getLibraryPath()
	{
		return new File(getWorkingDirectory() + "/lib/win32/x86_64/jd-eclipse.dll").getAbsolutePath();
	}

	private static String getWorkingDirectory()
	{
		File jarFile = new File(System.getProperty("java.class.path"));
		if(jarFile.exists())
		{
			return jarFile.getAbsoluteFile().getParentFile().getAbsolutePath();
		}
		return new File(".").getAbsoluteFile().getParentFile().getAbsolutePath();
	}

	protected native String decompile(String baseName, String qualifiedName);
}
