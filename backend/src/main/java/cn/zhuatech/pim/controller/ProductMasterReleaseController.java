/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.controller;

import cn.zhuatech.pim.common.ApiResponse;
import cn.zhuatech.pim.service.ProductMasterReleaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/pim")
public class ProductMasterReleaseController {
    private final ProductMasterReleaseService service;
    public ProductMasterReleaseController(ProductMasterReleaseService service) { this.service = service; }

    @PostMapping("/product-master-release")
    public ApiResponse<ProductMasterReleaseService.Assessment> assess(
        @Valid @RequestBody ProductMasterReleaseService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
