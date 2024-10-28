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
    @JsonProperty("type") val type: Post.Type,
    @JsonProperty("status") val status: Post.Status,
    @JsonProperty("lang") val lang: Post.Lang,
    @JsonProperty("cover") val cover: String?,
    @JsonProperty("correspondent") val author: Author,
    @JsonProperty("categories") val tags: List<Tag>,
    @JsonProperty("publishedAt") val publishedAt: Long?,
    @JsonProperty("created_at") val createdAt: Long,
    @JsonProperty("view_count") val viewCount: Long? = 0,
    @JsonProperty("share_link") val shareLink: String?,
) : Serializable {
    enum class Type {
        NEWS, ARTICLE, VIDEO, PODCAST, SURVEY, LAW
    }

    enum class Lang {
        MM, EN
    }

    enum class Status {
        DRAFT, SCHEDULED, PUBLISHED
    }

    companion object {
        fun fake(id: Long = 1): Post {
            return Post(
                id = id,
                title = "Post title",
                slug = "post-title",
                type = Post.Type.ARTICLE,
                status = Post.Status.PUBLISHED,
                lang = Post.Lang.MM,
                createdAt = 1688614712249,
                publishedAt = 1688614712249,
                body = null,
                cover = null,
                shareLink = "hello",
                author = Author(
                    id = 1,
                    name = "Author",
                    image = null,
                    role = "Editor"
                ),
                tags = listOf(Tag.fake(), Tag.fake())
            )
        }
    }
}
