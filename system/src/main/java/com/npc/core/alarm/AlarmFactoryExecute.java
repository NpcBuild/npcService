package com.npc.core.alarm;

import com.npc.core.alarm.service.AlarmWarnService;
import com.npc.core.alarm.template.NotifyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NPC
 * @description 内部保存了一个容器，主要用于缓存真正的发送类
 * @create 2023/4/9 15:29
 */
public class AlarmFactoryExecute {
    private static List<AlarmWarnService> serviceList = new ArrayList<>();

    public AlarmFactoryExecute(List<AlarmWarnService> alarmLogWarnServices) {
        serviceList = alarmLogWarnServices;
    }

    public static void addAlarmLogWarnService(AlarmWarnService alarmLogWarnService) {
        serviceList.add(alarmLogWarnService);
    }

    public static List<AlarmWarnService> getServiceList() {
        return serviceList;
    }

    public static void execute(NotifyMessage notifyMessage) {
        for (AlarmWarnService alarmWarnService : getServiceList()) {
            alarmWarnService.send(notifyMessage);
        }
    }

}
