import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appecommerce.model.Product

@Composable
fun ProductCardApi(product: Product, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .width(170.dp)
            .clickable { onClick() }
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image) // Changed from imageUrl to image
                .crossfade(true)
                .build(),
            contentDescription = product.name,
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(product.name, fontWeight = FontWeight.Bold)
        Text("${product.price} MAD", color = Color.Red)
    }
}
