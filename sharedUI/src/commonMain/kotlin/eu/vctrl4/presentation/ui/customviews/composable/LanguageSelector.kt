package eu.vctrl4.presentation.ui.customviews.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.theme.*
import eu.vctrl4.theme.Colors.cl_b2b2b2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(
	modifier: Modifier = Modifier,
	onLanguageChanged: (String) -> Unit = {}
                    ) {
	var expanded by remember { mutableStateOf(false) }

	val currentCode = LocalAppLocale.current.take(2).lowercase()
	val currentLanguage = supportedLanguages.find { it.code == currentCode } ?: supportedLanguages.last()

	ExposedDropdownMenuBox(
		expanded = expanded,
		onExpandedChange = { expanded = !expanded },
		modifier = modifier.background(color = Colors.cl_black_semitransparent, shape = RoundedCornerShape(8.dp))
	                      ) {
		OutlinedTextField(
			readOnly = true,
			value = "${currentLanguage.flagEmoji}   ${currentLanguage.displayName}",
			onValueChange = {},
			label = { Text(text="Language / Idioma", color = cl_b2b2b2) },
			trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
			colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(unfocusedTextColor = cl_b2b2b2, focusedTextColor = cl_b2b2b2),
			modifier = Modifier.menuAnchor()
		                 )

		ExposedDropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false }
		                   ) {
			supportedLanguages.forEach { language ->
				DropdownMenuItem(
					text = {
						Row {
							Text(language.flagEmoji)
							Spacer(modifier = Modifier.width(12.dp))
							Text(language.displayName)
						}
					},
					onClick = {
						changeAppLanguage(language.code)

						onLanguageChanged(language.code)

						expanded = false
					},
					contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
				                )
			}
		}
	}
}
