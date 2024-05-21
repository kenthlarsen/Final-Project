package tournament.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import tournament.entity.Team;

@Data
@NoArgsConstructor
public class TeamData {
	
		private Long teamId;
		private String teamName;
		private String teamCity;
		private String teamState;
		private String teamPhone;
		
		public TeamData(Team team) {
			teamId = team.getTeamId();
			teamName = team.getTeamName();
			teamCity = team.getTeamCity();
			teamPhone = team.getTeamPhone();
			
		
	}
}
