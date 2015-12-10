package cl.hcarrasco.remotecontrol.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import cl.hcarrasco.remotecontrol.msghandler.MsgHandler;

/**
 * Esta clase sera el manejador de los eventos que ocurran en el servidor
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        
    	boolean isFromAndroidApp = false;
        int maxBufferMessage = 250;
        ByteBuf in = (ByteBuf) msg;
        byte[] data = new byte[maxBufferMessage];
        int i = 0;
        try {
            while (in.isReadable()) {
                data[i] = in.readByte();
                i++;
            }
        } catch (Exception e) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, e);     
        }
        finally { ReferenceCountUtil.release(msg); }
        String msgFromDevice = new String (data);
        msgFromDevice = msgFromDevice.trim();
        System.out.println("desde cliente TCP: " + msgFromDevice);
        
        MsgHandler msgHandler = new MsgHandler();
        isFromAndroidApp = msgHandler.msgFilter(msgFromDevice);
        
        if (isFromAndroidApp){
        	msgHandler.processMsg(msgFromDevice);
        	ChannelFuture ft = ctx.channel().writeAndFlush(Unpooled.copiedBuffer(">hc;OK<", CharsetUtil.UTF_8));
        	System.out.println(ft.cause());
        } else {
        	System.out.println("Error.");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}