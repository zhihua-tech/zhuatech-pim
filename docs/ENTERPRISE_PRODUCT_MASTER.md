# 企业级黄金商品主档发布

[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)为 PIM 开源版增加商品主数据发布控制。

`POST /api/enterprise/pim/product-master-release` 检查责任人、重复商品、类目映射、监管属性、变体一致性、审批、完整度、本地化和生效时间，返回 `PUBLISH / REVIEW / BLOCKED`。

企业部署应与 ERP、OMS、电商渠道和 MDM 集成，并通过版本号、变更集和回滚策略控制下游同步。
