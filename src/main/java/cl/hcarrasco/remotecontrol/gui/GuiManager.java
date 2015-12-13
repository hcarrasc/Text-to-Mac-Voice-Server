package cl.hcarrasco.remotecontrol.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cl.hcarrasco.remotecontrol.msghandler.MsgHandler;
import cl.hcarrasco.remotecontrol.server.Start;

public class GuiManager implements Runnable{
	
    JFrame frame = new JFrame("Remote Control Android");
	JPanel panel = new JPanel();
	private JButton okButton;
	private JButton initOrStopButton;
	private JLabel serverStatusLabel;
	private JLabel initOrStopLabel;
	private JLabel portLabel;
	private JTextField portTxtField;
	private JLabel displayLabel;
	private JComboBox<?> typeMessageSelector;
	private JLabel statusLabelResult;
	private JLabel deviceLabel;
	private JLabel deviceLabelResult;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createComponents() {
		
	    String[] jcomp6Items = {"System Voice", "System Notification"};
	    serverStatusLabel = new JLabel ("Server Status:");
	    initOrStopLabel = new JLabel ("Control Server:");
	    portLabel = new JLabel ("Custom Port:");
	    portTxtField = new JTextField("");
	    displayLabel = new JLabel ("Display Message as:");
	    typeMessageSelector = new JComboBox (jcomp6Items);
	    statusLabelResult = new JLabel ("status");
	    deviceLabel = new JLabel ("Device Connected:");
	    deviceLabelResult = new JLabel ("device");
	    okButton = new JButton ("Save Preferences");
	    initOrStopButton = new JButton ("Start Server");
	    
	    frame.setPreferredSize (new Dimension (500, 335));
	    frame.setLayout (null);
	    frame.add (okButton);
	    frame.add (initOrStopButton);
	    frame.add (serverStatusLabel);
	    frame.add (initOrStopLabel);
	    frame.add (portLabel);
	    frame.add (portTxtField);
	    frame.add (displayLabel);
	    frame.add (typeMessageSelector);
	    frame.add (statusLabelResult);
	    frame.add (deviceLabel);
	    frame.add (deviceLabelResult);
	    
	    // In order as appears in form
	    initOrStopLabel.setBounds(80, 25, 90, 25);    initOrStopButton.setBounds(248, 28, 120, 25);
	    serverStatusLabel.setBounds (80, 70, 90, 25); statusLabelResult.setBounds (253, 70, 100, 25);
	    portLabel.setBounds (80, 110, 105, 30);        portTxtField.setBounds (250, 115, 187, 25);
	    displayLabel.setBounds (80, 160, 130, 25);    typeMessageSelector.setBounds (250, 160, 190, 25);
	    deviceLabel.setBounds (80, 205, 120, 25);     deviceLabelResult.setBounds (255, 205, 120, 25);

	    okButton.setBounds (180, 250, 145, 30); //x,y,w,h
	    
	    okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				if (!"".equals(portTxtField.getText()) && portTxtField.getText()!=null){
					System.out.println(portTxtField.getText());
					Start.port = Integer.parseInt(portTxtField.getText());
				}
				if (typeMessageSelector.getSelectedIndex()==0){
					MsgHandler.messageShowing = "voice";
				} else if (typeMessageSelector.getSelectedIndex()==1) {
					MsgHandler.messageShowing = "text-notification";
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
