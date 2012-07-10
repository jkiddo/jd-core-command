package jd.ide.eclipse.dialogs;

import jd.ide.eclipse.JavaDecompilerPlugin;

import org.eclipse.core.runtime.Status;
import org.eclipse.help.browser.IBrowser;
import org.eclipse.help.internal.browser.BrowserManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


/**
 * AboutDialog
 * 
 * @project Java Decompiler Eclipse Plugin
 * @author  Emmanuel Dupuy
 * @version 0.1.3
 */
@SuppressWarnings("restriction")
public class AboutDialog 
{
	private Shell  dialog;
	private Label  title;
	private Cursor cursorHand;
	
	public AboutDialog(Shell parent)
	{		
		// Create cursor
		this.cursorHand = new Cursor(parent.getDisplay(), SWT.CURSOR_HAND);
		
		// Create dialog
		Image iconImage = ImageDescriptor.createFromFile(
				JavaDecompilerPlugin.class, "/icons/jd_16.png").createImage();

		GridLayout dialogLayout = new GridLayout();
		dialogLayout.numColumns = 1;
		dialogLayout.marginLeft = dialogLayout.marginTop = 
			dialogLayout.marginRight = dialogLayout.marginBottom = 15;
		dialogLayout.verticalSpacing = 20;
		dialogLayout.makeColumnsEqualWidth = true;

		this.dialog = new Shell(
			parent, SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM|SWT.ON_TOP);		
		this.dialog.setText("About Java Decompiler");		
		this.dialog.setImage(iconImage);
		this.dialog.setLayout(dialogLayout);
		
		// Add main canvas
		createCanvas(this.dialog);
		
		// Add OK button
		GridData buttonOKGridData = new GridData();
		buttonOKGridData.horizontalAlignment = GridData.CENTER;
		buttonOKGridData.minimumWidth = 80;
		buttonOKGridData.grabExcessHorizontalSpace = true;
		
	    Button buttonOK = new Button(dialog, SWT.NONE);
	    buttonOK.setText("OK");
	    buttonOK.setLayoutData(buttonOKGridData);
	    buttonOK.addListener(SWT.Selection, new Listener()
		{		
	        public void handleEvent(Event event) 
	        { 
	        	AboutDialog.this.dialog.close(); 
	        }
		});
	}
	
