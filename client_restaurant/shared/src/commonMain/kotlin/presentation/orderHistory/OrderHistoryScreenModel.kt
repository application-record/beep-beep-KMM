package presentation.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Order
import domain.entity.OrderStatus
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState


class OrderHistoryScreenModel(
    private val restaurantId: String, private val ordersManagement: IManageOrderUseCase
) : BaseScreenModel<OrderHistoryScreenUiState, OrderHistoryScreenUiEffect>(OrderHistoryScreenUiState()),
    OrderHistoryScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getData()
    }

    private fun getData() {
        tryToExecute(
            {
                ordersManagement.getOrdersHistory(restaurantId, 1, 10)
            },
            ::onOrdersSuccess,
            ::onError
        )
    }

    private fun onOrdersSuccess(orders: List<Order>) {
        val ordersUiState = orders.map { it.toOrderHistoryUiState() }
        updateState { currentState ->
            currentState.copy(
                orders = ordersUiState,
                currentOrders = ordersUiState.filter {
                    currentState.selectedType == OrderHistoryScreenUiState.OrderSelectType.FINISHED &&
                            it.orderState == OrderStatus.DONE ||
                            currentState.selectedType == OrderHistoryScreenUiState.OrderSelectType.CANCELLED &&
                            it.orderState == OrderStatus.CANCELED
                }
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(errorState = errorState) }
    }

    override fun onClickBack() {
        sendNewEffect(OrderHistoryScreenUiEffect.Back)
    }

    override fun onClickTab(type: OrderHistoryScreenUiState.OrderSelectType) {
        updateState { currentState ->
            currentState.copy(
                selectedType = type,
                currentOrders = currentState.orders.filter {
                    type == OrderHistoryScreenUiState.OrderSelectType.FINISHED &&
                            it.orderState == OrderStatus.DONE ||
                            type == OrderHistoryScreenUiState.OrderSelectType.CANCELLED
                            && it.orderState == OrderStatus.CANCELED
                }
            )
        }
    }
}


