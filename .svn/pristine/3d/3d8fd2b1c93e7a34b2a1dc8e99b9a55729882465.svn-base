package view;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import java.io.OutputStream;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import model.vo.UopMaster_VORowImpl;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class AddEditUop {
    private RichTable ccTable;
    private RichTable fdcTable;
    private RichInputText ccNvbBinding;
    private String errorMsg = null;
    private RichTable cipTable;
    private RichInputText catBinding;
    private RichPopup journalDetailsPopup;
    private RichTable journalDetailTable;

    public AddEditUop() {
    }

    public void onClickSubmit(ActionEvent actionEvent) {
        boolean value = false;
        DCIteratorBinding uopHdrIter = ADFUtils.findIterator("UopMaster_VOIterator");
        UopMaster_VORowImpl uopHdrRow = (UopMaster_VORowImpl) uopHdrIter.getCurrentRow();
        if (uopHdrRow != null) {
            uopHdrRow.setStatus("APPROVED");
            uopHdrRow.setGlTransferFlag("N");
            value = true;
        }
        if (value) {
            dataPopulate();
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Submitted Successfully");
        }

    }

    public void dataPopulate() {
        ViewObject lineVO = ADFUtils.findIterator("UopLines_VOIterator").getViewObject();
      //  lineVO.clearCache();
        lineVO.executeQuery();
        RowSetIterator uopLine = lineVO.createRowSetIterator(null);
        //uopLine.reset();
        System.err.println(uopLine.getRowCount());        
        while (uopLine.hasNext()) {
            System.err.println("inside while");
            Row r = uopLine.next();
            System.err.println( " UopDeprLineId-------------" + r.getAttribute("UopDeprLineId"));
            JSFUtils.setExpressionValue("#{bindings.AssetCategoryId.inputValue}", r.getAttribute("AssetCategoryId"));
           //   System.out.println("Categroy id after setting in expression -----  " + r.getAttribute("AssetCategoryId"));
            BigDecimal ccNbvValue =
                (r.getAttribute("CcNbv") == null ? new BigDecimal(0) : (BigDecimal) r.getAttribute("CcNbv"));
            r.setAttribute("CcNbv", ccNbvValue.setScale(2, RoundingMode.HALF_UP));
            //
            BigDecimal CcTotalAdd =
                (r.getAttribute("CcTotalAddition") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("CcTotalAddition"));
            r.setAttribute("CcTotalAddition", CcTotalAdd.setScale(2, RoundingMode.HALF_UP));
            //
            BigDecimal ccTotAccuAssetCst =
                (r.getAttribute("CcTotAccuAssetCst") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("CcTotAccuAssetCst"));
            r.setAttribute("CcTotAccuAssetCst", ccTotAccuAssetCst.setScale(2, RoundingMode.HALF_UP));
            //
            BigDecimal ccAccuDeprOpening =
                (r.getAttribute("CcAccuDeprOpening") != null ? (BigDecimal) (r.getAttribute("CcAccuDeprOpening")) :
                 new BigDecimal(0));
            r.setAttribute("CcAccuDeprOpening", ccAccuDeprOpening.setScale(2, RoundingMode.HALF_UP));


            BigDecimal ccPeriodDepr =
                (r.getAttribute("CcPeriodDepr") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("CcPeriodDepr"));
            r.setAttribute("CcPeriodDepr", ccPeriodDepr.setScale(2, RoundingMode.HALF_UP));

            //
            BigDecimal retStr1 = new BigDecimal(0);
            Object obj1 = ADFUtils.findOperation("deprnReserve").execute();
            retStr1 = obj1 == null ? new BigDecimal(0) : new BigDecimal(obj1.toString());
            //System.err.println("retStrdeprnReserve" + retStr1);

            BigDecimal deprnReserve1 = new BigDecimal(0);
            BigDecimal deprnReserve2 = new BigDecimal(0);

            deprnReserve1 = ccAccuDeprOpening.add(ccPeriodDepr);

            r.setAttribute("CcTotAccuDepr", deprnReserve1.setScale(2, RoundingMode.HALF_UP));

            BigDecimal ccTotAccuDepr =
                (r.getAttribute("CcTotAccuDepr") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("CcTotAccuDepr"));

           //   System.err.println("After adding  --------" + ccTotAccuDepr);

            deprnReserve2 = ccTotAccuDepr.subtract(retStr1);


            ///  System.err.println("deprnReserve2--------" + deprnReserve2);

            r.setAttribute("CcTotAccuDepr", deprnReserve2.setScale(2, RoundingMode.HALF_UP));

//System.out.println("after setting the vakue ---- "+ r.getAttribute("CcTotAccuDepr"));
            //
            BigDecimal fdcAdditionCip =
                (r.getAttribute("FdcAdditionCip") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("FdcAdditionCip"));
            r.setAttribute("FdcAdditionCip", fdcAdditionCip.setScale(2, RoundingMode.HALF_UP));
            //
            BigDecimal fdcTotAccuAssetCst =
                (r.getAttribute("FdcTotAccuAssetCst") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("FdcTotAccuAssetCst"));
            r.setAttribute("FdcTotAccuAssetCst", fdcTotAccuAssetCst.setScale(2, RoundingMode.HALF_UP));
            //
            BigDecimal fdcPeriodDepr =
                (r.getAttribute("FdcPeriodDepr") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("FdcPeriodDepr"));
            r.setAttribute("FdcPeriodDepr", fdcPeriodDepr.setScale(2, RoundingMode.HALF_UP));

            //
            BigDecimal fdcTotAccuDepr =
                (r.getAttribute("FdcTotAccuDepr") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("FdcTotAccuDepr"));
            r.setAttribute("FdcTotAccuDepr", fdcTotAccuDepr.setScale(2, RoundingMode.HALF_UP));
            //
            BigDecimal fdcNbv =
                (r.getAttribute("FdcNbv") == null ? new BigDecimal(0) : (BigDecimal) r.getAttribute("FdcNbv"));
            r.setAttribute("FdcNbv", fdcNbv.setScale(2, RoundingMode.HALF_UP));
            // CIP Cost
            BigDecimal cipCost =
                (r.getAttribute("CipCost") == null ? new BigDecimal(0) : (BigDecimal) r.getAttribute("CipCost"));
            r.setAttribute("CipCost", cipCost.setScale(2, RoundingMode.HALF_UP));

            // CIP CipCaptizationCost
            BigDecimal cipCapCost =
                (r.getAttribute("CipCaptizationCost") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("CipCaptizationCost"));
            r.setAttribute("CipCaptizationCost", cipCapCost.setScale(2, RoundingMode.HALF_UP));
            //


            BigDecimal retStr = new BigDecimal(0);
            Object obj = ADFUtils.findOperation("cipBalance").execute();
            retStr = obj == null ? new BigDecimal(0) : new BigDecimal(obj.toString());
            // System.err.println("cipClosingBal prev------" + retStr);

            // CIP CocipClosingBalst
            BigDecimal cipClosingBal1 = new BigDecimal(0);
            BigDecimal cipClosingBal2 = new BigDecimal(0);

            cipClosingBal1 = cipCost.add(cipCapCost);
            
            cipClosingBal1 = cipClosingBal1 == null ? new BigDecimal(0) : cipClosingBal1;

            r.setAttribute("CipClsoingBalance", cipClosingBal1.setScale(2, RoundingMode.HALF_UP));


            BigDecimal cipClosingBal =
                (r.getAttribute("CipClsoingBalance") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("CipClsoingBalance"));

            /// System.err.println("After adding Cip cost " + cipClosingBal);


            cipClosingBal2 = cipClosingBal.add(retStr);


            // System.err.println("cipClosingBal new" + cipClosingBal2);
            
            cipClosingBal2 = cipClosingBal2 == null ? new BigDecimal(0) : cipClosingBal2;

            r.setAttribute("CipClsoingBalance", cipClosingBal2.setScale(2, RoundingMode.HALF_UP));
        }
       // uopLine.closeRowSetIterator();  
    }

    public void onClickSave(ActionEvent actionEvent) {

        dataPopulate();
        DCIteratorBinding uopHdrIter = ADFUtils.findIterator("UopMaster_VOIterator");
        UopMaster_VORowImpl uopHdrRow = (UopMaster_VORowImpl) uopHdrIter.getCurrentRow();
        if (uopHdrRow != null) {
            uopHdrRow.setStatus("DRAFT");
            uopHdrRow.setGlTransferFlag("N");
        }
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Information Saved Successfully");


    }


    public void onClickCancel(ActionEvent actionEvent) {
        if (refreshFilter()) {
            // catBinding.setValue("");
            //      AdfFacesContext.getCurrentInstance().addPartialTarget(catBinding);
            //        AdfFacesContext.getCurrentInstance().addPartialTarget(ccTable);
            System.err.println("Action listerner===");
            ViewObject HdrVO = ADFUtils.findIterator("UopMaster_VOIterator").getViewObject();

            HdrVO.applyViewCriteria(null);
            HdrVO.executeQuery();
            OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Rollback").execute();
        }

    }


    public void uopData(ActionEvent actionEvent) {
        String retStr = null;
        //Committing data before submitting for approval
        ADFUtils.findOperation("Commit").execute();
        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();
        DCIteratorBinding iterBindingdtl = bindings.findIteratorBinding("UopMaster_VOIterator");
        ViewObject vo = iterBindingdtl.getViewObject();
        Object obj = ADFUtils.findOperation("createUop").execute();
        if (obj != null) {
            retStr = obj.toString();
        }
        if (retStr != null && "S".equals(retStr)) {
            JSFUtils.addFacesInformationMessage("Successfully Created");
        } else {
            JSFUtils.addFacesInformationMessage(retStr);
        }
        dataPopulate();
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();

        DCIteratorBinding iter = bindings.findIteratorBinding("UopLines_VOIterator");
        ViewObject lineVo = iter.getViewObject();
        lineVo.executeQuery();
        
       
    }

    public void setCcTable(RichTable ccTable) {
        this.ccTable = ccTable;
    }

    public RichTable getCcTable() {
        return ccTable;
    }

    public void setFdcTable(RichTable fdcTable) {
        this.fdcTable = fdcTable;
    }

    public RichTable getFdcTable() {
        return fdcTable;
    }

    public void setCcNvbBinding(RichInputText ccNvbBinding) {
        this.ccNvbBinding = ccNvbBinding;
    }

    public RichInputText getCcNvbBinding() {
        return ccNvbBinding;
    }

    public void ccnbvValueChangeListener(ValueChangeEvent valueChangeEvent) {

        // Add event code here...
    }

    public void onClickRefresh(ActionEvent actionEvent) {
        String retStr = null;
        //Committing data before submitting for approval
        ADFUtils.findOperation("Commit").execute();
        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();
        DCIteratorBinding iterBindingdtl = bindings.findIteratorBinding("UopMaster_VOIterator");
        ViewObject vo = iterBindingdtl.getViewObject();


        //  BigDecimal hdrId = (BigDecimal) row.getAttribute("UopDeprHdrId");
        Object obj = ADFUtils.findOperation("refreshUop").execute();
        //   binding.getParamsMap().put("p_hdrId", hdrId);

        if (obj != null) {
            retStr = obj.toString();
        }
        if (retStr != null && "S".equals(retStr)) {
            JSFUtils.addFacesInformationMessage("Successfully refreshed");
        } else {
            JSFUtils.addFacesInformationMessage(retStr);
        }
        dataPopulate();
        ADFUtils.findOperation("Commit").execute();
     //   OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
        DCIteratorBinding iter = bindings.findIteratorBinding("UopLines_VOIterator");
        ViewObject lineVo = iter.getViewObject();
        lineVo.executeQuery();
    }

    public void onClickPeriod(ActionEvent actionEvent) {
        String no = null;
        String msg = null;
        //Committing data before submitting for approval
        //  ADFUtils.findOperation("Commit").execute();
        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();
        DCIteratorBinding iterBindingdtl = bindings.findIteratorBinding("UopMaster_VOIterator");
        ViewObject vo = iterBindingdtl.getViewObject();


        //  BigDecimal hdrId = (BigDecimal) row.getAttribute("UopDeprHdrId");
        OperationBinding bind = ADFUtils.findOperation("periodUop");
        ArrayList obj = (ArrayList) bind.execute();
        if (obj != null) {
            no = obj.get(0).toString();
            msg = obj.get(1).toString();
        }

        if (obj != null && "S".equals(no)) {
            JSFUtils.addFacesInformationMessage(msg);
            // ADFUtils.findOperation("Commit").execute();
            errorMsg = msg;
        } else {

            vo.getCurrentRow().setAttribute("WellId", null);
            vo.getCurrentRow().setAttribute("AssetBookCode", null);
            vo.getCurrentRow().setAttribute("CompanyCode", null);
            vo.getCurrentRow().setAttribute("Period", null);
            JSFUtils.addFacesErrorMessage(msg);
            errorMsg = msg;
        }

    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void onClickPeriod(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String no = null;
        String msg = null;
        //Committing data before submitting for approval
        //  ADFUtils.findOperation("Commit").execute();
        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();
        DCIteratorBinding iterBindingdtl = bindings.findIteratorBinding("UopMaster_VOIterator");
        ViewObject vo = iterBindingdtl.getViewObject();


        //  BigDecimal hdrId = (BigDecimal) row.getAttribute("UopDeprHdrId");
        OperationBinding bind = ADFUtils.findOperation("periodUop");
        ArrayList obj = (ArrayList) bind.execute();
        if (obj != null) {
            no = obj.get(0).toString();
            msg = obj.get(1).toString();
        }

        if (obj != null && "S".equals(no)) {
            JSFUtils.addFacesInformationMessage(msg);
            // ADFUtils.findOperation("Commit").execute();
            // errorMsg = msg;
        } else {

            vo.getCurrentRow().setAttribute("WellId", null);
            vo.getCurrentRow().setAttribute("AssetBookCode", null);
            vo.getCurrentRow().setAttribute("CompanyCode", null);
            vo.getCurrentRow().setAttribute("Period", null);
            JSFUtils.addFacesErrorMessage(msg);
            // errorMsg = msg;
        }

    }

    public void onDownloadUopDetails(FacesContext facesContext, OutputStream outputStream) {
        try {

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("UOP Data");
            HSSFRow rowhead = sheet.createRow((short) 0);


            //            HSSFSheet sheet1 =
            //                workbook.createSheet("Future Development Cost");
            //            HSSFRow rowhead1 = sheet1.createRow((short)0);
            //

            //            HSSFSheet sheet2 =
            //                workbook.createSheet("CIP");
            //            HSSFRow rowhead2 = sheet2.createRow((short)0);
            //

            rowhead.createCell(0).setCellValue("Category");
            sheet.setColumnWidth(0, 25000);
            rowhead.createCell(1).setCellValue("NBV -Opening");
            sheet.setColumnWidth(1, 5000);
            rowhead.createCell(2).setCellValue("Asset Cost-Opening");
            sheet.setColumnWidth(2, 5000);
            rowhead.createCell(3).setCellValue("Monthly Addition / Retirements");
            sheet.setColumnWidth(3, 7000);
            rowhead.createCell(4).setCellValue("Capitalization");
            sheet.setColumnWidth(4, 4500);
            rowhead.createCell(5).setCellValue("Total Additions");
            sheet.setColumnWidth(5, 5000);
            rowhead.createCell(6).setCellValue("Asset Cost-Closing");
            sheet.setColumnWidth(6, 4500);
            rowhead.createCell(7).setCellValue("Accumulated Depreciation-Opening");
            sheet.setColumnWidth(7, 7500);
            rowhead.createCell(8).setCellValue("Depreciation");
            sheet.setColumnWidth(8, 5000);
            rowhead.createCell(9).setCellValue("Accumulated Depreciation-Closing");
            sheet.setColumnWidth(9, 7000);
            rowhead.createCell(10).setCellValue("NBV-Closing");
            sheet.setColumnWidth(10, 4500);


            //            rowhead1.createCell(0).setCellValue("Category");
            //            sheet1.setColumnWidth(0, 25000);
            rowhead.createCell(11).setCellValue("NBV-Opening");
            sheet.setColumnWidth(11, 5000);
            rowhead.createCell(12).setCellValue("Asset Cost-Opening");
            sheet.setColumnWidth(12, 5000);
            rowhead.createCell(13).setCellValue("Monthly Addition / Retirements");
            sheet.setColumnWidth(13, 7000);
            rowhead.createCell(14).setCellValue("FDC TO CIP");
            sheet.setColumnWidth(14, 4500);
            rowhead.createCell(15).setCellValue("Addition+CIP");
            sheet.setColumnWidth(15, 5000);
            rowhead.createCell(16).setCellValue("Asset Cost-Closing");
            sheet.setColumnWidth(16, 5000);
            rowhead.createCell(17).setCellValue("Accumulated Depreciation-Opening");
            sheet.setColumnWidth(17, 6000);
            rowhead.createCell(18).setCellValue("Depreciation");
            sheet.setColumnWidth(18, 5000);
            rowhead.createCell(19).setCellValue("Accumulated Depreciation-Closing");
            sheet.setColumnWidth(19, 6000);
            rowhead.createCell(20).setCellValue("NBV-Closing");
            sheet.setColumnWidth(20, 4500);


            rowhead.createCell(21).setCellValue("CIP Cost");
            sheet.setColumnWidth(21, 5000);
            rowhead.createCell(22).setCellValue("CIP Capitalization Cost");
            sheet.setColumnWidth(22, 5000);
            rowhead.createCell(23).setCellValue("CIP Cost Closing Balance");
            sheet.setColumnWidth(23, 5500);
            //            rowhead2.createCell(0).setCellValue("Category");
            //            sheet2.setColumnWidth(0, 25000);


            ViewObject actVO = ADFUtils.findIterator("UopLines_VOIterator").getViewObject();
            actVO.executeQuery();
            if (actVO.getEstimatedRowCount() > 0) {
                RowSetIterator rs = actVO.createRowSetIterator(null);
                int excelRowData = 1;
                while (rs.hasNext()) {
                    Row actRow = rs.next();

                    String Trans_category =
                        actRow.getAttribute("Trans_category") != null ?
                        actRow.getAttribute("Trans_category").toString() : "";
                    BigDecimal CcOpeningNbv =
                        (actRow.getAttribute("CcOpeningNbv") != null ?
                         (BigDecimal) (actRow.getAttribute("CcOpeningNbv")) : new BigDecimal(0));
                    CcOpeningNbv = CcOpeningNbv.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CcAccuCstOpening =
                        (actRow.getAttribute("CcAccuCstOpening") != null ?
                         (BigDecimal) (actRow.getAttribute("CcAccuCstOpening")) : new BigDecimal(0));

                    CcAccuCstOpening = CcAccuCstOpening.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CcPeriodAdditions =
                        (actRow.getAttribute("CcPeriodAdditions") != null ?
                         (BigDecimal) (actRow.getAttribute("CcPeriodAdditions")) : new BigDecimal(0));

                    CcPeriodAdditions = CcPeriodAdditions.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CcCip =
                        (actRow.getAttribute("CcCip") != null ? (BigDecimal) (actRow.getAttribute("CcCip")) :
                         new BigDecimal(0));
                    CcCip = CcCip.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CcTotalAddition =
                        (actRow.getAttribute("CcTotalAddition") != null ?
                         (BigDecimal) (actRow.getAttribute("CcTotalAddition")) : new BigDecimal(0));
                    CcTotalAddition = CcTotalAddition.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CcTotAccuAssetCst =
                        (actRow.getAttribute("CcTotAccuAssetCst") != null ?
                         (BigDecimal) (actRow.getAttribute("CcTotAccuAssetCst")) : new BigDecimal(0));
                    CcTotAccuAssetCst = CcTotAccuAssetCst.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CcAccuDeprOpening =
                        (actRow.getAttribute("CcAccuDeprOpening") != null ?
                         (BigDecimal) (actRow.getAttribute("CcAccuDeprOpening")) : new BigDecimal(0));
                    CcAccuDeprOpening = CcAccuDeprOpening.setScale(2, RoundingMode.HALF_UP);

                    //                    BigDecimal CcPeriodDepr =
                    //                        actRow.getAttribute("CcPeriodDepr") != null ?
                    //                        (BigDecimal)(actRow.getAttribute("CcPeriodDepr")) : new BigDecimal(0);
                    BigDecimal CcPeriodDepr =
                        (actRow.getAttribute("CcPeriodDepr") != null ?
                         (BigDecimal) (actRow.getAttribute("CcPeriodDepr")) : new BigDecimal(0));
                    CcPeriodDepr = CcPeriodDepr.setScale(2, RoundingMode.HALF_UP);
                    System.out.println("CcPeriodDeprf------" + CcPeriodDepr);

                    BigDecimal CcTotAccuDepr =
                        (actRow.getAttribute("CcTotAccuDepr") != null ?
                         (BigDecimal) (actRow.getAttribute("CcTotAccuDepr")) : new BigDecimal(0));
                    CcTotAccuDepr = CcTotAccuDepr.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CcNbv =
                        (actRow.getAttribute("CcNbv") != null ? (BigDecimal) (actRow.getAttribute("CcNbv")) :
                         new BigDecimal(0));
                    CcNbv = CcNbv.setScale(2, RoundingMode.HALF_UP);


                    BigDecimal FdcOpeningNbv =
                        (actRow.getAttribute("FdcOpeningNbv") != null ?
                         (BigDecimal) (actRow.getAttribute("FdcOpeningNbv")) : new BigDecimal(0));
                    FdcOpeningNbv = FdcOpeningNbv.setScale(2, RoundingMode.HALF_UP);


                    BigDecimal FdcAccuCstOpening =
                        (actRow.getAttribute("FdcAccuCstOpening") != null ?
                         (BigDecimal) (actRow.getAttribute("FdcAccuCstOpening")) : new BigDecimal(0));
                    FdcAccuCstOpening = FdcAccuCstOpening.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal FdcPeriodAdditions =
                        (actRow.getAttribute("FdcPeriodAdditions") != null ?
                         (BigDecimal) (actRow.getAttribute("FdcPeriodAdditions")) : new BigDecimal(0));
                    FdcPeriodAdditions = FdcPeriodAdditions.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal FdcCip =
                        (actRow.getAttribute("FdcCip") != null ? (BigDecimal) (actRow.getAttribute("FdcCip")) :
                         new BigDecimal(0));
                    FdcCip = FdcCip.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal FdcAdditionCip =
                        (actRow.getAttribute("FdcAdditionCip") != null ?
                         (BigDecimal) (actRow.getAttribute("FdcAdditionCip")) : new BigDecimal(0));
                    FdcAdditionCip = FdcAdditionCip.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal FdcTotAccuAssetCst =
                        (actRow.getAttribute("FdcTotAccuAssetCst") != null ?
                         (BigDecimal) (actRow.getAttribute("FdcTotAccuAssetCst")) : new BigDecimal(0));
                    FdcTotAccuAssetCst = FdcTotAccuAssetCst.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal FdcAccuDeprOpening =
                        (actRow.getAttribute("FdcAccuDeprOpening") != null ?
                         (BigDecimal) (actRow.getAttribute("FdcAccuDeprOpening")) : new BigDecimal(0));
                    FdcAccuDeprOpening = FdcAccuDeprOpening.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal FdcPeriodDepr =
                        (actRow.getAttribute("FdcPeriodDepr") != null ?
                         (BigDecimal) (actRow.getAttribute("FdcPeriodDepr")) : new BigDecimal(0));
                    FdcPeriodDepr = FdcPeriodDepr.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal FdcTotAccuDepr =
                        (actRow.getAttribute("FdcTotAccuDepr") != null ?
                         (BigDecimal) (actRow.getAttribute("FdcTotAccuDepr")) : new BigDecimal(0));
                    FdcTotAccuDepr = FdcTotAccuDepr.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal FdcNbv =
                        (actRow.getAttribute("FdcNbv") != null ? (BigDecimal) (actRow.getAttribute("FdcNbv")) :
                         new BigDecimal(0));
                    FdcNbv = FdcNbv.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CipCost =
                        (actRow.getAttribute("CipCost") != null ? (BigDecimal) (actRow.getAttribute("CipCost")) :
                         new BigDecimal(0));
                    CipCost = CipCost.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CipCaptizationCost =
                        (actRow.getAttribute("CipCaptizationCost") != null ?
                         (BigDecimal) (actRow.getAttribute("CipCaptizationCost")) : new BigDecimal(0));
                    CipCaptizationCost = CipCaptizationCost.setScale(2, RoundingMode.HALF_UP);

                    BigDecimal CipClsoingBalance =
                        (actRow.getAttribute("CipClsoingBalance") != null ?
                         (BigDecimal) (actRow.getAttribute("CipClsoingBalance")) : new BigDecimal(0));
                    CipClsoingBalance = CipClsoingBalance.setScale(2, RoundingMode.HALF_UP);


                    HSSFRow row = sheet.createRow((short) excelRowData);
                    row.createCell(0).setCellValue(Trans_category);
                    row.createCell(1).setCellValue(CcOpeningNbv.doubleValue());
                    row.createCell(2).setCellValue(CcAccuCstOpening.doubleValue());
                    row.createCell(3).setCellValue(CcPeriodAdditions.doubleValue());
                    row.createCell(4).setCellValue(CcCip.doubleValue());
                    row.createCell(5).setCellValue(CcTotalAddition.doubleValue());
                    row.createCell(6).setCellValue(CcTotAccuAssetCst.doubleValue());
                    row.createCell(7).setCellValue(CcAccuDeprOpening.doubleValue());
                    row.createCell(8).setCellValue(CcPeriodDepr.doubleValue());
                    row.createCell(9).setCellValue(CcTotAccuDepr.doubleValue());
                    row.createCell(10).setCellValue(CcNbv.doubleValue());


                    //                     HSSFRow row1 = sheet1.createRow((short)excelRowData);
                    // row.createCell(0).setCellValue(Trans_category);
                    row.createCell(11).setCellValue(FdcOpeningNbv.doubleValue());
                    row.createCell(12).setCellValue(FdcAccuCstOpening.doubleValue());
                    row.createCell(13).setCellValue(FdcPeriodAdditions.doubleValue());
                    row.createCell(14).setCellValue(FdcCip.doubleValue());
                    row.createCell(15).setCellValue(FdcAdditionCip.doubleValue());
                    row.createCell(16).setCellValue(FdcTotAccuAssetCst.doubleValue());
                    row.createCell(17).setCellValue(FdcAccuDeprOpening.doubleValue());
                    row.createCell(18).setCellValue(FdcPeriodDepr.doubleValue());
                    row.createCell(19).setCellValue(FdcTotAccuDepr.doubleValue());
                    row.createCell(20).setCellValue(FdcNbv.doubleValue());
                    // row.createCell(0).setCellValue(Trans_category);
                    row.createCell(21).setCellValue(CipCost.doubleValue());
                    row.createCell(22).setCellValue(CipCaptizationCost.doubleValue());
                    row.createCell(23).setCellValue(CipClsoingBalance.doubleValue());
                    //                    HSSFRow row2 = sheet2.createRow((short)excelRowData);


                    excelRowData++;
                }
            }
            workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            System.err.println("BDS" + e.getMessage());
        }


    }

    public void onClickTransferToGl(ActionEvent actionEvent) {
        String xmlData = null;
        String[] a = null;
        String payRollWSDL = null;
        payRollWSDL = ApprovalPayload.TRANFER_GL_WSDL;

        String p_EmployeeNumber = "Test";

        xmlData = ApprovalPayload.payrollTypeXMLData(p_EmployeeNumber);

        System.err.println("xmlData =>" + xmlData);
        a = ApprovalProcess.invokeWsdl(xmlData, payRollWSDL, ApprovalPayload.TRANFER_GL_METHOD);
        if (a[0] != null && a[0].equals("True")) {
            JSFUtils.addFacesInformationMessage("Transfer to GL is successful");
            ADFUtils.findOperation("Commit").execute();

        } else {
            System.err.println("came else");
            JSFUtils.addFacesInformationMessage("Error");
        }
        ViewObject HdrVo = ADFUtils.findIterator("UopMaster_VOIterator").getViewObject();
     //   HdrVo.executeQuery();
//        /*refresh*/
//        FacesContext fc = FacesContext.getCurrentInstance();
//        String refreshpage = fc.getViewRoot().getViewId();
//        ViewHandler ViewH = fc.getApplication().getViewHandler();
//        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
//        UIV.setViewId(refreshpage);
//        fc.setViewRoot(UIV);
    }

    public void setCipTable(RichTable cipTable) {
        this.cipTable = cipTable;
    }

    public RichTable getCipTable() {
        return cipTable;
    }


    public boolean refreshFilter() {
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor) getCcTable().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            //            getCcTable().queueEvent(new QueryEvent(getCcTable(), queryDescriptor));
            getCcTable().clearCachedRow(ccTable);
        }
        return true;
    }


    public void setCatBinding(RichInputText catBinding) {
        this.catBinding = catBinding;
    }

    public RichInputText getCatBinding() {
        return catBinding;
    }

    public void getJournalDetails(ActionEvent actionEvent) {
        BigDecimal deprHdrId = new BigDecimal(0);
        BigDecimal assetCategoryId = new BigDecimal(0);
        ViewObject linesVO = ADFUtils.findIterator("UopLines_VOIterator").getViewObject();
        if (linesVO.getCurrentRow() != null) {
            deprHdrId = (BigDecimal) linesVO.getCurrentRow().getAttribute("UopDeprHdrId");
            assetCategoryId = (BigDecimal) linesVO.getCurrentRow().getAttribute("AssetCategoryId");
           
            ViewObject journalVO =
                ADFUtils.getApplicationModuleForDataControl("Oando_AMDataControl")
                .findViewObject("JournalDetails_ROVO");

            ViewCriteria viewCriteria = journalVO.createViewCriteria();
            ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
            viewCriteriaRow.setAttribute("UopDeprHdrId", deprHdrId);
            viewCriteriaRow.setAttribute("AssetCategoryId", assetCategoryId);
            viewCriteria.addRow(viewCriteriaRow);
            journalVO.applyViewCriteria(viewCriteria);
            journalVO.executeQuery();
            System.out.println("journalVO Query ----" + journalVO.getQuery());
            System.out.println("Row count  --------" + journalVO.getEstimatedRowCount());
        }
        journalDetailsPopup.show(new RichPopup.PopupHints());
    }

    public void onClickCancelPopup(ActionEvent actionEvent) {
        journalDetailsPopup.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(journalDetailsPopup); 
        
        FilterableQueryDescriptor queryDescriptor =
            (FilterableQueryDescriptor) getJournalDetailTable().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null)
        {
            queryDescriptor.getFilterCriteria().clear();
            getJournalDetailTable().queueEvent(new QueryEvent(getJournalDetailTable(), queryDescriptor));
        }
    }

    public void setJournalDetailsPopup(RichPopup journalDetailsPopup) {
        this.journalDetailsPopup = journalDetailsPopup;
    }

    public RichPopup getJournalDetailsPopup() {
        return journalDetailsPopup;
    }

    public void setJournalDetailTable(RichTable journalDetailTable) {
        this.journalDetailTable = journalDetailTable;
    }

    public RichTable getJournalDetailTable() {
        return journalDetailTable;
    }
}
