package sample;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class GameController {
    private static final String URL = "http://localhost:8080";
    private static final String REGISTER_PLAYER_URL = "/players/register";
    private static final String CREATE_GAME_URL = "/games/create";
    private static final String JOIN_GAME_URL = "/games/join";
    private static final String MAKE_MOVE = "/games/makeMove";

    private GameResponse game;
    private GameStatus gameStatus;

    private final VisualElements visualElements;

    GameController(VisualElements visualElements) {
        this.visualElements = visualElements;
    }

    void registerXPlayer() {
        registerPlayer(visualElements.getXName(),
                visualElements.getXPassword());
    }

    void registerOPlayer() {
        registerPlayer(visualElements.getOName(),
                visualElements.getOPassword());
    }

    private void registerPlayer(String name, String password) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client
                .target(URL)
                .path(REGISTER_PLAYER_URL)
                .queryParam("name", name)
                .queryParam("password", password);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(null);
        Text error = visualElements.getError();
        if (response.getStatus() == 200) {
            response.readEntity(RegisterPlayerResponse.class);
            error.setText("");
        } else {
            handleError(response, error);
        }
        response.close();
        client.close();
    }

    void createGame() {
        String name = visualElements.getXName();
        String password = visualElements.getXPassword();

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client
                .target(URL)
                .path(CREATE_GAME_URL)
                .queryParam("playerName", name)
                .queryParam("playerPassword", password);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(null);
        Text error = visualElements.getError();
        if (response.getStatus() == 200) {
            updateGameBoard(response);
            error.setText("");
        } else {
            handleError(response, error);
        }
        response.close();
        client.close();
    }

    void joinGame() {
        String name = visualElements.getOName();
        String password = visualElements.getOPassword();

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client
                .target(URL)
                .path(JOIN_GAME_URL)
                .queryParam("playerName", name)
                .queryParam("playerPassword", password)
                .queryParam("gameId", game.getId());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.put(Entity.json("{}"));
        Text error = visualElements.getError();
        if (response.getStatus() == 200) {
            updateGameBoard(response);
            error.setText("");
        } else {
            handleError(response, error);
        }
        response.close();
        client.close();
    }

    void makeMove(int buttonIndex) {
        String name = "";
        String password = "";
        if (gameStatus == GameStatus.MOVE_O) {
            name = visualElements.getOName();
            password = visualElements.getOPassword();
        } else if (gameStatus == GameStatus.MOVE_X) {
            name = visualElements.getXName();
            password = visualElements.getXPassword();
        }

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client
                .target(URL)
                .path(MAKE_MOVE)
                .queryParam("playerName", name)
                .queryParam("playerPassword", password)
                .queryParam("gameId", game.getId())
                .queryParam("gridPosition", buttonIndex);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(null);
        Text error = visualElements.getError();
        if (response.getStatus() == 200) {
            updateGameBoard(response);
            error.setText("");
        } else {
            handleError(response, error);
        }
        response.close();
        client.close();
    }

    private void updateGameBoard(Response response) {
        game = response.readEntity(GameResponse.class);
        gameStatus = GameStatus.valueOf(game.getGameStatus());

        Button[] buttons = visualElements.getButtons();
        for (int i = 0; i < game.getGrid().length; i++) {
            char c = game.getGrid()[i];
            buttons[i].setText(String.valueOf(c));
        }

        Text status = visualElements.getStatus();
        status.setText(game.getGameStatus());
    }

    private void handleError(Response response, Text error) {
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        error.setText(errorResponse.getMessage());
    }

}
