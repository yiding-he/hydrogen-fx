module com.hyd.fx {

    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.prefs;
    requires org.apache.commons.io;

    opens com.hyd.fx to javafx.fxml;
    exports com.hyd.fx;
    exports com.hyd.fx.app;
    exports com.hyd.fx.builders;
    exports com.hyd.fx.cells;
    exports com.hyd.fx.components;
    exports com.hyd.fx.concurrency;
    exports com.hyd.fx.dialog;
    exports com.hyd.fx.dialog.form;
    exports com.hyd.fx.enhancements;
    exports com.hyd.fx.helpers;
    exports com.hyd.fx.style;
    exports com.hyd.fx.system;
    exports com.hyd.fx.utils;
}
