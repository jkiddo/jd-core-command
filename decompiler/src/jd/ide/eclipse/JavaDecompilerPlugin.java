package jd.ide.eclipse;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

public class JavaDecompilerPlugin
{
	public static final String PLUGIN_ID = "jd.ide.eclipse";
	public static final String VERSION_JD_ECLIPSE = "0.1.3";
	public static final String VERSION_JD_CORE = "0.5.3";

	public static final String PREF_DISPLAY_LINE_NUMBERS = PLUGIN_ID + ".prefs.DisplayLineNumbers";
	public static final String PREF_DISPLAY_METADATA = PLUGIN_ID + ".prefs.DisplayMetadata";

	private static JavaDecompilerPlugin plugin = new JavaDecompilerPlugin();
	private IPreferenceStore store;

	public JavaDecompilerPlugin()
	{
		store = new IPreferenceStore() {

			@Override
			public void setValue(String arg0, boolean arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setValue(String arg0, String arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setValue(String arg0, long arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setValue(String arg0, int arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setValue(String arg0, float arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setValue(String arg0, double arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setToDefault(String arg0)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setDefault(String arg0, boolean arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setDefault(String arg0, String arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setDefault(String arg0, long arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setDefault(String arg0, int arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setDefault(String arg0, float arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void setDefault(String arg0, double arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void removePropertyChangeListener(IPropertyChangeListener arg0)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public void putValue(String arg0, String arg1)
			{
				System.out.println();
				// TODO Auto-generated method stub

			}

			@Override
			public boolean needsSaving()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isDefault(String arg0)
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getString(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getLong(String arg0)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getInt(String arg0)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public float getFloat(String arg0)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getDouble(String arg0)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getDefaultString(String arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getDefaultLong(String arg0)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getDefaultInt(String arg0)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public float getDefaultFloat(String arg0)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getDefaultDouble(String arg0)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean getDefaultBoolean(String arg0)
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean getBoolean(String arg0)
			{
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void firePropertyChangeEvent(String arg0, Object arg1, Object arg2)
			{
				// TODO Auto-generated method stub
				System.out.println();
			}

			@Override
			public boolean contains(String arg0)
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void addPropertyChangeListener(IPropertyChangeListener arg0)
			{
				// TODO Auto-generated method stub
				System.out.println();
			}
		};

		store.setDefault(JavaDecompilerPlugin.PREF_DISPLAY_LINE_NUMBERS, true);
		store.setDefault(JavaDecompilerPlugin.PREF_DISPLAY_METADATA, true);
	}

	public IPreferenceStore getPreferenceStore()
	{
		return store;

	}

	public static JavaDecompilerPlugin getDefault()
	{
		return plugin;
	}

	public void savePluginPreferences()
	{
		System.out.println();
	}
}