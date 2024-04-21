package fr.titouan.ecommerceapp.ui.screens.payment.commons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.ui.screens.payment.PaymentProcessID
import fr.titouan.ecommerceapp.ui.screens.payment.views.PaymentUiState

@Composable
fun PaymentStateCard(processId: PaymentProcessID) {
    // Déterminer le texte correspondant au processus
    val processText = when (processId) {
        PaymentProcessID.CARD -> "Processus de carte"
        PaymentProcessID.DELIVERY -> stringResource(id = R.string.payment_process_delivery)
        PaymentProcessID.PAYMENT -> stringResource(id = R.string.payment_process_payment)
        PaymentProcessID.SUCCESS -> stringResource(id = R.string.payment_process_success)
    }

    val cardCompleted = processId >= PaymentProcessID.CARD
    val deliveryCompleted = processId >= PaymentProcessID.DELIVERY
    val paymentCompleted = processId >= PaymentProcessID.PAYMENT
    val successCompleted = processId >= PaymentProcessID.SUCCESS

    Row {
        Canvas(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val bubbleRadius = 16.dp.toPx()

            val bubbleSpacing = (canvasWidth - 4 * bubbleRadius) / 3 // Espace entre les bulles

            drawCircle(color = if (cardCompleted) Color.Green else Color.Gray,
                radius = bubbleRadius,
                center = Offset(bubbleRadius, canvasHeight / 2f)
            )

            drawCircle(color = if (deliveryCompleted) Color.Green else Color.Gray,
                radius = bubbleRadius,
                center = Offset(bubbleRadius + bubbleRadius + bubbleSpacing, canvasHeight / 2f))

            drawCircle(color = if (paymentCompleted) Color.Green else Color.Gray,
                radius = bubbleRadius,
                center = Offset(bubbleRadius + 2 * (bubbleRadius + bubbleSpacing), canvasHeight / 2f))

            drawCircle(color = if (successCompleted) Color.Green else Color.Gray,
                radius = bubbleRadius,
                center = Offset(bubbleRadius + 3 * (bubbleRadius + bubbleSpacing), canvasHeight / 2f))

            drawLine(color = if (deliveryCompleted) Color.Green else Color.Gray,
                start = Offset(bubbleRadius * 2, canvasHeight / 2f),
                end = Offset(bubbleRadius + bubbleRadius + bubbleSpacing, canvasHeight / 2f))

            drawLine(color = if (paymentCompleted) Color.Green else Color.Gray,
                start = Offset(bubbleRadius * 2 + bubbleRadius + bubbleSpacing, canvasHeight / 2f),
                end = Offset(bubbleRadius + 2 * (bubbleRadius + bubbleSpacing), canvasHeight / 2f))

            drawLine(color = if (successCompleted) Color.Green else Color.Gray,
                start = Offset(bubbleRadius * 2 + 2 * (bubbleRadius + bubbleSpacing), canvasHeight / 2f),
                end = Offset(bubbleRadius + 3 * (bubbleRadius + bubbleSpacing), canvasHeight / 2f))
        }

    }
    Text(text = processText, modifier = Modifier
        .padding(top = 8.dp)
        .fillMaxWidth(), textAlign = TextAlign.Center)



}

