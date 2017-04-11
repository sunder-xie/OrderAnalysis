package org.sysu.sdcs.order.analysis.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.index.OrderIndex;
import org.sysu.sdcs.order.analysis.service.calculate.VarMemorizer;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.service.repository.ConfigureHelper;
import org.sysu.sdcs.order.analysis.service.repository.CustomerRepository;
import org.sysu.sdcs.order.analysis.service.repository.GoodsRepository;
import org.sysu.sdcs.order.analysis.service.repository.GoodsTypeRepository;
import org.sysu.sdcs.order.analysis.service.repository.OrderRepository;
import org.sysu.sdcs.order.analysis.service.repository.SupplierRepository;
import org.sysu.sdcs.order.analysis.utils.common.JSONUtil;

@Controller
@RequestMapping("/repository")
public class RepositoryController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private GoodsTypeRepository goodsTypeRepository;
	@Autowired
	private SupplierRepository supplierRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private IndexStore indexStore;
	@Autowired
	private ConfigureHelper configureHelper;
	@Autowired
	private VarMemorizer pointCache;

	private static final String PRODUCES = "text/json;charset=UTF-8";

	@ResponseBody
	@RequestMapping(value = "/customer", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllCustomer() throws Exception {
		return JSONUtil.serialize(customerRepository.getContent());
	}

	@ResponseBody
	@RequestMapping(value = "/goods", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllGoods() throws Exception {
		return JSONUtil.serialize(goodsRepository.getContent());
	}

	@ResponseBody
	@RequestMapping(value = "/goodsType", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllGoodsType() throws Exception {
		return JSONUtil.serialize(goodsTypeRepository.getContent());
	}

	@ResponseBody
	@RequestMapping(value = "/supplier", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllSupplier() throws Exception {
		return JSONUtil.serialize(supplierRepository.getContent());
	}

	@ResponseBody
	@RequestMapping(value = "/order", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllOrder() throws Exception {
		return JSONUtil.serialize(orderRepository.getContent());
	}

	@ResponseBody
	@RequestMapping(value = "/index", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllIndex() throws Exception {
		Map<IndexType, OrderIndex> allIndex = new HashMap<>();
		for (IndexType type : IndexType.values()) {
			allIndex.put(type, indexStore.getInstance(type));
		}
		return JSONUtil.serialize(allIndex);
	}

	@ResponseBody
	@RequestMapping(value = "/cluster", method = RequestMethod.GET, produces = PRODUCES)
	public String getCluster() throws Exception {
		return JSONUtil.serialize(pointCache.getClusters());
	}

	@ResponseBody
	@RequestMapping(value = "/point", method = RequestMethod.GET, produces = PRODUCES)
	public String getPoints() throws Exception {
		return JSONUtil.serialize(pointCache.getPoints());
	}

	@ResponseBody
	@RequestMapping(value = "/distance", method = RequestMethod.GET, produces = PRODUCES)
	public String getDistances() throws Exception {
		return JSONUtil.serialize(pointCache.getDistances());
	}

	@ResponseBody
	@RequestMapping(value = "/pointStatus", method = RequestMethod.GET, produces = PRODUCES)
	public String getStatus() throws Exception {
		return JSONUtil.serialize(pointCache.getPointStatus());
	}

	@ResponseBody
	@RequestMapping(value = "/configure", method = RequestMethod.GET, produces = PRODUCES)
	public String getConfig() throws Exception {
		return JSONUtil.serialize(configureHelper.getAllConfigure());
	}
}
