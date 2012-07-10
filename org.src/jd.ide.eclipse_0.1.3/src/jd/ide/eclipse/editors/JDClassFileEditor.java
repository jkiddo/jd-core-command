package jd.ide.eclipse.editors;

import java.lang.reflect.Method;
import java.util.Map;

import jd.ide.eclipse.JavaDecompilerPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.BufferManager;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jdt.internal.core.SourceMapper;
import org.eclipse.jdt.internal.ui.javaeditor.ClassFileEditor;
import org.eclipse.jdt.internal.ui.javaeditor.IClassFileEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;


/**
 * JDClassFileEditor
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.3
 * @see     org.eclipse.jdt.internal.ui.javaeditor.ClassFileEditor
 */
@SuppressWarnings("restriction")
public abstract class JDClassFileEditor extends ClassFileEditor 
{	
	protected void doSetInput(IEditorInput input) throws CoreException 
	{
		if (input instanceof IFileEditorInput) 
		{
			IJavaElement javaElement = JavaCore.create(
				((IFileEditorInput)input).getFile());
			setupSourceMapper(javaElement);
		} 
		else if (input instanceof IClassFileEditorInput) 
		{
			IClassFileEditorInput classFileEditorInput = 
				((IClassFileEditorInput)input);
			IClassFile file = classFileEditorInput.getClassFile();
			IJavaElement javaElement = file.getParent();
			
			setupSourceMapper(javaElement);			
			
			IBuffer buffer = 
				BufferManager.getDefaultBufferManager().getBuffer(file);

			if ((buffer != null) && (buffer.getLength() <= 0))
			{
				try 
				{
					// Remove the empty buffer
					Method method = BufferManager.class.getDeclaredMethod(
						"removeBuffer", new Class[] {IBuffer.class});
					method.setAccessible(true);
					method.invoke(
						BufferManager.getDefaultBufferManager(), 
						new Object[] {buffer});				
				} 
				catch (Exception e) 
				{
					JavaDecompilerPlugin.getDefault().getLog().log(new Status(
							Status.ERROR, JavaDecompilerPlugin.PLUGIN_ID, 
							0, e.getMessage(), e));
				}
			}
		}
		
		super.doSetInput(input);
	}
	
	@SuppressWarnings("unchecked")
	private void setupSourceMapper(IJavaElement javaElement) 
	{
		try 
		{
			// Search package fragment root.
			while ((javaElement != null) && 
				   (javaElement.getElementType() != 
					   IJavaElement.PACKAGE_FRAGMENT_ROOT))
			{
				javaElement = javaElement.getParent();
			}

			if ((javaElement != null) && 
				(javaElement instanceof PackageFragmentRoot))
			{
				PackageFragmentRoot root = (PackageFragmentRoot)javaElement;				
				SourceMapper sourceMapper = root.getSourceMapper();
				
				// Is "sourceMapper" an instance of "JDSourceMapper" ?
				if (!(sourceMapper instanceof JDSourceMapper))
				{
					// No. Setup a new source mapper.
					
					// The location of the archive file containing classes.
					IPath classePath = root.getPath();
					// The location of the archive file containing source.
					IPath sourcePath = root.getSourceAttachmentPath();
					if (sourcePath == null) sourcePath = classePath;
					// Specifies the location of the package fragment root 
					// within the zip (empty specifies the default root).
					IPath sourceAttachmentRootPath = 
						root.getSourceAttachmentRootPath();
					String sourceRootPath;
					if (sourceAttachmentRootPath == null)
					{
						sourceRootPath = null;
					}
					else
					{
						sourceRootPath = sourceAttachmentRootPath.toString();
						if ((sourceRootPath != null) && 
							(sourceRootPath.length() == 0))
							sourceRootPath = null;
					}
					// Options
					Map options = root.getJavaProject().getOptions(true);
					
					root.setSourceMapper(newSourceMapper(
						classePath, sourcePath, sourceRootPath, options));				
				}		
			}
		} 
		catch (CoreException e) 
		{
			JavaDecompilerPlugin.getDefault().getLog().log(new Status(
				Status.ERROR, JavaDecompilerPlugin.PLUGIN_ID, 
				0, e.getMessage(), e));
		}
	}
	
	@SuppressWarnings("unchecked")
	protected abstract SourceMapper newSourceMapper(
		IPath rootPath, IPath sourcePath, String sourceRootPath, Map options);
	
	public boolean isEditable() { return true; }
	
	public boolean isDirty() { return false; }
	
	public boolean isEditorInputReadOnly() { return false; }
}
