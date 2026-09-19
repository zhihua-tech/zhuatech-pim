/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.config;

import cn.zhuatech.pim.model.*;
import cn.zhuatech.pim.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration
public class DataInitializer {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Bean
    CommandLineRunner seed(ChannelRepository channels, ProductRecordRepository orders,
                           AttributeSetRepository attributeSets, EnrichmentTaskRepository enrichmentTasks,
                           UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (channels.count() > 0) return;
            Channel chemistry = channels.save(new Channel("CH-CHEM", "电商渠道", "产品数据中心", 180));
            Channel micro = channels.save(new Channel("CH-MICRO", "经销渠道", "研发中心", 120));
            Channel material = channels.save(new Channel("CH-MAT", "产品数据中心", "工程中心", 96));

            ProductRecord t1 = orders.save(new ProductRecord("PRD-260801-018", "GB-T-228", "智能网关产品主档", material, 24, 16, 1, LocalDate.now().plusDays(1), ProductRecord.Status.RUNNING, "S260801-A"));
            ProductRecord t2 = orders.save(new ProductRecord("PRD-260801-021", "HPLC-042", "工业平板产品主档", chemistry, 18, 8, 0, LocalDate.now().plusDays(1), ProductRecord.Status.RUNNING, "S260801-C"));
            ProductRecord t3 = orders.save(new ProductRecord("PRD-260802-006", "ISO-4833", "边缘服务器产品主档", micro, 12, 0, 0, LocalDate.now().plusDays(3), ProductRecord.Status.RELEASED, "S260802-B"));
            ProductRecord t4 = orders.save(new ProductRecord("PRD-260731-015", "ICP-017", "温湿度传感器产品主档", chemistry, 20, 20, 1, LocalDate.now(), ProductRecord.Status.COMPLETED, "S260731-D"));

            attributeSets.saveAll(List.of(
                new AttributeSet("ATTR-HPLC-03", "工业网关属性集 03", chemistry, AttributeSet.Status.RUNNING, 88),
                new AttributeSet("ATTR-ICP-02", "工业平板属性集", chemistry, AttributeSet.Status.IDLE, 76),
                new AttributeSet("ATTR-UTM-05", "边缘计算属性集", material, AttributeSet.Status.RUNNING, 91),
                new AttributeSet("ATTR-INC-08", "传感器属性集 08", micro, AttributeSet.Status.ALARM, 62)
            ));
            enrichmentTasks.saveAll(List.of(
                new EnrichmentTask("ENR-260801-032", t1, "留样审核", 6, 0, EnrichmentTask.Result.PASSED, "周妍"),
                new EnrichmentTask("ENR-260801-011", t2, "前处理审核", 3, 0, EnrichmentTask.Result.PASSED, "陆承"),
                new EnrichmentTask("ENR-260801-018", t4, "结果审核", 5, 1, EnrichmentTask.Result.FAILED, "周妍"),
                new EnrichmentTask("ENR-260802-003", t3, "收样确认", 4, 0, EnrichmentTask.Result.PENDING, "陆承")
            ));
            String demo = encoder.encode("Demo@2026");
            users.saveAll(List.of(
                new UserAccount("operator", demo, "陆承", UserAccount.Role.CONTENT_STEWARD, "CH-CHEM"),
                new UserAccount("planner", demo, "周妍", UserAccount.Role.PIM_MANAGER, null),
                new UserAccount("quality", demo, "顾清", UserAccount.Role.QUALITY, null),
                new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, null)
            ));
        };
    }
}
