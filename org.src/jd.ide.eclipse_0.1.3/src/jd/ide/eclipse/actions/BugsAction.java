package jd.ide.eclipse.actions;

import jd.ide.eclipse.JavaDecompilerPlugin;

import org.eclipse.core.runtime.Status;
import org.eclipse.help.browser.IBrowser;
import org.eclipse.help.internal.browser.BrowserManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;


/**
 * BugsAction
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.3
 * @see     org.eclipse.ui.IWorkbenchWindowActionDelegate
 */
@SuppressWarnings("restriction")
public class BugsAction implements IWorkbenchWindowActionDelegate 
{
	public void init(IWorkbenchWindow workbenchWindow) {}

	public void run(IAction action) 
	{
		IBrowser browser = BrowserManager.getInstance().createBrowser(false);
		
		try 
		{
			browser.displayURL("http://java.decompiler.free.fr/jd-eclipse/bugs");
		} 
		catch (Exception e) 
		{
			JavaDecompilerPlugin.getDefault().getLog().log(new Status(
					Status.ERROR, JavaDecompilerPlugin.PLUGIN_ID, 
					0, e.getMessage(), e));
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {}

	public void dispose() {}
}
