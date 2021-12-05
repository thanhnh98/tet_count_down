package com.thanh_nguyen.test_count_down.app.presentation.ui.main.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.AboutHeaderDataModel
import com.thanh_nguyen.test_count_down.app.model.AboutItemDataModel
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.about.item.content.AboutViewItem
import com.thanh_nguyen.test_count_down.app.presentation.ui.main.about.item.header.AboutHeaderViewItem
import com.thanh_nguyen.test_count_down.common.base.adapter.RecycleViewItem
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseCollectionFragmentMVVM
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentCalendarBinding
import kodeinViewModel

class AboutFragment: BaseCollectionFragmentMVVM<FragmentCalendarBinding, AboutViewModel>() {
    override val viewModel: AboutViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.fragment_calendar

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreatedX(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedX(view, savedInstanceState)
        showHeaderItem(
            AboutHeaderDataModel(
                "Năm Nhâm Dần có gì?",
                "Năm 2022 là năm Nhâm Dần hay còn gọi là năm con Hổ. Năm 2022 âm lịch được tính từ ngày 01/02/2022 đến hết ngày 21/01/2023 theo lịch dương. Về cuộc sống, người sinh năm 2022 Nhâm Dần sẽ có cuộc sống tràn ngập niềm vui. Trong công việc, mọi thứ đều tiến triển thuận lợi và thuận buồm xuôi gió."
            )
        )
        showListSectionData(listOf(
            AboutItemDataModel(
                "Tết 2022 vào ngày nào?",
                "Theo lịch vạn niên, mùng 1 Tết Nguyên đán Nhâm Dần 2022 sẽ rơi vào thứ Ba ngày 01/02/2022 dương lịch.\n" +
                        "\n" +
                        "Trong dịp Tết Nguyên đán năm nay, ngày mùng 4 âm lịch (tức ngày 4/2/2022) chính là ngày lập xuân.",
                activity?.getDrawable(R.drawable.lich_nghi_tet),
                "Lịch nghỉ tết 2022 (Nguồn: eBH)"
            ),
            AboutItemDataModel(
                "Tết đến rồi, về nhà thôi!",
                "Lại một năm nữa sắp qua đi, kết thúc một năm cũng chính là một khởi đầu cho một năm mới, khởi đầu cho mọi sự tốt lành mới sắp đến." +
                        "\n" +
                        "\n" +
                        "Chắc chắn đến thời điểm này tất cả mọi người đều cố gắng hoàn thành nốt những công việc còn đang dang dở để cùng về đoàn tụ với gia đình, cùng chia ngọt sẻ bùi, tâm sự với nhau mọi điều trong cuộc sống và cùng nhau đón Tết trong niềm vui của gia đình, người thân." +
                        "\n" +
                        "\n" +
                        "Vậy nhé, cùng nhau từng ngày đón tết 2022 thôi nào.",
                activity?.getDrawable(R.drawable.img_background),
                "Cùng về nhà với vòng tay ba mẹ! (Nguồn: Internet)"
            ),
        ))
    }

    override fun initClusters() {
        addCluster(AboutHeaderViewItem::class)
        addCluster(AboutViewItem::class)
    }

    private fun showHeaderItem(headerData: AboutHeaderDataModel){
        recyclerManager.replace(
            AboutHeaderViewItem::class,
            AboutHeaderViewItem(headerData)
        )
    }

    private fun showListSectionData(items: List<AboutItemDataModel>){
        recyclerManager.replace(
            AboutViewItem::class,
            createListAboutItem(items)
        )
    }

    private fun createListAboutItem(items: List<AboutItemDataModel>): List<AboutViewItem> {
        return items.map { aboutItemData ->
             AboutViewItem(
                 aboutItemData
            )
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        hideLoading()
    }
}