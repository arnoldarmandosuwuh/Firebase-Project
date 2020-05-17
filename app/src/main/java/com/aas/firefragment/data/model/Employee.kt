package com.aas.firefragment.data.model

import java.io.Serializable

class Employee(
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val salary: Int = 0
): Serializable