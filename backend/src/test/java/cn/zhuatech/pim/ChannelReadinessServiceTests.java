/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim;
import cn.zhuatech.pim.service.ChannelReadinessService;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class ChannelReadinessServiceTests {private final ChannelReadinessService service=new ChannelReadinessService();
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void approvesCompleteProduct(){var r=service.evaluate(new ChannelReadinessService.Request("SKU1",10,10,3,3,2,2,true,true));assertEquals(100,r.completenessScore());assertEquals("READY",r.status());}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void blocksSparseProduct(){var r=service.evaluate(new ChannelReadinessService.Request("SKU2",10,4,0,3,2,0,false,false));assertEquals("BLOCKED",r.status());}}
