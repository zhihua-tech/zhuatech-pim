/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.service;
import cn.zhuatech.pim.common.BusinessException; import cn.zhuatech.pim.dto.PimDto.*; import cn.zhuatech.pim.model.*; import cn.zhuatech.pim.repository.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service @Transactional(readOnly=true) public class PimService {
    private final ProductRecordRepository orders; private final PublicationRecordRepository reports; private final AttributeSetRepository attributeSet; private final EnrichmentTaskRepository enrichmentTasks; private final CurrentUserService current;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public PimService(ProductRecordRepository orders,PublicationRecordRepository reports,AttributeSetRepository attributeSet,EnrichmentTaskRepository enrichmentTasks,CurrentUserService current){this.orders=orders;this.reports=reports;this.attributeSet=attributeSet;this.enrichmentTasks=enrichmentTasks;this.current=current;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Dashboard shopfloorDashboard(){String center=current.get().getChannelCode();List<ProductRecord> list=center==null?orders.findAllByOrderByDueDateAsc():orders.findByChannelCodeOrderByDueDateAsc(center);return dashboard(list);}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Dashboard adminDashboard(){return dashboard(orders.findAllByOrderByDueDateAsc());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public List<ProductRecordView> productRecords(){return orders.findAllByOrderByDueDateAsc().stream().map(this::view).toList();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Transactional public ReportResult report(Long id,ReportRequest request){ProductRecord order=orders.findById(id).orElseThrow(()->new BusinessException("完善任务不存在"));if(order.getStatus()==ProductRecord.Status.COMPLETED)throw new BusinessException("已完成任务不能继续反馈");if(order.getCompletedQty()+request.goodQty()>order.getPlannedQty())throw new BusinessException("完成数量不能超过任务剩余数量");order.report(request.goodQty(),request.defectQty());reports.save(new PublicationRecord(order,request.operationName(),request.goodQty(),request.defectQty(),current.get().getFullName(),request.remark()));return new ReportResult(order.getOrderNo(),order.getCompletedQty(),order.getDefectQty(),progress(order),order.getStatus().name());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private Dashboard dashboard(List<ProductRecord> list){int planned=list.stream().mapToInt(ProductRecord::getPlannedQty).sum(),done=list.stream().mapToInt(ProductRecord::getCompletedQty).sum(),defects=list.stream().mapToInt(ProductRecord::getDefectQty).sum();int rate=planned==0?0:Math.round(done*100f/planned);List<Metric> metrics=List.of(new Metric("今日完善",String.format("%,d",planned),list.size()+" 张完善任务","blue"),new Metric("完善完成率",rate+"%",String.format("%,d / %,d",done,planned),"green"),new Metric("结果合规率",String.format("%.1f%%",done+defects==0?100d:done*100d/(done+defects)),defects+" 项异常","warn"),new Metric("属性集异常",attributeSet.countByStatus(AttributeSet.Status.ALARM)+"",enrichmentTasks.countByResult(EnrichmentTask.Result.PENDING)+" 项待审核","red"));return new Dashboard(metrics,list.stream().map(this::view).toList(),attributeSet.findAllByOrderByCodeAsc().stream().map(e->new AttributeSetView(e.getCode(),e.getName(),e.getChannel().getName(),e.getStatus().name(),e.getOee(),e.getLastHeartbeat())).toList(),enrichmentTasks.findTop10ByOrderByIdDesc().stream().map(i->new EnrichmentTaskView(i.getEnrichmentTaskNo(),i.getProductRecord().getOrderNo(),i.getProductRecord().getProductName(),i.getEnrichmentTaskType(),i.getEnrichmentTaskQty(),i.getDefectQty(),i.getResult().name(),i.getInspector())).toList());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private ProductRecordView view(ProductRecord o){return new ProductRecordView(o.getId(),o.getOrderNo(),o.getProductCode(),o.getProductName(),o.getChannel().getName(),o.getChannel().getWorkshop(),o.getPlannedQty(),o.getCompletedQty(),o.getDefectQty(),o.getDueDate(),o.getStatus().name(),o.getBatchNo(),progress(o));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private int progress(ProductRecord o){return o.getPlannedQty()==0?0:Math.min(100,Math.round(o.getCompletedQty()*100f/o.getPlannedQty()));}
}
