package com.thanh_nguyen.test_count_down.app.presentation.ui.main.handbook

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.FeatureItemModel
import com.thanh_nguyen.test_count_down.app.model.FeatureItemType
import com.thanh_nguyen.test_count_down.app.model.HandbookHeaderModel
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.about.AboutActivity
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.handbook.items.feature.FeatureViewItem
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.handbook.items.header.HandbookHeaderViewItem
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseCollectionFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentHandbookBinding
import com.thanh_nguyen.test_count_down.utils.showToastMessage
import kodeinViewModel

class HandbookFragment: BaseCollectionFragmentMVVM<FragmentHandbookBinding, HandbookViewModel>() {

    override fun inflateLayout(): Int = R.layout.fragment_handbook

    override val viewModel: HandbookViewModel by kodeinViewModel()

    private val onItemFeatureCallback = { featureData: FeatureItemModel ->
        when(featureData.type){
            FeatureItemType.DETAIL -> {
                gotoAboutActivity()
            }
            FeatureItemType.HOROSCOPE -> {
                activity?.showToastMessage("HOROSCOPE")
            }
        }
        Unit
    }

    override fun initClusters() {
        addCluster(HandbookHeaderViewItem::class.java)
        addCluster(FeatureViewItem::class.java)
    }

    override fun onViewCreatedX(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedX(view, savedInstanceState)
        showHeader()
        showFeatures()
    }

    private fun showHeader(){
        recyclerManager.replace(HandbookHeaderViewItem::class.java, HandbookHeaderViewItem(
            HandbookHeaderModel(
                "Cẩm nang ngày tết",
            )
        ))
    }

    private fun showFeatures(){
        recyclerManager.replace(FeatureViewItem::class.java, listOf(
            FeatureViewItem(
                FeatureItemModel(
                    ContextCompat.getDrawable(activity?:return, R.drawable.ic_new_year),
                    "Tết 2022",
                    FeatureItemType.DETAIL
                ),
                onItemFeatureCallback
            ),
            FeatureViewItem(
                FeatureItemModel(
                    ContextCompat.getDrawable(activity?:return, R.drawable.ic_horoscope),
                    "Tử vi",
                    FeatureItemType.DETAIL
                ),
                onItemFeatureCallback
            ),
            FeatureViewItem(
                FeatureItemModel(
                    ContextCompat.getDrawable(activity?:return, R.drawable.ic_wish),
                    "Chúc tết",
                    FeatureItemType.DETAIL
                ),
                onItemFeatureCallback
            ),
            FeatureViewItem(
                FeatureItemModel(
                    ContextCompat.getDrawable(activity?:return, R.drawable.ic_lucky_money),
                    "Lì xì",
                    FeatureItemType.DETAIL
                ),
                onItemFeatureCallback
            ),
            FeatureViewItem(
                FeatureItemModel(
                    ContextCompat.getDrawable(activity?:return, R.drawable.ic_chicken),
                    "Lịch âm",
                    FeatureItemType.DETAIL
                ),
                onItemFeatureCallback
            ),
        ))
    }

    private fun gotoAboutActivity(){
        startActivity(
            Intent(
                activity,
                AboutActivity::class.java
            )
        )
    }

    override fun getSpanCount(): Int = 3
}