package view;

import bean.User;
import controller.Controller;
import model.ModelData;

public class UsersView implements View{
    private Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void refresh(ModelData modelData) {
        if (modelData.isDisplayDeletedUserList()) {
            System.out.println("All deleted users:");
            for (User user : modelData.getUsers()) {
                System.out.println("\t" + user);
            }
        }
        else {
            System.out.println("All users:");
            for (User user : modelData.getUsers()) {
                System.out.println("\t" + user);
            }
        }
        System.out.println("===================================================");
    }

    public void fireEventShowAllUsers() {
        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }

    public void fireEventOpenUserEditForm(long id) {
        controller.onOpenUserEditForm(id);
    }

}
