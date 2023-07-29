package com.myanmarlabournews.blog.model

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
data class Tag(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("slug") val slug: String? = "undefined"
) : Serializable {
    companion object {
        fun fake(): Tag {
            return Tag(
                id = 1,
                name = "Tag",
            )
        }
    }
}
