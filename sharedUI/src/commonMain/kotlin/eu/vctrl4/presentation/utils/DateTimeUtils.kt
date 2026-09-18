package eu.vctrl4.presentation.utils

import kotlinx.datetime.*
import kotlinx.datetime.format.*
import kotlin.time.*
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

object DateTimeUtils
{
	const val UI_ONLY_DAY_MONTH_PATTERN = "d MMMM "
	const val UI_DATE_PATTERN_SHORT = "dd.MM.yyyy"
	const val UI_DATE_TIME_PATTERN_LONG = "dd.MM.yyyy HH:mm"
	const val UI_DATE_TIME_PATTERN_LONG_EE = "dd MMMM yyyy, EE HH:mm"
	const val SERVER_DATE_TIME_PATTERN_SHORT = "yyyy-MM-dd'T'HH:mm:ss"
	const val SERVER_DATE_TIME_PATTERN_LONG = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ"
	const val SERVER_DATE_PATTERN = "yyyy-MM-dd"

    @OptIn(ExperimentalTime::class)
    val timeSofiaNowInMillis: Instant
        get()
        {
            val sofiaTimeZone  = TimeZone.of("Europe/Sofia")
            val now = Clock.System.now()
            val offset = sofiaTimeZone.offsetAt(now)

            // Shifts the UTC instant by the offset seconds
            // and returns the new "local" millisecond value
            return now.plus(offset.totalSeconds, DateTimeUnit.SECOND)
        }

    @OptIn(FormatStringsInDatetimeFormats::class, ExperimentalTime::class)
    fun toDateTime(str: String?, formatSrc: String?, isSofiaTime: Boolean = false): Long?
    {
        if (str.isNullOrBlank() || formatSrc.isNullOrBlank()) return null

        try
        {
            val customFormat = LocalDateTime.Format {
                byUnicodePattern(formatSrc)
            }

            val localDateTime = customFormat.parse(str)

            val timeZone = if (isSofiaTime)
            {
                TimeZone.of("Europe/Sofia")
            } else
            {
	            TimeZone.UTC
            }

            return localDateTime.toInstant(timeZone).toEpochMilliseconds()

        } catch (e: Exception)
        { // kotlinx-datetime throws IllegalArgumentException or DateTimeFormatException
			e.printStackTrace()
            return null
        }
    }

	fun toServerDateTime(dtime: Long?): String?
	{
		return toDateTime(dtime, SERVER_DATE_TIME_PATTERN_SHORT, null)
	}

	fun toServerDate(dtime: Long?): String?
	{
		return toDateTime(dtime, SERVER_DATE_PATTERN, null)
	}

	fun toUIDate(dtime: Long?): String?
	{
		return toDateTime(dtime, UI_DATE_PATTERN_SHORT, null)
	}

    @OptIn(ExperimentalTime::class)
    fun toDateTime(lTime: Long?, formatDest: String?, timeZoneId: String? = null): String? {

        if (lTime == null || lTime == 0L || formatDest.isNullOrBlank()) return null

        try {
            val instant = Instant.fromEpochMilliseconds(lTime)

            val zone = if (!timeZoneId.isNullOrBlank()) {
                TimeZone.of(timeZoneId)
            } else {
                TimeZone.UTC
            }

            val localDateTime = instant.toLocalDateTime(zone)

            @OptIn(FormatStringsInDatetimeFormats::class)
            val format = LocalDateTime.Format {
                byUnicodePattern(formatDest)
            }
            return localDateTime.format(format)
        } catch (e: Exception) {
            // Catching specific parsing or timezone errors
			e.printStackTrace()
            return null
        }
    }

