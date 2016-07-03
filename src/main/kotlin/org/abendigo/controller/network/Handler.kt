package org.abendigo.controller.network

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.abendigo.controller.Plugins

internal object Handler : ByteToMessageDecoder() {

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
		val opcode = buf.readUnsignedByte().toInt()
		when (opcode) {
			0 -> {
				val count = buf.readUnsignedByte().toInt()
				repeat(count) {
					val pluginName = buf.readString()
					val enabled = buf.readBoolean()
					Plugins.nameToEnabled[pluginName] = enabled
				}
			}
		}
	}

	override fun channelActive(ctx: ChannelHandlerContext) {
		val listPluginsRequest = ctx.alloc().buffer()
		listPluginsRequest.writeByte(0)
		ctx.writeAndFlush(listPluginsRequest)
	}

}