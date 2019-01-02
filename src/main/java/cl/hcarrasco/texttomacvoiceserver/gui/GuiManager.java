package cl.hcarrasco.texttomacvoiceserver.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cl.hcarrasco.texttomacvoiceserver.msghandler.MsgHandler;
import cl.hcarrasco.texttomacvoiceserver.server.ServerSetup;

public class GuiManager implements Runnable{
	
	ServerSetup server;
	final static Logger logger = Logger.getLogger(GuiManager.class);
	
    JFrame frame = new JFrame("Text -> Mac Voice Server");
	JPanel panel = new JPanel();
	private JButton initOrStopButton;
	private JLabel serverStatusLabel;
	private JLabel initOrStopLabel;
	private JLabel portLabel;
	private JLabel portLabelResult;
	private JLabel displayLabel;
	private JComboBox<?> typeMessageSelector;
	private JLabel statusLabelResult;
	private JLabel deviceLabel;
	private String ip;
	public static JLabel deviceLabelResult;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createComponents() {
		
	    String[] jcomp6Items = {"System Voice", "System Notification"};
	    serverStatusLabel = new JLabel ("Server Status:");
	    initOrStopLabel = new JLabel ("Control Server:");
	    portLabel = new JLabel ("Accepting at port:");
	    portLabelResult = new JLabel("");
	    displayLabel = new JLabel ("Display Message as:");
	    typeMessageSelector = new JComboBox (jcomp6Items);
	    statusLabelResult = new JLabel ("");
	    deviceLabel = new JLabel ("Connected with:");
	    deviceLabelResult = new JLabel ("");
	    initOrStopButton = new JButton ("Start Server");
	    
	    frame.setPreferredSize (new Dimension (500, 300));
	    frame.setLayout (null);
	    frame.add (initOrStopButton);
	    frame.add (serverStatusLabel);
	    frame.add (initOrStopLabel);
	    frame.add (portLabel);
	    frame.add (portLabelResult);
	    frame.add (displayLabel);
	    frame.add (typeMessageSelector);
	    frame.add (statusLabelResult);
	    frame.add (deviceLabel);
	    frame.add (deviceLabelResult);
	    
	    // In order as appears in form
	    initOrStopLabel.setBounds(80, 25, 95, 30);    initOrStopButton.setBounds(248, 28, 120, 25); //x,y,w,h
	    serverStatusLabel.setBounds (80, 70, 90, 25); statusLabelResult.setBounds (253, 70, 100, 25);
	    portLabel.setBounds (80, 110, 130, 30);       portLabelResult.setBounds (250, 115, 187, 25);
	    displayLabel.setBounds (80, 160, 130, 25);    typeMessageSelector.setBounds (250, 160, 190, 25);
	    deviceLabel.setBounds (80, 205, 120, 25);     deviceLabelResult.setBounds (255, 205, 120, 25);
	    
	    Font font = statusLabelResult.getFont();
	    font.deriveFont(font.getStyle() | Font.BOLD);
	    statusLabelResult.setFont(font);
	    typeMessageSelector.setEnabled(false);
	    
	    initOrStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				if(server!=null){
					initOrStopButton.setText("Start Server");
					server.setServerStatusFlag("off");
					server.killServer();
					server = null;
					statusLabelResult.setForeground(Color.red);
					statusLabelResult.setText("STOPPED");
					typeMessageSelector.setEnabled(false);
					portLabelResult.setText("");
					deviceLabelResult.setText("");
					
				}else {
					server = new ServerSetup();
					initOrStopButton.setText("Stop Server");
					server.setServerStatusFlag("on");
					try {
						(new Thread(server)).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
					statusLabelResult.setForeground(Color.green);
					statusLabelResult.setText("RUNNING");
					typeMessageSelector.setEnabled(true);
					deviceLabelResult.setText(MsgHandler.clientConnected);
					try {
						InetAddress IP=InetAddress.getLocalHost();
						logger.info("IP of my system is := "+IP.getHostAddress());
						ip=""+IP.getHostAddress();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					portLabelResult.setText(""+ip+":1237");
				}
			}
	    });
    	typeMessageSelector.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	    	if (typeMessageSelector.getSelectedIndex()==0){
					MsgHandler.messageShowing = "voice";
					logger.info(MsgHandler.messageShowing);
				} else if (typeMessageSelector.getSelectedIndex()==1) {
					MsgHandler.messageShowing = "text-notification";
					logger.info(MsgHandler.messageShowing);
				}
    	    }
    	});
	}
	
	public void startGUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        int x = (int) ((dimension.getWidth() - 320));
        int y = (int) (35);
        frame.setLocation(x, y);
        frame.setResizable(false);
	    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add (panel);
	    frame.pack();
	    frame.setVisible (true);
	}

	@Override
	public void run() {
		createComponents();
		startGUI();
	}
}
