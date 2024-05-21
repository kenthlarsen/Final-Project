package tournament.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tournament.entity.Team;


	public interface TeamDao extends JpaRepository<Team, Long> {
		
	
}
