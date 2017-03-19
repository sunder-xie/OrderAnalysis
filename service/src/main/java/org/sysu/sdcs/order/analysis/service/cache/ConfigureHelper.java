package org.sysu.sdcs.order.analysis.service.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.dao.mapper.ConfigureMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Configure;
import org.sysu.sdcs.order.analysis.service.interfaces.Update;

@Component
public class ConfigureHelper implements Update {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigureHelper.class);
	private Map<String, String> configMap;
	@Autowired
	private ConfigureMapper configureMapper;

	@Override
	public void update() {
		try {
			List<Configure> configures = configureMapper.findAll();
			configMap = new HashMap<>();
			for (Configure configure : configures) {
				configMap.put(configure.getName(), configure.getContent());
			}
		} catch (Exception ex) {
			LOGGER.error("Update configure fail.", ex);
		}
	}

	public boolean getConfig(String name, String defaultContent) {
		boolean defaultValue = Boolean.parseBoolean(defaultContent);
		boolean value = defaultValue;
		try {
			if (configMap == null || !configMap.containsKey(name)) {
				return defaultValue;
			}
			String content = configMap.get(name);
			value = Boolean.parseBoolean(content);
		} catch (Exception ex) {
			LOGGER.error(String.format("There is not a configure name: %s.", name), ex);
			return defaultValue;
		}
		return value;
	}

	public Map<String, String> getAllConfigure() {
		return this.configMap;
	}
}
