import com.plm.dao.IUserDao;
import com.plm.domain.QueryVo;
import com.plm.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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

    private InputStream in;
    private SqlSession session;
    private IUserDao userDao;

    /**
     * 初始化
     * @throws Exception
     * @Before 定义此方法在测试方法之前执行
     */
    @Before
    public void init() throws Exception {
        // 1、读取配置文件
         in = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2、创建 SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        // 3、使用工厂生产 SqlSession对象
        // 如果在 factory.openSession(true);可以不需要session.commit();实现自动提交
         session = factory.openSession();
        // 4、使用 SqlSession创建 Dao接口的代理对象
         userDao = session.getMapper(IUserDao.class);
    }

    /**
     *  释放资源
     * @throws Exception
     * @After 定义此方法在测试方法之后执行
     */
    @After
    public void destory() throws Exception{

        // 6、手动提交事务  默认关闭自动提交事务功能
        session.commit();
        // 7、释放资源
        session.close();
        in.close();
    }

    /**
     * 测试查询所有
     */
    @Test
    public  void testFindAll() {

        // 5、使用代理对象执行方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println(user);
        }
    }

    /**
     * 保存用户信息
     */
    @Test
    public void testSaveUser() {
        // 要保存的用户信息
        User user = new User();
        user.setUsername("testSaveUser and get id");
        user.setAddress("武汉");
        user.setSex("男");
        user.setBirthday(new Date());

        System.out.println("保存操作之前 ："+user);
        // 5、使用代理对象执行方法
        userDao.saveUser(user);
        // 保存用户信息后，获取插入数据的 id
        System.out.println("保存操作之后 ："+user);
    }

    /**
     * 更新用户信息
     */
    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setId(53);
        user.setUsername("testUpdateUser");
        user.setAddress("武汉");
        user.setSex("男");
        user.setBirthday(new Date());
        // 5、使用代理对象执行方法
        userDao.updateUser(user);
    }

    /**
     *  根据 id 删除用户信息
     */
    @Test
    public void testDeleteUserById(){
        // 5、使用代理对象执行方法
        userDao.deleteUserById(53);
    }

    /**
     * 根据 id 查询用户
     */
    @Test
    public void testFindById(){
        // 5、使用代理对象执行方法
        User user = userDao.findById(51);
        System.out.println(user);
    }

    /**
     * 根据名称 模糊查询用户信息
     */
    @Test
    public void testFindByName(){
        // 5、使用代理对象执行方法
        List<User> lists = userDao.findByName("%王%");
        for(User user : lists){
            System.out.println(user);
        }
    }

    /**
     * 查询总用户数目
     */
    @Test
    public void testFindTotal(){
        // 5、使用代理对象执行方法
        int total = userDao.findTotal();
        System.out.println("total = "+total);
    }

    /**
     * 根据传入的条件查询用户信息
     */
    @Test
    public void testFindByCondition(){
        User user = new User();
        user.setUsername("老王");
        user.setSex("女");
        // 5、使用代理对象执行方法
        List<User> users = userDao.findUserByCondition(user);
        for(User u : users){
            System.out.println(u);
        }
    }

    /**
     *  根据 QueryVo 中提供的 id集合查询用户信息
     */
    @Test
    public void testFindUserInIds(){
        QueryVo queryVo = new QueryVo();
        List<Integer> lists = new ArrayList<Integer>();
        lists.add(41);
        lists.add(42);
        lists.add(46);
        queryVo.setIds(lists);

        // 5、使用代理对象执行方法
        List<User> users = userDao.findUserInIds(queryVo);
        for(User user : users){
            System.out.println(user);
        }
    }
}
