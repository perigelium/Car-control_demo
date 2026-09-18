package eu.vctrl4.presentation.ui.base

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.*
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.Queue
import eu.vctrl4.common.*
import eu.vctrl4.presentation.component.*
import eu.vctrl4.theme.*
import eu.vctrl4.ui.base.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.jetbrains.compose.resources.*

@Composable
fun MainNavDrawer(
	navController: NavHostController,
	title: String,
	errors: Flow<UIComponent> = MutableSharedFlow(),
	screenContent: @Composable (DrawerState) -> Unit
                 ) {
	val selectedItemIndex: MutableState<Int?> = rememberSaveable { mutableStateOf(null) }
	val coroutineScope: CoroutineScope = rememberCoroutineScope()
	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination

	val errorQueue = remember {
		mutableStateOf<Queue<UIComponent>>(Queue(mutableListOf()))
	}

	LaunchedEffect(errors) {
		errors.collect { errors ->
			errorQueue.appendToMessageQueue(errors)
		}
	}

	val items: List<NavigationItem> = listOf(
			NavigationItem(
				title = Res.string.order_management.asState,
				name = "nav_orders",
				unselectedIconRes = Res.drawable.ic_orders
			              ),
			NavigationItem(
				title = Res.string.search_tickets.asState,
				name = "nav_tickets",
				unselectedIconRes = Res.drawable.ic_tickets
			              ),
			NavigationItem(
				title = Res.string.online_board.asState,
				name = "nav_online_board",
				unselectedIconRes = Res.drawable.ic_onlineboard
			              ),

/*			NavigationItem(
				title = Res.string.discussion.asState, name = "nav_chats", unselectedIconRes = Res.drawable.ic_chats
			              ),*/

/*				        NavigationItem(
							title = Res.string.statistics_and_reports.asState,
							name = "nav_stats",
							unselectedIconRes = Res.drawable.ic_stats
									  )*/

		              )

	/*    val fontFamily = FontFamily(
			Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
		)*/

	ModalNavigationDrawer(
		drawerState = drawerState,
		scrimColor = Colors.cl_E5E5E5,
		drawerContent = {
			Column(
				modifier = Modifier.fillMaxWidth(0.85f).fillMaxHeight().background(Colors.white)
			      ) { // Drawer Header

				Column(
					modifier = Modifier.fillMaxWidth().padding(top = 24.dp, start = 24.dp)
				      ) {

					Text(
						text = Res.string.restrans.asState,
						textAlign = TextAlign.Start,
						color = Colors.cl_00549F,
						fontWeight = FontWeight.Bold,
						fontSize = 18.sp,
					    )

					Spacer(modifier = Modifier.height(24.dp))

					Text(
						text = title,
						textAlign = TextAlign.Start,
						color = Colors.cl_393854,
						fontSize = 18.sp,
					    )

					Spacer(modifier = Modifier.height(24.dp))

					HorizontalDivider(
						thickness = 1.dp,
						color = Colors.cl_b2b2b2,
						modifier = Modifier.padding(end = 16.dp)
					                 )
				}

				Spacer(modifier = Modifier.height(16.dp))

				//ModalDrawerSheet {

				Spacer(modifier = Modifier.height(16.dp))

				items.forEachIndexed { index, item ->
					NavigationDrawerItem(
						label = { Text(text = item.title) },
						selected = currentDestination?.route == item.name,
						onClick = {
							if (currentDestination?.route != item.name && navBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
								navController.navigate(item.name) { // Prevents stack buildup
									popUpTo(navController.graph.findStartDestination().id) {
										saveState = true
									}
									launchSingleTop = true
									restoreState = true
								}
							}
							selectedItemIndex.value = index

							coroutineScope.launch {
								drawerState.close()
							}
						},
						icon = {
							Image(
								painter = painterResource(item.selectedIconRes),
								colorFilter = ColorFilter.tint(Colors.cl_00549F),
								contentDescription = item.title
							     )
						},
						badge = {
							item.badgeCount?.let {
								Text(text = item.badgeCount.toString())
							}
						},
						modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
						colors = NavigationDrawerItemDefaults.colors(
							selectedContainerColor = Colors.cl_e8e8ef,
							unselectedContainerColor = Colors.transparent
						                                            )
					                    )
				}

				Spacer(modifier = Modifier.height(16.dp))

				HorizontalDivider(
					thickness = 1.dp,
					color = Colors.cl_b2b2b2,
					modifier = Modifier.padding(horizontal = 16.dp)
				                 )

				Spacer(modifier = Modifier.height(16.dp))

				val item = NavigationItem(
					title = Res.string.logout.asState,
					name = "nav_logout",
					unselectedIconRes = Res.drawable.ic_quit
				                         )

				NavigationDrawerItem(
					label = { Text(text = item.title) },
				                     selected = false,
				                     onClick = {
					                     navController.navigate(item.name) { // Prevents stack buildup
						                     popUpTo(navController.graph.findStartDestination().id) {
							                     saveState = true
						                     }
						                     launchSingleTop = true
						                     restoreState = true
					                     }
				                     },
				                     icon = {
					                     Image(
						                     painter = painterResource(item.selectedIconRes),
						                     colorFilter = ColorFilter.tint(Colors.cl_00549F),
						                     contentDescription = item.title
					                          )
				                     },
				                     badge = {
					                     item.badgeCount?.let {
						                     Text(text = item.badgeCount.toString())
					                     }
				                     },
				                     modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
				                    )
				Spacer(Modifier.weight(1f))

				Row(modifier = Modifier.fillMaxWidth()) {
					Spacer(modifier = Modifier.weight(1f))
					Text(
						text = BuildKonfig.VERSION_NAME + "." + BuildKonfig.VERSION_CODE,
						modifier = Modifier.padding(all = 8.dp),
						color = Colors.gray80,
						fontSize = 12.sp
					    )
				}
			}
		},
		content = {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			   ) {
				screenContent(drawerState)

				// process the queue
				if (!errorQueue.value.isEmpty()) {
					errorQueue.value.peek()?.let { uiComponent ->

						if (uiComponent is UIComponent.DialogTitleText) {
							CreateUIComponentDialog(
								title = uiComponent.title,
								description = uiComponent.description,
								onRemoveHeadFromQueue = { errorQueue.removeHeadMessage() })
						}

						if (uiComponent is UIComponent.DialogMsg) {
							AppTheme {
								GenericDialog(
									modifier = Modifier,
									title = "",
									description = uiComponent.alert.message,
									onRemoveHeadFromQueue = { errorQueue.removeHeadMessage() })
							}
						}

						if (uiComponent is UIComponent.Toast) {
							SelfDismissingPopup(
								message = uiComponent.message,
								onDismiss = { errorQueue.removeHeadMessage() })
						}
					}
				}

				/*            LaunchedEffect(key1 = errors) {
								errors.onEach { effect ->
									when (effect)
									{
				*//*                        is ChatListAction.ShowDialogWithMessage ->
                        {
                            AppTheme {
                                ShowSnackBar(
                                    title = effect.strMsg,
                                    snackbarVisibleState = true,
                                    onDismiss = { errorQueue.removeHeadMessage() },
                                    modifier = Modifier.align(Alignment.BottomCenter)
                                )
                            }
                        }*//*
                        is UIComponent.DialogAlert -> TODO()
                        is UIComponent.DialogSimple -> TODO()
                        is UIComponent.None -> TODO()
                        is UIComponent.Toast -> TODO()
                        is UIComponent.ToastSimple -> TODO()
                    }
                }.collect {}
            }*/

				/*            if (dlgMessage?.isNotBlank() == true)
							{
								Dialog(
									properties = DialogProperties(usePlatformDefaultWidth = true, dismissOnClickOutside = true),
									onDismissRequest = { onDismissError() }) {
									Surface(
										modifier = Modifier.fillMaxWidth(),
										shape = RoundedCornerShape(12.dp),
										color = Colors.cl_black_one_third_transparent
									) {
										Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
											Text(dlgMessage)
										}
									}
								}
							}*/

				/*            popupMessage?.let { msg ->
								AppTheme {
									val snackbarVisibleState = remember { mutableStateOf(true) }

									LaunchedEffect(popupMessage) {
										snackbarVisibleState.value = true
									}
									ShowSnackBar(
										title = msg,
										snackbarVisibleState = snackbarVisibleState.value,
										onDismiss = { snackbarVisibleState.value = false },
										modifier = Modifier.align(Alignment.BottomCenter)
									)
								}
							}*/
			}
		})
}

@Preview
@Composable
fun NavDrawerPreview() {
	val navController = rememberNavController()
	val drawerState = rememberDrawerState(DrawerValue.Open)
	MainNavDrawer(
		navController,
		title = Res.string.sample_full_name.asState,
		errors = MutableSharedFlow(),
		screenContent = {},
	             )
}