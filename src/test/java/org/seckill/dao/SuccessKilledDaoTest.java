package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        /**
         * JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@47a64f7d] will not be managed by Spring
         * Preparing: insert ignore into success_killed(seckill_id, user_phone, state) values (?, ?, 0)
         * Parameters: 1000(Long), 15267021120(Long)
         * Updates: 1
         * Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@46cf05f7]
         *
         * 第一次运行：insertCount = 1
         * 第二次运行：insertCount = 0
         */
        long id = 1000L;
        long phone = 15267021120L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount = " + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long id = 1000L;
        long phone = 15267021120L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
        /**
         * JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@4d0d9fe7] will not be managed by Spring
         * Preparing: select sk.seckill_id, sk.user_phone, sk.state, sk.create_time, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" from success_killed sk inner join seckill s on sk.seckill_id = s.seckill_id where sk.seckill_id = ? and sk.user_phone = ?
         * Parameters: 1000(Long), 15267021120(Long)
         * Total: 1
         * Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5f049ea1]
         * SuccessKilled{seckillId=1000,
         * userPhone=15267021120,
         * state=0,
         * createTime=Sat Jul 06 11:37:15 CST 2019}
         * Seckill{seckillId=1000,
         * name='1000元秒杀iphone6',
         * number=99,
         * startTime=Mon Jul 01 00:00:00 CST 2019,
         * endTime=Tue Nov 12 00:00:00 CST 2019,
         * createTime=Thu Jul 04 09:30:28 CST 2019}
         */
    }
}