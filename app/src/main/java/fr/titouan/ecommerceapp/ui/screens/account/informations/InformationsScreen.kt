package fr.titouan.ecommerceapp.ui.screens.account.informations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.titouan.ecommerceapp.R
import fr.titouan.ecommerceapp.model.User

@Composable
fun InformationsScreen(
                       user: User,
                       onSetInformationsClick: (User) -> Unit){
    var newMail = remember { mutableStateOf(user.mail) }
    var newAddress = remember { mutableStateOf("") }
    var newSurname = remember { mutableStateOf("") }
    var newName = remember { mutableStateOf("") }
    var newCity = remember { mutableStateOf("") }
    var newPostalCode = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .wrapContentWidth()
    ){
        OutlinedTextField(
            value = user.mail,
            onValueChange = { newValue ->  newMail.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))


//    Set address
        OutlinedTextField(
            value = user.mail,
            onValueChange = { newValue ->  newMail.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

//    set name
        OutlinedTextField(
            value = user.mail,
            onValueChange = { newValue ->  newMail.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

//    Set surname
        OutlinedTextField(
            value = user.mail,
            onValueChange = { newValue ->  newMail.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

//    set city
        OutlinedTextField(
            value = user.mail,
            onValueChange = { newValue ->  newMail.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))
// set postal code
        OutlinedTextField(
            value = user.mail,
            onValueChange = { newValue ->  newMail.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = { onSetInformationsClick(user) }) {
            Text(text = stringResource(id = R.string.nav_set_informations))
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}