package eu.vctrl4.presentation.ui.tickets.ticket_list.filter

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
import eu.vctrl4.business.constants.Constants.BY_ORDER_REGISTRATION
import eu.vctrl4.business.constants.Constants.BY_RENT_TIME_START
import eu.vctrl4.business.constants.Constants.CUSTOMER_COMPANY
import eu.vctrl4.business.constants.Constants.CUSTOMER_DEPARTMENT
import eu.vctrl4.business.constants.Constants.ORDER_STATE
import eu.vctrl4.business.constants.Constants.TICKET_STATES
import eu.vctrl4.business.constants.Constants.TIME_PERIOD_RANGE
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.*
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.presentation.utils.OrderUtils.prepareDefaultTicketsRequest
import eu.vctrl4.theme.*

private var activeFiltersMask: Int = 0
private var activeFiltersInt: MutableState<Int> = mutableIntStateOf(0)

@Composable
fun TicketsFiltersDialog(
	companies: List<Company>,
	ordersRequest: OrderListRequest,
	onSubmit: (OrderListRequest) -> Unit,
	onDismiss: () -> Unit
                        ) {
	val scrollState = rememberScrollState()
	var remOrdersRequest by remember { mutableStateOf(ordersRequest) }
	val showDateSelectionDialog = remember { mutableStateOf(false) }

	val selCompany: Company? = companies.firstOrNull { it.Id == remOrdersRequest.CustomerCompanyId }
	val stSelCompany: MutableState<Company?> = remember { mutableStateOf(selCompany) }
	val stSelCompanyName: MutableState<String?> = remember { mutableStateOf(selCompany?.Name) }

	val selDepartment: Department? =
		selCompany?.Departments?.firstOrNull { it.Id == remOrdersRequest.CustomerDepartmentId }
	val stSelDepartmentName: MutableState<String?> =
		remember { mutableStateOf(selDepartment?.Name) }

	val selTicketState: IdNameSimple? =
		TICKET_STATES.firstOrNull { it.Id == remOrdersRequest.TicketState }
	val stSelTicketState: MutableState<String?> = remember { mutableStateOf(selTicketState?.Name) }

	val rbOptions: List<IdNameValueName> = listOf(BY_ORDER_REGISTRATION, BY_RENT_TIME_START)
	val selPeriodType =
		if (remOrdersRequest.DateFrom != null) BY_RENT_TIME_START else BY_ORDER_REGISTRATION
	val remSelectedPeriodType: MutableState<IdNameValueName> =
		remember { mutableStateOf(selPeriodType) }

	var dateStartUI = ""
	var dateEndUI = ""

	if (remSelectedPeriodType.value == BY_ORDER_REGISTRATION) {
		remOrdersRequest.CreateDateFrom?.let {
			dateStartUI = DateTimeUtils.reformatDateTime(
				it, DateTimeUtils.SERVER_DATE_PATTERN, DateTimeUtils.UI_DATE_PATTERN_SHORT
			                                            ) ?: ""
		}
		remOrdersRequest.CreateDateTo?.let {
			dateEndUI = DateTimeUtils.reformatDateTime(
				it, DateTimeUtils.SERVER_DATE_PATTERN, DateTimeUtils.UI_DATE_PATTERN_SHORT
			                                          ) ?: ""
		}
	} else if (remSelectedPeriodType.value == BY_RENT_TIME_START) {
		remOrdersRequest.DateFrom?.let {
			dateStartUI = DateTimeUtils.reformatDateTime(
				it, DateTimeUtils.SERVER_DATE_PATTERN, DateTimeUtils.UI_DATE_PATTERN_SHORT
			                                            ) ?: ""
		}
		remOrdersRequest.DateTo?.let {
			dateEndUI = DateTimeUtils.reformatDateTime(
				it, DateTimeUtils.SERVER_DATE_PATTERN, DateTimeUtils.UI_DATE_PATTERN_SHORT
			                                          ) ?: ""
		}
	}
	var selectedDate = dateStartUI
	if (dateEndUI.isNotEmpty()) {
		selectedDate += " - $dateEndUI"
	}
	val stSelectedDates = remember { mutableStateOf(selectedDate) }
	val selectedDates: MutableState<Pair<String?, String?>> =
		remember { mutableStateOf(Pair(null, null)) }

	fun onResetClicked() {
		remOrdersRequest = prepareDefaultTicketsRequest()

		val dateStartUI = DateTimeUtils.reformatDateTime(
			remOrdersRequest.CreateDateFrom,
			DateTimeUtils.SERVER_DATE_PATTERN,
			DateTimeUtils.UI_DATE_PATTERN_SHORT
		                                                ) ?: ""
		val dateEndUI = DateTimeUtils.reformatDateTime(
			remOrdersRequest.CreateDateTo,
			DateTimeUtils.SERVER_DATE_PATTERN,
			DateTimeUtils.UI_DATE_PATTERN_SHORT
		                                              ) ?: ""
		stSelectedDates.value = "$dateStartUI - $dateEndUI"
		remSelectedPeriodType.value = BY_ORDER_REGISTRATION
		stSelCompanyName.value = Res.string.all.asState
		stSelDepartmentName.value = Res.string.all.asState
		stSelTicketState.value = Res.string.all.asState
		activeFiltersMask = 0
		activeFiltersInt.value = 0
	}

	Dialog(
		properties = DialogProperties(usePlatformDefaultWidth = false),
		onDismissRequest = { onDismiss() }) {

		Surface(modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.cl_f5f5f5) {

			Column(Modifier.fillMaxSize()) {

				Row(
					modifier = Modifier.fillMaxWidth().height(56.dp)
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
							modifier = Modifier.padding(start = 16.dp).drawBehind {
									drawCircle(
										color = Color.White,
										radius = this.size.minDimension + 2f
									          )
								})
					}

					Text(
						text = Res.string.clear.asState,
						textAlign = TextAlign.Center,
						color = Colors.white,
						fontSize = 18.sp,
						modifier = Modifier.padding(start = 16.dp).clickable {
								onResetClicked()
							},
						style = TextStyle(textDecoration = TextDecoration.Underline)
					    )
				}

				Column(
					modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
						.verticalScroll(scrollState).weight(1f),
					horizontalAlignment = Alignment.Start
				      ) {
					Spacer(modifier = Modifier.height(48.dp))

					Text(
						text = Res.string.period_type.asState,
						modifier = Modifier.padding(start = 8.dp),
						style = MaterialTheme.typography.titleMedium,
						color = Colors.cl_232323
					    )

					Spacer(modifier = Modifier.height(24.dp))

					RadioButtonsGroup(
						radioOptions = rbOptions,
						selectedOption = remSelectedPeriodType,
						onSelectionChanged = {
							remSelectedPeriodType.value = it

							if (it == BY_ORDER_REGISTRATION) {
								remOrdersRequest.DateFrom?.apply {
									val dateFrom = StringBuilder(this)
									remOrdersRequest.CreateDateFrom = dateFrom.toString()
								}
								remOrdersRequest.DateTo?.apply {
									val dateTo = StringBuilder(this)
									remOrdersRequest.CreateDateTo = dateTo.toString()
								}
								remOrdersRequest.DateFrom = null
								remOrdersRequest.DateTo = null
							} else if (it == BY_RENT_TIME_START) {
								remOrdersRequest.CreateDateFrom?.apply {
									val dateFrom = StringBuilder(this)
									remOrdersRequest.DateFrom = dateFrom.toString()
								}
								remOrdersRequest.CreateDateTo?.apply {
									val dateTo = StringBuilder(this)
									remOrdersRequest.DateTo = dateTo.toString()
								}
								remOrdersRequest.CreateDateFrom = null
								remOrdersRequest.CreateDateTo = null
							}
						})

					Spacer(modifier = Modifier.height(16.dp))

					Text(
						text = Res.string.date_period.asState,
						modifier = Modifier.padding(start = 8.dp),
						style = MaterialTheme.typography.titleMedium,
						color = Colors.cl_232323
					    )

					Spacer(modifier = Modifier.height(8.dp))

					CustomTextField(
						strValue = stSelectedDates.value,
						strHint = "",
						readOnly = true,
						modifier = Modifier.fillMaxWidth(),
						trailingIconResId = Res.drawable.icon_calendar,
						onRightIconClick = {
							showDateSelectionDialog.value = true
						})

					Spacer(modifier = Modifier.height(24.dp))

					Text(
						text = Res.string.customer.asState,
						modifier = Modifier.padding(start = 8.dp),
						style = MaterialTheme.typography.titleMedium,
						color = Colors.cl_232323
					    )

					Spacer(modifier = Modifier.height(16.dp))

					DropdownMenuBox(
						items = companies,
						modifier = Modifier,
						backColor = Colors.white,
						textColor = Colors.cl_232323,
						trailingIconColor = Colors.cl_232323,
						selectedItem = stSelCompanyName,
						onItemClick = {
							val selectedCompany = it as Company
							if (selectedCompany.Name.equals(Res.string.all.asState)) {
								remOrdersRequest.CustomerCompanyId = null
								subtractBitOf(CUSTOMER_COMPANY)
							} else {
								remOrdersRequest.CustomerCompanyId = selectedCompany.Id
								stSelCompany.value = it
								addBitOf(CUSTOMER_COMPANY)
							}
							remOrdersRequest.CustomerDepartmentId = null
							stSelDepartmentName.value = Res.string.all.asState
							subtractBitOf(CUSTOMER_DEPARTMENT)
						})

					Spacer(modifier = Modifier.height(32.dp))

					Text(
						text = Res.string.customer_department.asState,
						modifier = Modifier.padding(start = 8.dp),
						style = MaterialTheme.typography.titleMedium,
						color = Colors.cl_232323
					    )

					Spacer(modifier = Modifier.height(16.dp))

					DropdownMenuBox(
						items = stSelCompany.value?.Departments ?: listOf(),
						modifier = Modifier,
						backColor = Colors.white,
						textColor = Colors.cl_232323,
						trailingIconColor = Colors.cl_232323,
						selectedItem = stSelDepartmentName,
						onItemClick = {
							val selectedDepartment = it as Department
							if (selectedDepartment.Name.equals(Res.string.all.asState)) {
								remOrdersRequest.CustomerDepartmentId = null
								subtractBitOf(CUSTOMER_DEPARTMENT)
							} else {
								remOrdersRequest.CustomerDepartmentId = selectedDepartment.Id
								addBitOf(CUSTOMER_DEPARTMENT)
							}
						})
					Spacer(modifier = Modifier.height(32.dp))

					Text(
						text = Res.string.ticket_state.asState,
						modifier = Modifier.padding(start = 8.dp),
						style = MaterialTheme.typography.titleMedium,
						color = Colors.cl_232323
					    )
					Spacer(modifier = Modifier.height(16.dp))

					DropdownMenuBox(
						items = TICKET_STATES,
						modifier = Modifier,
						backColor = Colors.white,
						textColor = Colors.cl_232323,
						trailingIconColor = Colors.cl_232323,
						selectedItem = stSelTicketState,
						onItemClick = {
							addBitOf(ORDER_STATE)
							remOrdersRequest.TicketState = (it as IdNameSimple).Id
						})
				}

				if (showDateSelectionDialog.value) {
					DateRangePickerModal(isPastOrFuture = null, {

						val dateStartBg: String = DateTimeUtils.toUIDate(it.first) ?: ""
						val dateEndBg: String = DateTimeUtils.toUIDate(it.second) ?: ""

						stSelectedDates.value = dateStartBg
						if (dateEndBg.isNotEmpty()) {
							stSelectedDates.value += " - $dateEndBg"
						}
						val dateStartSrv = DateTimeUtils.toServerDate(it.first)
						val dateEndSrv = DateTimeUtils.toServerDate(it.second)
						selectedDates.value = Pair(dateStartSrv, dateEndSrv)

						if (remSelectedPeriodType.value.Name == BY_ORDER_REGISTRATION.Name) {
							remOrdersRequest.CreateDateFrom = dateStartSrv
							remOrdersRequest.CreateDateTo = dateEndSrv
						} else if (remSelectedPeriodType.value.Name == BY_RENT_TIME_START.Name) {
							remOrdersRequest.DateFrom = dateStartSrv
							remOrdersRequest.DateTo = dateEndSrv
						}
						addBitOf(TIME_PERIOD_RANGE)

						showDateSelectionDialog.value = false

					}, { showDateSelectionDialog.value = false })
				}

				Spacer(modifier = Modifier.height(32.dp))

				Box(
					modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 56.dp)
					.clip(shape = RectangleShape).clickable(enabled = true) {
						onSubmit(remOrdersRequest)
					}.background(Colors.cl_00549F), contentAlignment = Alignment.Center) {
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

private fun subtractBitOf(digit: Int) { //val singleOne = 1 shl digit
	val inversion = digit.inv()
	val zeroed = activeFiltersMask and inversion
	activeFiltersMask = zeroed
	activeFiltersInt.value = activeFiltersMask.countOneBits()
}

private fun addBitOf(digit: Int) {
	activeFiltersMask = activeFiltersMask or digit
	activeFiltersInt.value = activeFiltersMask.countOneBits()
}

@Preview
@Composable
fun TicketsFilterDialogPreview() {
	val companies: MutableList<Company> = ArrayList()

	val company = Company()
	company.Name = Res.string.all.asState

	companies.add(company)
	companies.add(Company())
	companies.add(Company())

	TicketsFiltersDialog(
		companies = companies.toList(),
		ordersRequest = OrderListRequest(RightsOfUserId = null),
		{},
		onDismiss = { },
	                    )

}