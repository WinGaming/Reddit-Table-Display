package eu.spiforge.reddit.table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class TableDisplay extends JavaPlugin {

	public void onEnable() {
		getCommand("table").setExecutor((sender, cmd, label, args) -> {
			if (sender instanceof Player) {
				try {
					File targetFile = new File("./data.csv");
					FileInputStream fis = new FileInputStream(targetFile);
					InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
					BufferedReader reader = new BufferedReader(isr);
					
					List<Hologram> holograms = new ArrayList<>();

					String line = null;
					while ((line = reader.readLine()) != null) {
						String[] values = line.split(";");

						for (int i = 0; i < values.length; i++) {
							if (holograms.size() < i + 1) {
								Hologram hologram = HologramsAPI.createHologram(this,
										((Player) sender).getLocation().clone().add(0 + i * 1, 2, 0));
								holograms.add(i, hologram);
							}

							holograms.get(i).appendTextLine(values[i]);
						}
					}

					reader.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			return true;
		});
	}

}
