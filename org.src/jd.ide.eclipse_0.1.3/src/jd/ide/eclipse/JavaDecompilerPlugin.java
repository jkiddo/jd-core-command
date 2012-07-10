package jd.ide.eclipse;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.3
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class JavaDecompilerPlugin extends AbstractUIPlugin 
{
	// The plug-in IDs
	public  static final String PLUGIN_ID         = "jd.ide.eclipse";
	private static final String EDITOR_ID_PREFIX  = PLUGIN_ID + ".editors.JDClassFileEditor.";	
	public  static final String UPDATE_ACTION_ID  = PLUGIN_ID + ".actions.NewVersionAvailableAction";
	public  static final String UPDATE_TOOLBAR_ID = "org.eclipse.ui.workbench.help";
	
	// Versions
	public static final String VERSION_JD_ECLIPSE = "0.1.3";
	public static final String VERSION_JD_CORE    = "0.5.3";

	// Preferences
	public static final String PREF_SETUP                = PLUGIN_ID + ".prefs.Setup";
	public static final String PREF_DISPLAY_LINE_NUMBERS = PLUGIN_ID + ".prefs.DisplayLineNumbers";
	public static final String PREF_DISPLAY_METADATA     = PLUGIN_ID + ".prefs.DisplayMetadata";
		
	// URLs
	public static final String URL_JDECLIPSE = "http://java.decompiler.free.fr/jd-eclipse";
	
	// The shared instance
	private static JavaDecompilerPlugin plugin;
	
	
	/**
	 * The constructor
	 */
	public JavaDecompilerPlugin() {}
	
	/*
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception 
	{
		super.start(context);
		plugin = this;		
		IPreferenceStore store = getPreferenceStore();

		// Is the first launch ?
		if (store.getBoolean(PREF_SETUP) == false)
		{
			// Setup ".class" file association
			Display.getDefault().syncExec(new SetupClassFileAssociationRunnable());
			store.setValue(PREF_SETUP, true);
		}
	}

	/*
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception 
	{
		plugin.savePluginPreferences();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * @return the shared instance
	 */
	public static JavaDecompilerPlugin getDefault() 
	{
		return plugin;
	}	

	private static class SetupClassFileAssociationRunnable implements Runnable
	{
		public void run()
		{
			EditorRegistry registry = (EditorRegistry)
				WorkbenchPlugin.getDefault().getEditorRegistry();
			
			// Will not work because this will not persist across sessions
			// registry.setDefaultEditor("*.class", id);
			
			IFileEditorMapping[] mappings = registry.getFileEditorMappings();
			
			// Search Class file editor mapping
			for (IFileEditorMapping mapping : mappings) 
			{
				if (mapping.getExtension().equals("class")) 
				{
					if (mapping instanceof FileEditorMapping) 
					{
						// Search Java Decompiler editor id
						for (IEditorDescriptor descriptor : mapping.getEditors())
						{
							String id = descriptor.getId();			
							if (id.startsWith(EDITOR_ID_PREFIX))
							{
					        	IEditorDescriptor desc = registry.findEditor(id);
					            ((FileEditorMapping)mapping).setDefaultEditor(
					            	(EditorDescriptor)desc);	
					            break;						
							}
						}
			        }
					
					break;
			    }
			}
			
			registry.setFileEditorMappings((FileEditorMapping[]) mappings);
			registry.saveAssociations();			
		}
	}	
}