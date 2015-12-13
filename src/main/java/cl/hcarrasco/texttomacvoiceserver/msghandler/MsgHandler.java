package cl.hcarrasco.texttomacvoiceserver.msghandler;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import cl.hcarrasco.texttomacvoiceserver.gui.GuiManager;

public class MsgHandler {
	
	public static String messageShowing = "voice";
	public static String clientConnected = "Nobody connected";
	
	public boolean msgFilter(String msgFromDevice){
		
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
	 * Handle message with protocol: >hc;msg=caca;sender=Héctor Carrasco<
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
			if("msg".equals(typeCommand)){
				if(messageShowing.equals("voice")){
					strCommand    = command[1];
					if (strCommand!=null){
						clientConnected = sender;
						GuiManager.deviceLabelResult.setText("");
						GuiManager.deviceLabelResult.setText(clientConnected);
						String[] cmd = {"osascript", "-e","say \""+strCommand.trim()+"\" "}; //using \"victoria\"
						Runtime.getRuntime().exec(cmd);
					}
				} else if (messageShowing.equals("text-notification")) {
					strCommand    = command[1];
					if (strCommand!=null){
						clientConnected = sender;
						GuiManager.deviceLabelResult.setText("");
						GuiManager.deviceLabelResult.setText(clientConnected);
						String title = "Message from "+sender;
						String subtitle = "";
						String[] cmd = {"osascript", "-e", "display notification \""+strCommand.trim()+"\" with title \""+title.trim()+"\" subtitle \""+subtitle.trim()+"\" sound name \"Glass\""};
						Runtime.getRuntime().exec(cmd);
					}
				}
			}
			if("volUp".equals(typeCommand)){
				String[] cmd = {"osascript", "-e","set volume "};
				Runtime.getRuntime().exec(cmd);
			}
			if("volDown".equals(typeCommand)){
				String[] cmd = {"osascript", "-e","set volume "};
	            Runtime.getRuntime().exec(cmd);
			}
			if("itunsPlay".equals(typeCommand)){
				String[] cmd = {"osascript", "-e","tell app \"iTunes\" to play"};
	            Runtime.getRuntime().exec(cmd);
			}
			if("itunsNext".equals(typeCommand)){
				String[] cmd = {"osascript", "-e","tell app \"iTunes\" to play next track"};
	            Runtime.getRuntime().exec(cmd);
			}
			if("itunsBack".equals(typeCommand)){
				String[] cmd = {"osascript", "-e","tell app \"iTunes\" to play previous track"};
	            Runtime.getRuntime().exec(cmd);
			}
			if("itunsStop".equals(typeCommand)){
				String[] cmd = {"osascript", "-e","tell app \"iTunes\" to pause"};
	            Runtime.getRuntime().exec(cmd);
			}
			if("mmov".equals(typeCommand.trim())){
				String[] coordinates = command[1].split(",");
				int xCoord = Integer.parseInt(coordinates[0].trim());
		    	int yCoord = Integer.parseInt(coordinates[1].trim());
		    	Robot robot = new Robot();
		    	robot.mouseMove(xCoord, yCoord);
			}
		
		} catch (IOException | AWTException | NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
