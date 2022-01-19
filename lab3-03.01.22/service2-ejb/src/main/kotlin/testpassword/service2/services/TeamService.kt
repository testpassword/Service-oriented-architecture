package testpassword.service2.services

import testpassword.service2.models.Team
import java.io.Serializable

data class TeamDto(val id: Int, val name: String, val membersIds: List<Int?>): Serializable

interface TeamService {

    fun getTeamsWithMembersIds(): Set<TeamDto>

    infix fun createTeam(team: Team): Int

    fun bindPersonToTeam(teamId: Int, personId: Int)
}