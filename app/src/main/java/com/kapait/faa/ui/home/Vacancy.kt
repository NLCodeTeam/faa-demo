package com.kapait.faa.ui.home

import android.view.textclassifier.TextLanguage
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@IgnoreExtraProperties
class Vacancy: Serializable {
    @Expose
    var title: String? = null
    @Expose
    var description: String? = null
    @Expose
    var country: String? = null
    @Expose
    var details: String? = null
    @Expose
    var bodyType: String? = null
    @Expose
    var eyeColor: String? = null
    @Expose
    var heirColor: String? = null
    @Expose
    var language: String? = null
    @Expose
    var location: String? = null
    @Expose
    var projectName: String? = null
    @Expose
    var vacancyTypeId: String? = null
    @Expose
    var age: Int = 0
    @Expose
    var compensation: Double = 0.0
    @Expose
    var height: Int = 0
    @Expose
    var weight: Int = 0
    @Expose
    var estimatedFinishTime: String? = null
    @Expose
    var estimatedStartTime: String? = null
    @Expose
    var publicationDateTime: String? = null
    @Expose
    var visibleFinishDateTime: String? = null
    @Expose
    var visibleStartDateTime: String? = null

    fun toCharacteristicList(): List<Characteristic> {
        val values = listOf(
                country, language, age.toString(), height.toString(),
                weight.toString(), bodyType, heirColor, eyeColor
        )

        if (values.size != FIELDS.size) return emptyList()

        val result = mutableListOf<Characteristic>()
        for (index in values.indices)
            result.add(Characteristic(FIELDS[index],values[index]))

        return result
    }

    companion object {
        val FIELDS = listOf(
                "Country:",
                "Language:",
                "Age:",
                "Height",
                "Weight",
                "Body Type:",
                "Hair Color:",
                "Eye Color:"
        )
    }
}
   