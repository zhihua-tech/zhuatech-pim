/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.repository; import cn.zhuatech.pim.model.ProductRecord; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface ProductRecordRepository extends JpaRepository<ProductRecord,Long>{/**
                                                                                    * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                    */
List<ProductRecord> findAllByOrderByDueDateAsc();/**
                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                     */
List<ProductRecord> findByChannelCodeOrderByDueDateAsc(String code);/**
                                                                                                                                                                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                         */
long countByStatus(ProductRecord.Status status);}
