package com.npc.core.alarm.config;

import com.npc.core.alarm.AlarmFactoryExecute;
import com.npc.core.alarm.aop.AlarmAspect;
import com.npc.core.alarm.provider.AlarmTemplateProvider;
import com.npc.core.alarm.provider.JdbcAlarmTemplateProvider;
import com.npc.core.alarm.provider.MemoryAlarmTemplateProvider;
import com.npc.core.alarm.provider.YamlAlarmTemplateProvider;
import com.npc.core.alarm.service.DingTalkWarnService;
import com.npc.core.alarm.service.MailWarnService;
import com.npc.core.alarm.service.WorkWeXinWarnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NPC
 * @description 自动装配类，用于装载自定义的bean
 * @create 2023/4/9 15:37
 */
@Slf4j
@Configuration
public class AlarmAutoConfiguration {

    // 邮件相关配置装载
    @Configuration
    @ConditionalOnProperty(prefix = MailConfig.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties({MailConfig.class})
    static class MailWarnServiceMethod {

        @Bean
        @ConditionalOnMissingBean(MailWarnService.class)
        public MailWarnService mailWarnService(final MailConfig mailConfig) {
            MailWarnService mailWarnService = new MailWarnService(mailConfig.getSmtpHost(), mailConfig.getSmtpPort(), mailConfig.getTo(), mailConfig.getFrom(), mailConfig.getUsername(), mailConfig.getPassword());
            mailWarnService.setSsl(mailConfig.getSsl());
            mailWarnService.setDebug(mailConfig.getDebug());
            AlarmFactoryExecute.addAlarmLogWarnService(mailWarnService);
            return mailWarnService;
        }
    }

    // 企业微信相关配置装载
    @Configuration
    @ConditionalOnProperty(prefix = WorkWeXinConfig.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties({WorkWeXinConfig.class})
    static class WorkWechatWarnServiceMethod {

        @Bean
        @ConditionalOnMissingBean(MailWarnService.class)
        public WorkWeXinWarnService workWechatWarnService(final WorkWeXinConfig workWeXinConfig) {
            return new WorkWeXinWarnService(workWeXinConfig.getKey(), workWeXinConfig.getToUser());
        }

        @Autowired
        void setDataChangedListener(WorkWeXinWarnService workWeXinWarnService) {
            AlarmFactoryExecute.addAlarmLogWarnService(workWeXinWarnService);
        }
    }

    // 钉钉相关配置装载
    @Configuration
    @ConditionalOnProperty(prefix = DingTalkConfig.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties({DingTalkConfig.class})
    static class DingTalkWarnServiceMethod {

        @Bean
        @ConditionalOnMissingBean(DingTalkWarnService.class)
        public DingTalkWarnService dingTalkWarnService(final DingTalkConfig dingtalkConfig) {
            DingTalkWarnService dingTalkWarnService = new DingTalkWarnService(dingtalkConfig.getToken(), dingtalkConfig.getSecret());
            AlarmFactoryExecute.addAlarmLogWarnService(dingTalkWarnService);
            return dingTalkWarnService;
        }
    }

    // 消息模板配置装载
    @Configuration
    @ConditionalOnProperty(prefix = TemplateConfig.PREFIX, name = "enabled", havingValue = "true")
    @EnableConfigurationProperties({TemplateConfig.class})
    static class TemplateConfigServiceMethod {

        @Bean
        @ConditionalOnMissingBean
        public AlarmTemplateProvider alarmTemplateProvider(TemplateConfig templateConfig) {
            if ("FILE" == templateConfig.getSource()) {
                return new YamlAlarmTemplateProvider(templateConfig);
            } else if ("JDBC" == templateConfig.getSource()) {
                // 数据库（如mysql）读取文件，未实现，可自行扩展
                return new JdbcAlarmTemplateProvider(templateId -> null);
            } else if ("MEMORY" == templateConfig.getSource()) {
                // 内存（如redis，本地内存）读取文件，未实现，可自行扩展
                return new MemoryAlarmTemplateProvider(templateId -> null);
            }
            return new YamlAlarmTemplateProvider(templateConfig);
        }


    }
    @Bean
    public AlarmAspect alarmAspect(@Autowired(required = false) AlarmTemplateProvider alarmTemplateProvider) {
        return new AlarmAspect(alarmTemplateProvider);
    }
}
