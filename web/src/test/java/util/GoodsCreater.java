package util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sysu.sdcs.order.analysis.model.common.GoodsParam;
import org.sysu.sdcs.order.analysis.model.common.Range;
import org.sysu.sdcs.order.analysis.model.database.entity.Goods;
import org.sysu.sdcs.order.analysis.utils.common.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring*.xml")
public class GoodsCreater {
	private static final String ROOTPATH = "src\\main\\resources\\data\\";

	@Test
	public void testReadFile() {
		Map<String, GoodsParam> types = new HashMap<>();
		types.put("电脑", new GoodsParam(2, new Range<Double>(3000.0, 7000.0), new Range<Long>(242L, 258L)));

		types.put("CPU", new GoodsParam(3, new Range<Double>(1000.0, 4000.0), new Range<Long>(195L, 199L)));

		types.put("显示屏", new GoodsParam(4, new Range<Double>(2000.0, 7000.0), new Range<Long>(201L, 222L)));

		types.put("电脑外设", new GoodsParam(5, new Range<Double>(100.0, 700.0), new Range<Long>(224L, 240L)));

		types.put("上衣", new GoodsParam(7, new Range<Double>(100.0, 500.0), new Range<Long>(278L, 306L)));
		types.put("裤子", new GoodsParam(8, new Range<Double>(100.0, 600.0), new Range<Long>(278L, 306L)));
		types.put("袜子", new GoodsParam(9, new Range<Double>(10.0, 40.0), new Range<Long>(278L, 306L)));
		types.put("裙子", new GoodsParam(10, new Range<Double>(200.0, 800.0), new Range<Long>(278L, 306L)));

		types.put("汽车", new GoodsParam(12, new Range<Double>(100000.0, 400000.0), new Range<Long>(133l, 153L)));

		types.put("汽车玻璃", new GoodsParam(13, new Range<Double>(1000.0, 3000.0), new Range<Long>(167L, 178L)));

		types.put("机油", new GoodsParam(14, new Range<Double>(200.0, 500.0), new Range<Long>(155L, 163L)));

		types.put("坐垫", new GoodsParam(15, new Range<Double>(200.0, 500.0), new Range<Long>(180L, 193L)));

		types.put("手机", new GoodsParam(17, new Range<Double>(1000.0, 8000.0), new Range<Long>(308L, 326L)));
		types.put("手机膜", new GoodsParam(18, new Range<Double>(20.0, 40.0), new Range<Long>(308L, 326L)));
		types.put("手机壳", new GoodsParam(19, new Range<Double>(10.0, 30.0), new Range<Long>(308L, 326L)));
		types.put("手机饰品", new GoodsParam(20, new Range<Double>(10.0, 50.0), new Range<Long>(308L, 326L)));
		types.put("手机耳机", new GoodsParam(21, new Range<Double>(100.0, 400.0), new Range<Long>(308L, 326L)));

		types.put("乒乓球", new GoodsParam(23, new Range<Double>(10.0, 30.0), new Range<Long>(328L, 342L)));
		types.put("篮球", new GoodsParam(24, new Range<Double>(100.0, 300.0), new Range<Long>(328L, 342L)));
		types.put("羽毛球", new GoodsParam(25, new Range<Double>(10.0, 40.0), new Range<Long>(328L, 342L)));
		types.put("自行车", new GoodsParam(26, new Range<Double>(300.0, 700.0), new Range<Long>(328L, 342L)));

		types.put("化妆品", new GoodsParam(28, new Range<Double>(300.0, 800.0), new Range<Long>(43L, 61L)));

		types.put("饰品", new GoodsParam(29, new Range<Double>(700.0, 6000.0), new Range<Long>(114L, 131L)));

		types.put("零食", new GoodsParam(30, new Range<Double>(10.0, 80.0), new Range<Long>(63L, 92L)));

		types.put("家具", new GoodsParam(31, new Range<Double>(3000.0, 8000.0), new Range<Long>(2L, 41L)));

		types.put("保健品", new GoodsParam(32, new Range<Double>(300.0, 900.0), new Range<Long>(94L, 112L)));
		Set<Entry<String, GoodsParam>> typeMaps = types.entrySet();
		Random rand = new Random(new Date().getTime());
		for (Entry<String, GoodsParam> typeMap : typeMaps) {
			List<String> goodsNames = FileUtil.read(ROOTPATH + "GoodsName/" + typeMap.getKey() + ".txt");

			for (String goodsName : goodsNames) {
				GoodsParam param = typeMap.getValue();
				long type = param.getType();
				long range = param.getSupplier().getMax() - param.getSupplier().getMin();
				int intRange = Integer.parseInt(range + "");
				long supplier = rand.nextInt(intRange) + param.getSupplier().getMin();
				double priceRange = param.getPrice().getMax() - param.getPrice().getMin();
				double price = rand.nextDouble() * priceRange;

				Goods goods = new Goods();
				goods.setName(goodsName);
				goods.setPrice(price);
				goods.setStock(rand.nextInt());
				goods.setSupplier(supplier);
				goods.setDesc(Strings.EMPTY);
				goods.setType(type);
				
				

			}
		}

	}

}
