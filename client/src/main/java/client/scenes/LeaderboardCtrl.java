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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardCtrl extends AbstractCtrl implements Initializable {

	private final ServerService server;
	private final MainCtrl mainCtrl;

	private ObservableList<LeaderboardEntry> data;

	@FXML
	private TableView<LeaderboardEntry> singleLeaderboard;

	@FXML
	private TableColumn<LeaderboardEntry, String> colUsername;

	@FXML
	private TableColumn<LeaderboardEntry, Number> colScore;

	@FXML
	private TableColumn<LeaderboardEntry, Number> colRanking;


	@Inject
	public LeaderboardCtrl(ServerService server, MainCtrl mainCtrl) {
		this.server = server;
		this.mainCtrl = mainCtrl;
	}


	public void initialize(URL location, ResourceBundle resources) {
		colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().name()));
		colScore.setCellValueFactory(q -> new SimpleIntegerProperty(q.getValue().score()));

		colRanking.setCellValueFactory(q -> {
			ReadOnlyObjectWrapper<Number> finalRank;
			finalRank = new ReadOnlyObjectWrapper<>(singleLeaderboard.getItems().indexOf(q.getValue()) + 1);
			return finalRank;
		});
	}


	public void refresh() {
		var leaderboardEntries = server.getLeaderboardData();

		data = FXCollections.observableList(leaderboardEntries);
		singleLeaderboard.setItems(data);
	}


	// Links to the return button
	public void returnHome() {
		mainCtrl.showHome();
	}
}
