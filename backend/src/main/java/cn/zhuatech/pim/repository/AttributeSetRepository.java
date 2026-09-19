/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.repository; import cn.zhuatech.pim.model.AttributeSet; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface AttributeSetRepository extends JpaRepository<AttributeSet,Long>{/**
                                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                  */
List<AttributeSet> findAllByOrderByCodeAsc();/**
                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                               */
long countByStatus(AttributeSet.Status status);}
