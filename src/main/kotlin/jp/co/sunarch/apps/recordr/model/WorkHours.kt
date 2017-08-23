package jp.co.sunarch.apps.recordr.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat

data class WorkHours(val minutes: Long = 0) : Comparable<WorkHours> {

  operator fun plus(other: WorkHours): WorkHours {
    return WorkHours(minutes + other.minutes)
  }

  operator fun minus(other: WorkHours): WorkHours {
    return WorkHours(minutes - other.minutes)
  }

  operator fun times(times: Long): WorkHours {
    return WorkHours(minutes * times)
  }

  operator fun div(div: Long): WorkHours {
    return WorkHours(minutes / div)
  }

  override fun toString(): String {
    val hours = BigDecimal.valueOf(this.minutes / MINUTES_IN_HOUR)
    val minutes = BigDecimal.valueOf(this.minutes % MINUTES_IN_HOUR)
        .divide(MINUTES_IN_HOUR_DECIMAL, 2, RoundingMode.HALF_UP)

    val format = NumberFormat.getInstance()
    format.maximumFractionDigits = 2
    format.minimumFractionDigits = 2
    return format.format(hours.add(minutes))
  }

  override fun compareTo(other: WorkHours): Int {
    return java.lang.Long.compare(minutes, other.minutes)
  }

  companion object {
    private val MINUTES_IN_HOUR = 60
    private val MINUTES_IN_HOUR_DECIMAL = BigDecimal.valueOf(MINUTES_IN_HOUR.toLong())

    val ZERO = WorkHours.ofMinutes(0)

    fun of(hours: Int, minutes: Int): WorkHours {
      return WorkHours(hours.toLong() * MINUTES_IN_HOUR + minutes)
    }

    fun ofHours(hours: Int): WorkHours {
      return WorkHours(hours.toLong() * MINUTES_IN_HOUR)
    }

    fun ofMinutes(minutes: Int): WorkHours {
      return WorkHours(minutes.toLong())
    }

    fun between(start: WorkTime, end: WorkTime): WorkHours {
      require(start <= end, { "The start must be before or equals to end" })

      val duration = start.durationTo(end)
      val minutes = duration.toMinutes()
      return WorkHours(minutes)
    }
  }

}
