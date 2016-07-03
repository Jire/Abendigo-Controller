package org.abendigo.controller.network

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import org.abendigo.controller.network.Handler

internal object Client {

	const val DEFAULT_PORT = 58585

	lateinit var channel: Channel

	fun connect(port: Int = DEFAULT_PORT): ChannelFuture {
		val future = Bootstrap()
				.group(NioEventLoopGroup(1))
				.channel(NioSocketChannel::class.java)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(object : ChannelInitializer<SocketChannel>() {
					override fun initChannel(ch: SocketChannel) {
						ch.pipeline().addLast(Handler)
					}
				}).connect("localhost", port)
		channel = future.channel()
		return future
	}

}