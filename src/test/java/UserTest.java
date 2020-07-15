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
public class UserTest {

    private InputStream in;
    private SqlSessionFactory factory;
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
        factory = builder.build(in);
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
     * 测试查询所有用户信息，以及对应的账户信息
     */
    @Test
    public  void testFindAll() {

        // 5、使用代理对象执行方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println("========每个用户的信息========");
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }

    /**
     *  测试查询所有用户信息，以及对应的角色信息
     */
    @Test
    public void testFindAllandRole(){
        // 5、使用代理对象执行方法
        List<User> users = userDao.findAllandRole();
        for (User user : users){
            System.out.println("========每个用户的信息========");
            System.out.println(user);
            System.out.println(user.getRoles());
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

    /**
     *  测试 SqlSession对象的一级缓存
     *
     *      MyBatis 的一级缓存
     *          指的是 SqlSession对象的缓存
     *          查询后，查询结果会存入SqlSession对象提供的一个区域（结构是一个Map），
     *          再次查询时，会先去SqlSession对中查询是否已经存在，如果存在直接取出来使用。
     *
     *          当 SqlSession对象消失后(session.close()、session.clearCache())，
     *          一级缓存也就消失了。
     *
     *          一级缓存是SqlSession对象范围的缓存，当调用 SqlSession的修改、删除、添加、
     *          commit()、close()等方法时，会清空一级缓存。
     */
    @Test
    public void testFirstLevelCache(){

        User user = userDao.findById(41);
        System.out.println("user = "+user);
        User user1 = userDao.findById(41);
        System.out.println("user1 = "+user1);
        System.out.println("user == user1 "+(user == user1)); // true


        System.out.println("======测试更新用户信息后一级缓存会被清除=======");
        user = userDao.findById(49);
        System.out.println("user = "+user);

        // 更新用户信息
        user.setUsername("update user clear cache");
        user.setAddress("dssds");
        userDao.updateUser(user);

        user1 = userDao.findById(49);
        System.out.println("user1 = "+user1);
        System.out.println("user == user1 "+(user == user1)); // false
    }

    /**
     *  测试 SqlSessionFactory对象的二级缓存
     *
     *      同一个SqlSessionFactory对象创建的SqlSession共享缓存
     *
     *      使用步骤：
     *          （1）让MyBatis框架支持二级缓存（SqlMapConfig.xml内配置）
     *              <setting name="cacheEnabled" value="true"/>
     *          （2）让当前的映射文件支持二级缓存（IUserDao.xml内配置）
     *              <cache></cache>
     *          （3）让当前的操作支持二级缓存（select标签内配置）
     *              useCache="true"
     *
     *      二级缓存中存放的是数据而不是对象，当再次查询时，二级缓存中有对应数据，
     *      获取这个数据，自动创建对应的实体类进行封装后返回新的对象，因此使用
     *      user1 == user2;进行对象比较时返回false
     */
    @Test
    public void testSecondLevelCache(){

        SqlSession sqlSession1 = factory.openSession();
        IUserDao dao1 = sqlSession1.getMapper(IUserDao.class);
        User user1 = dao1.findById(41);
        System.out.println("user1 = "+user1);
        sqlSession1.commit(); // 关闭一级缓存

        SqlSession sqlSession2 = factory.openSession();
        IUserDao dao2 = sqlSession2.getMapper(IUserDao.class);
        User user2 = dao2.findById(41);
        System.out.println("user2 = "+user2);
        sqlSession2.commit(); // 关闭一级缓存

        System.out.println("user1 == user2 "+(user1 == user2)); // false
    }
}
