package ru.sber.mvcExample.repository

import java.util.concurrent.ConcurrentHashMap

class AddressBookRepository {
    private val repoRealisation = ConcurrentHashMap<String, AddressInfo>()
}

data class AddressInfo(var name: String, var address: String)