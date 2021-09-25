package ru.kosenko.cloud;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        new Client().clientStart();
    }

    public void clientStart() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(); // Thread пул.
        try {
            Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder(), new StringDecoder());
                        }
                    });
            // Запускаем
            ChannelFuture future = client.connect("localhost", 9000).sync();

            System.out.println("Клиент стартанул!");

            while (true) {  // Отправляем сообщение каждые 5 сек.
                future.channel().writeAndFlush("Привет сервер! Я клиент!").sync();
                Thread.sleep(5000);
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
