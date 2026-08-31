/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ProductMasterReleaseServiceTest {
    private final ProductMasterReleaseService service = new ProductMasterReleaseService();

    @Test void publishesGovernedGoldenProduct() {
        var result = service.assess(new ProductMasterReleaseService.Request(
            "SKU-001", true, true, true, false, false, true, true, 100, true, true));
        assertThat(result.decision()).isEqualTo(ProductMasterReleaseService.Decision.PUBLISH);
        assertThat(result.publishable()).isTrue();
    }

    @Test void blocksMasterDataAndRegulatoryFailures() {
        var result = service.assess(new ProductMasterReleaseService.Request(
            "SKU-002", false, false, false, true, false, false, false, 100, true, true));
        assertThat(result.decision()).isEqualTo(ProductMasterReleaseService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSize(6);
    }

    @Test void reviewsEnrichmentAndEffectiveDate() {
        var result = service.assess(new ProductMasterReleaseService.Request(
            "SKU-003", true, true, true, false, false, true, true, 80, false, false));
        assertThat(result.decision()).isEqualTo(ProductMasterReleaseService.Decision.REVIEW);
        assertThat(result.actions()).hasSize(3);
    }
}
