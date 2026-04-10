package com.hurwan.kaggle.lib.kaggle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class KaggleTool {
	
	static public String downloadDataset(String datasetName, String datasetSavePath) throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
			
			ProcessBuilder pb = new ProcessBuilder(
				"kaggle", "datasets", "download",
				"-d", datasetName,
				"-p", datasetSavePath,
				"--unzip");
			pb.redirectErrorStream(true);
			
			Process process = pb.start();
			
			try (BufferedReader bf = new BufferedReader(
					new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
				String line;
				while((line = bf.readLine()) != null) {
					sb.append(line).append(System.lineSeparator());
					System.out.println("[KaggleTool] [READ] [LOAD] " + line);
				}
				System.out.println("[KaggleTool] [READ] [END] " + sb);
			}
			
			int exitCode = process.waitFor();
			System.out.println("[KaggleTool] [exitCode] " + exitCode);
			
			if(exitCode != 0) {
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}
}
