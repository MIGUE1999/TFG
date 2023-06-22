package com.padeltournaments.presentation.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.padeltournaments.R
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.presentation.composables.ProfilesBookedLazyRow
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.presentation.viewmodels.DetailProductViewModel
import com.padeltournaments.presentation.viewmodels.RazorPayments
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.PaymentSucceed
import com.padeltournaments.util.Rol

@Composable
fun TournamentDetailScreen( context : Context,
                            navController: NavController,
                            idTournament: String?,
                            tournamentViewModel: CreateTournamentViewModel = hiltViewModel(),
                            session: LoginPref,
                            detailProductViewModel: DetailProductViewModel = hiltViewModel()
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Green)
    ) {

        TournamentDetailScreenContent(
            context,
            navController,
            idTournament,
            tournamentViewModel,
            session,
            detailProductViewModel
        )


    }
}

@Composable
fun TournamentDetailTopSection(
    navController: NavController,
    createTournamentViewModel: CreateTournamentViewModel
) {
    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(36.dp)
                .offset(10.dp, 16.dp)
                .zIndex(1f)
                .clickable {
                    navController.popBackStack()
                }
        )
        TournamentDetailHeader(createTournamentViewModel)
    }
}

@Composable
fun TournamentDetailScreenContent(context : Context,
                           navController: NavController,
                           idTournament: String?,
                           tournamentViewModel: CreateTournamentViewModel = hiltViewModel(),
                           session: LoginPref,
                           detailProductViewModel: DetailProductViewModel = hiltViewModel()
)
{
    LaunchedEffect(idTournament) {
        if (idTournament != null) {
            tournamentViewModel.setTournamentById(idTournament.toInt())
        }
    }

    val userId = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()

    idTournament?.let { detailProductViewModel.setTournamentId(it.toInt()) }
    val playersBooked by detailProductViewModel.usersInscriptedTournament.collectAsState(initial = emptyList())
    idTournament?.let { detailProductViewModel.checkPlayerInTournament(idTournament = it.toInt(), idUser = userId ) }

    val isPlayerInTournament by detailProductViewModel.isUserInTournament.collectAsState()

    LaunchedEffect(PaymentSucceed.inscriptionSucceed) {
        if(PaymentSucceed.inscriptionSucceed) {
            if (idTournament != null && !isPlayerInTournament) {
                 detailProductViewModel.insertPlayerTournamentRelationByUserId(userId.toString(), tournamentViewModel, idTournament)
                PaymentSucceed.inscriptionSucceed = false
            }
        }
        idTournament?.let { detailProductViewModel.checkPlayerInTournament(idTournament = it.toInt(), idUser = userId ) }
    }

    TournamentDetailSection(tournamentInfo = tournamentViewModel,
        detailProductViewModel = detailProductViewModel,
        session = session,
        playersBooked = playersBooked,
        navController = navController,
        isPlayerInTournament = isPlayerInTournament
    )
}
@Composable
fun TournamentDetailSection(
    tournamentInfo: CreateTournamentViewModel,
    detailProductViewModel: DetailProductViewModel,
    modifier: Modifier = Modifier,
    session: LoginPref,
    playersBooked: List<UserEntity>,
    navController: NavController,
    isPlayerInTournament: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            TournamentDetailTopSection(
                navController = navController,
                createTournamentViewModel = tournamentInfo
            )

        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tournamentInfo.nameTournament.value,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(24.dp))

            TournamentDateSection(tournamentInfo = tournamentInfo)
            Spacer(modifier = Modifier.height(24.dp))

            TournamentDetailDataSection(
                category = tournamentInfo.category.value,
                prize = tournamentInfo.prizeTournament.value
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column() {
                Text(text = "Jugadores Inscritos: " + playersBooked.size + "/" + tournamentInfo.maxNumberInscriptions.value)
                if(tournamentInfo.maxNumberInscriptions.value != "") {
                    LinearProgressIndicator(
                        progress = playersBooked.size.toFloat() / tournamentInfo.maxNumberInscriptions.value.toFloat(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            ProfilesBookedLazyRow(profiles = playersBooked)


            if(session.getUserDetails()[LoginPref.KEY_ROL] == Rol.player) {
                Spacer(modifier = Modifier.height(24.dp))
                PayButton(
                    tournamentInfo.inscriptionCost.value,
                    isPlayerInTournament = isPlayerInTournament,
                )
            } else {
                    Spacer(modifier = Modifier.height(24.dp))
                    PayButton(
                        tournamentInfo.inscriptionCost.value,
                        isPlayerInTournament = true,
                        isOrg = true
                    )

            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun TournamentDateSection(tournamentInfo: CreateTournamentViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.primary)
                .height(35.dp)
        ) {
            Text(
                text = tournamentInfo.dateIni.value,
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.primary)
                .height(35.dp)
        ) {
            Text(
                text = tournamentInfo.dateEnd.value,
                color = Color.White,
                fontSize = 18.sp
            )
        }

    }
}

@Composable
fun TournamentDetailDataSection(
    category: String,
    prize: String,
    sectionHeight: Dp = 80.dp
) {
    val euroSymbol = '\u20AC'
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TournamentDetailDataItem(
            dataValue = category,
            dataIcon = painterResource(id = R.drawable.category),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier
            .size(1.dp, sectionHeight)
            .background(Color.LightGray))
        TournamentDetailDataItem(
            dataValue = prize + euroSymbol,
            dataIcon = painterResource(id = R.drawable.prize),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TournamentDetailDataItem(
    dataValue: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(painter = dataIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = dataValue,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
private fun TournamentDetailHeader(
    tournamentViewModel: CreateTournamentViewModel = hiltViewModel()
)
{
    tournamentViewModel.poster.value?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "TournamentPoster",
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .aspectRatio(1f) // Agrega este modificador para mantener la relación de aspecto
        )
    }

}
@Composable
fun PayButton( inscriptionCost: String,
               isPlayerInTournament: Boolean,
               isOrg: Boolean = false) {
    val euroSymbol = '\u20AC'
    val context = LocalContext.current
    val activity = context as Activity
    var text = "INSCRIBIRME - $inscriptionCost $euroSymbol"
    if (isPlayerInTournament) {
        text = "INSCRITO"
    }

    if (isOrg){
        text = "PRECIO - $inscriptionCost $euroSymbol"
    }
    Button(onClick = {
                     val razorPayments = RazorPayments(activity)
                    razorPayments.startPayment(inscriptionCost.toInt())
                    PaymentSucceed.isBookCourt = false
    },
        enabled = !isPlayerInTournament,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = MaterialTheme.shapes.medium) {
        Text(text = text)
    }
    Spacer(modifier = Modifier.height(16.dp))

}


