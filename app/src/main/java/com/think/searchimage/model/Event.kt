package com.think.searchimage.model

class Event<T>(private var data: T) {

    var isAlreadyHandled = false
        private set

    fun getData(): T? {
        if (isAlreadyHandled) {
            return null
        }
        isAlreadyHandled = true
        return data
    }

    fun peekContent(): T {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }

}