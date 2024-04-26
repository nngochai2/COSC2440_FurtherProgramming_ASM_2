module org.nikisurance {
    requires jakarta.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    exports org.nikisurance.entity;
    exports org.nikisurance.util;

}