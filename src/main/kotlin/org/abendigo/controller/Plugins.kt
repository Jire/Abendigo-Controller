package org.abendigo.controller

import java.util.concurrent.ConcurrentHashMap
import javax.swing.JLabel

internal object Plugins {

	var hotkeyChangePlugin: String? = null

	val nameToEnabled = ConcurrentHashMap<String, Boolean>()
	val nameToLabel = ConcurrentHashMap<String, JLabel>()
	val hotkeyToName = ConcurrentHashMap<Int, String>()
	val nameToHotkey = ConcurrentHashMap<String, Int>()

}