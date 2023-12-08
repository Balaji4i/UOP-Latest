package view;

import com.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchUop {
    public SearchUop() {
    }

    public void onClickEdit(ActionEvent actionEvent) {
        Object obj =  ADFContext.getCurrent().getPageFlowScope().get("headerId");
          System.err.println("Object Name"+obj);
                 ViewObject leaseHdrVO = ADFUtils.findIterator("UopMaster_VOIterator").getViewObject();
                 ViewCriteria hdrVC = leaseHdrVO.createViewCriteria();
                 ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
                 hdrVcr.setAttribute("UopDeprHdrId", obj);
                 
                 hdrVC.addRow(hdrVcr);
                 leaseHdrVO.applyViewCriteria(hdrVC);
                 leaseHdrVO.executeQuery();
        }
    }

