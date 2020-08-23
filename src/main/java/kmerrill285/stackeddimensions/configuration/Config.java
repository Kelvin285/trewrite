package kmerrill285.stackeddimensions.configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import net.minecraft.util.ResourceLocation;

public class Config {
	
	private static final String defaultConfig = "Using the configuration file for Stacked Dimensions 1.14 is really simple!\r\n" + 
			"On each line, specify a dimension, then specify the above or below dimensions.\r\n" + 
			"You can use \"above\", \"below\", or both above and below.\r\n" + 
			"In order for a dimension configuration to be registered though, it MUST have a dimension registered somewhere in the line.\r\n" + 
			"The order in which you put \"dimension\", \"above\" and \"below\" does not matter.\r\n" + 
			"You can also use \"min\" and \"max\" to specify the top and bottom of the dimension.\r\n" +
			"=======================================================\r\n" + 
			"Config starts below here\r\n" + 
			"=======================================================\r\n" + 
			"\r\n" + 
			"dimension=minecraft:overworld below=minecraft:the_nether above=minecraft:the_end\r\n" + 
			"dimension=minecraft:the_nether above=minecraft:overworld max=127\r\n" + 
			"dimension=minecraft:the_end below=minecraft:overworld";
	
	public static void loadConfig() {
		File file = new File("stackeddimensions/stacked_dimensions.config");
		if (!file.exists())
			try {
				File f = new File("stackeddimensions");
				f.mkdirs();
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write(defaultConfig);
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		//format:   dimension=minecraft:the_nether above=minecraft:overworld below=minecraft:the_end
		try {
			Scanner scanner = new Scanner(file);
			ArrayList<String> lines = new ArrayList<String>();
			while(scanner.hasNext()) {
				lines.add(scanner.nextLine());
			}
			for (String s : lines) {
				if (s.startsWith("#")) continue;
				ResourceLocation dimension = null;
				ResourceLocation above = null;
				ResourceLocation below = null;
				int min = 0;
				int max = 255;
				String[] s2 = s.split(" ");
				for (String s3 : s2) {
					String[] s4 = s3.split("=");
					if (s4.length == 0) continue;
					if (s4[0].equals("dimension")) {
						if (s4.length > 1) {
							String location = s4[1].replace("\"", "");
							dimension = new ResourceLocation(location);
						}
						
					}
					if (s4[0].equals("above")) {
						if (s4.length > 1) {
							String location = s4[1].replace("\"", "");
							above = new ResourceLocation(location);
						}
						
					}
					if (s4[0].equals("below")) {
						if (s4.length > 1) {
							String location = s4[1].replace("\"", "");
							below = new ResourceLocation(location);
						}
						
					}
					if (s4[0].equals("min")) {
						if (s4.length > 1) {
							String location = s4[1].replace("\"", "");
							min = Integer.parseInt(location);
						}
						
					}
					if (s4[0].equals("max")) {
						if (s4.length > 1) {
							String location = s4[1].replace("\"", "");
							max = Integer.parseInt(location);
						}
						
					}
				}
				if (dimension != null) {
					DimensionConfigs.configs.add(new DimensionConfiguration(dimension, above, below, min, max));
				}
			}
		}catch (Exception e) {
			System.err.println("Could not load config file!");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
