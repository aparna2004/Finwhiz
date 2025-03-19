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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview;
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.expensetracker.ui.theme.*
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.HomeViewModelFactory
import com.example.expensetracker.widget.ExpenseTextView
import org.w3c.dom.Text

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

            CardItem(modifier = Modifier.constrainAs(card){
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            TransactionList(modifier = Modifier.fillMaxWidth().constrainAs(list){
                top.linkTo(card.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            })
        }
    }
}

@Composable
fun CardItem(modifier: Modifier){
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
                ExpenseTextView(text = "$5000", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
                amount = "500000",
                imaget = R.drawable.ic_income
            )
            Spacer(modifier = Modifier.size(8.dp))
            CardRowItem(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                title = "Expense",
                amount = "20000",
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
fun TransactionList(modifier: Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ExpenseTextView(text = "Recent Transactions", fontSize = 20.sp)
            ExpenseTextView(text = "See All", fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterEnd))
        }
        TransactionItem( title = "Netflix","$ 200", icon = R.drawable.ic_netflix, date = "Today", color = Color.Red)
        TransactionItem( title = "Starbucks","$ 200", icon = R.drawable.ic_starbucks, date = "Yesterday", color = Color.Red)
        TransactionItem( title = "Netflix","$ 25", icon = R.drawable.ic_youtube, date = "Yesterday", color = Color.DarkGray)
        TransactionItem( title = "Paypal","$ 20", icon = R.drawable.ic_paypal, date = "Today", color = Color.Red)
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
