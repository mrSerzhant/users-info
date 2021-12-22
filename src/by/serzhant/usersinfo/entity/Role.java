package by.serzhant.usersinfo.entity;

public enum Role {
    USER(1), CUSTOMER(1), ADMIN(2), PROVIDER(2), SUPER_ADMIN(3);

    private int grade;

     Role(int grade){
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }
}
