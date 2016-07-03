package org.abendigo.controller

import org.abendigo.controller.Plugins.hotkeyChangePlugin
import org.abendigo.controller.network.Client
import org.abendigo.controller.network.writeString
import org.abendigo.controller.overlay.Overlay
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import java.awt.Color

internal object Keyboard : NativeKeyListener {

	override fun nativeKeyTyped(e: NativeKeyEvent) {
	}

	override fun nativeKeyPressed(e: NativeKeyEvent) {
		val code = e.rawCode
		if (code == 120 /* F9 */) Overlay.toggleHidden()
		else if (Plugins.hotkeyToName.containsKey(code)) {
			val plugin = Plugins.hotkeyToName[code]!!
			val enabled = Plugins.nameToEnabled[plugin]!!
			Plugins.nameToEnabled[plugin] = !enabled
			val channel = Client.channel
			val buf = channel.alloc().buffer()
			buf.writeByte(if (enabled) 1 else 2).writeString(plugin)
			channel.writeAndFlush(buf)
			Plugins.nameToLabel[plugin]!!.foreground = if (enabled) Color.RED else Color.GREEN
		} else {
			val plugin = hotkeyChangePlugin
			if (plugin != null) {
				Plugins.hotkeyToName[code] = plugin
				Plugins.nameToLabel[plugin]!!.text = Overlay.labelText(plugin, code)
				hotkeyChangePlugin = null
			}
		}
	}

	override fun nativeKeyReleased(e: NativeKeyEvent) {
	}

}