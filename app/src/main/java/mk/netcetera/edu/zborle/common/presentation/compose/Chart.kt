import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.VerticalPosition
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

@Composable
fun BarChart(values: List<Int>) {
  val modelProducer = remember { CartesianChartModelProducer.build() }
  LaunchedEffect(Unit) {
    modelProducer.tryRunTransaction {
      columnSeries {
        series(
          x = listOf(1, 2, 3, 4, 5, 6), // X values for the attempts numbers.
          y = values
        )
      }
    }
  }

  CartesianChartHost(
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(min = 100.dp)
      .padding(horizontal = 16.dp),
    chart = rememberCartesianChart(
      rememberColumnCartesianLayer(
        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
          rememberLineComponent(
            color = ZborleTheme.statusColors.correctBackground,
            thickness = 16.dp
          )
        ),
        spacing = 4.dp,
        dataLabel = rememberTextComponent(color = Color.Black),
        dataLabelVerticalPosition = VerticalPosition.Top,
        axisValueOverrider = AxisValueOverrider.adaptiveYValues(1.2f)
      ),
      bottomAxis = rememberBottomAxis(guideline = null),
    ),
    modelProducer = modelProducer,
    scrollState = rememberVicoScrollState(scrollEnabled = false),
    zoomState = rememberVicoZoomState(zoomEnabled = false),
    horizontalLayout = HorizontalLayout.FullWidth()
  )
}