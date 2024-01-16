package app.mp.view.widget.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import app.mp.R
import app.mp.model.model.Audio

@Composable
fun AudioListItemView(item: Audio, isPlaying: Boolean, modifier: Modifier = Modifier) {
    val backgroundColor by animateColorAsState(
        if (isPlaying) Color.White.copy(alpha = 0.1f) else Color.Transparent,
        label = "background",
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = modifier.fillMaxWidth()
            .wrapContentHeight()
            .drawBehind { drawRect(color = backgroundColor) }
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Content(audio = item)
        PlayingIcon(visible = isPlaying)
    }
}

@Composable
private fun Content(audio: Audio) {
    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth(fraction = 0.7f)) {
        val textStyle = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(color = Color.DarkGray, blurRadius = 8f, offset = Offset(1f, 1f)),
        )

        Text(
            text = audio.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = TextUnit(16f, TextUnitType.Sp),
            style = textStyle
        )
        Text(
            text = audio.author,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = TextUnit(12f, TextUnitType.Sp),
            modifier = Modifier.padding(top = 8.dp),
            style = textStyle
        )
    }
}

@Composable
fun PlayingIcon(visible: Boolean) {
    Box(modifier = Modifier.wrapContentSize().padding(end = 8.dp)) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300)),
        ) {
            Image(
                painter = painterResource(R.drawable.round_star_36),
                contentDescription = "playing",
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFF, showBackground = true)
@Composable
fun Preview() {
    val audio = Audio(
        id = 1,
        name = "Audio track audio track audio track audio track audio track",
        author = "John John John John John John John John John",
        duration = 2.0,
        fileSize = 100,
        uri = ""
    )

    Box(modifier = Modifier.fillMaxWidth().height(400.dp), contentAlignment = Alignment.Center) {
        AudioListItemView(item = audio, isPlaying = true)
    }
}