package org.abendigo.controller.network

import io.netty.buffer.ByteBuf

private val sb = StringBuilder()

fun ByteBuf.readString(): String {
	val length = readUnsignedByte().toInt()
	sb.delete(0, sb.length)
	for (i in 1..length) sb.append(readUnsignedByte().toChar())
	return sb.toString()
}

fun ByteBuf.writeString(string: String) {
	val bytes = string.toByteArray()
	writeByte(bytes.size)
	writeBytes(bytes)
}