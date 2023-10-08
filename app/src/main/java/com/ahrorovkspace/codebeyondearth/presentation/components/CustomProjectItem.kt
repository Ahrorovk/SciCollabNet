package com.ahrorovkspace.codebeyondearth.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ahrorovkspace.codebeyondearth.app.theme.GreySecond
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesItem

@Composable
fun CustomProjectItem(
    projectByCategoriesItem: GetProjectByCategoriesItem,
    onApplyClick: (Int) -> Unit,
    onBrieflyDescClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(GreySecond)
            .clickable { onBrieflyDescClick(projectByCategoriesItem.id) }
    ) {
        Column {
            Text(text = projectByCategoriesItem.name, modifier = Modifier.padding(7.dp), color = Color(0x75000000))

            Spacer(modifier = Modifier.padding(5.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Spacer(modifier = Modifier.padding(5.dp))
                    projectByCategoriesItem.categories.forEach {
                        ProjectCategoryItem(
                            name = it.name
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                    }
                }
            }
            if (projectByCategoriesItem.images.isNotEmpty())
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(300.dp),
                        painter = rememberAsyncImagePainter(model = projectByCategoriesItem.images[0].original_url),
                        contentDescription = "image_url"
                    )
                }

            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = projectByCategoriesItem.description,
                maxLines = 2,
                modifier = Modifier.padding(7.dp), color = Color(0x75000000)
            )

            Column {
                Row {
                    ProjectActionItem(
                        number = projectByCategoriesItem.downloads_count,
                        icon = Icons.Default.Download
                    ) {

                    }
                    CustomButton(
                        text = "Apply", textSize = 14, isLoading = false,
                        width = 80, height = 35
                    ) {
                        onApplyClick(projectByCategoriesItem.id)
                    }
                }
                Spacer(modifier = Modifier.padding(2.dp))

                Row {
                    ProjectActionItem(
                        number = projectByCategoriesItem.likes_count,
                        icon = Icons.Default.Favorite
                    ) {

                    }

                    Spacer(modifier = Modifier.padding(2.dp))
                    ProjectActionItem(
                        number = projectByCategoriesItem.comments_count,
                        icon = Icons.Default.Comment
                    ) {

                    }

                    Spacer(modifier = Modifier.padding(2.dp))

                    ProjectActionItem(
                        number = projectByCategoriesItem.views_count,
                        icon = Icons.Default.RemoveRedEye
                    ) {

                    }
                }
            }
        }
    }
}