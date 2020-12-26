package com.example.plainolnotes4

import com.example.plainolnotes4.data.NoteEntity
import java.util.*

class SampleDataProvider {
    companion object {
        private val text1 = "simple text"
        private val text2 = "text 2 with \n lines "
        private val text3 = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras pellentesque vehicula orci quis cursus. Vivamus nulla elit, eleifend nec sagittis sed, eleifend vitae lorem. Duis sodales dui sit amet vestibulum dignissim. Praesent commodo, ex eu feugiat cursus, lorem tortor facilisis tortor, vitae vestibulum turpis dolor eu ipsum. Donec egestas turpis a interdum aliquet. Cras efficitur lorem non lorem semper mattis. Integer interdum risus libero, interdum mattis dui ornare eu. Sed accumsan eget felis sit amet ornare. Aliquam vel ex lorem. Integer ante nibh, hendrerit ac lectus ac, molestie viverra erat. Donec pharetra vulputate orci, ut sagittis nisl efficitur et. Aliquam leo tortor, cursus a commodo non, volutpat eu felis. Morbi eu mauris arcu. Curabitur elit metus, commodo at tellus vel, porttitor porttitor diam. Pellentesque vitae pellentesque velit. Phasellus venenatis, urna eu commodo suscipit, dui nunc lacinia purus, eleifend pulvinar orci elit in ante.

            Sed id arcu accumsan, gravida diam eu, porta odio. Praesent eu enim et mauris facilisis tincidunt a et ante. Nullam posuere, ex a venenatis rutrum, elit neque rhoncus lorem, at consectetur erat dolor mollis justo. Duis nisl risus, ultrices in interdum ac, sollicitudin tincidunt nulla. In ultrices turpis id tellus posuere pellentesque. Etiam fermentum sem auctor mauris suscipit, vitae gravida erat lobortis. Nullam volutpat tincidunt eleifend. Etiam diam dolor, varius ac lacus eu, mollis lacinia eros. Aenean dolor dui, blandit ut arcu ut, volutpat congue odio. Nunc finibus ante ac tortor imperdiet, eget iaculis sapien sagittis. Sed a risus lorem. Cras a lorem ullamcorper, bibendum ex ut, feugiat elit.
        """.trimIndent()

        private fun getDates(diff: Long): Date = Date(Date().time + diff)
        fun getNotes() = arrayListOf(
            NoteEntity(getDates(0), text1),
            NoteEntity(getDates(1), text2),
            NoteEntity(getDates(2), text3)
        )
    }
}