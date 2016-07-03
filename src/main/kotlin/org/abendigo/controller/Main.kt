@file:JvmName("Main")

package org.abendigo.controller

import org.abendigo.controller.network.Client
import org.abendigo.controller.overlay.Overlay
import org.jnativehook.GlobalScreen
import java.util.logging.Level
import java.util.logging.Logger

fun main(args: Array<String>) {
	val out = System.out
	System.setOut(null)
	with(Logger.getLogger(GlobalScreen::class.java.`package`.name)) {
		level = Level.OFF
		useParentHandlers = false
	}
	GlobalScreen.registerNativeHook()
	GlobalScreen.addNativeKeyListener(Keyboard)
	System.setOut(out)

	Client.connect().syncUninterruptibly()

	Overlay.isVisible = true
}