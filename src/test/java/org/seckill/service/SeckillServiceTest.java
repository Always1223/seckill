package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);    //花括号代表占位符，逗号后面的对应占位符的值
    }

    @Test
    public void getById() {
        long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
        /**
         * Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6692b6c6]
         *
         * seckill=Seckill{seckillId=1000, name='1000元秒杀iphone6', number=99, startTime=Mon Jul 01 00:00:00 CST 2019, endTime=Tue Nov 12 00:00:00 CST 2019, createTime=Thu Jul 04 09:30:28 CST 2019}
         */
    }

    @Test
    public void exportSeckillUrl() {
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
        /**
         * exposer=
         * Exposer{exposed=true,
         * md5='ca0d78165838fb7a1039a63eb00665ef',
         * seckillId=1000,
         * now=0, start=0, end=0}
         */
    }

    @Test
    public void executeSeckill() {
        long id = 1000L;
        long phone = 15267021120L;
        String md5 = "ca0d78165838fb7a1039a63eb00665ef";
        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", execution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
        }
        /**
         * 当phone = 15267021120L时：
         * Creating a new SqlSession
         * Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@27adc16e] will be managed by Spring
         * Preparing: UPDATE seckill SET number = (number - 1) WHERE ( seckill_id = ? and start_time <= ? and end_time >= ? and number >= 0)
         * Parameters: 1000(Long), 2019-06-24 14:06:03.18(Timestamp), 2019-06-24 14:06:03.18(Timestamp)
         * Updates: 1
         * Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e] from current transaction
         * Preparing: insert ignore into success_killed(seckill_id, user_phone, state) values (?, ?, 0)
         * Parameters: 1000(Long), 15267021120(Long)
         * Updates: 0
         * Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * seckill repeated
         *
         *
         * 当phone = 15267021121L时：
         * Creating a new SqlSession
         * Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@27adc16e] will be managed by Spring
         * Preparing: UPDATE seckill SET number = (number - 1) WHERE ( seckill_id = ? and start_time <= ? and end_time >= ? and number >= 0)
         * Parameters: 1000(Long), 2019-06-24 14:14:34.515(Timestamp), 2019-06-24 14:14:34.515(Timestamp)
         * Updates: 1
         * Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e] from current transaction
         * Preparing: insert ignore into success_killed(seckill_id, user_phone, state) values (?, ?, 0)
         * Parameters: 1000(Long), 15267021121(Long)
         * Updates: 1
         * Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e] from current transaction
         * Preparing: select sk.seckill_id, sk.user_phone, sk.state, sk.create_time, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" from success_killed sk inner join seckill s on sk.seckill_id = s.seckill_id where sk.seckill_id = ? and sk.user_phone = ?
         * Parameters: 1000(Long), 15267021121(Long)
         * Total: 1
         * Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5495333e]
         * result=
         * SeckillExecution{seckillId=1000, state=1, stateInfo='秒杀成功',
         * successKilled=SuccessKilled{seckillId=1000, userPhone=15267021121, state=0, createTime=Sat Jul 06 16:15:15 CST 2019}}
         */
    }

    @Test
    public void executeSeckillProcedure() {
        long seckillId = 1001;
        long phone = 13587280002L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info(execution.getStateInfo());
        }
    }
}