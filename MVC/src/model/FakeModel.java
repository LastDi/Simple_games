package model;

import bean.User;
import java.util.ArrayList;
import java.util.List;

public class FakeModel implements Model{
    private ModelData modelData = new ModelData();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("John", 1, 3));
        users.add(new User("Peter", 2, 5));
        modelData.setUsers(users);
    }

    @Override
    public void changeUserData(String name, long id, int level) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadDeletedUsers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadUserById(long userid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUserById(long userid) {
        throw new UnsupportedOperationException();
    }
}