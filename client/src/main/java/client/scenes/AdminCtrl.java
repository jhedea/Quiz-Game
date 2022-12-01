package client.scenes;

import client.service.ServerService;
import client.utils.NumberUtils;
import com.google.inject.Inject;
import commons.model.Activity;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCtrl extends AbstractCtrl implements Initializable {
	private final ServerService serverService;
	private final MainCtrl mainCtrl;

	@FXML
	private TextField idTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField energyTextField;
	@FXML
	private TextField urlTextField;

	@FXML
	private Button saveButton;
	@FXML
	private Button removeButton;

	@FXML
	private Label errorLabel;

	@FXML
	private TableView<Activity> table;
	@FXML
	private TableColumn<Activity, String> columnOne;
	@FXML
	private TableColumn<Activity, Number> columnTwo;

	private final ObservableList<Activity> activities = FXCollections.observableArrayList();

	private Long selectedActivityId;

	@Inject
	public AdminCtrl(ServerService serverService, MainCtrl mainCtrl) {
		this.serverService = serverService;
		this.mainCtrl = mainCtrl;
	}

	// Called by JavaFX while initializing the scene
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnOne.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().name()));
		columnTwo.setCellValueFactory(x -> new SimpleFloatProperty(x.getValue().energyInWh()));

		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> onItemClick(newSel));
		table.setItems(activities);
	}

	// Called by MainCtrl when entering the scene
	@Override
	public void init() {
		super.init();
		activities.setAll(serverService.getActivities());
		onItemClick(null);
	}

	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ESCAPE) returnHome();
	}

	public void returnHome() {
		mainCtrl.showHome();
	}

	private void onItemClick(Activity activity) {
		selectedActivityId = activity != null ? activity.id() : null;

		if (selectedActivityId != null) {
			idTextField.setText(String.valueOf(selectedActivityId));
			nameTextField.setText(activity.name());
			energyTextField.setText(String.valueOf(activity.energyInWh()));
			urlTextField.setText(activity.imageUrl());

			saveButton.setText("Update activity");
			removeButton.setDisable(false);
		} else {
			clearEditFields();

			saveButton.setText("Add activity");
			removeButton.setDisable(true);
		}

		resetError();
	}

	public void onNewClick() {
		unselectActivity();
	}

	public void onSaveClick() {
		var name = nameTextField.getText();
		var url = urlTextField.getText();
		var energy = NumberUtils.parseFloatOrNull(energyTextField.getText());

		if (energy == null) {
			errorLabel.setText("Invalid energy value");
			return;
		} else {
			resetError();
		}

		if (selectedActivityId == null) {
			var activity = new Activity(-1, name, url, energy);
			addActivity(activity);
		} else {
			var activity = new Activity(selectedActivityId, name, url, energy);
			updateActivity(activity);
		}
	}

	public void onRemoveClick() {
		if (selectedActivityId == null) return;
		removeActivity(selectedActivityId);
		unselectActivity();
	}

	private void addActivity(Activity activity) {
		var addedActivity = serverService.addActivity(activity);
		if (addedActivity != null) {
			activities.add(addedActivity);
			selectActivity(addedActivity);
		}
	}

	private void updateActivity(Activity updatedActivity) {
		activities.replaceAll(a -> a.id() == updatedActivity.id() ? updatedActivity : a);
		serverService.updateActivity(updatedActivity);
		selectActivity(updatedActivity);
	}

	private void removeActivity(long id) {
		activities.removeIf(a -> a.id() == id);
		serverService.removeActivity(id);
		unselectActivity();
	}

	private void clearEditFields() {
		idTextField.clear();
		nameTextField.clear();
		energyTextField.clear();
		urlTextField.clear();
	}

	private void resetError() {
		errorLabel.setText("");
	}

	private void selectActivity(Activity activity) {
		table.getSelectionModel().select(activity);
	}

	private void unselectActivity() {
		table.getSelectionModel().clearSelection();
	}
}
