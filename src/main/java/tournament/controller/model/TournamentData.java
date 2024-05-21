package tournament.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import tournament.entity.Game;
import tournament.entity.Team;
import tournament.entity.Tournament;

@Data
@NoArgsConstructor
public class TournamentData {

	private Long tournamentId;
	private String tournamentName;
	private String tournamentAddress;
	private String tournamentCity;
	private String tournamentState;
	private String tournamentZip;
	private String tournamentPhone;
	private Set<TournamentGame> games = new HashSet<>();
	private Set<TournamentTeam> teams = new HashSet<>();

	public TournamentData(Tournament tournament) {

		tournamentId = tournament.getTournamentId();
		tournamentName = tournament.getTournamentName();
		tournamentAddress = tournament.getTournamentAddress();
		tournamentCity = tournament.getTournamentCity();
		tournamentState = tournament.getTournamentState();
		tournamentZip = tournament.getTournamentZip();
		tournamentPhone = tournament.getTournamentPhone();

		for (Game game : tournament.getGames()) {
			games.add(new TournamentGame(game));
		}

		for (Team team : tournament.getTeams()) {
			teams.add(new TournamentTeam(team));
		}
	}

	@Data
	@NoArgsConstructor
	public static class TournamentGame {

		private Long gameId;
		private String gameName;
		private String gameTime;
		private Set<Tournament> tournament = new HashSet<>();

		public TournamentGame(Game game) {
			gameId = game.getGameId();
			gameName = game.getGameName();
			gameTime = game.getGameTime();
			
		}
	}

	@Data
	@NoArgsConstructor
	public static class TournamentTeam {

		private Long teamId;
		private String teamName;
		private String teamCity;
		private String teamState;
		private String teamPhone;

		public TournamentTeam(Team team) {
			teamId = team.getTeamId();
			teamName = team.getTeamName();
			teamCity = team.getTeamCity();
			teamState = team.getTeamState();
			teamPhone = team.getTeamPhone();

		}
	}

}