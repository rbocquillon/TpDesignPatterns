package fr.imtld.ilog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Menu;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;

public class DayTimeUI implements ServerListener, ServerStopCause {
	private Shell sShell;
	private Menu mbMain = null;
	private Menu mnFile = null;
	private Menu mnServer = null;
	private ToolBar tlbMain = null;
	private Group grpServer;
	private Text txtPort = null;
	private Composite grpStatus;
	private Label lblLeft = null;
	private Label lblRight = null;
	protected DayTimeServer server;
	protected boolean started;
	protected SelectionAdapter lsnServer;
	protected ToolItem tiStart;
	protected MenuItem miStart;
	protected MenuItem miStop;
	protected String details;

	/**
	 * This method initializes tlbMain
	 * 
	 * @return
	 */
	private ToolBar createTlbMain() {
		if (tlbMain == null) {
			tlbMain = new ToolBar(sShell, SWT.NONE);
			tlbMain.setLayoutData(new FormData());
			tiStart = new ToolItem(tlbMain, SWT.PUSH);
			tiStart.setText("Start");
			getStartAdapter();
			tiStart.addSelectionListener(lsnServer);
		}
		return tlbMain;
	}

	private void startStop() {
		if (started)
			server.stop();
		else {
			String strPort = txtPort.getText();
			try {
				int iPort = Integer.parseInt(strPort);
				server.start(iPort);
			} catch (NumberFormatException e) {
				details = strPort + " is not an integer";
				updateUI();
			}
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		final Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("C:/Users/Robin/Documents/Eclipse/DesignPatterns/src/fr/imtld/ilog/daytimeui.properties");
		prop.load(input); // load a properties file
		//System.out.println(prop.getProperty("factory"));
		String propfact = prop.getProperty("factory");
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Class<?> cls = cl.loadClass(propfact);
		Constructor<?> ct = cls.getDeclaredConstructor();
		Factory fa = (Factory) ct.newInstance();
		//System.out.println(fa.getClass().toString());
		
		//Factory f = Factory.getFactory();
		Factory.setFactory(fa);
		DayTimeUI ui1 = Factory.getFactory().createDayTimeUI();
		ui1.doIt();
		Display display = Display.getDefault();
		while (!ui1.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private boolean isDisposed() {
		return getShell().isDisposed();
	}

	public Shell getShell() {
		if(sShell==null) {
			sShell=new Shell();
			sShell.setText("A DayTime server");
			createTlbMain();
			getgrpServer();
			getgrpStatus();
		}
		return sShell;
	}
	
	public Group getgrpServer() {
		if (grpServer==null) {
			grpServer = new Group(sShell, SWT.NONE);
			FormData fdServer = new FormData();
			fdServer.top = new FormAttachment(50);
			fdServer.left = new FormAttachment(50);
			grpServer.setLayoutData(fdServer);
			grpServer.setLayout(new RowLayout());
			grpServer.setText("Set the service port :");
			Label lblService = new Label(grpServer, SWT.NONE);
			lblService.setText("Service port");
			txtPort = new Text(grpServer, SWT.BORDER);
			txtPort.setText("13");
			grpServer.pack(true);
			Point ptSize = grpServer.getSize();
			fdServer.left.offset = -ptSize.x / 2;
			fdServer.top.offset = -ptSize.y / 2;
			grpServer.pack(true);
		}
		return grpServer;
	}
	
	protected void doIt() {
		server = Factory.getFactory().createDayTimeServer(this);
		sShell = getShell();

		// group server
		grpServer = getgrpServer();

		// group status
		grpStatus = getgrpStatus();
		
	}

	public Composite getgrpStatus() {
		if(grpStatus==null) {
			grpStatus = new Composite(sShell, SWT.BORDER);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 3;
			FormData fd_grpStatus = new FormData();
			fd_grpStatus.left = new FormAttachment(0, 5);
			fd_grpStatus.bottom = new FormAttachment(100, -5);
			fd_grpStatus.right = new FormAttachment(100, -5);
			grpStatus.setLayoutData(fd_grpStatus);
			grpStatus.setLayout(gridLayout);
			lblLeft = new Label(grpStatus, SWT.NONE);
			GridData gd_lblLeft = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			int height = 20;
			gd_lblLeft.heightHint = height;
			lblLeft.setLayoutData(gd_lblLeft);
			lblLeft.setText("Stopped.");
			Label lblSep = new Label(grpStatus, SWT.SEPARATOR);
			GridData gd_lblSep = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_lblSep.heightHint = height;
			lblSep.setLayoutData(gd_lblSep);
			lblRight = new Label(grpStatus, SWT.NONE);
			GridData gd_lblRight = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_lblRight.widthHint = 200;
			gd_lblRight.heightHint = height;
			lblRight.setText("");
			lblRight.setLayoutData(gd_lblRight);
			
			sShell.setSize(new Point(450, 350));
			sShell.setLayout(new FormLayout());
			mbMain = new Menu(sShell, SWT.BAR);
			getStartAdapter();
			MenuItem miFile = new MenuItem(mbMain, SWT.CASCADE);
			miFile.setText("&File");
			MenuItem miServer = new MenuItem(mbMain, SWT.CASCADE);
			miServer.setText("&Server");
			mnServer = new Menu(miServer);
			miStart = new MenuItem(mnServer, SWT.PUSH);
			miStart.setText("St&art");
			miStart.addSelectionListener(lsnServer);
			miStop = new MenuItem(mnServer, SWT.PUSH);
			miStop.setText("St&op");
			miStop.addSelectionListener(lsnServer);
			miServer.setMenu(mnServer);
			mnFile = new Menu(miFile);
			MenuItem miExit = new MenuItem(mnFile, SWT.PUSH);
			miExit.setText("E&xit");
			SelectionAdapter lsnExit = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					sShell.dispose();
				}
			};
			miExit.addSelectionListener(lsnExit);
			miFile.setMenu(mnFile);
			sShell.setMenuBar(mbMain);
			sShell.open();
		}
		return grpStatus;
	}

	protected SelectionAdapter getStartAdapter() {
		if (lsnServer == null) {
			lsnServer = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					startStop();
				}
			};
		}
		return lsnServer;
	}

	@Override
	public void serverStopped(int iCause, int iPort) {
		details = "";
		switch (iCause) {
		case NORMAL:
			started = false;
			details = "by user";
			break;
		case PORT_CONTENTION:
			started = false;
			details = "port " + iPort + "doesn't work !";
			break;
		}
		asyncUpdateUI();
	}

	protected void updateUI() {
		lblLeft.setText(started ? "Started" : "Stopped");
		lblRight.setText(details);
		txtPort.setEnabled(!started);
		miStart.setEnabled(!started);
		miStop.setEnabled(started);
		tiStart.setText(started ? "Stop" : "Start");
	}

	protected void asyncUpdateUI() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				updateUI();
			}
		});
	}

	@Override
	public void serverStarted(int iPort) {
		started = true;
		details = "";
		asyncUpdateUI();
	}

}
