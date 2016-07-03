package org.abendigo.controller

import java.util.*
import javax.swing.JLabel

internal object Plugins {

	var hotkeyChangePlugin: String? = null

	val nameToEnabled = HashMap<String, Boolean>()
	val nameToLabel = HashMap<String, JLabel>()
	val hotkeyToName = HashMap<Int, String>()
	val nameToHotkey = HashMap<String, Int>()

}