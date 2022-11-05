package com.skarlat.tripexpenses.data.repository

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NameDictionaryImpl @Inject constructor(
    tripRepository: ITripRepository,
    participantsRepository: IParticipantRepository
) : NameDictionary, MutableMap<String, String> by mutableMapOf() {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + CoroutineName("NameDictionaryImpl.scope()"))

    private val nameMap = combine(
        tripRepository.tripNameMapFlow,
        participantsRepository.participantNamesMapFlow
    ) { arrayOfMaps ->
        buildMap {
            arrayOfMaps.forEach {
                this@buildMap.putAll(it)
            }
        }
    }
        .onEach { putAll(it) }
        .stateIn(
            coroutineScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyMap()
        ).launchIn(coroutineScope)


    private val ITripRepository.tripNameMapFlow: Flow<Map<String, String>>
        get() = getTripsFlow().map { tripList -> tripList.associate { trip -> trip.id to trip.name } }
    private val IParticipantRepository.participantNamesMapFlow: Flow<Map<String, String>>
        get() = getParticipantsFlow().map { participantList -> participantList.associate { it.id to it.name } }
}