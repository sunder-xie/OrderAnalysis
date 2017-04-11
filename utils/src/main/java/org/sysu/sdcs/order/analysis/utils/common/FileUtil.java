package org.sysu.sdcs.order.analysis.utils.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	public static List<String> read(String path) {
		List<String> lines = new ArrayList<>();
		try {
			File file = new File(path);
			if (!file.exists()) {
				LOGGER.warn("File: {} is not exist.", path);
			}
			InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader reader = new BufferedReader(streamReader);
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (Exception ex) {
			LOGGER.error("Read file fail.", ex);
		}
		return lines;
	}

	public static String readAll(String path) {
		StringBuilder sb = new StringBuilder();
		try {
			File file = new File(path);
			if (!file.exists()) {
				LOGGER.warn("File: {} is not exist.", path);
			}
			InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader reader = new BufferedReader(streamReader);
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
		} catch (Exception ex) {
			LOGGER.error("Read file fail.", ex);
		}
		return sb.toString();
	}

	public static void write(String path, List<String> lines) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileStream = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileStream, "UTF-8"));
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			LOGGER.error("Write file fail.", ex);
		}
	}
}
