package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * ҵ��ӿڣ�վ�ڡ�ʹ���ߡ��Ƕ���ƽӿ�  ��ʹ���ߣ�ʹ���ߣ�ʹ���ߣ���
 * �������棺�����������ȣ��������������ͣ�return ����/�쳣��
 */
public interface SeckillService {

    /**
     * ��ѯ������ɱ��¼
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * ��ѯ������ɱ��¼
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * ��ɱ����ʱ�����ɱ�ӿڵ�ַ���������ϵͳʱ�����ɱ����ʱ��
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * ִ����ɱ����
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, SeckillCloseException, RepeatKillException;

    /**
     * ִ����ɱ���� by �洢����
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
