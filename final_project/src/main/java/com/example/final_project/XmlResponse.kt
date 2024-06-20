package com.example.final_project

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class XmlResponse(
    @Element
    val body: MyXmlBody?
)

@Xml(name = "body")
data class MyXmlBody(
    @Element
    val items: MyXmlItems?
)

@Xml(name = "items")
data class MyXmlItems(
    @Element
    val item: List<MyXmlItem>?
)

@Xml(name = "item")
data class MyXmlItem(
    @PropertyElement(name = "tm")
    val tm: String?,
    @PropertyElement(name = "totalCityName")
    val totalCityName: String?,
    @PropertyElement(name = "doName")
    val doName: String?,
    @PropertyElement(name = "cityName")
    val cityName: String?,
    @PropertyElement(name = "cityAreaId")
    val cityAreaId: String?,
    @PropertyElement(name = "kmaTci")
    val kmaTci: String?,
    @PropertyElement(name = "TCI_GRADE")
    val tciGrade: String?
) {
    constructor() : this(null, null, null, null, null, null, null)
}
