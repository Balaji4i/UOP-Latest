package view;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import model.vo.RetirementLines_VORowImpl;
import model.vo.Retirement_VORowImpl;

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

public class AddEditRetirement {
    private RichInputText retirementCostValue;
    private RichPopup categoryPopup;
    java.util.Set unitCheckedOfSets = new java.util.HashSet();
    private RichTable categoryList;

    public AddEditRetirement() {
    }

    public void onClickSave(ActionEvent actionEvent) {
        DCIteratorBinding introHdrIter = ADFUtils.findIterator("Retirement_VOIterator");
        Retirement_VORowImpl introHdrRow = (Retirement_VORowImpl) introHdrIter.getCurrentRow();
        if (introHdrRow != null) {
            introHdrRow.setStatus("APPROVED");
            introHdrRow.setGlTransferFlag("N");
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Submitted Successfully");
        }

    }

    public void onClickCancel(ActionEvent actionEvent) {
        ViewObject HdrVO = ADFUtils.findIterator("Retirement_VOIterator").getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Rollback").execute();
    }

    public void onChangeOfRetirementCost(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding introHdrIter = ADFUtils.findIterator("RetirementLines_VOIterator");
        RetirementLines_VORowImpl introHdrRow = (RetirementLines_VORowImpl) introHdrIter.getCurrentRow();
        BigDecimal deprnReserveCost = new BigDecimal(0);
        BigDecimal assetCostBalance =
            (introHdrRow.getAttribute("AssetCost") == null ? new BigDecimal(0) :
             (BigDecimal) introHdrRow.getAttribute("AssetCost"));
        BigDecimal accDepreciation =
            (introHdrRow.getAttribute("AccumDeprnCost") == null ? new BigDecimal(0) :
             (BigDecimal) introHdrRow.getAttribute("AccumDeprnCost"));
        String value = valueChangeEvent.getNewValue().toString();
        BigDecimal retirementCost = new BigDecimal(value);
        //
        //        System.out.println("assetCostBalance  ------ " + assetCostBalance);
        //        System.out.println("accDepreciation ------ " +accDepreciation);
        //        System.out.println(" value ---------- " + value);
        //        System.out.println(" retirementCost ---------- " + retirementCost);
        if (retirementCost.compareTo(assetCostBalance) > 0) {
            // System.out.println("Error");
            JSFUtils.addFacesErrorMessage("Asset Cost to Retire must not be greater than Asset Cost");
            retirementCostValue.setValue("");
            retirementCostValue.setValue(null);
        } else if (assetCostBalance.compareTo(BigDecimal.ZERO) == 0) {
            // System.out.println("Error 1");
            JSFUtils.addFacesErrorMessage("Asset Cost Amount is Zero.");
            retirementCostValue.setValue("");
            retirementCostValue.setValue(null);
        } else {
            // System.out.println("Success");
            deprnReserveCost =
                (accDepreciation.multiply(retirementCost.divide(assetCostBalance, 16, RoundingMode.HALF_UP)));
            introHdrRow.setAttribute("DeprnReserveCost", deprnReserveCost.setScale(2, RoundingMode.HALF_UP));
        }

    }

    public void setRetirementCostValue(RichInputText retirementCostValue) {
        this.retirementCostValue = retirementCostValue;
    }

    public RichInputText getRetirementCostValue() {
        return retirementCostValue;
    }

    public void onClickTransferToGl(ActionEvent actionEvent) {
        String xmlData = null;
        String[] a = null;
        String payRollWSDL = null;
        payRollWSDL = ApprovalPayload.TRANFER_GL_WSDL;
        ViewObject HdrVo = ADFUtils.findIterator("Retirement_VOIterator").getViewObject();

        String p_EmployeeNumber = "Test";


        xmlData = ApprovalPayload.payrollTypeXMLData(p_EmployeeNumber);

        System.err.println("xmlData =>" + xmlData);
        a = ApprovalProcess.invokeWsdl(xmlData, payRollWSDL, ApprovalPayload.TRANFER_GL_METHOD);
        if (a[0] != null && a[0].equals("True")) {
            System.err.println("came in");
            HdrVo.getCurrentRow().setAttribute("SetFlag", "Y");
            //System.err.println(HdrVo.getCurrentRow().getAttribute("GlTransferFlag"));
            JSFUtils.addFacesInformationMessage("Transfer to GL is successful");
            ADFUtils.findOperation("Commit").execute();

        } else {
            System.err.println("came else");
            JSFUtils.addFacesInformationMessage("Error");

        }
     //   HdrVo.executeQuery();
//        /*refresh*/
//        FacesContext fc = FacesContext.getCurrentInstance();
//        String refreshpage = fc.getViewRoot().getViewId();
//        ViewHandler ViewH = fc.getApplication().getViewHandler();
//        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
//        UIV.setViewId(refreshpage);
//        fc.setViewRoot(UIV);
    }

