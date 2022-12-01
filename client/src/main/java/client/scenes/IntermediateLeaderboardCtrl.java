package client.scenes;

import client.service.ServerService;
import com.google.inject.Inject;
import commons.model.LeaderboardEntry;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class IntermediateLeaderboardCtrl extends AbstractCtrl implements Initializable {
	private final ServerService server;
	private final MainCtrl mainCtrl;
	private static final int TIME_PASSED = 3000;
	@FXML
	private Label state;

	private List<LeaderboardEntry> intermediateList;

	@FXML
	private TableView<LeaderboardEntry> intermediateLeaderboard;

	@FXML
	private TableColumn<LeaderboardEntry, Number> colRanking;

	@FXML
	private TableColumn<LeaderboardEntry, String> colUsername;

	@FXML
	private TableColumn<LeaderboardEntry, Number> colScore;


	private ObservableList<LeaderboardEntry> data;

	@Inject
	public IntermediateLeaderboardCtrl(ServerService server, MainCtrl mainCtrl) {
		this.server = server;
		this.mainCtrl = mainCtrl;
	}

	public void setIntermediateList(List<LeaderboardEntry> list) {
		intermediateLeaderboard
				.setItems(FXCollections.observableList(list));
	}

	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  {@code null} if the location is not known.
	 * @param resources The resources used to localize the root object, or {@code null} if
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().name()));
		colScore.setCellValueFactory(q -> new SimpleIntegerProperty(q.getValue().score()));

		colRanking.setCellValueFactory(q -> {
			ReadOnlyObjectWrapper<Number> finalRank;
			finalRank = new ReadOnlyObjectWrapper<>(intermediateLeaderboard.getItems().indexOf(q.getValue()) + 1);
			return finalRank;
		});
	}
}
