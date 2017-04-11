package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sysu.sdcs.order.analysis.dao.mapper.SupplierMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Supplier;

/**
 * Unit test for RedisDAO.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring*.xml")
public class MapperTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private SupplierMapper mapper;

	@Test
	public void testSet() {
		Supplier supplier = new Supplier();
		supplier.setName("test");
		supplier.setPhone("test");
		try {
			mapper.add(supplier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
