package cl.hcarrasco.texttomacvoiceserver.server;

import cl.hcarrasco.texttomacvoiceserver.gui.GuiManager;

public class Start {

    public static void main(String[] args) throws Exception {
    
    	GuiManager guiManager = new GuiManager();
		(new Thread(guiManager)).start();

    }
}