package markdown

import TitleLine
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import textColor

@Composable
fun MarkdownHeader(header: MarkdownObject.Header) {
    when (header.level) {
        MarkdownObject.Header.Level.H1 -> H1(header.text)
        MarkdownObject.Header.Level.H2 -> H2(header.text)
        MarkdownObject.Header.Level.H3 -> H3(header.text)
        MarkdownObject.Header.Level.H4 -> H4(header.text)
        MarkdownObject.Header.Level.H5 -> H5(header.text)
        MarkdownObject.Header.Level.H6 -> H6(header.text)
    }
}

@Composable
private fun H1(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor(),
            fontWeight = FontWeight.Black,
            fontSize = 24.sp
        )
        TitleLine()
    }
}

@Composable
private fun H2(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor(),
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        )
        TitleLine()
    }
}

@Composable
fun H3(text: String) {
    Text(
        text = text,
        color = textColor(),
        fontWeight = FontWeight.Black,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
fun H4(text: String) {
    Text(
        text = text,
        color = textColor(),
        fontWeight = FontWeight.Black,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
fun H5(text: String) {
    Text(
        text = text,
        color = textColor(),
        fontWeight = FontWeight.Black,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
fun H6(text: String) {
    Text(
        text = text,
        color = textColor(),
        fontWeight = FontWeight.Black,
        fontSize = 12.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}
