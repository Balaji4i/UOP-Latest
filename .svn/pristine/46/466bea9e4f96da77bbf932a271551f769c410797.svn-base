package view;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import java.math.BigDecimal;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import model.vo.CipMaster_VORowImpl;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class AddEditCipMaster {
    private RichPopup onClickCategoryPopup;
    private RichPopup categoryPopup;
    java.util.Set unitCheckedOfSets = new java.util.HashSet();
    private RichTable categoryList;
    

    public AddEditCipMaster() {
    }

    public void onClickSave(ActionEvent actionEvent) {
        DCIteratorBinding introHdrIter = ADFUtils.findIterator("CipMaster_VOIterator");
        CipMaster_VORowImpl introHdrRow = (CipMaster_VORowImpl) introHdrIter.getCurrentRow();
        if( introHdrRow!= null){
            introHdrRow.setStatus("APPROVED");
            introHdrRow.setGlTransferFlag("N");
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Submitted Successfully");
        }

    }

    public void onClickCancel(ActionEvent actionEvent) {
        ViewObject HdrVO = ADFUtils.findIterator("CipMaster_VOIterator").getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Rollback").execute();
    }

    public void onClickTransferToGl(ActionEvent actionEvent) {
        String xmlData = null;
        String[] a = null;
        String payRollWSDL = null;
        payRollWSDL = ApprovalPayload.TRANFER_GL_WSDL;
        ViewObject HdrVo = ADFUtils.findIterator("CipMaster_VOIterator").getViewObject();

        String p_EmployeeNumber = "Test";

        xmlData = ApprovalPayload.payrollTypeXMLData(p_EmployeeNumber);

        System.err.println("xmlData =>" + xmlData);
        a = ApprovalProcess.invokeWsdl(xmlData, payRollWSDL, ApprovalPayload.TRANFER_GL_METHOD);
        if (a[0] != null && a[0].equals("True")) {
            System.err.println("came in");
            HdrVo.getCurrentRow().setAttribute("SetFlag", "Y");
           // System.err.println(HdrVo.getCurrentRow().getAttribute("SetFlag"));
            JSFUtils.addFacesInformationMessage("Transfer to GL is successful");
            ADFUtils.findOperation("Commit").execute();

        } else {
            System.err.println("came else");            
            JSFUtils.addFacesInformationMessage("Error");
            
        }
    }

    public void setOnClickCategoryPopup(RichPopup onClickCategoryPopup) {
        this.onClickCategoryPopup = onClickCategoryPopup;
    }

    public RichPopup getOnClickCategoryPopup() {
        return onClickCategoryPopup;
    }

    public String onCallPopup() {
        // Add event code here...
        return null;
    }

    public void setCategoryPopup(RichPopup categoryPopup) {
        this.categoryPopup = categoryPopup;
    }

    public RichPopup getCategoryPopup() {
        return categoryPopup;
    }

    public String onCallCategoryPopup() {
       
        
        ViewObject HdrVo = ADFUtils.findIterator("CipMaster_VOIterator").getViewObject();
        ViewObject popUpVo = ADFUtils.findIterator("CIPAssestCategory_ROVOIterator").getViewObject();
        HdrVo.getCurrentRow().getAttribute("WellId");
        HdrVo.getCurrentRow().getAttribute("PeriodName");
        ADFContext.getCurrent()
                  .getSessionScope()
                  .put("WellId", HdrVo.getCurrentRow().getAttribute("WellId"));
        ADFContext.getCurrent()
                  .getSessionScope()
                  .put("PeriodName", HdrVo.getCurrentRow().getAttribute("PeriodName"));

        ViewObject blockingDetails = ADFUtils.findIterator("CIPAssestCategory_ROVOIterator").getViewObject();
        RowSetIterator unitListIterator = blockingDetails.createRowSetIterator(null);
        while (unitListIterator.hasNext()) {
            Row r = unitListIterator.next();
            //System.err.println("CHECK BOX VALUE---" + r.getAttribute("Trans_Select"));
            if (r.getAttribute("Trans_Select") != null) {
                r.setAttribute("Trans_Select", "N");
            }

        }
        categoryPopup.show(new RichPopup.PopupHints());
        return null;
    }

    public void ListingofUnits() {
        ViewObject blockingLineVO = ADFUtils.findIterator("CipLines_VOIterator").getViewObject();
        RowSetIterator DtlRs = blockingLineVO.createRowSetIterator(null);
        while (DtlRs.hasNext()) {
            Row DtlRow = DtlRs.next();
            if (DtlRow.getAttribute("AssetCategoryId") != null) {
                //System.err.println("UnitId " + DtlRow.getAttribute("UnitId"));
                unitCheckedOfSets.add(DtlRow.getAttribute("AssetCategoryId"));
            }
        }
    }


    public void onClickOk(ActionEvent actionEvent) {
//        ListingofUnits();
//        ViewObject unitList = ADFUtils.findIterator("CIPAssestCategory_ROVOIterator").getViewObject();
//        ViewObject blockingDetails = ADFUtils.findIterator("CipLines_VOIterator").getViewObject();
//
//
//        RowSetIterator unitListIterator = unitList.createRowSetIterator(null);
//        while (unitListIterator.hasNext()) {
//            Row r = unitListIterator.next();
//
//
//                   System.err.println("CHECK BOX VALUE---" + r.getAttribute("Trans_Select"));
//                   if (r.getAttribute("Trans_Select") != null) {
//
//
//                       String select = (String) r.getAttribute("Trans_Select");
//                       if (select != null) {
//                           System.err.println(select);
//                           
//                           if (unitCheckedOfSets.add(r.getAttribute("AssetCategoryId"))) {
//                               
//                               Row blockingCurrRow = blockingDetails.createRow();
//                               blockingCurrRow.setAttribute("AssetCategoryId", r.getAttribute("AssetCategoryId"));
//                               blockingCurrRow.setAttribute("CipCostCumulative", r.getAttribute("CipClsoingBalance"));
//                               // blockingCurrRow.setAttribute("CaptalizeAmount", r.getAttribute("UnitId"));
//                               
//                               //UnblockFlag
//                               // blockingCurrRow.setAttribute("UnblockFlag", "Y");
//                               blockingDetails.insertRow(blockingCurrRow);
//                               // blockingDetails.executeQuery();
//                           }
//        
//
//                    if (unitCheckedOfSets.add(r.getAttribute("AssetCategoryId"))) {
//
//                        Row blockingCurrRow = blockingDetails.createRow();
//                        blockingCurrRow.setAttribute("AssetCategoryId", r.getAttribute("AssetCategoryId"));
//                        blockingCurrRow.setAttribute("CipCostCumulative", r.getAttribute("CipClsoingBalance"));
//                        // blockingCurrRow.setAttribute("CaptalizeAmount", r.getAttribute("UnitId"));
//
//                        //UnblockFlag
//                        // blockingCurrRow.setAttribute("UnblockFlag", "Y");
//                        blockingDetails.insertRow(blockingCurrRow);
//                        // blockingDetails.executeQuery();
//                    }
//
//
//                }
//
//
//            }
//        }

        categoryPopup.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(categoryPopup);
        FilterableQueryDescriptor queryDescriptor =
            (FilterableQueryDescriptor) getCategoryList().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null)
        {
            queryDescriptor.getFilterCriteria().clear();
            getCategoryList().queueEvent(new QueryEvent(getCategoryList(), queryDescriptor));
        }
    }

    public void onClickCancelPopup(ActionEvent actionEvent) {
        categoryPopup.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(categoryPopup); 
       
        FilterableQueryDescriptor queryDescriptor =
            (FilterableQueryDescriptor) getCategoryList().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null)
        {
            queryDescriptor.getFilterCriteria().clear();
            getCategoryList().queueEvent(new QueryEvent(getCategoryList(), queryDescriptor));
        }
        
    }
