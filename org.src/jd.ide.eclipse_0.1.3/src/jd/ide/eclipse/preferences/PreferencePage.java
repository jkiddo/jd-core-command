package jd.ide.eclipse.preferences;

import jd.ide.eclipse.JavaDecompilerPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * PreferencePage
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.3
 */
public class PreferencePage
	extends FieldEditorPreferencePage implements IWorkbenchPreferencePage 
{
	public PreferencePage() 
	{
		super(SWT.NONE);
		setPreferenceStore(JavaDecompilerPlugin.getDefault().getPreferenceStore());
		setDescription("JD-Eclipse preference page");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() 
	{
		Composite fieldEditorParent = getFieldEditorParent();
		
		new Label(fieldEditorParent, SWT.NONE);
		
		addField(new BooleanFieldEditor(
			JavaDecompilerPlugin.PREF_DISPLAY_LINE_NUMBERS, 
			"Display line numbers", fieldEditorParent));
		addField(new BooleanFieldEditor(
			JavaDecompilerPlugin.PREF_DISPLAY_METADATA, 
			"Display metadata", fieldEditorParent));
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {}
}