package com.padeltournaments.presentation.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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

    val onBackPressed: () -> Unit = {
        navController.popBackStack()
    }
    Scaffold(
        topBar = {
            TournamentDetailAppBar { onBackPressed() }
        },
        content = {
            TournamentDetailScreenContent(context,
                navController,
                idTournament,
                tournamentViewModel,
                session,
                detailProductViewModel
            )
        }
    )
}

@Composable
fun TournamentDetailAppBar(onBackPressed: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Retroceder")
            }
        },
        title = { Text(text = "PadelTournaments") }
    )
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

    detailProductViewModel.getPlayerTournamentsByUserId(userId)

    val tournaments by detailProductViewModel.tournamentsFlow.collectAsState(initial = emptyList())

    LaunchedEffect(PaymentSucceed.inscriptionSucceed) {
        if(PaymentSucceed.inscriptionSucceed) {
            if (idTournament != null) {
                val keyId = session.getUserDetails()[LoginPref.KEY_ID]
                keyId?.let { detailProductViewModel.insertPlayerTournamentRelationByUserId(it, tournamentViewModel, idTournament) }
            }
        }
    }
    if(idTournament != null) {
        tournaments.forEach { tournament ->
            if (tournament.id == idTournament.toInt()) {
                tournamentViewModel.enableInscription.value = false
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
    ) {
        TournamentDetailHeader(tournamentViewModel)
        
        //ProfilesBookedLazyRow(profiles = )

        //MovieDetailVideos(viewModel)

        //MovieDetailSummary(viewModel)

        //MovieDetailReviews(viewModel)

        if(session.getUserDetails()[LoginPref.KEY_ROL] == Rol.player) {
            PayButton(
                tournamentViewModel.inscriptionCost.value,
                enableInscription = tournamentViewModel.enableInscription.value
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

}

///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////

@Composable
private fun TournamentDetailHeader(
    tournamentViewModel: CreateTournamentViewModel = hiltViewModel()
)
{
    Column(modifier= Modifier.background(Color.Blue)) {
        tournamentViewModel.poster.value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "TournamentPoster",
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = tournamentViewModel.nameTournament.value.uppercase() ?: "",
            style = MaterialTheme.typography.h5,
            color = Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Fecha de inicio: ${tournamentViewModel.dateIni.value}",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Fecha de fin: ${tournamentViewModel.dateEnd.value}",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }

}


/*
@Composable
private fun MovieDetailHeader(
    viewModel: MovieDetailViewModel
) {
    val movie: Movie? by viewModel.movieFlow.collectAsState(initial = null)

    Column {

        var palette by remember { mutableStateOf<Palette?>(null) }
        NetworkImage(
            networkUrl = Api.getBackdropPath(movie?.backdrop_path),
            circularReveal = CircularReveal(duration = 300),
            shimmerParams = null,
            bitmapPalette = BitmapPalette {
                palette = it
            },
            modifier = Modifier
                .height(280.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = movie?.title ?: "",
            style = MaterialTheme.typography.h5,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Release Date: ${movie?.release_date}",
            style = MaterialTheme.typography.body1,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        RatingBar(
            rating = (movie?.vote_average ?: 0f) / 2f,
            color = Color(palette?.vibrantSwatch?.rgb ?: 0),
            modifier = Modifier
                .height(15.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun MovieDetailVideos(
    viewModel: MovieDetailViewModel
) {
    val videos by viewModel.videoListFlow.collectAsState(listOf())

    videos.whatIfNotNullOrEmpty {

        Column {

            Spacer(modifier = Modifier.height(23.dp))

            Text(
                text = stringResource(R.string.trailers),
                style = MaterialTheme.typography.h6,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            ) {

                items(items = videos) { video ->

                    VideoThumbnail(video)

                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }
}

@Composable
private fun VideoThumbnail(
    video: Video
) {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
    ) {

        ConstraintLayout(
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .clickable(
                    onClick = {
                        val playVideoIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(video.key)))
                        context.startActivity(playVideoIntent)
                    }
                )
        ) {
            val (thumbnail, icon, box, title) = createRefs()

            var palette by remember { mutableStateOf<Palette?>(null) }
            NetworkImage(
                networkUrl = Api.getYoutubeThumbnailPath(video.key),
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(thumbnail) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                bitmapPalette = BitmapPalette {
                    palette = it
                }
            )

            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.icon_youtube),
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(20.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Crossfade(
                targetState = palette,
                modifier = Modifier
                    .height(25.dp)
                    .constrainAs(box) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {

                Box(
                    modifier = Modifier
                        .background(Color(it?.darkVibrantSwatch?.rgb ?: 0))
                        .alpha(0.7f)
                        .fillMaxSize()
                )
            }

            Text(
                text = video.name,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.85f)
                    .padding(horizontal = 8.dp)
                    .constrainAs(title) {
                        top.linkTo(box.top)
                        bottom.linkTo(box.bottom)
                    }
            )
        }
    }
}

@Composable
private fun MovieDetailSummary(
    viewModel: MovieDetailViewModel
) {
    val movie: Movie? by viewModel.movieFlow.collectAsState(initial = null)
    val keywords by viewModel.keywordListFlow.collectAsState(listOf())

    keywords.whatIfNotNullOrEmpty {

        Column {

            Spacer(modifier = Modifier.height(23.dp))

            Text(
                text = stringResource(R.string.summary),
                style = MaterialTheme.typography.h6,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = movie?.overview ?: "",
                style = MaterialTheme.typography.body1,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            FlowRow {

                it.forEach {

                    Keyword(it)
                }
            }
        }
    }
}

@Composable
private fun Keyword(keyword: Keyword) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        elevation = 8.dp,
        color = purple200,
        modifier = Modifier.padding(8.dp)
    ) {

        Text(
            text = keyword.name,
            style = MaterialTheme.typography.body1,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun MovieDetailReviews(
    viewModel: MovieDetailViewModel
) {
    val reviews by viewModel.reviewListFlow.collectAsState(listOf())

    reviews.whatIfNotNullOrEmpty {

        Column {

            Spacer(modifier = Modifier.height(23.dp))

            Text(
                text = stringResource(R.string.reviews),
                style = MaterialTheme.typography.h6,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Column {

                reviews.forEach {

                    Review(it)
                }
            }
        }
    }
}

@Composable
private fun Review(
    review: Review
) {
    var expanded: Boolean by remember { mutableStateOf(false) }

    Column {

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = review.author,
            style = MaterialTheme.typography.body1,
            color = Color.White,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (expanded) {
            Text(
                text = review.content,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .clickable { expanded = !expanded }
            )
        } else {
            Text(
                text = review.content,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .clickable { expanded = !expanded }
            )
        }
    }
}


 */

@Composable
fun PayButton( inscriptionCost: String,
               enableInscription: Boolean) {
    val euroSymbol = '\u20AC'
    val context = LocalContext.current
    val activity = context as Activity
    var text = "INSCRIBIRME - $inscriptionCost $euroSymbol"
    if (!enableInscription) {
        text = "INSCRITO"
    }
    Button(onClick = {
                     val razorPayments = RazorPayments(activity)
                    razorPayments.startPayment(inscriptionCost.toInt())
    },
        enabled = enableInscription,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = MaterialTheme.shapes.medium) {
        Text(text = text)
    }
}