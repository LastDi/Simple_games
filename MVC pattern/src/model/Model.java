package model;

public interface Model {
    ModelData getModelData();
    void loadDeletedUsers();
    void loadUsers();
    void loadUserById(long userid);
    void deleteUserById(long userid);
    void changeUserData(String name, long id, int level);
}

