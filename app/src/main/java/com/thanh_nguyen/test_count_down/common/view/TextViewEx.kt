/*
 * Created by Thanh Nguyen on 11/22/21, 5:22 PM
 */

package com.thanh_nguyen.test_count_down.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView

class TextViewEx: AppCompatTextView {

    constructor(context: Context): super(context){
        init(context, null)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet): super(context, attrs){
        init(context, attrs)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

    }
}