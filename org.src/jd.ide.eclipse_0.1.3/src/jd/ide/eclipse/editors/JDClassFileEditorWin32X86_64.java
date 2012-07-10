package jd.ide.eclipse.editors;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.internal.core.SourceMapper;

/**
 * JDClassFileEditor
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.2
 * @see     org.eclipse.jdt.internal.ui.javaeditor.ClassFileEditor
 */
@SuppressWarnings("restriction")
public class JDClassFileEditorWin32X86_64 extends JDClassFileEditor
{
	@SuppressWarnings("unchecked")
	protected SourceMapper newSourceMapper(
		IPath rootPath, IPath sourcePath, String sourceRootPath, Map options)
	{
		return new JDSourceMapperWin32X86_64(
			rootPath, sourcePath, sourceRootPath, options);
	}
}
