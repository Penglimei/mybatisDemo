import com.plm.dao.IUserDao;
import com.plm.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;


/**
 *  MyBatis执行 Dao接口方法中间涉及的设计模式：
 *      创建者模式：
 *          用 SqlSessionFactoryBuilder创建 SqlSessionFactory工厂对象
 *
 *              优点：把对象创建细节隐藏，直接调用方法就可以拿到对象
 *
 *      工厂模式：
 *          用 SqlSessionFactory创建 SqlSession对象
 *
 *              优点；降低类之间的依赖关系（解耦）
 *
 *      代理模式：
 *          用 SqlSession创建 Dao接口的代理对象
 *
 *              优点：不修改源码的基础上对已有方法增强
 */
public class MyBatisTest {

    public static void main(String[] args) throws Exception {
        // 1、读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2、创建 SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        // 3、使用工厂生产 SqlSession对象
        SqlSession session = factory.openSession();
        // 4、使用 SqlSession创建 Dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        // 5、使用代理对象执行方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println(user);
        }
        // 6、 释放资源
        session.close();
        in.close();
    }
}
