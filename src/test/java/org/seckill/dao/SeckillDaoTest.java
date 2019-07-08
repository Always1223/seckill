package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/**
 * ����spring��junit���ϣ���Ϊ��junit����ʱ����springIOC����
 * spring-test,junit
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�����ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //ע��DAOʵ��������
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() {
        long id = 1000L;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /**
         * 1000Ԫ��ɱiphone6
         * Seckill{seckillId=1000,
         * name='1000Ԫ��ɱiphone6',
         * number=100,
         * startTime=Mon Jul 01 00:00:00 CST 2019,
         * endTime=Tue Nov 12 00:00:00 CST 2019,
         * createTime=Thu Jul 04 09:30:28 CST 2019}
         */
    }

    @Test
    public void queryAll() {
        /**
         * Caused by: org.apache.ibatis.binding.BindingException:
         * Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
         *
         * List<Seckill> queryAll(int offset, int limit)
         * javaû�б����βεļ�¼��queryAll(int offset, int limit)->queryAll(args 0, args 1)
         *
         * Seckill{seckillId=1000, name='1000Ԫ��ɱiphone6', number=100, startTime=Mon Jul 01 00:00:00 CST 2019, endTime=Tue Nov 12 00:00:00 CST 2019, createTime=Thu Jul 04 09:30:28 CST 2019}
         * Seckill{seckillId=1001, name='500Ԫ��ɱipad2', number=200, startTime=Mon Jul 01 00:00:00 CST 2019, endTime=Tue Jul 02 00:00:00 CST 2019, createTime=Thu Jul 04 09:30:28 CST 2019}
         * Seckill{seckillId=1002, name='300Ԫ��ɱС��4', number=300, startTime=Fri Jul 05 00:00:00 CST 2019, endTime=Tue Nov 12 00:00:00 CST 2019, createTime=Thu Jul 04 09:30:28 CST 2019}
         * Seckill{seckillId=1003, name='200Ԫ��ɱ����note', number=400, startTime=Fri Nov 01 00:00:00 CST 2019, endTime=Tue Nov 12 00:00:00 CST 2019, createTime=Thu Jul 04 09:30:28 CST 2019}
         */
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for(Seckill seckill : seckills)
            System.out.println(seckill);
    }

    @Test
    public void reduceNumber() {
        /**
         * JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@1d483de4] will not be managed by Spring
         * Preparing: UPDATE seckill SET number = (number - 1) WHERE ( seckill_id = ? and start_time <= ? and end_time >= ? and number >= 0)
         * Parameters: 1000(Long), 2019-07-05 18:38:33.8(Timestamp), 2019-07-05 18:38:33.8(Timestamp)
         * Updates: 1
         * Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7fc4780b]
         * updateCount = 1
         */
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount = " + updateCount);
    }


}