package testpassword.service2.services

import testpassword.service2.models.Team

interface TeamService {

    fun getTeamsWithMembersIds(): Set<TeamServiceImpl.TeamDto>

    infix fun createTeam(team: Team): Int

    fun bindPersonToTeam(teamId: Int, personId: Int)
}