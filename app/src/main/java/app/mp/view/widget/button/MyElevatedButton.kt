package app.mp.view.widget.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import app.mp.R
import app.mp.databinding.MyElevatedButtonBinding
import com.google.android.material.card.MaterialCardView

class MyElevatedButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttribute: Int = 0,
) : MaterialCardView(context, attributeSet, defStyleAttribute) {

    private val binding: MyElevatedButtonBinding

    init {
        binding = MyElevatedButtonBinding.inflate(LayoutInflater.from(context), this, true)

        attributeSet?.let {
            val style = context.obtainStyledAttributes(it, R.styleable.MyElevatedButton)
            val borderColor = style.getColor(
                R.styleable.MyElevatedButton_borderColor,
                ContextCompat.getColor(context, R.color.blue)
            )
            val borderWidth = style.getDimension(R.styleable.MyElevatedButton_borderWidth, 0f)
            val backgroundColor = style.getColor(
                R.styleable.MyElevatedButton_backgroundColor,
                ContextCompat.getColor(context, R.color.blue_50)
            )
            val iconSrc = style.getResourceId(
                R.styleable.MyElevatedButton_icon,
                R.drawable.round_music_note_36
            )
            val title = style.getString(R.styleable.MyElevatedButton_title)
            val textColor = style.getColor(R.styleable.MyElevatedButton_textColor, borderColor)

            style.recycle()

            binding.apply {
                button.apply {
                    setCardBackgroundColor(backgroundColor)
                    strokeColor = borderColor
                    strokeWidth = borderWidth.toInt()
                }
                icon.setImageResource(iconSrc)
                this.title.text = title
                this.title.setTextColor(textColor)
            }
        }

    }

}