package testpassword.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import testpassword.models.Coordinates

@Repository interface CoordinatesRepo: CrudRepository<Coordinates, Int>