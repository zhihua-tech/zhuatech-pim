/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.*;
import java.util.*;
import org.junit.jupiter.api.Test;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
class ChannelContentRegressionTests {
  private final ChannelReadinessService service = new ChannelReadinessService();

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void zeroImageRequirementIsSatisfied() {
    var r =
        service.evaluate(new ChannelReadinessService.Request("SKU", 1, 1, 0, 0, 0, 0, true, true));
    assertEquals("READY", r.status());
    assertEquals(100, r.completenessScore());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void requiredImagesRemainAGap() {
    var r =
        service.evaluate(new ChannelReadinessService.Request("SKU", 1, 1, 0, 1, 0, 0, true, true));
    assertNotEquals("READY", r.status());
    assertTrue(r.gaps().contains("补充渠道要求的商品图片"));
  }
}
