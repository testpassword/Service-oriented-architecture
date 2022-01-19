package testpassword.service2.services

import testpassword.service2.models.Dragon

enum class SORT_TYPE { MIN, MAX }

interface DragonService { infix fun getDragonWithDeepestCave(type: SORT_TYPE): List<Dragon> }