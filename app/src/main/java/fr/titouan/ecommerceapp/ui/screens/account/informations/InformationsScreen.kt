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
    var newMail = remember { mutableStateOf(user.safeMail) }
    var newAddress = remember { mutableStateOf(user.safeCity) }
    var newSurname = remember { mutableStateOf(user.safeSurname) }
    var newName = remember { mutableStateOf(user.safeName) }
    var newCity = remember { mutableStateOf(user.safeCity) }
    var newPostalCode = remember { mutableStateOf(user.safePostCode) }

    var newUserInfo = remember {
        User(
            idUser =  user.idUser,
            name =  newName.value,
            mail = newMail.value,
            password = user.password,
            surname = newSurname.value,
            gender = null,
            city = newCity.value,
            postCode = newPostalCode.value)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .wrapContentWidth()
    ){
        OutlinedTextField(
            value = newMail.value,
            onValueChange = { newValue ->  newMail.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))


//    Set address
        OutlinedTextField(
            value = newAddress.value,
            onValueChange = { newValue ->  newAddress.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

//    set name
        OutlinedTextField(
            value = newName.value,
            onValueChange = { newValue ->  newName.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

//    Set surname
        OutlinedTextField(
            value = newSurname.value,
            onValueChange = { newValue ->  newSurname.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

//    set city
        OutlinedTextField(
            value = newCity.value,
            onValueChange = { newValue ->  newCity.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))
// set postal code
        OutlinedTextField(
            value = newPostalCode.value,
            onValueChange = { newValue ->  newPostalCode.value = newValue },
            label = { Text("Email") },
            placeholder = { Text(text = stringResource(id = R.string.login_placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = { onSetInformationsClick(newUserInfo) }) {
            Text(text = stringResource(id = R.string.nav_set_informations))
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}