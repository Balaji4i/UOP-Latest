package view;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;

public class AddEditWell {
    public AddEditWell() {
    }

    public void onClickSave(ActionEvent actionEvent) {
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Information Saved Successfully");
    }

    public void onClickAdd(ActionEvent actionEvent) {
    
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("CreateInsert1").execute();
        // Add event code here...
    }

    public void onClickCancel(ActionEvent actionEvent) {
        ViewObject HdrVO = ADFUtils.findIterator("WellMasterVOIterator").getViewObject();
        
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Rollback1").execute();
    }

    public void onClickDelete(ActionEvent actionEvent) {
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Delete").execute();
    }

    public void onClickAddChild(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateInsert2").execute();
    }

    public void onClickDeleteChild(ActionEvent actionEvent) {
        ADFUtils.findOperation("Delete1").execute();
    }
}
