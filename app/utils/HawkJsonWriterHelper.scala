package utils

object HawkJsonWriterHelper {

  def write(obj: Any, tabCount: Int = 2): String = {

    val fieldInfo = obj.getClass.getDeclaredFields.map(f => {
      f.setAccessible(true)
      (f.getName, f.get(obj), f.getType)
    })

    var output = "{\n"

    fieldInfo.foreach(info => {
      if (info._1 != "$outer") {
        output += s"""${" " * tabCount}"${info._1}" = ${formatValue(
          info._2,
          info._3,
          tabCount
        )}\n"""
      }
    })

    if (tabCount > 2) {
      output += " " * (tabCount - tabCount / 2) + "}"
    } else {
      output += "}"
    }
    output
  }

  def formatValue(value: Any, fieldType: AnyRef, tabCount: Int): AnyRef = {
    fieldType.toString match {
      case s: String if s.contains("String") => s""""$value""""
      case i: String if i.contains("int")    => s"$value"
      //case x: String                         =>
        HawkJsonWriterHelper.write(value, tabCount * 2)
    }
  }
}
