package cl.hcarrasco.texttomacvoiceserver.msghandler;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import org.apache.log4j.Logger;

import cl.hcarrasco.texttomacvoiceserver.gui.GuiManager;

public class MsgHandler {
	
	public static String messageShowing = "voice";
	public static String clientConnected = "Nobody connected";
	final static Logger logger = Logger.getLogger(MsgHandler.class);
	
	public boolean msgFilter(String msgFromDevice){
		
		logger.info("desde cliente TCP: " + msgFromDevice);
		String strValidator;
		msgFromDevice = msgFromDevice.replaceAll(">", "");
		strValidator  = msgFromDevice.split(";")[0];
		if("hc".equals(strValidator)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Handle message with protocol: >hc;msg=hello;sender=Héctor Carrasco<
	 * @param msgFromDevice
	 */
	public void processMsg(String msgFromDevice){
		
		String[] command;
		String strCommand;
		String strSender;
		String typeCommand;
		String sender;
		
		msgFromDevice = msgFromDevice.replaceAll(">", "");
		msgFromDevice = msgFromDevice.replaceAll("<", "");
		
		strCommand    = msgFromDevice.split(";")[1];
		strSender     = msgFromDevice.split(";")[2];
		
		command       = strCommand.split("=");
		sender        = strSender.split("=")[1];
		typeCommand   = command[0];
		
		try{
			switch(typeCommand) {

			case "msg":
				if(messageShowing.equals("voice")){
					strCommand    = command[1];
					if (strCommand!=null){
						clientConnected = sender;
						GuiManager.deviceLabelResult.setText("");
						GuiManager.deviceLabelResult.setText(clientConnected);
						Runtime.getRuntime().exec(new String[] {"osascript", "-e","say \""+strCommand.trim()+"\" "});
					}
				} else if (messageShowing.equals("text-notification")) {
					strCommand    = command[1];
					if (strCommand!=null){
						clientConnected = sender;
						GuiManager.deviceLabelResult.setText("");
						GuiManager.deviceLabelResult.setText(clientConnected);
						String title = "Message from "+sender;
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Runtime.getRuntime().exec(new String[] {"osascript", "-e", "display notification \""+strCommand.trim()+"\" with title \""+title.trim()+"\" sound name \"Glass\""});
					}
				}
				break;
			case "volUp" :
				Runtime.getRuntime().exec(new String[] {"osascript", "-e","set volume "});
				break;
			case "volDown" :
				Runtime.getRuntime().exec(new String[] {"osascript", "-e","set volume "});
				break;
			case "itunsPlay":
				Runtime.getRuntime().exec(new String[] {"osascript", "-e","tell app \"iTunes\" to play"});
				clientConnected = sender;
				GuiManager.deviceLabelResult.setText("");
				GuiManager.deviceLabelResult.setText(clientConnected);
				break;
			case "itunsNext" :
				Runtime.getRuntime().exec(new String[] {"osascript", "-e","tell app \"iTunes\" to play next track"});
				clientConnected = sender;
				GuiManager.deviceLabelResult.setText("");
				GuiManager.deviceLabelResult.setText(clientConnected);
				break;
			case "itunsBack" :
				Runtime.getRuntime().exec(new String[] {"osascript", "-e","tell app \"iTunes\" to play previous track"});
				clientConnected = sender;
				GuiManager.deviceLabelResult.setText("");
				GuiManager.deviceLabelResult.setText(clientConnected);
				break;
			case "itunsStop" :
				Runtime.getRuntime().exec(new String[] {"osascript", "-e","tell app \"iTunes\" to pause"});
				clientConnected = sender;
				GuiManager.deviceLabelResult.setText("");
				GuiManager.deviceLabelResult.setText(clientConnected);
				break;
			case "mute" :
				Runtime.getRuntime().exec(new String[] {"osascript", "-e","set volume output muted true"});
				clientConnected = sender;
				GuiManager.deviceLabelResult.setText("");
				GuiManager.deviceLabelResult.setText(clientConnected);
				break;
			case "mmov" :
				String[] coordinates = command[1].split(",");
				int xCoord = Integer.parseInt(coordinates[0].trim());
				int yCoord = Integer.parseInt(coordinates[1].trim());
				Robot robot = new Robot();
				robot.mouseMove(xCoord, yCoord);
				break;
			case "dmsg" :
				Runtime.getRuntime().exec(new String[] {"osascript", "-e", "display notification \""+strCommand.trim()+"\" sound name \"Glass\""});
				clientConnected = sender;
				GuiManager.deviceLabelResult.setText("");
				GuiManager.deviceLabelResult.setText(clientConnected);
				break;
			default :
				logger.info("Invalid option");
			}

		} catch (IOException | AWTException | NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