	@OptIn(FormatStringsInDatetimeFormats::class)
	fun reformatDateTime(str: String?, formatSrc: String?, formatDst: String?): String? {
		if (str.isNullOrBlank() || formatSrc.isNullOrBlank() || formatDst.isNullOrBlank()) return null

		return try {
			val sourceFormat = when (formatSrc) {
				// Long ISO/Server pattern requiring dynamic nanosecond length + timezone parsing
				SERVER_DATE_TIME_PATTERN_SHORT, SERVER_DATE_TIME_PATTERN_LONG -> {
					DateTimeComponents.Format {
						year(); char('-'); monthNumber(); char('-'); day(); char('T')
						hour(); char(':'); minute(); char(':'); second()

						// Native dynamic sub-second parser (safely reads 1 to 9 digits)
						optional {
							char('.')
							secondFraction(minLength = 1, maxLength = 9)
						}
						// Native timezone parser
						optional {
							offset(UtcOffset.Formats.ISO)
						}
					}
				}

				// Date-only template
				SERVER_DATE_PATTERN -> {
					DateTimeComponents.Format {
						alternativeParsing({
							                   // Try reading as full date-time first
							                   year(); char('-'); monthNumber(); char('-'); day(); char('T')
							                   hour(); char(':'); minute(); char(':'); second()
/*							                   optional {
								                   char('.')
								                   secondFraction(minLength = 1, maxLength = 9)
							                   }*/
						                   }) {
							// Fall back to pure date if time components aren't in the raw string
							year(); char('-'); monthNumber(); char('-'); day()
						}
					}
				}

				// Fallback: Use standard unicode strings for other simple layouts
				else -> {
					DateTimeComponents.Format {
						byUnicodePattern(formatSrc)
					}
				}
			}

			// 1. Process the raw string into a structured component model
			val components = sourceFormat.parse(str)

			// 2. Hydrate empty properties to eliminate "does not define a default value"
			if (components.hour == null) components.hour = 0
			if (components.minute == null) components.minute = 0
			if (components.second == null) components.second = 0
			if (components.nanosecond == null) components.nanosecond = 0

			// 3. Output the result string via the targeted structure
			val destFormat = DateTimeComponents.Format {
				byUnicodePattern(formatDst)
			}

			destFormat.format(components)
		} catch (e: Exception) {
			e.printStackTrace()
			return null
		}
	}

	fun getCurrentSofiaTimeAsString(formatDest: String?): String? {
		if (formatDest.isNullOrBlank()) return null

		try {
			val now = Clock.System.now()
			val romeTimeZone = TimeZone.of("Europe/Sofia")

			// 1. Get the local date/time in Rome
			val localDateTimeRome = now.toLocalDateTime(romeTimeZone)

			// 2. Get the current offset for Rome (handles DST automatically)
			val romeOffset = romeTimeZone.offsetAt(now)

			@OptIn(FormatStringsInDatetimeFormats::class)
			val format = DateTimeComponents.Format {
				byUnicodePattern(formatDest)
			}

			// 3. Combine both components into the format
			val result =  format.format {
				setDateTime(localDateTimeRome)
				setOffset(romeOffset)
			}
			val fourHourDigitFormat = result.replace(Regex("T(\\d{2}):"), "T00$1:")
			return fourHourDigitFormat
		} catch (e: Exception) {
			e.printStackTrace()
			return null
		}
	}

    fun getHoursMinsStringFromMinutes(mins: Long): String {
        val hours = mins / 60
        val min = mins % 60

        // padStart ensures the numbers are always 2 digits (e.g., "05")
        val hStr = hours.toString().padStart(2, '0')
        val mStr = min.toString().padStart(2, '0')

        return "$hStr:$mStr"
    }

    fun getTimerAsStringFromMins(mins: Int): String {
        val duration = mins.minutes

        // Extract components using built-in duration properties
        val days = duration.inWholeDays
        val hours = duration.inWholeHours % 24
        val minutes = duration.inWholeMinutes % 60

        val sDays = if (days > 0) "${days}d:" else ""

        // Only show hours if days > 0 OR hours > 0
        val sHours = if (days > 0 || hours > 0) {
            val hStr = hours.toString().padStart(2, '0')
            "${hStr}h:"
        } else ""

        val sMinutes = "${minutes.toString().padStart(2, '0')}m"

        return "$sDays$sHours$sMinutes"
    }

    fun getTodayBeginInMillis(): Long
    {
	    val sofiaTimeZone  = TimeZone.of("Europe/Sofia")
        val timeZoneUTC = TimeZone.UTC
        val todayMSK = Clock.System.now().toLocalDateTime(sofiaTimeZone).date
        val todayBeginMillis = todayMSK.atStartOfDayIn(timeZoneUTC).toEpochMilliseconds() // convert with no offset

        return todayBeginMillis
    }

    // Helper to handle February in leap years
    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}