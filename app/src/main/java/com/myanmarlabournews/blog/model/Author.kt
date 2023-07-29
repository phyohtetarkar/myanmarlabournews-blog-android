package com.myanmarlabournews.blog.model

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
data class Author(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("slug") val slug: String? = "undefined",
    @JsonProperty("image") val image: String?,
    @JsonProperty("role") val role: String?,
) : Serializable
