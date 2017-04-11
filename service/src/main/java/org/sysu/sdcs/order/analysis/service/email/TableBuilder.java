package org.sysu.sdcs.order.analysis.service.email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.ClusterModel;
import org.sysu.sdcs.order.analysis.service.repository.GoodsTypeRepository;

@Component
public class TableBuilder {
	private static final String prefix = "<html><head><meta charset=\'UTF-8\'/></head><body>";
	private static final String suffix = "</body></html>";
	private static final String table = "<table>";
	private static final String _table = "</table>";
	private static final String th = "<th>";
	private static final String _th = "</th>";
	private static final String tr = "<tr>";
	private static final String _tr = "</tr>";
	private static final String td = "<td>";
	private static final String _td = "</td>";
	private static final int MAX_TYPE = 4;
	@Autowired
	private GoodsTypeRepository repository;

	public String getTable(String title, List<ClusterModel> cluster) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(title);
		sb.append(table);
		int i = 1;
		for (ClusterModel clusterModel : cluster) {
			appendContent(i, clusterModel, sb);
			i++;
		}
		sb.append(_table);
		sb.append(suffix);
		return sb.toString();
	}

	private void appendContent(int index, ClusterModel cluster, StringBuilder sb) {
		sb.append(tr);
		sb.append(th);
		sb.append("Cluster-");
		sb.append(index);
		sb.append(_th);
		sb.append(_tr);
		sb.append(tr);
		sb.append(td);
		sb.append("type");
		sb.append(_td);
		sb.append(td);
		sb.append("count");
		sb.append(_td);
		sb.append(_tr);
		for (int i = 0; i < MAX_TYPE; i++) {
			if (i >= cluster.getCounter().size()) {
				break;
			}
			sb.append(tr);
			String type = cluster.getCounter().get(i).getType();
			int count = cluster.getCounter().get(i).getSize();
			sb.append(td);
			sb.append(repository.get(type).getDesc());
			sb.append(_td);
			sb.append(td);
			sb.append(count);
			sb.append(_td);
			sb.append(_tr);
		}
	}
}
