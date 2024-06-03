package tournament.controller.model;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import tournament.service.TournamentService;


@RestController
@RequestMapping("/")
@Slf4j
public class TournamentController {

		@Autowired
		public TournamentService tournamentService;

		@PostMapping("/tournament")
		@ResponseStatus(code = HttpStatus.CREATED)
		public TournamentData saveTournament(@RequestBody TournamentData tournamentData) {
			log.info("Adding tournament {}", tournamentData);
			return tournamentService.saveTournament(tournamentData);
		}

		@PutMapping("/tournament/{tournamentId}")
		public TournamentData updateTournament(@RequestBody TournamentData tournamentData, @PathVariable Long tournamentId) {
			tournamentData.setTournamentId(tournamentId);
			log.info("Updated Tournament Id={}", tournamentData);
			return tournamentService.saveTournament(tournamentData);
		}
				
		@PostMapping("/{tournamentId}/game")
		@ResponseStatus(code = HttpStatus.CREATED)
		public GameData addGame(@PathVariable Long tournamentId, @RequestBody GameData tournamentGame) {
			log.info("Creating game {} for tournament with ID={}", tournamentGame, tournamentId);
			return tournamentService.saveGame(tournamentId, tournamentGame);
		}

		@PostMapping("/{tournamentId}/team")
		@ResponseStatus(code = HttpStatus.CREATED)
		public TeamData addTournamentTeam(@PathVariable Long tournamentId, @RequestBody TeamData tournamentTeam) {
			log.info("Creating team {} for tournament with ID={}", tournamentTeam, tournamentId);
			return tournamentService.saveTeam(tournamentId, tournamentTeam);
		}

		@GetMapping
		public List<TournamentData> retrieveAllTournaments() {
			log.info("Retrieving all Tournaments.");
			return tournamentService.retrieveAllTournaments();
		}

		@GetMapping("/{tournamentId}")
		public TournamentData retrieveTournamentById(@PathVariable Long tournamentId) {
			log.info("Retrieving Tournament with ID={}", tournamentId);
			return tournamentService.getTournamentById(tournamentId);
		}

		@DeleteMapping("/tournament/{tournamentId}")
		public Map<String, String> deleteTournamentById(@PathVariable Long tournamentId) {
			log.info("Deleting Tournament with ID=()", tournamentId);
			tournamentService.deleteTournamentById(tournamentId);
			return Map.of("message", "Deletion of Tournament with ID=" + tournamentId + " was successful.");
		
	}
		@DeleteMapping("/team/{teamId}")
		public Map<String, String> deleteTeamById(@PathVariable Long teamId) {
			log.info("Deleting Team with ID=()", teamId);
			tournamentService.deleteTeamById(teamId);
			return Map.of("message", "Deletion of Team with ID=" + teamId + " was successful.");
		
	}
}
