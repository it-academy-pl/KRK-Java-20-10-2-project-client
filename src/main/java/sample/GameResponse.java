package sample;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class GameResponse {
    private long id;
    private char[] grid;
    private String playerX;
    private String playerO;
    private String gameStatus;
}
