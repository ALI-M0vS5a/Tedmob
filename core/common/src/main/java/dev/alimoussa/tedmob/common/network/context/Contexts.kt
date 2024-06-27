package dev.alimoussa.tedmob.common.network.context

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.sendEmail(
    recipient: String,
    subject: String = "Inquiry from Your Business Name",
    message: String = """
        Dear [Recipient Name],

        Thank you for your continued partnership. We are writing to discuss or inform you about a matter of importance related to our ongoing projects. Please feel free to reach out to us at your earliest convenience so we can address this matter directly.

        Best regards,
        [Your Full Name]
        [Your Position]
        [Your Contact Information]
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

