package tournament.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import tournament.entity.Game;

@Data
@NoArgsConstructor
public class GameData {
	
	private Long gameId;
	private String gameName;
	private String gameTime;
	
	public GameData(Game game) {
		gameId = game.getGameId();
		gameName = game.getGameName();
		gameTime = game.getGameTime();
	}

}
