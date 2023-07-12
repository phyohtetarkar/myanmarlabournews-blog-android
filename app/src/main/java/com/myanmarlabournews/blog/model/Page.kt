package com.myanmarlabournews.blog.model

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
data class Page<T>(
    @JsonProperty("list") val list: List<T>,
    @JsonProperty("total_page") val totalPage: Int,
    @JsonProperty("current_page") val currentPage: Int,
    @JsonProperty("page_size") val pageSize: Int,
) : Serializable