/*
 * Copyright 2020 Daniel Spiewak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sbtghactions

object RenderFunctions {

  def wrap(str: String): String =
    if (str.indexOf('\n') >= 0)
      "|\n" + indent(str, 1)
    else if (isSafeString(str))
      str
    else
      s"'${str.replace("'", "''")}'"

  private def isSafeString(str: String): Boolean =
    !(str.indexOf(':') >= 0 || // pretend colon is illegal everywhere for simplicity
      str.indexOf('#') >= 0 || // same for comment
      str.indexOf('!') == 0 ||
      str.indexOf('*') == 0 ||
      str.indexOf('-') == 0 ||
      str.indexOf('?') == 0 ||
      str.indexOf('{') == 0 ||
      str.indexOf('}') == 0 ||
      str.indexOf('[') == 0 ||
      str.indexOf(']') == 0 ||
      str.indexOf(',') == 0 ||
      str.indexOf('|') == 0 ||
      str.indexOf('>') == 0 ||
      str.indexOf('@') == 0 ||
      str.indexOf('`') == 0 ||
      str.indexOf('"') == 0 ||
      str.indexOf('\'') == 0 ||
      str.indexOf('&') == 0)

  def indentOnce(output: String): String = indent(output, 1)

  def indent(output: String, level: Int): String = {
    val space = (0 until level * 2).map(_ => ' ').mkString
    (space + output.replace("\n", s"\n$space")).replaceAll("""\n[ ]+\n""", "\n\n")
  }

  def renderParamWithList(paramName: String, items: Seq[String]): String = {
    val rendered = items.map(wrap)
    if (rendered.map(_.length).sum < 40) // just arbitrarily...
      rendered.mkString(s"$paramName: [", ", ", "]")
    else
      rendered.map("- " + _).map(indentOnce).mkString(s"$paramName:\n", "\n", "\n")
  }

  object SnakeCase {
    private val re = "[A-Z]+".r

    def apply(property: String): String =
      re.replaceAllIn(property.head.toLower +: property.tail, { m => s"_${m.matched.toLowerCase}" })
  }

}
