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
	private JLabel jcomp2;
	private JLabel jcomp3;
	private JTextField portTxtField;
	private JLabel jcomp5;
	private JComboBox<?> typeMessageSelector;
	private JLabel jcomp7;
	private JLabel jcomp8;
	private JLabel jcomp9;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createComponents() {
		
	    String[] jcomp6Items = {"System Voice", "System Notification"};
	    jcomp2 = new JLabel ("Server Status:");
	    jcomp3 = new JLabel ("Custom Port:");
	    portTxtField = new JTextField("");
	    jcomp5 = new JLabel ("Display Message as:");
	    typeMessageSelector = new JComboBox (jcomp6Items);
	    jcomp7 = new JLabel ("status");
	    jcomp8 = new JLabel ("Device Connected:");
	    jcomp9 = new JLabel ("device");
	    okButton = new JButton ("Save Preferences");
	    
	    frame.setPreferredSize (new Dimension (289, 297));
	    frame.setLayout (null);
	    frame.add (okButton);
	    frame.add (jcomp2);
	    frame.add (jcomp3);
	    frame.add (portTxtField);
	    frame.add (jcomp5);
	    frame.add (typeMessageSelector);
	    frame.add (jcomp7);
	    frame.add (jcomp8);
	    frame.add (jcomp9);
	    
	    okButton.setBounds (75, 225, 145, 30);
	    jcomp2.setBounds (20, 20, 90, 25);
	    jcomp3.setBounds (20, 60, 95, 30);
	    portTxtField.setBounds (150, 65, 120, 25);
	    jcomp5.setBounds (20, 110, 130, 25);
	    typeMessageSelector.setBounds (150, 110, 120, 25);
	    jcomp7.setBounds (150, 20, 100, 25);
	    jcomp8.setBounds (20, 155, 120, 25);
	    jcomp9.setBounds (150, 155, 120, 25);
	    
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
	
	public void startGUI (){
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
