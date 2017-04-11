package org.sysu.sdcs.order.analysis.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.interfaces.Handler;
import org.sysu.sdcs.order.analysis.service.calculate.VarMemorizer;
import org.sysu.sdcs.order.analysis.service.email.EmailService;
import org.sysu.sdcs.order.analysis.service.email.TableBuilder;
import org.sysu.sdcs.order.analysis.service.processor.ClusterProcessor;
import org.sysu.sdcs.order.analysis.service.processor.DistanceProcessor;
import org.sysu.sdcs.order.analysis.service.processor.PointProcessor;
import org.sysu.sdcs.order.analysis.service.processor.PointStatusProcessor;

@Service
public class ClusterProcessHandler implements Handler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClusterProcessHandler.class);
	@Autowired
	private DistanceProcessor distanceProcessor;
	@Autowired
	private PointProcessor pointProcessor;
	@Autowired
	private PointStatusProcessor pointStatusProcessor;
	@Autowired
	private ClusterProcessor clusterProcessor;
	@Autowired
	private VarMemorizer memorizer;
	@Autowired
	private EmailService emailService;
	@Autowired
	private TableBuilder tableBuilder;
	private String TITLE = "Analysis Result";

	@Override
	public void handle() {
		LOGGER.info("Begin update all cluster processor.");
		long beginTime = System.currentTimeMillis();
		memorizer.initial();
		pointProcessor.process();
		distanceProcessor.process();
		pointStatusProcessor.process();
		clusterProcessor.process();
		emailService.send(TITLE, tableBuilder.getTable(TITLE, memorizer.getClusterModel()));
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update all cluster processor, spend {}ms.", finishTime - beginTime);
	}

}
