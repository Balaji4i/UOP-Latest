package view;

import com.view.utils.ADFUtils;

import com.view.utils.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;

public class AddEditCip {
    public AddEditCip() {
    }

    public void onClickSave(ActionEvent actionEvent) {
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Information Saved Successfully");
    }

    public void onClickCancel(ActionEvent actionEvent) {
        ViewObject HdrVO = ADFUtils.findIterator("Cip_VOIterator").getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Rollback").execute();
    }
}
