module org.nikisurance {
    requires jakarta.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires jdk.jdi;

    exports org.nikisurance.entity;
    exports org.nikisurance.util;
    opens org.nikisurance to javafx.fxml;
    opens org.nikisurance.entity to org.hibernate.orm.core;
}