package org.abendigo.controller

import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

internal object Keyboard : NativeKeyListener {

	override fun nativeKeyTyped(e: NativeKeyEvent) {
	}

	override fun nativeKeyPressed(e: NativeKeyEvent) {
		if (e.keyCode == 67) Overlay.toggleHidden()
	}

	override fun nativeKeyReleased(e: NativeKeyEvent) {
	}

}