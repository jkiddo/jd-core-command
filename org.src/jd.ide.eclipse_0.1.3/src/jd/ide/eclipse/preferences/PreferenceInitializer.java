package jd.ide.eclipse.preferences;

import jd.ide.eclipse.JavaDecompilerPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.3
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer 
{
	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() 
	{
		IPreferenceStore store = 
			JavaDecompilerPlugin.getDefault().getPreferenceStore();
		store.setDefault(JavaDecompilerPlugin.PREF_DISPLAY_LINE_NUMBERS, true);
		store.setDefault(JavaDecompilerPlugin.PREF_DISPLAY_METADATA, true);
	}
}
