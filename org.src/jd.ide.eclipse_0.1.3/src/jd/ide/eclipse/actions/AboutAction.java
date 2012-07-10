package jd.ide.eclipse.actions;

import jd.ide.eclipse.dialogs.AboutDialog;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;


/**
 * AboutAction
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.3
 * @see     org.eclipse.ui.IWorkbenchWindowActionDelegate
 */
public class AboutAction implements IWorkbenchWindowActionDelegate 
{
	private IWorkbenchWindow workbenchWindow;
	
	public void init(IWorkbenchWindow workbenchWindow) 
	{
		this.workbenchWindow = workbenchWindow;
	}

	public void run(IAction action) 
	{	    
		new AboutDialog(this.workbenchWindow.getShell()).open();
	}

	public void selectionChanged(IAction action, ISelection selection) {}

	public void dispose() 
	{
		this.workbenchWindow = null;
	}
}
