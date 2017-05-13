package org.sysu.sdcs.order.analysis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.dao.mapper.CustomerMapper;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsMapper;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsTypeMapper;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderDetailMapper;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderMapper;
import org.sysu.sdcs.order.analysis.dao.mapper.SupplierMapper;
import org.sysu.sdcs.order.analysis.dao.redis.RedisDAO;
import org.sysu.sdcs.order.analysis.model.mvc.request.EmailRequest;
import org.sysu.sdcs.order.analysis.service.classifier.CustomerClassifier;
import org.sysu.sdcs.order.analysis.service.email.EmailService;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.service.repository.CustomerRepository;
import org.sysu.sdcs.order.analysis.service.repository.GoodsRepository;
import org.sysu.sdcs.order.analysis.service.repository.GoodsTypeRepository;
import org.sysu.sdcs.order.analysis.service.repository.OrderRepository;
import org.sysu.sdcs.order.analysis.utils.common.JSONUtil;

@Controller
@RequestMapping("/utils")
public class UtilsController {
	private static final String ROOTPATH = "D:\\Workspace\\OrderAnalysis\\web\\src\\main\\resources\\data\\";
	@Autowired
	private EmailService emailService;
	@Autowired
	private RedisDAO redisDAO;
	@Autowired
	private CustomerClassifier classifier;
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private GoodsTypeMapper goodsTypeMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private CustomerRepository customerReposity;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private GoodsTypeRepository goodsTypeRepository;
	@Autowired
	private IndexStore indexStore;

	@ResponseBody
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public void sendEmail(EmailRequest request) {
		emailService.send(request.getRecipients(), request.getTitle(), request.getContent());
	}

