package eu.vctrl4.presentation.ui.customviews.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.utils.*
import kotlin.time.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit
)
{
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateSelected(datePickerState.selectedDateMillis)
            onDismiss()
        }) {
            Text(Res.string.apply.asState)
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(Res.string.cancel.asState)
        }
    }) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    isPastOrFuture: Boolean? = null,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit, onDismiss: () -> Unit
)
{
    val today = Clock.System.now().toEpochMilliseconds()
    val selectableDates = object : SelectableDates
    {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean
        {
            return if (isPastOrFuture == false) utcTimeMillis >= today
            else if (isPastOrFuture == true) utcTimeMillis <= today else true
        }
        override fun isSelectableYear(year: Int): Boolean {
            return year >= 2025
        }
    }

    val dateRangePickerState = rememberDateRangePickerState(selectableDates = selectableDates)
    val dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() }

    fun getFormattedDate(timeInMillis: Long): String?
	{
	    return DateTimeUtils.toDateTime(timeInMillis, DateTimeUtils.UI_DATE_PATTERN_SHORT)
    }

    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateRangeSelected(
                Pair(
                    dateRangePickerState.selectedStartDateMillis, dateRangePickerState.selectedEndDateMillis
                )
            )
            onDismiss()
        }) {
            Text(Res.string.apply.asState)
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(Res.string.cancel.asState)
        }
    }) {
        DateRangePicker(state = dateRangePickerState,
            title = {
                Text(text = Res.string.select_period_start_and_end_date.asState, modifier = Modifier
                    .padding(16.dp))
            },
            headline = {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)) {
                    Box(Modifier.weight(1f)) {
                        (if(dateRangePickerState.selectedStartDateMillis!=null) dateRangePickerState.selectedStartDateMillis?.let { getFormattedDate(it) } else "")?.let { Text(text = it) }
                    }
                    Box(Modifier.weight(1f)) {
                        (if(dateRangePickerState.selectedEndDateMillis!=null) dateRangePickerState.selectedEndDateMillis?.let { getFormattedDate(it) } else "")?.let { Text(text = it) }
                    }
/*                    Box(Modifier.weight(0.2f)) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Ok")
                    }*/
                }
            },
            showModeToggle = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DateRangePickerPreview()
{
    DateRangePickerModal(false,  {}, {})
}