package tournament.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tournament.entity.Game;


	public interface GameDao  extends JpaRepository<Game, Long> {

}