//    public void resetTableFilter(ActionEvent actionEvent)
//       {
//           FilterableQueryDescriptor queryDescriptor =
//               (FilterableQueryDescriptor) getCategoryList().getFilterModel();
//           if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null)
//           {
//               queryDescriptor.getFilterCriteria().clear();
//               getCategoryList().queueEvent(new QueryEvent(getCategoryList(), queryDescriptor));
//           }
//       }
   
   

    public void setCategoryList(RichTable categoryList) {
        this.categoryList = categoryList;
    }

    public RichTable getCategoryList() {
        return categoryList;
    }

    public void onClickAddinPopup(ActionEvent actionEvent) {
        ListingofUnits();
               ViewObject unitList = ADFUtils.findIterator("CIPAssestCategory_ROVOIterator").getViewObject();
               ViewObject blockingDetails = ADFUtils.findIterator("CipLines_VOIterator").getViewObject();
                       Row r = unitList.getCurrentRow();
                         if (unitCheckedOfSets.add(r.getAttribute("AssetCategoryId"))) {
                                                      Row blockingCurrRow = blockingDetails.createRow();
                                      blockingCurrRow.setAttribute("AssetCategoryId", r.getAttribute("AssetCategoryId"));
                                      blockingCurrRow.setAttribute("CipCostCumulative", r.getAttribute("CipClsoingBalance"));
                                                                  blockingDetails.insertRow(blockingCurrRow);
                                                                  
                       }
    }

    public void onChangeCapitlizeAmt(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
            ViewObject cipLinesVoIter = ADFUtils.findIterator("CipLines_VOIterator").getViewObject();
            if (cipLinesVoIter.getCurrentRow().getAttribute("CipCostCumulative") != null) {
                BigDecimal cipCostCumulative =
                    cipLinesVoIter.getCurrentRow().getAttribute("CipCostCumulative") != null ?
                    (BigDecimal) cipLinesVoIter.getCurrentRow().getAttribute("CipCostCumulative") : new BigDecimal(0);

                BigDecimal capitizeAmt = new BigDecimal(valueChangeEvent.getNewValue().toString());
              //  System.out.println("cipCostCumulative   -------" +cipCostCumulative );
                
               // System.out.println("capitizeAmt  -------" + capitizeAmt);

                if (capitizeAmt.compareTo(cipCostCumulative) < 0) {
                   // System.out.println("Allow -------");
                } else if (cipCostCumulative.compareTo(BigDecimal.ZERO) == 0 ) {
                   // System.out.println("Error 1");
                    JSFUtils.addFacesErrorMessage("Cumulative CIP Cost Amount is Zero.");
                    cipLinesVoIter.getCurrentRow().setAttribute("CaptalizeAmount", "");
                    RichInputText t3 = (RichInputText) JSFUtils.findComponentInRoot("it20");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(t3);
                } else {
                  //  System.out.println("Error");
                    JSFUtils.addFacesErrorMessage("Captalize Amount Should be less than Cumulative CIP Cost Amount ");
                    cipLinesVoIter.getCurrentRow().setAttribute("CaptalizeAmount", "");
                    RichInputText t3 = (RichInputText) JSFUtils.findComponentInRoot("it20");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(t3);
                    
                }

            }


        }
    }
}
