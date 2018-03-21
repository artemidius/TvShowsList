package com.tomtom.tom.tvshowslist.base


interface Navigator {
    companion object {
        val FORMULAR_FRAGMENT = "formular"
        val PEOPLE_LIST_FRAGMENT = "people_list"
        val CHAT_FRAGMENT = "chat"
    }

    fun navigateTo(fragment:String)
}