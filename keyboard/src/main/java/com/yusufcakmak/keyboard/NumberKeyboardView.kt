package com.yusufcakmak.keyboard

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatEditText


class NumberKeyboardView : RelativeLayout {

    private var listener: KeyboardListener? = null
    private var rlytDelete: RelativeLayout? = null
    private lateinit var numberTvList: MutableList<Button>
    private var inputText = ""

    constructor(context: Context) : super(context) {
        inflateViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateViews()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        inflateViews()
    }

    fun setKeyboardListener(listener: KeyboardListener) {
        this.listener = listener
    }


    private fun inflateViews() {
        val container = View.inflate(context, R.layout.keyboard_layout, this)

        rlytDelete = container.findViewById(R.id.rlytDelete)

        numberTvList = ArrayList(SUM_KEYS)
        numberTvList.add(container.findViewById(R.id.btn0))
        numberTvList.add(container.findViewById(R.id.btn1))
        numberTvList.add(container.findViewById(R.id.btn2))
        numberTvList.add(container.findViewById(R.id.btn3))
        numberTvList.add(container.findViewById(R.id.btn4))
        numberTvList.add(container.findViewById(R.id.btn5))
        numberTvList.add(container.findViewById(R.id.btn6))
        numberTvList.add(container.findViewById(R.id.btn7))
        numberTvList.add(container.findViewById(R.id.btn8))
        numberTvList.add(container.findViewById(R.id.btn9))

        prepareListeners()
    }

    private fun prepareListeners() {

        for (i in numberTvList.indices) {
            val btn = numberTvList[i]
            btn.setOnClickListener {
                listener?.onTextChanged(addToText(i))
            }
        }

        rlytDelete?.setOnClickListener {
            listener?.onTextChanged(dropLast())
        }


        rlytDelete?.setOnLongClickListener {
            cleanText()
            true
        }
    }

    private fun addToText(num: Int) : String {
        inputText += num
        return inputText
    }

    private fun dropLast(): String {
        inputText = if (inputText.length == 1) {
            ""
        } else {
            inputText.dropLast(1)
        }

        return inputText
    }

    private fun cleanText() {
        inputText = ""
        listener?.onTextChanged(inputText)
    }

    companion object {
        const val SUM_KEYS = 10
    }
}