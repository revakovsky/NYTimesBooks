package com.revakovsky.thenytimesbooks.presentation.screens.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.presentation.models.CategoryUi
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.TextRegular
import com.revakovsky.thenytimesbooks.presentation.widgets.TextTitle

@Composable
fun CategoryItem(
    category: CategoryUi,
    showDivider: Boolean,
    onCategoryClick: (categoryName: String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCategoryClick(category.categoryName) }
    ) {

        Column(modifier = Modifier.padding(dimens.medium)) {

            TextTitle(
                text = category.categoryName,
                singleLine = true
            )

            TextRegular(
                modifier = Modifier.padding(top = dimens.extraSmall),
                text = stringResource(R.string.published_date, category.publishedDate),
            )

            Spacer(modifier = Modifier.height(dimens.smallest))

            TextRegular(text = stringResource(R.string.updated, category.howOftenIsItUpdated))

        }

        if (showDivider) Divider(
            modifier = Modifier.padding(horizontal = dimens.medium)
        )

    }

}
