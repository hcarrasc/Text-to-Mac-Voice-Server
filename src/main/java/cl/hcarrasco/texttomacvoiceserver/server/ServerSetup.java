package cl.hcarrasco.texttomacvoiceserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class ServerSetup implements Runnable{
	
	EventLoopGroup bossGroup = new NioEventLoopGroup(); 
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    ChannelFuture f; 
    private String ServerStatusFlag = "off";
    private int    port = 1234;
    
    @Override
	public void run() {
		try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) 
             .childHandler(new ChannelInitializer<SocketChannel>() { 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	 byte[] HEX = {Byte.valueOf(String.valueOf(Integer.parseInt("3C", 16)))};
                	 ByteBuf delimiter = Unpooled.copiedBuffer(HEX);
                     ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65*1024, delimiter));
                     ch.pipeline().addLast(new ServerHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)
             //.option(ChannelOption.TCP_NODELAY, false)
             .childOption(ChannelOption.SO_KEEPALIVE, true); 

             // Bind and start to accept incoming connections.
             f = b.bind(port).sync(); 
             f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
             workerGroup.shutdownGracefully();
             bossGroup.shutdownGracefully();
        }
	}
	
	public void killServer(){
		workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        System.out.println("Server killed.-");
	}

	public String getServerStatusFlag() {
		return ServerStatusFlag;
	}
	public void setServerStatusFlag(String serverStatusFlag) {
		ServerStatusFlag = serverStatusFlag;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
