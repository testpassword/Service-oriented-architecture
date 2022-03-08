package testpassword.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import testpassword.models.Team

@Repository interface TeamsRepo: CrudRepository<Team, Int>