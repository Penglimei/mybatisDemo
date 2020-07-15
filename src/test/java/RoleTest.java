import com.plm.dao.IAccountDao;
import com.plm.dao.IRoleDao;
import com.plm.domain.Account;
import com.plm.domain.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RoleTest {

    private InputStream in;
    private SqlSession session;
    private IRoleDao roleDao;

    @Before
    public void init() throws Exception {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        roleDao = session.getMapper(IRoleDao.class);
    }

    @After
    public void destory() throws IOException {

        session.commit();
        session.close();
        in.close();
    }

    /**
     * 查询所有角色信息
     */
    @Test
    public void testFindAll(){

        List<Role> roles = roleDao.findAll();

        for (Role role : roles){
            System.out.println("=========每个 role 的信息========");
            System.out.println(role);
            System.out.println(role.getUsers());
        }
    }
}
