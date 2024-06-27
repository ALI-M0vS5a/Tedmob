package dev.alimoussa.tedmob.common.network.context

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.sendEmail(
    recipient: String,
    subject: String = "Lorem Ipsum Dolor Sit Amet",
    message: String = """
        Dear John Doe,

        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 

        Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

        Thank you for your attention, and I look forward to your reply.

        Best regards,

        Ali Moussa
        Android Developer
        ali999.1mousa@gmail.com
        """.trimIndent()
) {
    val mIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }

    try {
        startActivity(Intent.createChooser(mIntent, "Choose an email client:"))
    } catch (e: Exception) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
    }
}


fun Context.dialPhone(phone: String) {
    if (phone.isNotEmpty()) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        try {
            startActivity(Intent.createChooser(intent, "Choose a dialer client:"))
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}

