package org.abendigo.controller

import org.abendigo.controller.Plugins.hotkeyToName
import org.abendigo.controller.Plugins.nameToHotkey
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object Settings {

	const val DEFAULT_FILE = "settings.ini"

	var overlayX = 0
	var overlayY = 0

	fun load(file: String = DEFAULT_FILE) {
		val path = Paths.get(file)
		if (!path.toFile().exists()) return

		for (line in Files.readAllLines(path)) {
			if (line.isEmpty() || line.startsWith(";")) continue

			val split = line.split("=")
			val property = split[0]
			val value = split[1]

			when (property) {
				"overlayX" -> overlayX = value.toInt()
				"overlayY" -> overlayY = value.toInt()
				else -> if (property.endsWith("_hotkey")) {
					val pluginName = property.split("_")[0]
					val hotkey = value.toInt()

					nameToHotkey[pluginName] = hotkey
					hotkeyToName[hotkey] = pluginName

					println("set $pluginName to $hotkey")
				}
			}
		}
	}

	private val saveWorker = Executors.newSingleThreadScheduledExecutor()

	fun save(file: String = DEFAULT_FILE) {
		with(Files.newBufferedWriter(Paths.get(file))) {
			write("overlayX=$overlayX")
			newLine()
			write("overlayY=$overlayY")
			newLine()
			for ((name, hotkey) in nameToHotkey) {
				write("${name}_hotkey=$hotkey")
				newLine()
			}
			close()
		}
	}

	fun startAutosave() = saveWorker.scheduleAtFixedRate({ save() }, 3, 3, TimeUnit.SECONDS)

}