    public void ListingofUnits() {
        ViewObject blockingLineVO = ADFUtils.findIterator("RetirementLines_VOIterator").getViewObject();
        RowSetIterator DtlRs = blockingLineVO.createRowSetIterator(null);
        while (DtlRs.hasNext()) {
            Row DtlRow = DtlRs.next();
            if (DtlRow.getAttribute("AssetCategoryId") != null) {
                //System.err.println("UnitId " + DtlRow.getAttribute("UnitId"));
                unitCheckedOfSets.add(DtlRow.getAttribute("AssetCategoryId"));
            }
        }
    }

    public String onCallCategoryPopup() {
        ViewObject HdrVo = ADFUtils.findIterator("Retirement_VOIterator").getViewObject();
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

    public void setCategoryPopup(RichPopup categoryPopup) {
        this.categoryPopup = categoryPopup;
    }

    public RichPopup getCategoryPopup() {
        return categoryPopup;
    }

    public void onClickOk(ActionEvent actionEvent) {
//        ListingofUnits();
//        ViewObject unitList = ADFUtils.findIterator("CIPAssestCategory_ROVOIterator").getViewObject();
//        ViewObject blockingDetails = ADFUtils.findIterator("RetirementLines_VOIterator").getViewObject();
//
//
//        RowSetIterator unitListIterator = unitList.createRowSetIterator(null);
//        while (unitListIterator.hasNext()) {
//            Row r = unitListIterator.next();
//
//
//            System.err.println("CHECK BOX VALUE---" + r.getAttribute("Trans_Select"));
//            if (r.getAttribute("Trans_Select") != null) {
//
//
//                String select = (String) r.getAttribute("Trans_Select");
//                if (select != null) {
//                    System.err.println(select);
//
//                    if (unitCheckedOfSets.add(r.getAttribute("AssetCategoryId"))) {
//
//                        Row blockingCurrRow = blockingDetails.createRow();
//                        blockingCurrRow.setAttribute("AssetCategoryId", r.getAttribute("AssetCategoryId"));
//                        blockingCurrRow.setAttribute("AssetCost", r.getAttribute("CcTotAccuAssetCst"));
//                        blockingCurrRow.setAttribute("AccumDeprnCost", r.getAttribute("CcTotAccuDepr"));
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

    public void setCategoryList(RichTable categoryList) {
        this.categoryList = categoryList;
    }

    public RichTable getCategoryList() {
        return categoryList;
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

    public void onClickAddinPopup(ActionEvent actionEvent) {
        ListingofUnits();
               ViewObject unitList = ADFUtils.findIterator("CIPAssestCategory_ROVOIterator").getViewObject();
               ViewObject blockingDetails = ADFUtils.findIterator("RetirementLines_VOIterator").getViewObject();
                       Row r = unitList.getCurrentRow();
                         if (unitCheckedOfSets.add(r.getAttribute("AssetCategoryId"))) {
                                                      Row blockingCurrRow = blockingDetails.createRow();
                           blockingCurrRow.setAttribute("AssetCategoryId", r.getAttribute("AssetCategoryId"));
                           blockingCurrRow.setAttribute("AssetCost", r.getAttribute("CcTotAccuAssetCst"));
                           blockingCurrRow.setAttribute("AccumDeprnCost", r.getAttribute("CcTotAccuDepr"));
                                                                  blockingDetails.insertRow(blockingCurrRow);
                                                                  
                       }
        
    }
}
