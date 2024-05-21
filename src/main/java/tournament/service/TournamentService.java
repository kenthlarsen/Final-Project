package tournament.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tournament.controller.model.GameData;
import tournament.controller.model.TeamData;
import tournament.controller.model.TournamentData;
import tournament.dao.GameDao;
import tournament.dao.TeamDao;
import tournament.dao.TournamentDao;
import tournament.entity.Game;
import tournament.entity.Team;
import tournament.entity.Tournament;


@Service
public class TournamentService {

	@Autowired
	private TournamentDao tournamentDao;

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private GameDao gameDao;

	public TournamentData saveTournament(TournamentData tournamentData) {
		Long tournamentId = tournamentData.getTournamentId();
		Tournament tournament = findOrCreateTournament(tournamentId);
		copyTournamentFields(tournament, tournamentData);
		tournament = tournamentDao.save(tournament);
		return tournamentData;
	}

	private Tournament findOrCreateTournament(Long tournamentId) {
		if (Objects.isNull(tournamentId)) {
			return new Tournament();
		} else {
			try {
				return findTournamentById(tournamentId);
			} catch (Exception e) {
				throw e;
			}
		}

	}

	private Tournament findTournamentById(Long tournamentId) {
		return tournamentDao.findById(tournamentId)
				.orElseThrow(() -> new NoSuchElementException("Tournament with ID=" + tournamentId + " was not found."));
	}

	public TournamentData getTournamentById(Long tournamentId) {
		TournamentData tournamentData = null;
		Tournament tournament = tournamentDao.findById(tournamentId)
				.orElseThrow(() -> new NoSuchElementException("Tournament with ID=" + tournamentId + " was not found."));
		if (tournament != null) {
			tournamentData = new TournamentData(tournament);
		}
		return tournamentData;
	}

	private void copyTournamentFields(Tournament tournament, TournamentData tournamentData) {
		tournament.setTournamentId(tournamentData.getTournamentId());
		tournament.setTournamentName(tournamentData.getTournamentName());
		tournament.setTournamentAddress(tournamentData.getTournamentAddress());
		tournament.setTournamentCity(tournamentData.getTournamentCity());
		tournament.setTournamentState(tournamentData.getTournamentState());
		tournament.setTournamentZip(tournamentData.getTournamentZip());
		tournament.setTournamentPhone(tournamentData.getTournamentPhone());
	}

	@Transactional(readOnly = false)
	public TeamData saveTeam(Long tournamentId, TeamData teamData) {
		Tournament tournament = findTournamentById(tournamentId);

		Long teamId = teamData.getTeamId();
		Team team = findOrCreateTournamentTeam(teamId, tournamentId);
		copyTeamFields(team, teamData);
		team.setTournament(tournament);
		tournament.getTeams().add(team);

		return new TeamData(teamDao.save(team));
	}

	private void copyTeamFields(Team team, TeamData teamData) {
		team.setTeamId(teamData.getTeamId());
		team.setTeamName(teamData.getTeamName());
		team.setTeamCity(teamData.getTeamCity());
		team.setTeamState(teamData.getTeamState());
		team.setTeamPhone(teamData.getTeamPhone());
	}

	private Team findTeamById(Long tournamentId, Long teamId) {
		Team team = teamDao.findById(teamId)
				.orElseThrow(() -> new NoSuchElementException("Team with ID=" + teamId + " does not exist."));

		if (team.getTournament().getTournamentId() != tournamentId) {
			throw new IllegalArgumentException(
					"Team with ID-" + teamId + " is not a team in the tournament with ID=" + tournamentId + ".");
		}
		return team;
	}

	private Team findOrCreateTournamentTeam(Long teamId, Long tournamentId) {
		Team team;

		if (Objects.isNull(teamId)) {
			team = new Team();
		} else {
			team = findTeamById(teamId, tournamentId);
		}
		return team;
	}

	private Game findOrCreateTournamentGame(Long gameId, Long tournamentId) {
		Game game;

		if (Objects.isNull(gameId)) {
			game = new Game();
		} else {
			game = findGameById(gameId, tournamentId);
		}
		return game;
	}

	
	@Transactional(readOnly = false)
	public GameData saveGame(Long tournamentId, GameData tournamentGame) {
		Tournament tournament = findTournamentById(tournamentId);
		Long gameId = tournamentGame.getGameId();
		Game game = findOrCreateTournamentGame(gameId, tournamentId);
		copyGameFields(game, tournamentGame);
		game.getTournament().add(tournament);
		tournament.getGames().add(game);
		return new GameData(gameDao.save(game));
	}

	

	private void copyGameFields(Game game, GameData gameData) {
		game.setGameId(gameData.getGameId());
		game.setGameName(gameData.getGameName());
		game.setGameTime(gameData.getGameTime());
		
	}

	private Game findGameById(Long gameId, Long tournamentId) {
		Game game = gameDao.findById(gameId)
				.orElseThrow(() -> new NoSuchElementException("Game with ID=" + gameId + " was not found."));
		boolean found = false;
		for (Tournament tournament : game.getTournament()) {
			if (tournament.getTournamentId() == tournamentId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Game with ID=" + gameId + " does not match the tournamentID " + tournamentId);

		}
		return game;
	}

	@Transactional(readOnly = true)
	public List<TournamentData> retrieveAllTournaments() {
		List<Tournament> tournaments = tournamentDao.findAll();
		List<TournamentData> response = new LinkedList<>();

		for (Tournament tournament : tournaments) {
			TournamentData psd = new TournamentData(tournament);
			psd.getGames().clear();
			psd.getTeams().clear();

			response.add(psd);
		}
		return response;
	}

	@Transactional(readOnly = true)
	public TournamentData retrieveTournamentById(Long tournamentId) {
		Tournament tournament = tournamentDao.findById(tournamentId)
				.orElseThrow(() -> new NoSuchElementException("Tournament with ID=" + tournamentId + " was not found."));
		boolean found = false;
		if (tournament.getTournamentId() == tournamentId) {
			found = true;
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Game with ID=" + tournamentId + " does not match the Tournament ID " + tournamentId);
		}
		return new TournamentData(tournament);

	}

	@Transactional(readOnly = false)
	public void deleteTournamentById(Long tournamentId) {
		Tournament tournament = findTournamentById(tournamentId);
		tournamentDao.delete(tournament);

	}

}