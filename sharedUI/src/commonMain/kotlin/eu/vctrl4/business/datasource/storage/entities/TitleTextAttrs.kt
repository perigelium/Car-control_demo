package eu.vctrl4.business.datasource.storage.entities


@kotlinx.serialization.Serializable
data class TitleTextAttrs(
	val title: String? = null, val imgRes: Int? = null, val strText: String, val titleTextWidthBetween: Int = 160
)
