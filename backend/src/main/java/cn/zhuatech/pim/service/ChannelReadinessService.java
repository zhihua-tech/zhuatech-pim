/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.service;
import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class ChannelReadinessService {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public Result evaluate(Request r){double attributes=Math.min(1,r.populatedAttributes()/(double)r.requiredAttributes());double images=Math.min(1,r.availableImages()/(double)Math.max(1,r.minimumImages()));double translations=r.translationsRequired()==0?1:Math.min(1,r.translationsCompleted()/(double)r.translationsRequired());int score=(int)Math.round(attributes*60+images*15+translations*10+(r.taxonomyMapped()?8:0)+(r.approved()?7:0));List<String> gaps=new ArrayList<>();if(attributes<1)gaps.add("补齐必填商品属性");if(images<1)gaps.add("补充渠道要求的商品图片");if(translations<1)gaps.add("完成目标市场翻译");if(!r.taxonomyMapped())gaps.add("映射渠道类目");if(!r.approved())gaps.add("完成商品内容审批");String status=score==100?"READY":score>=75?"ENRICH":"BLOCKED";if(gaps.isEmpty())gaps.add("商品信息满足渠道发布条件");return new Result(score,status,gaps);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Request(@NotBlank String sku,@Min(1) int requiredAttributes,@Min(0) int populatedAttributes,@Min(0) int availableImages,@Min(0) int minimumImages,@Min(0) int translationsRequired,@Min(0) int translationsCompleted,@NotNull Boolean taxonomyMapped,@NotNull Boolean approved){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Result(int completenessScore,String status,List<String> gaps){}
}
