package carsharing.view;

public enum Type {
    CUSTOMER ("customer"),
    COMPANY ("company"),
    CAR ("car");

    private String title;

    public String getTitle() {
        return title;
    }

    Type(String title) {
        this.title = title;
    }
}
