/*
 * Copyright 2020 Appinventiv. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.think.searchimage.network.newNetwork


class Result<T> {
    var data: T? = null
    private var failureResponse: FailureResponse?
    var status: Status
        private set
    var msg: String? = null
        private set
    val code = 0
    var statusCode = 0
        private set

    constructor(data: T, statusCode: Int, msg: String?, failureResponse: FailureResponse?, status: Status) {
        this.data = data
        this.failureResponse = failureResponse
        this.status = status
        this.statusCode = statusCode
        this.msg = msg
    }

    constructor(statusCode: Int, msg: String?, failureResponse: FailureResponse?, status: Status) {

        this.failureResponse = failureResponse
        this.status = status
        this.statusCode = statusCode
        this.msg = msg
    }

    constructor(data: T, failureResponse: FailureResponse?, status: Status) {
        this.data = data
        this.failureResponse = failureResponse
        this.status = status
    }

    constructor(data: T, failureResponse: FailureResponse?, status: Status, code: Int) {
        this.data = data
        this.failureResponse = failureResponse
        this.status = status
    }

    constructor(failureResponse: FailureResponse?) {
        this.failureResponse = failureResponse
        status = Status.FAILURE
    }

    constructor(failureResponse: FailureResponse?, status: Status) {
        this.failureResponse = failureResponse
        this.status = status
    }

    constructor(data: T) {
        this.data = data
        failureResponse = null
        status = Status.SUCCESS
    }

    constructor(data: T, status: Status) {
        this.data = data
        failureResponse = null
        this.status = status
    }

    val isSuccessful: Boolean
        get() = status == Status.SUCCESS

    fun getFailureResponse(): FailureResponse? {
        return failureResponse
    }

    val isFailed: Boolean
        get() = status == Status.FAILURE

    enum class Status {
        SUCCESS, FAILURE, INPROGRESS
    }
}
