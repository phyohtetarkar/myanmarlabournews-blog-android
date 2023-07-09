package com.myanmarlabournews.blog.model

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
data class Post(
    @JsonProperty("id") val id: Long,
    @JsonProperty("title") val title: String,
    @JsonProperty("slug") val slug: String,
    @JsonProperty("body") val body: String?,
    @JsonProperty("type") val type: String,
    @JsonProperty("status") val status: String,
    @JsonProperty("lang") val lang: String,
    @JsonProperty("cover") val cover: String?,
    @JsonProperty("correspondent") val author: Author,
    @JsonProperty("categories") val tags: List<Tag>,
    @JsonProperty("publishedAt") val publishedAt: Long?,
    @JsonProperty("created_at") val createdAt: Long,
    @JsonProperty("view_count") val viewCount: Long? = 0,
    @JsonProperty("share_link") val shareLink: String?,
) : Serializable
