package util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sysu.sdcs.order.analysis.utils.common.FileUtil;

public class temp {
	private static final String ROOTPATH = "src\\main\\resources\\data\\";

	@Test
	public void testReadFile() {
		List<String> temp = FileUtil.read(ROOTPATH + "temp.csv");
		int T = 10001;
		List<String> ignore = new ArrayList<>();
		while (T-- != 0) {
			boolean flag = false;
			for (String t : temp) {
				byte[] a = t.getBytes();
				StringBuffer sb = new StringBuffer();
				for(byte b :a){
					if(b>0)
						sb.append((char)b);
				}
				String str = sb.toString();
				if (Integer.parseInt(str) == T) {
					flag = true;
					break;
				}
			}
			if (flag == false) {
				ignore.add(T + "");
			}
		}
		FileUtil.write(ROOTPATH + "answer.txt", ignore);
	}

}
