package ge.dev.waroffingerskmm.android.screens.rally

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ge.dev.waroffingerskmm.android.R
import org.koin.androidx.compose.getViewModel

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */

@Composable
fun RallyModeScreen(selectedPlayerId: String, viewModel: RallyViewModel = getViewModel()) {
  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize()
      .clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
      ) { viewModel.onTap() }
  ) {
    viewModel.selectedCarId = selectedPlayerId

    val offset1 by animateDpAsState(viewModel.offsetCar1.dp)
    val offset2 by animateDpAsState(viewModel.offsetCar2.dp)

    val (car1, road1, car2, road2) = createRefs()

    Image(
      painter = painterResource(id = R.drawable.ic_road),
      contentScale = ContentScale.FillBounds,
      contentDescription = "road 1",
      modifier = Modifier
        .constrainAs(road1) {
          bottom.linkTo(parent.bottom)
          start.linkTo(parent.start)
        }
        .fillMaxHeight()
        .width(150.dp)
    )

    Image(
      painter = painterResource(id = R.drawable.red_car),
      contentDescription = "red car",
      modifier = Modifier
        .constrainAs(car1) {
          bottom.linkTo(parent.bottom, margin = 20.dp)
          start.linkTo(road1.start)
          end.linkTo(road1.end)
        }
        .offset(y = offset1)
        .width(100.dp)
        .height(100.dp)
    )

    Image(
      painter = painterResource(id = R.drawable.ic_road),
      contentScale = ContentScale.FillBounds,
      contentDescription = "road 2",
      modifier = Modifier
        .constrainAs(road2) {
          bottom.linkTo(parent.bottom)
          end.linkTo(parent.end)
        }
        .fillMaxHeight()
        .width(150.dp)
    )

    Image(
      painter = painterResource(id = R.drawable.car_yellow),
      contentDescription = "red car",
      modifier = Modifier
        .constrainAs(car2) {
          bottom.linkTo(parent.bottom, margin = 20.dp)
          start.linkTo(road2.start)
          end.linkTo(road2.end)
        }
        .offset(y = offset2)
        .width(100.dp)
        .height(100.dp)
    )
  }
}