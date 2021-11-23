package com.thanh_nguyen.baseproject.common

import com.thanh_nguyen.baseproject.R

class Constants {
    companion object{
        const val APP_ID = "3809D924-093D-466B-9E7D-BF7823718FB9"
        val GLOBAL_WISH = listOf(
            "Chúc bạn của tôi có một buổi sáng thật tốt lành, một ngày làm việc hiệu quả, may mắn và thành công. Nhớ nở những nụ cười thật tươi và xinh xắn trong ngày hôm nay nhé!",
            "Thành công chỉ đến với những người luôn biết nỗ lực và cố gắng. Mình tin chắc rằng cậu sẽ thành công. Chúc cậu buổi sáng may mắn và tràn đầy năng lượng.",
            "Có 2 điều mà bạn không thể thay đổi, đó chính là ngày hôm qua và ngày mai. Ngày hôm qua đã trôi qua rồi còn ngày mai thì sắp tới. Vì thế, hãy sống trọn vẹn cho ngày hôm nay bạn nhé. Chúc bạn ngày mới vui vẻ!",
            "Thật may mắn và tuyệt vời khi bắt đầu một ngày mới tại nơi làm việc với một ông chủ tuyệt vời như sếp. Chúc sếp có một ngày làm việc tốt lành gặp nhiều niềm vui và thật nhiều may mắn!",
            "Cơ hội sẽ đến với những người từ bỏ việc chờ đợi và bắt đầu theo đuổi. Thành công sẽ đến với những ai thôi nghĩ suy và bắt tay vào hành động. Chúc bạn có một khởi đầu thuận lợi, một buổi sáng tốt lành.",
            "Vào một ngày đẹp trời như hôm nay, em muốn bắt đầu ngày mới của mình bằng cách chúc anh/chị ngày mới thành công. Cảm ơn anh/chị bởi anh/chị đã luôn quan tâm, hỗ trợ và giúp đỡ em trong công việc cũng như cuộc sống!",
            "Khi nhận được tin nhắn này thì bạn hãy cười lên nhé, vì ít nhất đâu đó quanh đây có một người mong bạn hạnh phúc, vui vẻ và luôn yêu đời. Chúc bạn một ngày mới tốt lành nhé!",
            "Chúc buổi sáng tốt lành. Chúc bạn của tôi có một ngày mới ngập tràn những khoảnh khắc thú vị và hào hứng nhất.",
            "Một đóa hoa cho ngày mới hạnh phúc. Một lời chúc cho vạn sự bình an. Chúc cho người nhận được tin nhắn này có một ngày mới tuyệt vời!",
            "Mỗi buổi sáng là một khởi đầu mới, mong ước mới, hy vọng mới. Mỗi ngày mới là một ngày thật tuyệt vời và ý nghĩa cho mỗi chúng ta. Anh/chị hãy nhận lời chúc này cho một ngày hoàn hảo nhé! Chúc anh/chị ngày mới an lành.",
            "Những điều ngọt ngào, hạnh phúc và tốt lành nhất đang chờ đợi em trong ngày hôm nay đấy. Tỉnh dậy và chào đón chúng thôi nào! Chúc em một ngày mới an lành!",
            "Mỗi ngày tới văn phòng tôi đều có được cảm giác thoải mái bởi được gặp những đồng nghiệp đầy năng lượng như bạn. Chúc bạn có một ngày mới tốt lành, thành công và may mắn.",
            "Trong cuộc sống, giai đoạn khó khăn nhất không phải là không ai hiểu bạn, mà là… bạn không hiểu chính mình.",
            "Cuộc đời này thật ngắn ngủi, đừng dành… dù chỉ một phút cho những người, những việc khiến bạn buồn.",
            "Có sinh sẽ có tử, song chỉ cần bạn vẫn đang có mặt trên đời này, thì phải sống bằng cách tốt nhất. Có thể không có tình yêu, không có đồ hàng hiệu, song không thể không vui vẻ.",
            "Thứ không cần, có tốt đến đâu cũng là rác.",
            "Nếu bạn không mù, thì đừng dùng tai để hiểu tôi.",
            "Sự lợi hại thực sự không phải là bạn quen biết bao nhiêu người, mà là vào lúc bạn gặp hoạn nạn, có bao nhiêu người quen biết bạn.",
            "Không nghe không hỏi không nhất định là đã quên, song chắc chắn là đã xa cách. Cả hai trầm lặng quá lâu, đến chủ động cũng cần có dũng khí.",
            "Đừng nên dùng những lời tuyệt tình để làm tổn thương đến người mà bạn yêu vào lúc tâm tình tồi tệ nhất.",
            "Có những lúc, không có lần sau, không có cơ hội bắt đầu lại. Có những lúc, bỏ lỡ hiện tại, vĩnh viễn không còn cơ hội nữa. Hãy dùng thái độ cam tâm tình nguyện để sống một cuộc sống an ổn.",
            "Phụ nữ, không cần phải nghiêng nước nghiêng thành, chỉ cần một người đàn ông nghiêng về cô ấy cả một đời!"
        ).random()

        val GLOBAL_SOUND = listOf(
            R.raw.hpny,
            R.raw.hpnyrm,
            R.raw.hpnyrm2
        ) .random()
    }


    class Exception{
        companion object{
            val CANCELLATION_EXCEPTION = -999
        }
    }

    class BundleKey{
        companion object{
            const val EMAIL = "EMAIL"
            const val NAME = "NAME"
        }
    }

    object EventDate{
        const val LUNAR_NEW_YEAR = "01/02/2022"
    }
}