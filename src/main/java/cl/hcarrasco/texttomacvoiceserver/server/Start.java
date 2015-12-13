package cl.hcarrasco.texttomacvoiceserver.server;

import cl.hcarrasco.texttomacvoiceserver.gui.GuiManager;

public class Start {

    public static void main(String[] args) throws Exception {
    
    	String OS = System.getProperty("os.name").toLowerCase();
    	if(OS.contains("mac")){
    		GuiManager guiManager = new GuiManager();
		(new Thread(guiManager)).start();
    	} else {
    		System.exit(0);
    	}
    	
    }
}