	protected void createCanvas(Composite parent)
	{
		// Add border canvas
		RowLayout borderCanvasLayout = new RowLayout();
		borderCanvasLayout.marginLeft = borderCanvasLayout.marginTop = 
			borderCanvasLayout.marginRight = borderCanvasLayout.marginBottom = 1;

		Canvas borderCanvas = new Canvas(parent, SWT.NONE);
		borderCanvas.setBackground(
			parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		borderCanvas.setLayout(borderCanvasLayout);
		
		// Add canvas
		RowLayout canvasLayout = new RowLayout(SWT.HORIZONTAL);
		canvasLayout.marginLeft = canvasLayout.marginTop = 
			canvasLayout.marginRight = canvasLayout.marginBottom = 15;
		canvasLayout.spacing = 15;
		
		Canvas canvas = new Canvas(borderCanvas, SWT.NONE);
		canvas.setBackground(
			parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		canvas.setLayout(canvasLayout);
		canvas.setBackgroundMode(SWT.INHERIT_DEFAULT);

		// Add JD logo
		Image logoImage = ImageDescriptor.createFromFile(
				JavaDecompilerPlugin.class, "/icons/jd_64.png").createImage();
		new Label(canvas, SWT.NONE).setImage(logoImage);
		
		// Add text canvas
		createTextCanvas(canvas);
	}

	protected void createTextCanvas(Composite parent)
	{
		// Add text canvas
		RowLayout textCanvasLayout = new RowLayout(SWT.VERTICAL);
		textCanvasLayout.marginLeft = textCanvasLayout.marginTop = 
			textCanvasLayout.marginRight = textCanvasLayout.marginBottom = 0;
		textCanvasLayout.spacing = 3;

		Canvas textCanvas = new Canvas(parent, SWT.NONE);
		textCanvas.setLayout(textCanvasLayout);
		
		// Get font data
		FontData[] fontDatas = textCanvas.getFont().getFontData();
		String fontName;
		int fontHeight;
		
		if ((fontDatas == null) || (fontDatas.length == 0))
		{
			fontName   = "Arial";
			fontHeight = 12;
		}
		else
		{
			fontName = fontDatas[0].getName();
			fontHeight = fontDatas[0].getHeight();
		}

		// Add title
		Font titleFont = new Font(
			dialog.getDisplay(), fontName, fontHeight+2, SWT.BOLD);
		
		this.title = new Label(textCanvas, SWT.NONE);
		this.title.setText("Java Decompiler");
		this.title.setFont(titleFont);
		this.title.setCursor(this.cursorHand);
		this.title.addListener(SWT.MouseUp, new Listener()
		{		
	        public void handleEvent(Event event) 
	        { 
	        	AboutDialog.this.dialog.close(); 
	        	openBrowser();
	        }
		});
		this.title.addListener(SWT.MouseHover, new Listener()
		{		
	        public void handleEvent(Event event) 
	        { 
	        	AboutDialog.this.title.setForeground(new Color(
	        		AboutDialog.this.title.getDisplay(), 0x00, 0x00, 0xcc));
	        }
		});
		this.title.addListener(SWT.MouseExit, new Listener()
		{		
	        public void handleEvent(Event event) 
	        { 
	        	AboutDialog.this.title.setForeground(
	        		AboutDialog.this.title.getDisplay().getSystemColor(SWT.COLOR_BLACK));
	        }
		});
		
		// Add version canvas
		GridLayout versionCanvasLayout = new GridLayout(2, false);
		versionCanvasLayout.marginLeft = 4;
		versionCanvasLayout.marginTop = versionCanvasLayout.marginRight = 
			versionCanvasLayout.marginBottom = 0;
		versionCanvasLayout.verticalSpacing = 2;
		versionCanvasLayout.horizontalSpacing = 10;

		Canvas versionCanvas = new Canvas(textCanvas, SWT.NONE);
		versionCanvas.setLayout(versionCanvasLayout);
				
	    // Add plugin version
		new Label(versionCanvas, SWT.NONE).setText("JD-Eclipse");
		new Label(versionCanvas, SWT.NONE).setText(
			"version " + JavaDecompilerPlugin.VERSION_JD_ECLIPSE);
	    
	    // Add Core version
		new Label(versionCanvas, SWT.NONE).setText("JD-Core");
		new Label(versionCanvas, SWT.NONE).setText(
			"version " + JavaDecompilerPlugin.VERSION_JD_CORE);
		
		// Add Copyright 
		new Label(textCanvas, SWT.NONE).setText("© 2008-2009 Emmanuel Dupuy");
	}
	
	protected void openBrowser()
	{
		IBrowser browser = BrowserManager.getInstance().createBrowser(false);

		try 
		{
			browser.displayURL(JavaDecompilerPlugin.URL_JDECLIPSE);
		} 
		catch (Exception e) 
		{
			JavaDecompilerPlugin.getDefault().getLog().log(new Status(
				Status.ERROR, JavaDecompilerPlugin.PLUGIN_ID, 
				0, e.getMessage(), e));
		}		
	}
	
	public void open()
	{	    
	    this.dialog.pack();
	    
    	Rectangle parentSize = this.dialog.getParent().getBounds();
	    Rectangle dialogSize = this.dialog.getBounds();
    	int x = (parentSize.width - dialogSize.width)/2 + parentSize.x;
	    int y = (parentSize.height - dialogSize.height)/2 + parentSize.y;

	    this.dialog.setLocation(x, y);	    
		this.dialog.open();
	}
}
