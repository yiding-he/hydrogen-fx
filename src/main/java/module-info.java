module com.hyd.fx {

    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.prefs;
    requires fontawesomefx;
    requires org.apache.commons.io;

    opens com.hyd.fx to javafx.fxml;
    exports com.hyd.fx;
}