	@ResponseBody
	@RequestMapping(value = "/redisSet/{key}/{value}", method = RequestMethod.GET)
	public boolean sendEmail(@PathVariable("key") String key, @PathVariable("value") String value) {
		return redisDAO.set(key, value.getBytes());
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCluster", method = RequestMethod.GET)
	public String getClusterByCustomer(int customer) {
		int cluster = classifier.getClusterByCustomer(customer);
		return JSONUtil.serialize(cluster);
	}
	
//
//	@ResponseBody
//	@RequestMapping(value = "/addSupplier", method = RequestMethod.GET)
//	public void addSupplier() {
//		List<String> suppliers = FileUtil
//				.read("D:\\Workspace\\OrderAnalysis\\web\\src\\main\\resources\\data\\Supplier.txt");
//		for (String supplierName : suppliers) {
//			Supplier supplier = new Supplier();
//			supplier.setName(supplierName);
//			supplier.setPhone(Strings.EMPTY);
//			try {
//				supplierMapper.add(supplier);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/addGoodsType", method = RequestMethod.GET)
//	public void addGoodsType() {
//		List<String> goodsTypes = FileUtil
//				.read("D:\\Workspace\\OrderAnalysis\\web\\src\\main\\resources\\data\\GoodsType.txt");
//		for (String goodsTypeDesc : goodsTypes) {
//			GoodsType goodsType = new GoodsType();
//			goodsType.setDesc(goodsTypeDesc);
//			try {
//				goodsTypeMapper.add(goodsType);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
//	public void addCustomer() {
//		List<String> customerNames = FileUtil
//				.read("D:\\Workspace\\OrderAnalysis\\web\\src\\main\\resources\\data\\CustomerName.txt");
//		Random rand = new Random(new Date().getTime());
//		for (String customerName : customerNames) {
//			Customer customer = new Customer();
//			customer.setName(customerName);
//			customer.setAge(12 + rand.nextInt(50));
//			customer.setSex(rand.nextBoolean() ? Sex.male : Sex.female);
//			customer.setAddress(Strings.EMPTY);
//			try {
//				customerMapper.add(customer);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/addGoods", method = RequestMethod.GET)
//	private void addGoods() {
//
//		Set<Entry<String, GoodsParam>> typeMaps = getGoodsParam().entrySet();
//		Random rand = new Random(new Date().getTime());
//		for (Entry<String, GoodsParam> typeMap : typeMaps) {
//			List<String> goodsNames = FileUtil.read(ROOTPATH + "GoodsName\\" + typeMap.getKey() + ".txt");
//
//			for (String goodsName : goodsNames) {
//				GoodsParam param = typeMap.getValue();
//				long type = param.getType();
//				long range = param.getSupplier().getMax() - param.getSupplier().getMin();
//				int intRange = Integer.parseInt(range + "");
//				long supplier = rand.nextInt(intRange) + param.getSupplier().getMin();
//				double priceRange = param.getPrice().getMax() - param.getPrice().getMin();
//				double price = rand.nextDouble() * priceRange + param.getPrice().getMin();
//				String pstr = String.format("%.2f", price);
//				Goods goods = new Goods();
//				goods.setName(goodsName);
//				goods.setPrice(Double.parseDouble(pstr));
//				goods.setStock(rand.nextInt(10000));
//				goods.setSupplier(supplier);
//				goods.setDesc(Strings.EMPTY);
//				goods.setType(type);
//				try {
//					goodsMapper.add(goods);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/addOrder", method = RequestMethod.GET)
//	public void addOrder() {
//		int T = 10000;
//		Random rand = new Random(new Date().getTime());
//		while (T-- != 0) {
//			Order order = new Order();
//			int size = customerReposity.getContent().size();
//			order.setCustomer(rand.nextInt(size) + 1);
//			order.setTime(DateUtil.add(DateUtil.getDateStart(new Date()), rand.nextInt(30) - 30));
//			try {
//				orderMapper.add(order);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/addOrderDetail", method = RequestMethod.GET)
//	public void addOrderDetail() {
//		int T = orderRepository.getContent().size();
//		Random rand = new Random(new Date().getTime());
//		while (T-- != 0) {
//			int append = rand.nextInt(3);
//			int mode = rand.nextInt(6);
//			insertDetail(T, mode, append);
//		}
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/addOrderDetail2", method = RequestMethod.GET)
//	public void addOrderDetail2() {
//		Random rand = new Random(new Date().getTime());
//		List<String> ignoreNum = FileUtil.read("D:\\Workspace\\OrderAnalysis\\web\\src\\main\\resources\\data\\answer.txt");
//		for(String i : ignoreNum) {
//			int append = rand.nextInt(3);
//			int mode = rand.nextInt(6);
//			insertDetail(Integer.parseInt(i), mode, append);
//		}
//	}
//
//	private void insertDetail(int order, int mode, int append) {
//		Random rand = new Random(new Date().getTime());
//		int typeSize = goodsTypeRepository.getContent().size();
//		List<List<Long>> typeComb = getTypeComb();
//		List<OrderDetail> details = new ArrayList<>();
//		if (mode != 5) {
//			List<Long> types = typeComb.get(mode);
//			for (Long type : types) {
//				OrderDetail detail = getOrderDetail(type, order, rand);
//				if (detail != null) {
//					try {
//						orderDetailMapper.add(detail);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			while (append-- != 0) {
//				long type = rand.nextInt(typeSize) + 1;
//				OrderDetail detail = getOrderDetail(type, order, rand);
//				if (detail != null) {
//					try {
//						orderDetailMapper.add(detail);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		} else {
//			int size = rand.nextInt(5) + 1;
//			while (size-- != 0) {
//				long type = rand.nextInt(typeSize) + 1;
//				OrderDetail detail = getOrderDetail(type, order, rand);
//				if (detail != null) {
//					try {
//						orderDetailMapper.add(detail);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		details.size();
//	}
//
//	private OrderDetail getOrderDetail(Long type, long order, Random rand) {
//		OrderDetail detail = new OrderDetail();
//		try {
//			List<String> goods = indexStore.get(IndexType.Type2Goods, type.toString());
//			if (goods == null || goods.isEmpty())
//				return null;
//			int i = rand.nextInt(goods.size());
//			String goodsIndexStr = goods.get(i);
//			GoodsModel goodsModel = goodsRepository.get(goodsIndexStr);
//			detail.setOrder(order);
//			detail.setCount(rand.nextInt(1) + 1);
//			detail.setGoods(Long.parseLong(goodsIndexStr));
//			detail.setPrice(goodsModel.getPrice());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return null;
//		}
//		return detail;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/getTypeComb", method = RequestMethod.GET)
//	public List<List<Long>> getTypeComb() {
//		List<List<Long>> result = new ArrayList<>();
//		String typeCombs = "2,3,4,5|7,8,9,10|12,13,14,15|17,18,19,20,21|23,24,25,26";
//		String[] typeComb = typeCombs.split("[|]");
//		for (String types : typeComb) {
//			String[] allType = types.split("[,]");
//			List<Long> typeList = new ArrayList<>();
//			for (String type : allType) {
//				typeList.add(Long.parseLong(type));
//			}
//			result.add(typeList);
//		}
//		return result;
//	}
//
//	private Map<String, GoodsParam> getGoodsParam() {
//		Map<String, GoodsParam> param = new HashMap<>();
//		param.put("电脑", new GoodsParam(2, new Range<Double>(3000.0, 7000.0), new Range<Long>(242L, 258L)));
//
//		param.put("CPU", new GoodsParam(3, new Range<Double>(1000.0, 4000.0), new Range<Long>(195L, 199L)));
//
//		param.put("显示屏", new GoodsParam(4, new Range<Double>(2000.0, 7000.0), new Range<Long>(201L, 222L)));
//
//		param.put("电脑外设", new GoodsParam(5, new Range<Double>(100.0, 700.0), new Range<Long>(224L, 240L)));
//
//		param.put("上衣", new GoodsParam(7, new Range<Double>(100.0, 500.0), new Range<Long>(278L, 306L)));
//		param.put("裤子", new GoodsParam(8, new Range<Double>(100.0, 600.0), new Range<Long>(278L, 306L)));
//		param.put("袜子", new GoodsParam(9, new Range<Double>(10.0, 40.0), new Range<Long>(278L, 306L)));
//		param.put("裙子", new GoodsParam(10, new Range<Double>(200.0, 800.0), new Range<Long>(278L, 306L)));
//
//		param.put("汽车", new GoodsParam(12, new Range<Double>(100000.0, 400000.0), new Range<Long>(133l, 153L)));
//
//		param.put("汽车玻璃", new GoodsParam(13, new Range<Double>(1000.0, 3000.0), new Range<Long>(167L, 178L)));
//
//		param.put("机油", new GoodsParam(14, new Range<Double>(200.0, 500.0), new Range<Long>(155L, 163L)));
//
//		param.put("坐垫", new GoodsParam(15, new Range<Double>(200.0, 500.0), new Range<Long>(180L, 193L)));
//
//		param.put("手机", new GoodsParam(17, new Range<Double>(1000.0, 8000.0), new Range<Long>(308L, 326L)));
//		param.put("手机膜", new GoodsParam(18, new Range<Double>(20.0, 40.0), new Range<Long>(308L, 326L)));
//		param.put("手机壳", new GoodsParam(19, new Range<Double>(10.0, 30.0), new Range<Long>(308L, 326L)));
//		param.put("手机饰品", new GoodsParam(20, new Range<Double>(10.0, 50.0), new Range<Long>(308L, 326L)));
//		param.put("手机耳机", new GoodsParam(21, new Range<Double>(100.0, 400.0), new Range<Long>(308L, 326L)));
//
//		param.put("乒乓球", new GoodsParam(23, new Range<Double>(10.0, 30.0), new Range<Long>(328L, 342L)));
//		param.put("篮球", new GoodsParam(24, new Range<Double>(100.0, 300.0), new Range<Long>(328L, 342L)));
//		param.put("羽毛球", new GoodsParam(25, new Range<Double>(10.0, 40.0), new Range<Long>(328L, 342L)));
//		param.put("自行车", new GoodsParam(26, new Range<Double>(300.0, 700.0), new Range<Long>(328L, 342L)));
//
//		param.put("化妆品", new GoodsParam(28, new Range<Double>(300.0, 800.0), new Range<Long>(43L, 61L)));
//
//		param.put("饰品", new GoodsParam(29, new Range<Double>(700.0, 6000.0), new Range<Long>(114L, 131L)));
//
//		param.put("零食", new GoodsParam(30, new Range<Double>(10.0, 80.0), new Range<Long>(63L, 92L)));
//
//		param.put("家具", new GoodsParam(31, new Range<Double>(3000.0, 8000.0), new Range<Long>(2L, 41L)));
//
//		param.put("保健品", new GoodsParam(32, new Range<Double>(300.0, 900.0), new Range<Long>(94L, 112L)));
//		return param;
//	}
}
