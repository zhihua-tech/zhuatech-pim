/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.controller;
import cn.zhuatech.pim.common.ApiResponse;import cn.zhuatech.pim.service.ChannelReadinessService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/pim/insights/channel-readiness") public class ChannelReadinessController {private final ChannelReadinessService service;/**
                                                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                               */
public ChannelReadinessController(ChannelReadinessService service){this.service=service;}/**
                                                                                                                                                                                                                                                        * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                        */
@PostMapping ApiResponse<ChannelReadinessService.Result> evaluate(@Valid @RequestBody ChannelReadinessService.Request request){return ApiResponse.ok(service.evaluate(request));}}
