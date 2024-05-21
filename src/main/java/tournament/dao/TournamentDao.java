package tournament.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tournament.entity.Tournament;

	public interface TournamentDao extends JpaRepository<Tournament, Long> {

	
}
