package dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class STest {
	@Test
	public void test() {
		List<List<Long>> result = new ArrayList<>();
		String typeCombs = "2,3,4,5|7,8,9,10|12,13,14,15|17,18,19,20,21|23,24,25,26";
		String[] typeComb = typeCombs.split("[|]");
		for (String types : typeComb) {
			String[] allType = types.split(",");
			List<Long> typeList = new ArrayList<>();
			for (String type : allType) {
				typeList.add(Long.parseLong(type));
			}
			result.add(typeList);
		}
		result.size();
	}
}
