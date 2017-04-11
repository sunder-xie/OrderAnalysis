package util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sysu.sdcs.order.analysis.utils.common.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring*.xml")
public class GoodsNameCreater {
	private static final String BRAND = "﻿Brand";
	private static final String NUMBER = "Number";
	private static final String PREFIX = "Prefix";
	private static final String SUFFIX = "Suffix";
	private static final String ROOTPATH = "src\\main\\resources\\data\\";

	@Test
	public void testReadFile() {
		List<String> types = new ArrayList<>();
		types.add("电脑");
		types.add("机油");
		/*types.add("CPU");
		types.add("上衣");
		types.add("裤子");
		types.add("袜子");
		types.add("裙子");
		types.add("手机");
		types.add("手机饰品");
		types.add("手机膜");
		types.add("手机壳");
		types.add("手机耳机");
		types.add("显示屏");
		types.add("汽车");
		types.add("汽车玻璃");
		types.add("电脑外设");
		types.add("篮球");
		types.add("羽毛球");
		types.add("乒乓球");
		types.add("自行车");
		types.add("零食");
		types.add("饰品");
		types.add("化妆品");
		types.add("保健品");
		types.add("坐垫");
		types.add("家具");*/
		for (String type : types) {
			Map<String, List<String>> contents = getFileContent(ROOTPATH + "GoodsOrigin/" + type + ".txt");
			List<String> result = getResult(contents);
			FileUtil.write(ROOTPATH + "GoodsName/" + type + ".txt", result);
		}
	}

	private List<String> getResult(Map<String, List<String>> maps) {
		Set<String> contents = new HashSet<>();
		int T = 1000;
		int brand = maps.get(BRAND).size();
		int number = maps.get(NUMBER).size();
		int prefix = maps.get(PREFIX).size();
		int suffix = maps.get(SUFFIX).size();
		StringBuffer sb = new StringBuffer();

		Random rand = new Random(new Date().getTime());
		while (T-- != 0) {
			int id = rand.nextInt(brand);
			sb.append(maps.get(BRAND).get(id));
			sb.append(" ");
			int type = rand.nextInt(2);
			if (type == 0 && prefix != 0) {
				id = rand.nextInt(prefix);
				sb.append(maps.get(PREFIX).get(id));
			}
			id = rand.nextInt(number);
			sb.append(maps.get(NUMBER).get(id));
			if (type == 1 && suffix != 0) {
				id = rand.nextInt(suffix);
				sb.append(maps.get(SUFFIX).get(id));
			}
			contents.add(sb.toString());
			sb.setLength(0);
		}
		List<String> result = new ArrayList<>();
		result.addAll(contents);
		return result;
	}

	private Map<String, List<String>> getFileContent(String path) {
		List<String> lines = FileUtil.read(path);
		Map<String, List<String>> contents = new HashMap<>();
		for (String line : lines) {
			String[] vars = line.split("\t");
			List<String> temp = new ArrayList<>();
			for (int i = 1; i < vars.length; i++) {
				temp.add(vars[i]);
			}
			contents.put(vars[0], temp);
		}
		return contents;
	}
}
