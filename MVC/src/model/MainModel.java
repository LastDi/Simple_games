package model;

import bean.User;
import model.service.UserService;
import model.service.UserServiceImpl;
import java.util.List;

public class MainModel implements Model{
    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();

    private List<User> getAllUsers() {
        return userService.filterOnlyActiveUsers(userService.getUsersBetweenLevels(1, 100));
    }

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {
        modelData.setDisplayDeletedUserList(false);
        modelData.setUsers(getAllUsers());
    }

    public void loadDeletedUsers() {
        modelData.setDisplayDeletedUserList(true);
        modelData.setUsers(userService.getAllDeletedUsers());
    }
    @Override
    public void loadUserById(long userId) {
        modelData.setActiveUser(userService.getUsersById(userId));
    }

    @Override
    public void deleteUserById(long userid) {
        modelData.setActiveUser(userService.deleteUser(userid));
        modelData.setUsers(getAllUsers());
    }

    @Override
    public void changeUserData(String name, long id, int level) {
        modelData.setActiveUser(userService.createOrUpdateUser(name, id, level));
        modelData.setUsers(getAllUsers());
    }
}

