package service1.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import service1.models.Coordinates

@Repository interface CoordinatesRepo: CrudRepository<Coordinates, Int>