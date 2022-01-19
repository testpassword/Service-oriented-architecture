package testpassword.service2.services

import testpassword.service2.models.Dragon
import javax.ejb.Remote

enum class SORT_TYPE { MIN, MAX }

@Remote interface DragonService { infix fun getDragonWithDeepestCave(type: SORT_TYPE): List<Dragon> }