package com.npc.core.utils.uuid;

/**
 * @author NPC
 * @description 简易雪花算法实现
 * Snowflake生成的64位ID：1位符号位 + 41位时间戳位 + 10位数据中心ID和机器ID + 12位序列号
 * 符号位通常是0，保证ID是正数
 * 时间戳位 41位可以使用69年
 * 通常分为5位数据中心ID和5位机器ID，最多支持32个数据中心，每个数据中心最多32台机器
 * 序列号 记录同一毫秒内生成的不同ID，12位序列号支持每个节点每毫秒产生4096个ID序号
 * @create 2024/5/14 16:32
 */
public  class  SnowflakeIdGenerator  {

    private  long  datacenterId;  //  数据中心ID
    private  long  machineId;        //  机器ID
    private  long  sequence  =  0L;  //  序列号，用于同一毫秒内生成多个ID时区分
    private  long  lastTimestamp  =  -1L;  //  上一次时间戳，上一次生成ID的时间戳

    private  final  long  twepoch  =  1288834974657L; // 系统的起始时间戳
    private  final  long  datacenterIdBits  =  5L; // 数据中心ID所占的位数
    private  final  long  machineIdBits  =  5L; // 机器ID所占的位数
    private  final  long  maxDatacenterId  =  -1L  ^  (-1L  <<  datacenterIdBits); // 数据中心ID的最大值（位运算）
    private  final  long  maxMachineId  =  -1L  ^  (-1L  <<  machineIdBits); // 机器ID的最大值（位运算）
    private  final  long  sequenceBits  =  12L; // 序列号占用的位数

    private  final  long  machineIdShift  =  sequenceBits; // 机器ID的偏移位数
    private  final  long  datacenterIdShift  =  sequenceBits  +  machineIdBits; // 数据中心ID的偏移位数
    private  final  long  timestampLeftShift  =  sequenceBits  +  machineIdBits  +  datacenterIdBits; // 时间戳的偏移位数
    private  final  long  sequenceMask  =  -1L  ^  (-1L  <<  sequenceBits); // 保证序列号在指定范围内循环

    /**
     * 构造函数
     * @param datacenterId 数据中心ID
     * @param machineId 机器ID
     */
    public  SnowflakeIdGenerator(long  datacenterId,  long  machineId)  {
        if  (datacenterId  >  maxDatacenterId  ||  datacenterId  <  0)  {
            throw  new  IllegalArgumentException("datacenterId  can't  be  greater  than  %d  or  less  than  0");
        }
        if  (machineId  >  maxMachineId  ||  machineId  <  0)  {
            throw  new  IllegalArgumentException("machineId  can't  be  greater  than  %d  or  less  than  0");
        }
        this.datacenterId  =  datacenterId;
        this.machineId  =  machineId;
    }

    /**
     * ID生成（线程安全）
     * @return
     */
    public  synchronized  long  nextId()  {
        long  timestamp  =  System.currentTimeMillis();

        // 防止时钟回拨导致ID重复
        if  (timestamp  <  lastTimestamp)  {
            throw  new  RuntimeException("Clock  moved  backwards.  Refusing  to  generate  id");
        }

        // 如果是同一毫秒内，通过增加序列号生成不同的ID；如果序列号溢出，则等待下一毫秒内
        if  (lastTimestamp  ==  timestamp)  {
            sequence  =  (sequence  +  1)  &  sequenceMask;
            if  (sequence  ==  0)  {
                timestamp  =  tilNextMillis(lastTimestamp);
            }
        }  else  {
            // 如果当前时间戳大于上一次时间戳，重置序列号为0
            sequence  =  0L;
        }

        lastTimestamp  =  timestamp;

        // 组合
        return  ((timestamp  -  twepoch)  <<  timestampLeftShift)  |
                (datacenterId  <<  datacenterIdShift)  |
                (machineId  <<  machineIdShift)  |
                sequence;
    }

    /**
     * 序列号溢出后，等待到下一毫秒
     * @param lastTimestamp
     * @return
     */
    private  long  tilNextMillis(long  lastTimestamp)  {
        long  timestamp  =  System.currentTimeMillis();
        while  (timestamp  <=  lastTimestamp)  {
            timestamp  =  System.currentTimeMillis();
        }
        return  timestamp;
    }
}
