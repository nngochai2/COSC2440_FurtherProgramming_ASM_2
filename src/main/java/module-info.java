module org.nikisurance {
    requires jakarta.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires jdk.jdi;
    requires com.zaxxer.hikari;
    requires jdk.jshell;
    requires spring.beans;
    requires spring.context;
    requires spring.data.jpa;
    requires jakarta.servlet;
    requires commons.fileupload;

    exports org.nikisurance.entity;
    exports org.nikisurance.util;
    exports org.nikisurance;
    opens org.nikisurance to javafx.fxml;
    opens org.nikisurance.util to javafx.fxml;
    opens org.nikisurance.controller to javafx.fxml;
    opens org.nikisurance.entity to org.hibernate.orm.core;
}