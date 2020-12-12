/** module file for project */

module com.abhaysp.tdlj {
	requires javafx.fxml;
	requires javafx.controls;

	opens com.abhaysp.tdlj to javafx.fxml, javafx.graphics;
}