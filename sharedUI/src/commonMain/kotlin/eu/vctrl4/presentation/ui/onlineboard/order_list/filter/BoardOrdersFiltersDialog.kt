package eu.vctrl4.presentation.ui.onlineboard.order_list.filter

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.Constants.CUSTOMER_COMPANY
import eu.vctrl4.business.constants.Constants.CUSTOMER_DEPARTMENT
import eu.vctrl4.business.constants.Constants.TIME_PERIOD_RANGE
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.*
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.presentation.utils.OrderUtils.prepareDefaultReportRequest
import eu.vctrl4.storage.remote.entities.*
import eu.vctrl4.theme.*
import eu.vctrl4.ui.custom_views.composable.*

private var activeFiltersMask: Int = 0
private var activeFiltersInt: MutableState<Int> = mutableIntStateOf(0)

@Composable
fun BoardOrdersFiltersDialog(
    companies: List<Company>, request: OrderReportRequest, onSubmit: (OrderReportRequest) -> Unit, onDismiss: () -> Unit
)
{
    val stRequest = remember { mutableStateOf(request) }
    val scrollState = rememberScrollState()
    val showDateSelectionDialog = remember { mutableStateOf(false) }

    val selectedDate = remember { mutableStateOf(stRequest.value.RentDate ?: "") }

    val selCompany: Company? =
        companies.firstOrNull { stRequest.value.CustomerCompanyIN?.isNotEmpty() == true && it.Id == stRequest.value.CustomerCompanyIN!![0] }
    val stSelCompany: MutableState<Company?> = remember { mutableStateOf(selCompany) }
    val stSelCompanyName: MutableState<String?> = remember { mutableStateOf(selCompany?.Name) }

    val selDepartment: Department? =
        selCompany?.Departments?.firstOrNull { stRequest.value.CustomerDepartmentIN?.isNotEmpty() == true && it.Id == stRequest.value.CustomerDepartmentIN!![0] }
    val stSelDepartment: MutableState<Department?> = remember { mutableStateOf(selDepartment) }
    val stSelDepartmentName: MutableState<String?> = remember { mutableStateOf(selDepartment?.Name) }

    if (selectedDate.value.isNotEmpty())
    {
        addBitOf(TIME_PERIOD_RANGE)
    }

    fun onResetClicked()
    {
        stRequest.value = prepareDefaultReportRequest()

        stSelCompanyName.value = Res.string.all.asState
        stSelDepartmentName.value = Res.string.all.asState
        activeFiltersMask = 0
        activeFiltersInt.value = 0
    }

    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = { onDismiss() }) {

        Surface(modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.cl_f5f5f5) {

            Column(Modifier.fillMaxSize()) {

                Row(
	                modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = Colors.cl_00549F),
	                verticalAlignment = Alignment.CenterVertically,
	                horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Row {

                        Text(
	                        text = Res.string.filter.asState,
	                        textAlign = TextAlign.Center,
	                        color = Colors.cl_white,
	                        fontSize = 23.sp
                        )

                        Text(
	                        text = activeFiltersInt.value.toString(),
	                        textAlign = TextAlign.Center,
	                        color = Colors.cl_00549F,
	                        fontSize = 15.sp,
	                        modifier = Modifier
                                .padding(start = 16.dp)
                                .drawBehind { drawCircle(color = Color.White, radius = this.size.minDimension + 2f) })
                    }

                    Text(
	                    text = Res.string.clear.asState,
	                    textAlign = TextAlign.Center,
	                    color = Colors.white,
	                    fontSize = 18.sp,
	                    modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                onResetClicked()
                            },
	                    style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {

                    Spacer(modifier = Modifier.height(48.dp))

                    Text(
                        text = Res.string.for_date.asState,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = Colors.cl_232323
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CustomTextField(
	                    strValue = selectedDate.value,
	                    strHint = "",
	                    readOnly = true,
	                    modifier = Modifier.fillMaxWidth(),
	                    trailingIconResId = Res.drawable.icon_calendar,
	                    onRightIconClick = {
                            showDateSelectionDialog.value = true
                        })

                    if (companies.isNotEmpty())
                    {
                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
	                        text = Res.string.customer.asState,
	                        modifier = Modifier.padding(start = 8.dp),
	                        style = MaterialTheme.typography.titleMedium,
	                        color = Colors.cl_232323
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        DropdownMenuBox(
	                        items = companies,
	                        hint = "",
	                        modifier = Modifier,
	                        backColor = Colors.white,
	                        textColor = Colors.cl_232323,
	                        trailingIconColor = Colors.cl_232323,
	                        selectedItem = stSelCompanyName,
	                        onItemClick = {
                                val selectedCompany = it as Company
                                if (selectedCompany.Name.equals(Res.string.all.asState))
                                {
                                    stRequest.value.CustomerCompanyIN = null
                                    subtractBitOf(CUSTOMER_COMPANY)
                                } else
                                {
                                    stRequest.value.CustomerCompanyIN = arrayListOf(selectedCompany.Id)
                                    stSelDepartmentName.value = Res.string.all.asState
                                    stSelCompany.value = it
                                    addBitOf(CUSTOMER_COMPANY)
                                    subtractBitOf(CUSTOMER_DEPARTMENT)
                                }
                            })

                        Spacer(modifier = Modifier.height(48.dp))

                        Text(
                            text = Res.string.customer_department.asState,
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.titleMedium,
                            color = Colors.cl_232323
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        /*                            val departments =
                                                        (companies[selectedCompanyIndex.intValue]).Departments?.filter { it.HasChildren == false }
                                                            ?: ArrayList()
                                                    val departmentsNotExpired: MutableList<Department> =
                                                        departments.filter { it.ExpirationDate == null } as MutableList<Department>

                                                    val emptyDepartment = Department()
                                                    emptyDepartment.Name = Res.string.all.asState
                                                    departmentsNotExpired.add(
                                                        0,
                                                        emptyDepartment
                                                    ) */ //selectedDepartment.value = departmentsNotExpired.get(0)

                        DropdownMenuBox(
	                        items = stSelCompany.value?.Departments ?: listOf(),
	                        hint = "",
	                        modifier = Modifier,
	                        backColor = Colors.white,
	                        textColor = Colors.cl_232323,
	                        trailingIconColor = Colors.cl_232323,
	                        selectedItem = stSelDepartmentName,
	                        onItemClick = {
                                val selectedDepartment = it as Department
                                if (selectedDepartment.Name.equals(Res.string.all.asState))
                                {
                                    stRequest.value.CustomerDepartmentIN = null
                                    subtractBitOf(CUSTOMER_DEPARTMENT)
                                } else
                                {
                                    stRequest.value.CustomerDepartmentIN = arrayListOf(selectedDepartment.Id)
                                    addBitOf(CUSTOMER_DEPARTMENT)
                                }
                            })
                    }
                }

                if (showDateSelectionDialog.value)
                {
                    DatePickerModal({ dateStartLong ->

                        val startDateBg: String = DateTimeUtils.toUIDate(dateStartLong) ?: ""
                        selectedDate.value = startDateBg

                        val startDateSrv = DateTimeUtils.toServerDate(dateStartLong)
                        stRequest.value.RentDate = startDateSrv

                        addBitOf(TIME_PERIOD_RANGE)

                        showDateSelectionDialog.value = false

                    }, { showDateSelectionDialog.value = false })
                }

                Spacer(modifier = Modifier.height(32.dp))

                Box(
	                modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .clip(shape = RectangleShape)
                        .clickable(enabled = true) {

                            /*                                if (activeFiltersInt.value == 0)
                                                        {
                                                            onSubmit(null)
                                                        } else
                                                        {*/
                            onSubmit(stRequest.value) //}
                            activeFiltersMask = 0
                            activeFiltersInt.value = 0
                        }
                        .background(Colors.cl_00549F), contentAlignment = Alignment.Center) {
                    Text(
	                    text = Res.string.apply_filter.asState,
	                    textAlign = TextAlign.Center,
	                    color = Colors.white,
	                    fontSize = 17.sp
                    )
                }
            }
        }
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
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
}*/

private fun subtractBitOf(digit: Int)
{ //val singleOne = 1 shl digit
    val inversion = digit.inv()
    val zeroed = activeFiltersMask and inversion
    activeFiltersMask = zeroed
    activeFiltersInt.value = activeFiltersMask.countOneBits()
}

private fun addBitOf(digit: Int)
{
    activeFiltersMask = activeFiltersMask or digit
    activeFiltersInt.value = activeFiltersMask.countOneBits()
}

@Preview
@Composable
fun BoardOrdersFilterDialogPreview()
{
    val companies: MutableList<Company> = ArrayList()

    val company = Company()
    company.Name = Res.string.all.asState

    companies.add(company)
    companies.add(Company())
    companies.add(Company())

    BoardOrdersFiltersDialog(
        companies = listOf(), onSubmit = {}, onDismiss = {}, request = OrderReportRequest("")
    )
}