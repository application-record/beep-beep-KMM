package org.thechance.common.presentation.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.composable.BpIconButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.composable.BpTextButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.composables.table.TotalItemsIndicator
import org.thechance.common.presentation.composables.table.UserRow
import org.thechance.common.presentation.uistate.UserScreenUiState


object UserScreen : Screen, KoinComponent {

    private val screenModel: UserScreenModel by inject()

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()

        UserContent(state = state)
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun UserContent(
        state: UserScreenUiState,
    ) {
        //This need to change to get it from state
        var selectedUser by remember { mutableStateOf<String?>(null) }
        var selectedPage by remember { mutableStateOf(1) }
        var numberItemInPage by remember { mutableStateOf(10) }
        var search by remember { mutableStateOf("") }
        val pageCount = 10
        val isDropMenuExpanded = remember { mutableStateOf(false) }

        Column(
            Modifier.background(Theme.colors.surface).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
        ) {

            val headers = listOf(
                Header("No.", 1f),
                Header("Users", 3f),
                Header("Username", 3f),
                Header("Email", 3f),
                Header("Country", 3f),
                Header("Permission", 3f),
                Header("", 1f),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                BpSimpleTextField(
                    modifier = Modifier.widthIn(max = 440.dp),
                    hint = "Search for users",
                    onValueChange = { search = it },
                    text = search,
                    keyboardType = KeyboardType.Text,
                    trailingPainter = painterResource("search.svg")
                )
                Row {
                    BpIconButton(
                        content = {
                            Text(
                                "Filter",
                                style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
                            )
                        },
                        onClick = { isDropMenuExpanded.value = true },
                        painter = painterResource("sort.svg"),
                        modifier = Modifier.cursorHoverIconHand()
                    )
                    BpDropdownMenu(
                        expanded = isDropMenuExpanded.value,
                        onDismissRequest = { isDropMenuExpanded.value = false },
                        offset = DpOffset.Zero.copy(y = Theme.dimens.space16),
                        shape = RoundedCornerShape(Theme.radius.medium)
                            .copy(topStart = CornerSize(Theme.radius.small)),
                        modifier = Modifier.height(400.dp)
                    ) {
                        DropdownMenuItem(
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.width(400.dp),
                            onClick = { isDropMenuExpanded.value = false },
                            text = {
                                Column(Modifier.fillMaxSize()) {
                                    Text(
                                        "Filter",
                                        style = Theme.typography.headline,
                                        color = Theme.colors.contentPrimary,
                                        modifier = Modifier.padding(start = 24.dp, top = 24.dp)
                                    )
                                    Text(
                                        "Permission",
                                        style = Theme.typography.titleLarge,
                                        color = Theme.colors.contentPrimary,
                                        modifier = Modifier.padding(
                                            start = 24.dp,
                                            top = 40.dp,
                                            bottom = 16.dp
                                        ),
                                    )

                                    FlowRow(
                                        modifier = Modifier.fillMaxWidth()
                                            .background(Color(0xff151515))
                                            .padding(start = 24.dp, top = 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        maxItemsInEachRow = 3,
                                    ) {
                                        BpChip(
                                            painter = painterResource("chart.xml"),
                                            onClick = {},
                                            label = "Dashboard admin",
                                            isSelected = true
                                        )
                                        BpChip(
                                            painter = painterResource("ic_restaurant_empty.svg"),
                                            onClick = {},
                                            label = "Restaurant owner",
                                            isSelected = false,
                                        )
                                        BpChip(
                                            painter = painterResource("ic_taxi_empty.svg"),
                                            onClick = {},
                                            label = "Taxi driver",
                                            isSelected = false,
                                            modifier = Modifier.padding(top = 16.dp)
                                        )
                                        BpChip(
                                            painter = painterResource("ic_users_empty.svg"),
                                            onClick = {},
                                            label = "End user",
                                            isSelected = false,
                                            modifier = Modifier.padding(top = 16.dp)
                                        )
                                        BpChip(
                                            painter = painterResource("ic_support.xml"),
                                            onClick = {},
                                            label = "Support",
                                            isSelected = true,
                                            modifier = Modifier.padding(top = 16.dp)
                                        )
                                        BpChip(
                                            painter = painterResource("ic_delivery.xml"),
                                            onClick = {},
                                            label = "Delivery",
                                            isSelected = false,
                                            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                                        )
                                    }
                                    Text(
                                        "Country",
                                        style = Theme.typography.titleLarge,
                                        color = Theme.colors.contentPrimary,
                                        modifier = Modifier.padding(
                                            start = 24.dp,
                                            top = 32.dp,
                                            bottom = 16.dp
                                        )
                                    )
                                    Column(
                                        Modifier.fillMaxWidth().background(Color(0xff151515))
                                            .padding(horizontal = 24.dp, vertical = 16.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        BpCheckBox(
                                            label = "Palestine",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Iraq",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Jordan",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Egypt",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Syria",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Other",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                    }
                                    Row(
                                        Modifier.padding(24.dp),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        BpTextButton(
                                            "Cancel",
                                            onClick = { isDropMenuExpanded.value = false },
                                            modifier = Modifier.cursorHoverIconHand()
                                        )
                                        BpOutlinedButton(
                                            title = "Save",
                                            onClick = { isDropMenuExpanded.value = false },
                                            shape = RoundedCornerShape(Theme.radius.small),
                                            modifier = Modifier.height(32.dp).weight(1f)
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }

            BpTable(
                data = state.users,
                key = { it.username },
                headers = headers,
                modifier = Modifier.fillMaxWidth(),
                rowsCount = pageCount,
                offset = selectedPage - 1,
                rowContent = { user ->
                    UserRow(
                        onClickEditUser = { selectedUser = it },
                        user = user,
                        position = state.users.indexOf(user) + 1,
                    )
                },
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TotalItemsIndicator(
                    numberItemInPage = numberItemInPage,
                    totalItems = 190,
                    itemType = "user",
                    onItemPerPageChange = { numberItemInPage = it.toIntOrNull() ?: 10 }
                )

                BpPager(
                    maxPages = pageCount,
                    currentPage = selectedPage,
                    onPageClicked = { selectedPage = it },
                )
            }
        }
    }
}