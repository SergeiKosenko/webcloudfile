package ru.kosenko.cloud;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        new Server().start();
    }

    public void start() throws InterruptedException {

        // Thread пул. Устанавливает новые соединения с клиентами.
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        // Thread пул. Обрабатывает все входящие event от клиентов.
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        // Для каждого клиента создается канал. Иницилизация. Кодер, Декодер.
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder(), new StringEncoder(), new ServerDecoder());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Запускаем сервер
            ChannelFuture syng = server.bind(9000).sync();
            syng.channel().closeFuture().sync();
        } finally {

            // Завершаем, останавливаем потоки.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
