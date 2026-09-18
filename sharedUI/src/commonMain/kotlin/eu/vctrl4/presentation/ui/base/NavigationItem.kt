package eu.vctrl4.presentation.ui.base

import org.jetbrains.compose.resources.*

data class NavigationItem(
	val title: String,
	val name:String,
	val unselectedIconRes: DrawableResource,
	val selectedIconRes: DrawableResource = unselectedIconRes,
	val badgeCount: Int? = null
)