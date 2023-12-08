package view;

import com.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.share.ADFContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchWell {
    public SearchWell() {
    }

    public void onClickDelete(ActionEvent actionEvent) {
        ViewObject leaseHdrVO = ADFUtils.findIterator("WellMasterVOIterator").getViewObject();
        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();
        AttributeBinding personIdBinding = (AttributeBinding) bindings.getControlBinding("WellId");
        System.err.println("personIdBinding.getInputValue() = "+personIdBinding.getInputValue());
        String personId =personIdBinding.getInputValue()==null ? "0" : personIdBinding.getInputValue().toString();
        deleteLeaseReqHdr(personId);
        ADFUtils.findOperation("Commit").execute();
        leaseHdrVO.executeQuery();
    }
    
    public void deleteLeaseReqHdr(Object leaseReqHdrId) {
           ViewObject leaseHdrVO = ADFUtils.findIterator("WellMasterVOIterator").getViewObject();
           ViewCriteria hdrVC = leaseHdrVO.createViewCriteria();
           ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
           hdrVcr.setAttribute("WellId", leaseReqHdrId);
           hdrVC.addRow(hdrVcr);
           leaseHdrVO.applyViewCriteria(hdrVC);
           leaseHdrVO.executeQuery();
           System.err.println("leaseHdrVO.getEstimatedRowCount() = " + leaseHdrVO.getEstimatedRowCount());
           if (leaseHdrVO.getEstimatedRowCount() > 0) {
               Row hdrRow = leaseHdrVO.first();
               Object hdrId = hdrRow.getAttribute("WellId");
               System.err.println("Deleting LeaseReqHdrId = " + hdrId);
               deleteLeaseReqDtl(hdrId);
               hdrRow.remove();
            OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
           }
           leaseHdrVO.applyViewCriteria(null);  

       }

       public void deleteLeaseReqDtl(Object leaseReqHdrId) {
           ViewObject leaseReqDtlVO = ADFUtils.findIterator("WellDtlsVOIterator").getViewObject();
           ViewCriteria vc1 = leaseReqDtlVO.createViewCriteria();
           ViewCriteriaRow vcr = vc1.createViewCriteriaRow();
           vcr.setAttribute("WellId", leaseReqHdrId);
           vc1.addRow(vcr);
           leaseReqDtlVO.applyViewCriteria(vc1);
           leaseReqDtlVO.executeQuery();
           System.err.println("leaseReqDtlVO.getEstimatedRowCount() = " + leaseReqDtlVO.getEstimatedRowCount());
           if (leaseReqDtlVO.getEstimatedRowCount() > 0) {
               System.err.println("Inside If");
               RowSetIterator rsDtl = leaseReqDtlVO.createRowSetIterator(null);
               System.err.println("rsDtl.getFetchedRowCount() === "+rsDtl.getFetchedRowCount());
               while (rsDtl.hasNext()) {
                   System.err.println("Inside While");
                   Row r = rsDtl.next();
                   System.err.println("rsDtl.getCurrentRowIndex() === "+rsDtl.getCurrentRowIndex());
                   
                    
    //                   Object leaseReqDtlId = r.getAttribute("IntroLtrLineId");
    //                   deleteLeaseReqDtl(leaseReqDtlId);
    //                   System.err.println("Deleting LeaseReqDtlId = " + leaseReqDtlId);
                   r.remove();
                   
                  // deleteLeaseReqDtl(leaseReqDtlId);now i put this, no need this, we r using loop
                   OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
               }
           }
           leaseReqDtlVO.applyViewCriteria(null);
           leaseReqDtlVO.executeQuery(); 
           
       }

    public void onClickEdit(ActionEvent actionEvent) {
       
  Object obj =  ADFContext.getCurrent().getPageFlowScope().get("headerId");
    System.err.println("Object Name"+obj);
           ViewObject leaseHdrVO = ADFUtils.findIterator("WellMasterVOIterator").getViewObject();
           ViewCriteria hdrVC = leaseHdrVO.createViewCriteria();
           ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
           hdrVcr.setAttribute("WellId", obj);
           
           hdrVC.addRow(hdrVcr);
           leaseHdrVO.applyViewCriteria(hdrVC);
           leaseHdrVO.executeQuery();
}
}
