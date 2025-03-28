package com.example.expensetracker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.ui.theme.*
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.HomeViewModelFactory
import com.example.expensetracker.widget.ExpenseTextView
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(){

    val viewModel :HomeViewModel = HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar, add) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            {
                Column(modifier = Modifier.align(Alignment.CenterStart)) {
                    ExpenseTextView(text = "Good Afternoon", fontSize = 16.sp, color = Color.White)
                    ExpenseTextView(text = "Aparna", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            val state = MutableStateFlow<List<ExpenseEntity>>(emptyList())

            val expenses = viewModel.getTotalExpense(state.value)
            val income = viewModel.getTotalIncome(state.value)
            val balance = viewModel.getBalance(state.value)

            CardItem(modifier = Modifier.constrainAs(card){
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, balance, income, expenses)

            TransactionList(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                } , list = state.value
            )
        }
    }
}

@Composable
fun CardItem(modifier: Modifier, balance: String, income: String, expenses: String){
    Column(modifier = modifier
        .padding(16.dp)
        .fillMaxWidth()
        .shadow(16.dp)
        .height(200.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Zinc)
        .padding(16.dp)

    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                ExpenseTextView(text = "Total Balance", fontSize = 16.sp, color = Color.White)
                ExpenseTextView(text = balance, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Image(
                painter = painterResource(id = R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

        }
        Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
        {
            CardRowItem(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                title = "Income",
                amount = income,
                imaget = R.drawable.ic_income
            )
            Spacer(modifier = Modifier.size(8.dp))
            CardRowItem(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                title = "Expense",
                amount = expenses,
                imaget = R.drawable.ic_expense
            )

        }

    }
}

@Composable
fun CardRowItem(modifier: Modifier, title: String, amount: String, imaget: Int) {
    Column(modifier = modifier) {
        Row {

            Image(
                painter = painterResource(id = imaget),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(8.dp))
            ExpenseTextView(text = title, fontSize = 16.sp, color = Color.White)

        }
        Spacer(modifier = Modifier.size(4.dp))
        ExpenseTextView(text = amount, fontSize = 20.sp, color = Color.White)
    }
}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>) {
    LazyColumn (modifier = modifier.padding(horizontal = 16.dp)) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                ExpenseTextView(text = "Recent Transactions", fontSize = 20.sp)
                ExpenseTextView(text = "See All", fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterEnd))
            }
        }

        items(list) { item ->
            TransactionItem(
                title = item.title,
                amount = item.amount.toString(),
                icon = if (item.type == "Income") R.drawable.ic_paypal else R.drawable.ic_netflix,
                date = item.date,
                color = if (item.type == "Income") Color.Green else Color.Red
            )
        }
    }
}

@Composable
fun TransactionItem(title: String, amount: String, icon: Int, date: String, color: Color) {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row {
            Image(
                painter = painterResource(id = icon), contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                ExpenseTextView(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                ExpenseTextView(text = date, fontSize = 12.sp)

            }
        }
        ExpenseTextView(text = amount, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterEnd), color = color, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
@Preview
fun PreviewHomeScreen(showBackground: Boolean = true){
    HomeScreen()
}
