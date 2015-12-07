package cl.hcarrasco.remotecontrol.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import cl.hcarrasco.remotecontrol.gui.GuiManager;
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

public class Start {

    public static int port = 1234;

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
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
             .childOption(ChannelOption.SO_KEEPALIVE, true); 

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); 

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    

    public static void main(String[] args) throws Exception {
    	
    	try {
    	    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
    	 } catch (Exception e) {
    	            e.printStackTrace();
    	 }
    
    	GuiManager guiManager = new GuiManager();
    	guiManager.createComponents();
    	guiManager.startGUI();
        
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 1234;
        }
        System.out.println("Iniciando servidor en 127.0.0.1:1234");
        new Start().run();
    }
}