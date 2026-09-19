/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class ProductMasterReleaseService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.dataOwnerAssigned()) blockers.add("商品主数据未指定责任人");
        if (!request.duplicateCheckPassed()) blockers.add("商品重复性检查未通过");
        if (!request.taxonomyMapped()) blockers.add("企业类目与渠道类目未完成映射");
        if (request.regulatedProduct() && !request.regulatoryAttributesApproved()) blockers.add("受监管商品属性尚未批准");
        if (!request.variantConsistencyPassed()) blockers.add("规格变体与主商品数据不一致");
        if (!request.finalApprovalComplete()) blockers.add("最终发布审批未完成");
        if (!blockers.isEmpty()) {
            actions.add("保持商品草稿状态，关闭主数据和合规阻断项");
            return new Assessment(Decision.BLOCKED, false, blockers, actions);
        }
        if (request.completenessPercent() < 95 || !request.localizedContentReady()
            || !request.effectiveDateConfirmed()) {
            if (request.completenessPercent() < 95) actions.add("将商品完整度提升至至少 95%");
            if (!request.localizedContentReady()) actions.add("补齐目标市场的本地化内容");
            if (!request.effectiveDateConfirmed()) actions.add("确认主数据生效时间和下游同步窗口");
            return new Assessment(Decision.REVIEW, false, blockers, actions);
        }
        actions.add("发布黄金商品主档并生成下游同步版本");
        return new Assessment(Decision.PUBLISH, true, blockers, actions);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String productCode, boolean dataOwnerAssigned,
                          boolean duplicateCheckPassed, boolean taxonomyMapped,
                          boolean regulatedProduct, boolean regulatoryAttributesApproved,
                          boolean variantConsistencyPassed, boolean finalApprovalComplete,
                          @Min(0) @Max(100) int completenessPercent,
                          boolean localizedContentReady, boolean effectiveDateConfirmed) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Assessment(Decision decision, boolean publishable, List<String> blockers,
                             List<String> actions) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { PUBLISH, REVIEW, BLOCKED }
}
