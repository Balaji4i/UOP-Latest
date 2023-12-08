package view;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;
//import model.vo.DutyAllowance_VORowImpl;
public class CipAttachmentPageFlow {
    private RichInputFile inputFile;
    private RichPopup attachmentPopup;
    private String fileName;
          private String contentType;
         private InputStream inputstream;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setInputstream(InputStream inputstream) {
        this.inputstream = inputstream;
    }

    public InputStream getInputstream() {
        return inputstream;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }
    private UploadedFile file;
    public CipAttachmentPageFlow() {
    }
    public BlobDomain getBlob(InputStream inputstream) {
               BlobDomain blobDomain = null;
               OutputStream out = null;
               try {
                   
                   blobDomain = new BlobDomain();
                   out = blobDomain.getBinaryOutputStream();
                   IOUtils.copy(inputstream, out);
               } catch (IOException e) {
                   e.printStackTrace();
               } catch (SQLException e) {
                   e.fillInStackTrace();
               }
               return blobDomain;
           }

    public String onCallPopup() {
      //   JSFUtils.showPopup(attachmentPopup);
//        inputFile.resetValue();
//                       inputFile.setValid(true);
                       
//                       System.out.println("11111111111111111");
//                System.out.println("------Start");
//                       ViewObject ReimVo =
//                           ADFUtils.getApplicationModuleForDataControl("Oando_AM").findViewObject("CipMaster_VO");
//                       
//                       ViewObject LineVo =
//                           ADFUtils.getApplicationModuleForDataControl("Oando_AM").findViewObject("Attachment_VO");
//                       ViewCriteria viewCriteria = LineVo.createViewCriteria();
//                       ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
//                       viewCriteriaRow.setAttribute("SourceDocumentId", ReimVo.getCurrentRow().getAttribute("ReimbursementRequestId"));
//                       viewCriteria.addRow(viewCriteriaRow);
//                       LineVo.applyViewCriteria(viewCriteria);
//                       LineVo.executeQuery();
//                       System.out.println("-------------Final-------------");
//                System.out.println("22222222222222");  
                attachmentPopup.show(new RichPopup.PopupHints());
                System.out.println("3333333333");
                return null;
    }
    
          
    public void onClickAdd(ActionEvent actionEvent) {
        ViewObject ReimVo =
                   ADFUtils.findIterator("CipMaster_VOIterator").getViewObject();
               String File =inputFile.getValue() != null ? inputFile.getValue().toString() : "";
               if (!(File.equals(""))){
          
               BigDecimal id =
                   ReimVo.getCurrentRow().getAttribute("TransactionId") !=
                   null ?
                   (BigDecimal)ReimVo.getCurrentRow().getAttribute("TransactionId") :
                   new BigDecimal(0);
               System.out.println(id+"---------------------id");
               String sourceDocument ="Cip";

               
               System.out.println(fileName+contentType);
               BlobDomain blob = null;

               System.out.println(inputstream +"----inputS");
               
               

        
               if(inputstream != null){
               blob = getBlob(inputstream);
               System.out.println(blob +"------------------blob");
               
               
               ViewObject AttachmentVo =
                   ADFUtils.getApplicationModuleForDataControl("Oando_AMDataControl").findViewObject("Attachment_VO");
               Row newRow = AttachmentVo.createRow();
               newRow.setAttribute("Attachment", blob);
               newRow.setAttribute("AttachmentName",fileName );
               newRow.setAttribute("AttachmentType", contentType);
               newRow.setAttribute("SourceDocument", sourceDocument);
               newRow.setAttribute("SourceDocumentId",id);
               
               AttachmentVo.insertRow(newRow);
               
                   RichTable t1 =
                       (RichTable)JSFUtils.findComponentInRoot("t1");
                   AdfFacesContext.getCurrentInstance().addPartialTarget(t1);
               }
               }
               else{
                   System.out.println("No file is added");  
               }
             
    }

    public void onClickAttachCancel(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setInputFile(RichInputFile inputFile) {
        this.inputFile = inputFile;
    }

    public RichInputFile getInputFile() {
        return inputFile;
    }

    public void onChangeAttachment(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                       file = (UploadedFile)valueChangeEvent.getNewValue();
               try {
                   inputstream = file.getInputStream();
                   
                  
               } catch (IOException e)
               {
               } System.out.println(inputstream+"-----input Streem");
               
                       fileName = file.getFilename();
                     
                       contentType = file.getContentType();
                      
    }

    public void onClickDownload(FacesContext facesContext, OutputStream outputStream) {
        BlobDomain blob =
                    (BlobDomain)ADFUtils.getBoundAttributeValue("Attachment");
                try {
                    IOUtils.copy(blob.getInputStream(), outputStream);
                    blob.closeInputStream();
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }

    public void setAttachmentPopup(RichPopup attachmentPopup) {
        this.attachmentPopup = attachmentPopup;
    }

    public RichPopup getAttachmentPopup() {
        return attachmentPopup;
    }
}
