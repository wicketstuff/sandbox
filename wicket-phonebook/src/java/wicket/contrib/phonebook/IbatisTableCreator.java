package wicket.contrib.phonebook;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * Created by IntelliJ IDEA.
 * User: gwyeva1
 * Date: 28-May-2006
 * Time: 09:05:59
 * To change this template use File | Settings | File Templates.
 */
public class IbatisTableCreator extends SqlMapClientDaoSupport implements InitializingBean {

    @Override
	protected void initDao() throws Exception {
        super.initDao();
        getSqlMapClientTemplate().insert("createTable", null);
    }
}
