package cl.hcarrasco.remotecontrol.server;

import cl.hcarrasco.remotecontrol.gui.GuiManager;

public class Start {

    public static void main(String[] args) throws Exception {
    
    	GuiManager guiManager = new GuiManager();
		(new Thread(guiManager)).start();

    }
}