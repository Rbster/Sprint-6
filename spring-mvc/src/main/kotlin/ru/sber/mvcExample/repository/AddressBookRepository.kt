package ru.sber.mvcExample.repository

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class AddressBookRepository {
    private val repoRealisation = ConcurrentHashMap<String, AddressInfo>()
    private fun nextId(): String {
        if (repoRealisation.size == 0) {
            return "id1"
        } else {
            val idxes = repoRealisation.keys().asSequence()
                .map { it.slice(2 until it.length).toInt() }
                .sorted()
                .toList()
            if (idxes[idxes.size - 1] == idxes.size) {
                return "id${idxes.size}"
            }
            for (i in 1..idxes[idxes.size - 1]) {
                if (i != idxes[i - 1]) {
                    return "id$i"
                }
            }
            return "id0" // never happens
        }
    }
    fun add(element: AddressInfo): String? {
        val id = nextId()
        return try {
            repoRealisation[id] = element
            id
        } catch (e: Exception) {
            null
        }
    }
    fun delete(id: String): Boolean {
        return  try { repoRealisation.remove(id) != null } catch (e: Exception) { false }
    }
    fun edit(id: String, form: AddressSearchForm): Boolean  {
        val oldElement = repoRealisation[id] ?: return false
        val newElement = AddressInfo(form.name ?: oldElement.name, form.address ?: oldElement.address)
        return  try { repoRealisation.replace(id, newElement) != null } catch (e: Exception) { false }
    }
    fun view(id: String) = repoRealisation[id]
    fun list(searchForm: AddressSearchForm = AddressSearchForm(null, null)) = repoRealisation
        .toList()
        .asSequence()
        .filter { (searchForm.address ?: it.second.address) == it.second.address}
        .filter { (searchForm.name ?: it.second.name) == it.second.name}
        .toMap()
}

data class AddressInfo(var name: String, var address: String)
data class AddressSearchForm(val name: String?, val address: String?)