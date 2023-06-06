module brock.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens brock.finalproject to javafx.fxml;
    exports brock.finalproject;
    exports controller;
    exports model;
    opens controller to javafx.fxml;
}