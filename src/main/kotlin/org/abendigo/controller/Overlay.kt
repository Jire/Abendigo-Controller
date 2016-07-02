package org.abendigo.controller

import java.awt.Color
import java.awt.Font
import java.awt.Point
import java.awt.event.*
import javax.swing.JLabel
import javax.swing.JWindow
import javax.swing.SwingConstants

object Overlay : JWindow() {

	private const val WIDTH = 250
	private const val HEIGHT = 400

	private val BOX_COLOR = Color(0, 0, 0, 60)
	private val INSERT_TO_HIDE_FONT = Font("Dialog", Font.PLAIN, 12)

	private var hidden = false

	private lateinit var hide: JLabel
	private lateinit var mousePressedPoint: Point

	init {
		setSize(WIDTH, HEIGHT)
		isAlwaysOnTop = true
		layout = null
		background = BOX_COLOR
		setBounds(0, 0, WIDTH, HEIGHT)

		hide = JLabel(hideText(), SwingConstants.CENTER)
		hide.font = INSERT_TO_HIDE_FONT
		hide.foreground = Color.WHITE
		hide.setBounds(0, 0, width, 20)
		add(hide)

		addMouseListener(object : MouseAdapter() {
			override fun mousePressed(e: MouseEvent) {
				mousePressedPoint = e.point
			}
		})
		addMouseMotionListener(object : MouseMotionAdapter() {
			override fun mouseDragged(e: MouseEvent) {
				var newX = e.locationOnScreen.x - mousePressedPoint.x
				var newY = e.locationOnScreen.y - mousePressedPoint.y
				if (newX < 0) newX = 0
				if (newY < 0) newY = 0
				setLocation(newX, newY)
				repaint()
			}
		})
	}

	override fun repaint() {
		hide.text = hideText()
	}

	private fun hideText() = "Press F9 to ${if (hidden) "show" else "hide"}"

	fun toggleHidden() {
		hidden = !hidden
		setSize(WIDTH, if (hidden) 20 else HEIGHT)
		repaint()
	}

}