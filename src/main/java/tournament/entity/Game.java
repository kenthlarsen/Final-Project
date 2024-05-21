package tournament.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Game {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long gameId;
		private String gameName;
		private String gameTime;

		@JsonIgnore
		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		@ManyToMany(mappedBy = "games", cascade = CascadeType.PERSIST)
		private Set<Tournament> tournament = new HashSet<>();
	
}

	