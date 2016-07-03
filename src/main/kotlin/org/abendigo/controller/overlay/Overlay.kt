package org.abendigo.controller.overlay

import org.abendigo.controller.Plugins.hotkeyChangePlugin
import org.abendigo.controller.Plugins.hotkeyToName
import org.abendigo.controller.Plugins.nameToEnabled
import org.abendigo.controller.Plugins.nameToHotkey
import org.abendigo.controller.Plugins.nameToLabel
import org.abendigo.controller.Settings
import java.awt.*
import java.awt.event.*
import javax.swing.JLabel
import javax.swing.JSeparator
import javax.swing.JWindow
import javax.swing.SwingConstants

object Overlay : JWindow() {

	private const val WIDTH = 200

	private val BOX_COLOR = Color(0, 0, 0, 60)
	private val INSERT_TO_HIDE_FONT = Font("Dialog", Font.PLAIN, 15)

	private var hidden = false

	private lateinit var hide: JLabel
	private lateinit var mousePressedPoint: Point

	private var expandedHeight = 0

	init {
		setSize(WIDTH, HEIGHT)
		isAlwaysOnTop = true
		layout = null
		background = BOX_COLOR
		setBounds(Settings.overlayX, Settings.overlayY, WIDTH, 0)

		hide = JLabel(hideText(), SwingConstants.CENTER)
		hide.font = INSERT_TO_HIDE_FONT
		hide.foreground = Color.WHITE
		hide.setBounds(0, 0, width, 20)
		add(hide)

		val separator = JSeparator()
		separator.setBounds(0, 22, width, 20)
		add(separator)

		val mouseMotionListener = object : MouseMotionAdapter() {
			override fun mouseDragged(e: MouseEvent) {
				if (hotkeyChangePlugin == null) {
					var x = e.locationOnScreen.x - mousePressedPoint.x
					var y = e.locationOnScreen.y - mousePressedPoint.y
					if (x < 0) x = 0
					if (y < 0) y = 0

					setLocation(x, y)
					Settings.overlayX = x
					Settings.overlayY = y

					repaint()
				}
			}
		}

		var y = 30
		var defaultHotkey = 97 // default start at NumPad-1
		for ((name, enabled) in nameToEnabled) {
			val useDefaultHotkey = !nameToHotkey.containsKey(name)
			if (useDefaultHotkey) {
				hotkeyToName[defaultHotkey] = name
				nameToHotkey[name] = defaultHotkey++
			}

			val label = JLabel(labelText(name, nameToHotkey[name]!!), SwingConstants.CENTER)
			label.font = INSERT_TO_HIDE_FONT
			label.foreground = if (enabled) Color.GREEN else Color.RED
			label.setBounds(0, y, width, 20)
			label.addMouseListener(object : MouseAdapter() {
				override fun mousePressed(e: MouseEvent) {
					if (e.button == 1) mousePressedPoint = e.point
					else if (hotkeyChangePlugin == null) {
						hotkeyToName.remove(nameToHotkey[name]!!)
						label.text = "$name (?)"
						hotkeyChangePlugin = name
					}
				}
			})
			label.addMouseMotionListener(mouseMotionListener)

			add(label)

			nameToLabel[name] = label

			y += 20
		}

		expandedHeight = y + 20
		setSize(width, expandedHeight)

		addMouseListener(object : MouseAdapter() {
			override fun mousePressed(e: MouseEvent) {
				mousePressedPoint = e.point
			}
		})
		addMouseMotionListener(mouseMotionListener)
	}

	override fun repaint() {
		hide.text = hideText()
	}

	private fun hideText() = "Abendigo (F9 to ${if (hidden) "show" else "hide"})"

	fun toggleHidden() {
		hidden = !hidden
		setSize(WIDTH, if (hidden) 20 else expandedHeight)
		repaint()
	}

	fun labelText(pluginName: String, keyCode: Int) = "$pluginName (${KeyEvent.getKeyText(keyCode)})"

}