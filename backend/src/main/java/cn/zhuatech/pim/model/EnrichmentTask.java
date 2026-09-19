/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="pim_enrichment_task") public class EnrichmentTask extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Result { PENDING, PASSED, FAILED }
    @Column(nullable=false,unique=true,length=32) private String enrichmentTaskNo; @ManyToOne(optional=false,fetch=FetchType.LAZY) private ProductRecord productRecord;
    @Column(nullable=false,length=30) private String enrichmentTaskType; @Column(nullable=false) private int enrichmentTaskQty; @Column(nullable=false) private int defectQty; @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Result result;
    @Column(length=50) private String inspector; @Column(nullable=false) private LocalDateTime createdAt;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected EnrichmentTask(){} /**
                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                  */
public EnrichmentTask(String enrichmentTaskNo,ProductRecord productRecord,String enrichmentTaskType,int enrichmentTaskQty,int defectQty,Result result,String inspector){this.enrichmentTaskNo=enrichmentTaskNo;this.productRecord=productRecord;this.enrichmentTaskType=enrichmentTaskType;this.enrichmentTaskQty=enrichmentTaskQty;this.defectQty=defectQty;this.result=result;this.inspector=inspector;this.createdAt=LocalDateTime.now();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getEnrichmentTaskNo(){return enrichmentTaskNo;} /**
                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                   */
public ProductRecord getProductRecord(){return productRecord;} /**
                                                                                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                  */
public String getEnrichmentTaskType(){return enrichmentTaskType;} /**
                                                                                                                                                                                                    * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                    */
public int getEnrichmentTaskQty(){return enrichmentTaskQty;} /**
                                                                                                                                                                                                                                                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                 */
public int getDefectQty(){return defectQty;} /**
                                                                                                                                                                                                                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                              */
public Result getResult(){return result;} /**
                                                                                                                                                                                                                                                                                                                                                        * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                        */
public String getInspector(){return inspector;}
}
