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

class FailureResponse {
    var code: Int
    var message: String
    var status: Status

    enum class Status {
        CONNECTION_FAILED, NO_INTERNET, UNKNOWN_ERROR, EMPTY_DATA, SESSION_EXPIRED
    }

    constructor(code: Int, message: String, status: Status) {
        this.code = code
        this.message = message
        this.status = status
    }

    val isNoNetwork: Boolean
        get() = status == Status.NO_INTERNET

    val isUnKnownError: Boolean
        get() = status == Status.UNKNOWN_ERROR

    val isEmptyDataError: Boolean
        get() = status == Status.EMPTY_DATA
}

