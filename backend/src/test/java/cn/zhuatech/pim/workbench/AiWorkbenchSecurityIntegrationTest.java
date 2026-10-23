/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.workbench;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cn.zhuatech.pim.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@SpringBootTest
@AutoConfigureMockMvc
class AiWorkbenchSecurityIntegrationTest {
  @Autowired MockMvc mvc;
  @Autowired JwtService jwt;

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void securityFilterRejectsAnonymous() throws Exception {
    mvc.perform(
            post("/api/workbench/channel-content-matrix")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"fields\":[{\"id\":\"title\",\"value\":\"商品A\"},{\"id\":\"description\",\"value\":\"\"}],\"imageIds\":[\"IMG1\"],\"availableLocales\":[\"zh-CN\"],\"channels\":[{\"id\":\"WEB\",\"requiredFields\":[\"title\",\"description\"],\"minimumImages\":2,\"requiredLocales\":[\"zh-CN\",\"en-US\"]}]}"))
        .andExpect(status().isUnauthorized());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void securityFilterRejectsInvalidBearer() throws Exception {
    mvc.perform(
            post("/api/workbench/channel-content-matrix")
                .header("Authorization", "Bearer invalid-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"fields\":[{\"id\":\"title\",\"value\":\"商品A\"},{\"id\":\"description\",\"value\":\"\"}],\"imageIds\":[\"IMG1\"],\"availableLocales\":[\"zh-CN\"],\"channels\":[{\"id\":\"WEB\",\"requiredFields\":[\"title\",\"description\"],\"minimumImages\":2,\"requiredLocales\":[\"zh-CN\",\"en-US\"]}]}"))
        .andExpect(status().isUnauthorized());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void authenticatedCallerCanCompute() throws Exception {
    mvc.perform(
            post("/api/workbench/channel-content-matrix")
                .header("Authorization", "Bearer " + jwt.generate("operator"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"fields\":[{\"id\":\"title\",\"value\":\"商品A\"},{\"id\":\"description\",\"value\":\"\"}],\"imageIds\":[\"IMG1\"],\"availableLocales\":[\"zh-CN\"],\"channels\":[{\"id\":\"WEB\",\"requiredFields\":[\"title\",\"description\"],\"minimumImages\":2,\"requiredLocales\":[\"zh-CN\",\"en-US\"]}]}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void authenticatedCallerGetsValidationError() throws Exception {
    mvc.perform(
            post("/api/workbench/channel-content-matrix")
                .header("Authorization", "Bearer " + jwt.generate("operator"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void existingBusinessEndpointRegression() throws Exception {
    mvc.perform(
            post("/api/pim/insights/channel-readiness")
                .header("Authorization", "Bearer " + jwt.generate("planner"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"sku\":\"SKU\",\"requiredAttributes\":1,\"populatedAttributes\":1,\"availableImages\":0,\"minimumImages\":0,\"translationsRequired\":0,\"translationsCompleted\":0,\"taxonomyMapped\":true,\"approved\":true}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.status").value("READY"));
  }
}
